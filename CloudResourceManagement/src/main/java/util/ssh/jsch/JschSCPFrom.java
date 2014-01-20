package util.ssh.jsch;

import com.cattles.util.ssh.SSHException;
import com.cattles.util.ssh.SSHMonitor;
import com.cattles.util.ssh.SSHResult;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.io.*;
import java.util.List;

import static com.cattles.util.ssh.SCPUtil.*;
import static com.cattles.util.ssh.SCPUtil.BUF_SIZE;
import static com.cattles.util.ssh.SCPUtil.LINE_FEED;
import static com.cattles.util.ssh.SCPUtil.REST_SIZE;
import static com.cattles.util.ssh.SCPUtil.parseRemoteURL;
import static com.cattles.util.ssh.SCPUtil.sendAck;
import static com.cattles.util.ssh.SCPUtil.trackProgress;
import static com.cattles.util.ssh.SCPUtil.waitForAck;
import static com.cattles.util.ssh.SSHResult.makeFailedResult;

public class JschSCPFrom implements JschSCPExecutable {

    public SSHResult execSCP(Session sshSession, String origin, String dest,
                             String option, List<SSHMonitor> monitors) {
        SSHResult result = new SSHResult("SCP " + option + " " + origin + " " + dest);
        Channel channel = null;
        try {
            String command = "scp -f -r " + option + parseRemoteURL(origin);
            channel = sshSession.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();

            channel.connect();
            sendAck(out);
            result.append("Beginning transfer " + origin + "\n");
            startRemoteCopy(dest, in, out, result, monitors);
            result.setSuccess(true);
            result.append("Transfer complete!\n");
        } catch (SSHException e) {
            makeFailedResult(result, e, "SCP error! probably is remote server response error!\n");
        } catch (JSchException e) {
            makeFailedResult(result, e, "SCP error!\n");
        } catch (IOException e) {
            makeFailedResult(result, e, "SCP error! probably is IO error!\n");
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
        }

        return result;
    }

    private void startRemoteCopy(String dest, InputStream in, OutputStream out
            , SSHResult result, List<SSHMonitor> monitors) throws IOException, SSHException {
        File curFile = new File(dest);
        while (true) {
            // C0644 filesize filename - header for a regular file
            // T time 0 time 0\n - present if perserve time.
            // D directory - this is the header for a directory.
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            while (true) {
                int read = in.read();
                if (read < 0) {
//                    throw new SSHException("No response from server!");
                    return;
                }
                if ((byte) read == LINE_FEED) {
                    break;
                }
                stream.write(read);
            }
            String serverResponse = stream.toString("UTF-8");
            char serResp = serverResponse.charAt(0);
            if (serResp == 'C') {
                //Transfer file
                parseAndFetch(serverResponse, curFile, in, out, result, monitors);
            } else if (serResp == 'D') {
                //Create directory
                curFile = parseAndTransDirectory(serverResponse, curFile, result, monitors);
                sendAck(out);
            } else if (serResp == 'E') {
                //End of current direction transferring
                curFile = curFile.getParentFile();
                sendAck(out);
            } else if (serResp == '\01' || serResp == '\02') {
                throw new SSHException("Server indicated an error,error code: " + serResp + ",message: " + serverResponse.substring(1));
            }
        }
    }

    private File parseAndTransDirectory(String serverResponse, File localFile, SSHResult result, List<SSHMonitor> monitors) throws SSHException, IOException {
        int start = serverResponse.indexOf(" ");
        start = serverResponse.indexOf(" ", start + 1);
        String dirName = serverResponse.substring(start + 1);
        if (!localFile.isDirectory())
            throw new SSHException(localFile.getCanonicalFile() + " is not directory!can't create directory in it!");

        File dir = new File(localFile, dirName);
        dir.mkdir();
        String msg = dir.getCanonicalPath() + " is created!\n";
        result.append(msg);
        if (!monitors.isEmpty()) {
            for (SSHMonitor mon : monitors) {
                mon.info(msg);
            }
        }
        return dir;
    }

    private void parseAndFetch(String serverResponse, File localFile, InputStream in, OutputStream out
            , SSHResult result, List<SSHMonitor> monitors) throws IOException, SSHException {
        int end = serverResponse.indexOf(" ");
        int start = end + 1;
        end = serverResponse.indexOf(" ", start);
        long filesize = Long.parseLong(serverResponse.substring(start, end));
        String filename = serverResponse.substring(end + 1);
        File transFile = (localFile.isDirectory()) ? new File(localFile, filename) : localFile;
        transferFileFromRemote(transFile, filesize, in, out, result, monitors);
        waitForAck(in);
        sendAck(out);
    }

    private void transferFileFromRemote(File localFile, long fileSize, InputStream in, OutputStream out,
                                        SSHResult result, List<SSHMonitor> monitors) throws IOException {
        byte[] buf = new byte[BUF_SIZE];
        sendAck(out);
        // read a content of lfile
        FileOutputStream fos = new FileOutputStream(localFile);
        int length;
        long transferred = 0;
        long initFilesize = fileSize;

        try {
            while (true) {
                length = in.read(buf, 0, (BUF_SIZE < fileSize) ? BUF_SIZE : (int) fileSize);
                if (length < 0) {
                    throw new EOFException("Unexpected end of stream.");
                }
                fos.write(buf, 0, length);
                fileSize -= length;
                transferred += length;
                if (fileSize == 0) {
                    break;
                }
                if (!monitors.isEmpty() && fileSize > REST_SIZE) {
                    for (SSHMonitor monitor : monitors) {
                        monitor.info(localFile.getCanonicalPath() + " is transferring, progress is " + trackProgress(initFilesize, transferred));
                    }
                }
                if (fileSize > REST_SIZE) {
                    try {
                        Thread.sleep(100);
                    } catch (Exception ee) {
                    }
                    ;
                }
            }
        } finally {
            fos.flush();
            fos.close();
        }
        result.append(localFile.getCanonicalPath() + " transferred successful!\n");
    }

}
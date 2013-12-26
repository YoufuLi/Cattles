package com.cattles.ssh.jsch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.cattles.ssh.SSHException;
import com.cattles.ssh.SSHMonitor;
import com.cattles.ssh.SSHResult;

import static com.cattles.ssh.SSHResult.makeFailedResult;
import static com.cattles.ssh.SCPUtil.*;

public class JschSCPTo implements JschSCPExecutable {
    public SSHResult execSCP(Session sshSession, String origin, String dest,
                             String option, List<SSHMonitor> monitors) {
        SSHResult result = new SSHResult("SCP " + option + " " + origin + " " + dest);
        boolean isAll = false;
        int length = origin.length();
        String filePath = origin;
        if (origin.substring(length - 1).equals("*")) {
            filePath = origin.substring(0, length - 1);
            isAll = true;
        }
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                makeFailedResult(result, new SSHException("File or Directory " + file.getCanonicalPath() + " dosen't exists!"), "SCP error! probably is local file not exists\n");
                return result;
            }
            if (file.isFile()) {
                doSingleFileTransfer(sshSession, file, parseRemoteURL(dest),
                        option, result, monitors);
            } else if (file.isDirectory()) {
                if (isAll) {
                    doMultipleFileTransfer(sshSession, file, parseRemoteURL(dest), option, result, monitors);
                } else {
                    doMultipleTransfer(sshSession, file, parseRemoteURL(dest), option, result, monitors);
                }
            }
            result.setSuccess(true);
            result.append("Transfer complete!\n");
        } catch (JSchException e) {
            makeFailedResult(result, e, "SCP error!\n");
        } catch (IOException e) {
            makeFailedResult(result, e, "SCP error! probably is IO error!\n");
        } catch (SSHException e) {
            makeFailedResult(result, e, "SCP error! probably is remote server response error!\n");
        }

        return result;
    }

    private void doSingleFileTransfer(Session sshSession, File localFile, String dest, String option
            , SSHResult result, List<SSHMonitor> monitors) throws JSchException, IOException, SSHException {
        String command = "scp -t " + option + " " + dest;
        Channel channel = sshSession.openChannel("exec");
        try {
            ((ChannelExec) channel).setCommand(command);

            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            waitForAck(in);
            result.append("Beginning transfer " + localFile.getCanonicalPath() + "\n");
            sendFileToRemote(localFile, in, out, result, monitors);
        } finally {
            if (channel != null) channel.disconnect();
        }
    }

    private void doMultipleTransfer(Session sshSession, File localFile, String dest, String option
            , SSHResult result, List<SSHMonitor> monitors) throws JSchException, IOException, SSHException {
        String command = "scp -r -d -t " + option + " " + dest;
        Channel channel = sshSession.openChannel("exec");
        try {
            ((ChannelExec) channel).setCommand(command);

            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            waitForAck(in);
            result.append("Beginning transfer " + localFile.getCanonicalPath() + "\n");
            sendDirectoryToRemote(localFile, in, out, result, monitors);
            result.append(localFile.getCanonicalPath() + " transferred successful!" + "\n");
        } finally {
            if (channel != null) channel.disconnect();
        }
    }

    private void doMultipleFileTransfer(Session sshSession, File localFile, String dest, String option
            , SSHResult result, List<SSHMonitor> monitors) throws JSchException, IOException, SSHException {
        String command = "scp -r -d -t " + option + " " + dest;
        Channel channel = sshSession.openChannel("exec");
        try {
            ((ChannelExec) channel).setCommand(command);

            OutputStream out = channel.getOutputStream();
            InputStream in = channel.getInputStream();
            channel.connect();
            waitForAck(in);
            result.append("Beginning transfer " + localFile.getCanonicalPath() + "\n");
            digDirectory(localFile, in, out, result, monitors);
            out.write("E\n".getBytes());
            out.flush();
            waitForAck(in);
            result.append(localFile.getCanonicalPath() + " transferred successful!" + "\n");
        } finally {
            if (channel != null) channel.disconnect();
        }
    }

    private void digDirectory(File localFile, InputStream in, OutputStream out, SSHResult result, List<SSHMonitor> monitors) throws IOException, SSHException {
        String msg = "Directory " + localFile.getCanonicalPath() + " is transferred!\n";
        result.append(msg);
        if (!monitors.isEmpty()) {
            for (SSHMonitor mon : monitors) {
                mon.info(msg);
            }
        }
        for (File file : localFile.listFiles()) {
            if (file.isFile()) {
                sendFileToRemote(file, in, out, result, monitors);
            } else if (file.isDirectory()) {
                sendDirectoryToRemote(file, in, out, result, monitors);
            }
        }
    }

    private void sendDirectoryToRemote(File dir, InputStream in, OutputStream out, SSHResult result, List<SSHMonitor> monitors) throws IOException, SSHException {
        String command = "D0755 0 " + dir.getName() + "\n";
        out.write(command.getBytes());
        out.flush();
        waitForAck(in);
        digDirectory(dir, in, out, result, monitors);
        out.write("E\n".getBytes());
        out.flush();
        waitForAck(in);
    }


    private void sendFileToRemote(File localFile, InputStream in, OutputStream out, SSHResult result, List<SSHMonitor> monitors) throws IOException, SSHException {
        // send "C0644 filesize filename", where filename should not include '/'
        long fileSize = localFile.length();
        String command = "C0644 " + fileSize + " " + localFile.getName() + "\n";
        out.write(command.getBytes());
        out.flush();
        waitForAck(in);

        // send a content of localFile
        FileInputStream fis = new FileInputStream(localFile);
        byte[] buf = new byte[BUF_SIZE];
        long transferred = 0;
        try {
            while (true) {
                int len = fis.read(buf, 0, buf.length);
                if (len <= 0) break;
                out.write(buf, 0, len);

                transferred += len;
                if (!monitors.isEmpty() && fileSize > REST_SIZE) {
                    for (SSHMonitor monitor : monitors) {
                        monitor.info(localFile.getCanonicalPath() + " is transferring, progress is " + trackProgress(fileSize, transferred));
                    }
                }
                /*删除部分*/
//				if(fileSize>REST_SIZE){
//					try{Thread.sleep(100);}catch(Exception ee){};
//				}
            }
            out.flush();
            sendAck(out);
            waitForAck(in);
        } finally {
            fis.close();
        }
        result.append(localFile.getCanonicalPath() + " transferred successful!\n");
    }

}
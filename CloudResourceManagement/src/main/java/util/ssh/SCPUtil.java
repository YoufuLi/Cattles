package util.ssh;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SCPUtil {
    public static final int BUF_SIZE = 1024;
    public static final int REST_SIZE = 102400;//sleep valve
    public static final byte LINE_FEED = 0x0a;//\n

    public static boolean isSCPto(String from, String to) throws SSHException {
        boolean isFromRemote = isRemote(from);
        boolean isToRemote = isRemote(to);
        if (isFromRemote && !isToRemote) {
            return false;
        } else if (!isFromRemote && isToRemote) {
            return true;
        } else {
            throw new SSHException("from and to both are remote(or local) that is not supported.");
        }
    }

    public static boolean isRemote(String uri) {
        return uri.indexOf('@') < 0 ? false : true;
    }

    /**
     * Reads the response, throws a SSHException if the response
     * indicates an error.
     *
     * @param in
     * @throws java.io.IOException
     * @throws SSHException
     */
    public static void waitForAck(InputStream in) throws IOException, SSHException {
        int b = in.read();
        if (b == -1) {
            throw new SSHException("No response from server!");
        } else if (b != 0) {
            StringBuffer sb = new StringBuffer();
            int c = in.read();
            while (c > 0 && c != '\n') {
                sb.append((char) c);
                c = in.read();
            }
            if (b == 1) {
                throw new SSHException("Server indicated an error: " + sb.toString());
            } else if (b == 2) {
                throw new SSHException("Server indicated a fatal error: " + sb.toString());
            } else {
                throw new SSHException("Unknown response, code " + b + " message: " + sb.toString());
            }
        }
    }

    /**
     * Send an ack.
     *
     * @param out the output stream to use
     * @throws java.io.IOException on error
     */
    public static void sendAck(OutputStream out) throws IOException {
        byte[] buf = new byte[1];
        buf[0] = 0;
        out.write(buf);
        out.flush();
    }


    public static final int trackProgress(long filesize, long totalLength) {
        int percent = (int) Math.round(Math
                .floor((totalLength / (double) filesize) * 100));
        return percent;
    }

    /**
     * parse the remote file url to the file path
     * parse user@xxx:rfile to rfile
     *
     * @param remote
     * @return
     * @throws SSHException
     */
    public static String parseRemoteURL(String remote) throws SSHException {
        int i = remote.indexOf(':') + 1;
        if (i == 0) throw new SSHException("Remote path is incorrect!");
        return remote.substring(i);
    }


}
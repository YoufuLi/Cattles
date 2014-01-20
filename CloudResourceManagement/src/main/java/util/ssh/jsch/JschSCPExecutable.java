package util.ssh.jsch;

import com.cattles.util.ssh.SSHMonitor;
import com.cattles.util.ssh.SSHResult;
import com.jcraft.jsch.Session;

import java.util.List;

public interface JschSCPExecutable {
    public SSHResult execSCP(Session sshSession, String origin, String dest, String option, List<SSHMonitor> monitors);
}
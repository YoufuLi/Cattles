package com.cattles.ssh.jsch;

import java.util.List;

import com.jcraft.jsch.Session;
import com.cattles.ssh.SSHMonitor;
import com.cattles.ssh.SSHResult;

public interface JschSCPExecutable {
    public SSHResult execSCP(Session sshSession, String origin, String dest, String option, List<SSHMonitor> monitors);
}

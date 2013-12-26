package com.cattles.ssh.jsch;

import com.cattles.ssh.SSHMonitor;
import com.cattles.ssh.SSHResult;
import com.jcraft.jsch.Session;

import java.util.List;

public interface JschSCPExecutable {
    public SSHResult execSCP(Session sshSession, String origin, String dest, String option, List<SSHMonitor> monitors);
}
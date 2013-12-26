package com.cattles.nihao.jsch;

import com.cattles.nihao.SSHMonitor;
import com.cattles.nihao.SSHResult;
import com.jcraft.jsch.Session;

import java.util.List;

public interface JschSCPExecutable {
    public SSHResult execSCP(Session sshSession, String origin, String dest, String option, List<SSHMonitor> monitors);
}
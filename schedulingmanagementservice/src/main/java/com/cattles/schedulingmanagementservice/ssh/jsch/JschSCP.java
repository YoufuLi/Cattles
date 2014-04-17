package com.cattles.schedulingmanagementservice.ssh.jsch;

import com.cattles.schedulingmanagementservice.ssh.SCPUtil;
import com.cattles.schedulingmanagementservice.ssh.SSHException;
import com.cattles.schedulingmanagementservice.ssh.SSHMonitor;
import com.cattles.schedulingmanagementservice.ssh.SSHResult;
import com.jcraft.jsch.Session;

import java.util.List;

public class JschSCP {
    private static JschSCPExecutable scpTo = new JschSCPTo();
    private static JschSCPExecutable scpFrom = new JschSCPFrom();

    public static JschSCPExecutable getScpExecutor(String origin, String dest) throws SSHException {
        if (SCPUtil.isSCPto(origin, dest)) {
            return scpTo;
        } else {
            return scpFrom;
        }
    }

    public static SSHResult SCP(Session sshSession, String origin, String dest, String option, List<SSHMonitor> monitors) {
        try {
            return getScpExecutor(origin, dest).execSCP(sshSession, origin, dest, option, monitors);
        } catch (SSHException e) {
            SSHResult result = new SSHResult("SCP " + option + " " + origin + " to " + dest);
            SSHResult.makeFailedResult(result, e, "SCP failed! probably the origin and dest both are remote or local!");
            return result;
        }
    }
}
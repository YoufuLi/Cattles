package com.cattles.schedulingmanagementservice.falkon.commandexecutor;

import com.cattles.schedulingmanagementservice.ssh.CommandExecutable;
import com.cattles.schedulingmanagementservice.ssh.SSHFactory;
import com.cattles.schedulingmanagementservice.ssh.jsch.JschCommandExecutor;

public class FalkonExecFactory implements SSHFactory {
    public CommandExecutable getCmdExec() {
        return new JschCommandExecutor();
    }

    public CommandExecutable getCmdExec(String type) {
        if (type.equalsIgnoreCase("cluster")) {
            return new FalkonClusterExecutor();
        } else if (type.equalsIgnoreCase("server")) {
            return new FalkonServerExecutor();
        } else if (type.equalsIgnoreCase("worker")) {
            return new FalkonWorkerExecutor();
        } else {
            return new JschCommandExecutor();
        }
    }
}
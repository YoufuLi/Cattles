package com.cattles.schedulingmanagementservice.gearman.commandexecutor;

import com.cattles.schedulingmanagementservice.ssh.CommandExecutable;
import com.cattles.schedulingmanagementservice.ssh.SSHFactory;
import com.cattles.schedulingmanagementservice.ssh.jsch.JschCommandExecutor;

public class GearmanExecFactory implements SSHFactory {
    public CommandExecutable getCmdExec() {
        return new JschCommandExecutor();
    }

    public CommandExecutable getCmdExec(String type) {
        if (type.equalsIgnoreCase("cluster")) {
            return new GearmanClusterExecutor();
        } else if (type.equalsIgnoreCase("server")) {
            return new GearmanServerExecutor();
        } else if (type.equalsIgnoreCase("worker")) {
            return new GearmanWorkerExecutor();
        } else {
            return new JschCommandExecutor();
        }
    }
}
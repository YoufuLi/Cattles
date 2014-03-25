package com.cattles.executionservice.falkon.commandexecutor;

import com.cattles.util.ssh.CommandExecutable;
import com.cattles.util.ssh.SSHFactory;
import com.cattles.util.ssh.jsch.JschCommandExecutor;

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
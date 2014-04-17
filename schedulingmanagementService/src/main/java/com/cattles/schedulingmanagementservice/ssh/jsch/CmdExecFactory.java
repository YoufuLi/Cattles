package com.cattles.schedulingmanagementservice.ssh.jsch;

import com.cattles.schedulingmanagementservice.ssh.CommandExecutable;
import com.cattles.schedulingmanagementservice.ssh.SSHFactory;

public class CmdExecFactory implements SSHFactory {
    public CommandExecutable getCmdExec() {
        return new JschCommandExecutor();
    }
}
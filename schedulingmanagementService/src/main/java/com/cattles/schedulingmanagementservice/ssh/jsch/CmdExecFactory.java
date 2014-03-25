package com.cattles.schedulingmanagementservice.ssh.jsch;

import com.cattles.util.ssh.CommandExecutable;
import com.cattles.util.ssh.SSHFactory;

public class CmdExecFactory implements SSHFactory {
    public CommandExecutable getCmdExec() {
        return new JschCommandExecutor();
    }
}
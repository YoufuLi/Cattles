package com.cattles.ssh.jsch;

import com.cattles.ssh.CommandExecutable;
import com.cattles.ssh.SSHFactory;

public class CmdExecFactory implements SSHFactory {

    public CommandExecutable getCmdExec() {
        return new JschCommandExecutor();
    }

}

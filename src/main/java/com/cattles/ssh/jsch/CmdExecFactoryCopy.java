package com.cattles.ssh.jsch;

import com.cattles.ssh.CommandExecutable;
import com.cattles.ssh.SSHFactory;

public class CmdExecFactoryCopy implements SSHFactory {

    public CommandExecutable getCmdExec() {
        return new JschCommandExecutor();
    }
}
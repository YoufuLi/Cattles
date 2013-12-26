package com.cattles.nihao.jsch;

import com.cattles.nihao.CommandExecutable;
import com.cattles.nihao.SSHFactory;

public class CmdExecFactory implements SSHFactory {

    public CommandExecutable getCmdExec() {
        return new JschCommandExecutor();
    }
}
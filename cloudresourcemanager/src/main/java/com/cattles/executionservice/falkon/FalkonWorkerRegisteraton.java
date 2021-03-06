package com.cattles.executionservice.falkon;

import com.cattles.executionservice.falkon.commandexecutor.FalkonExecFactory;
import com.cattles.util.Constant;
import com.cattles.util.ssh.CommandExecutable;
import com.cattles.util.ssh.ConnInfo;
import com.cattles.util.ssh.SSHResult;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonWorkerRegisteraton extends Thread {
    private static Logger logger = Logger.getLogger(FalkonWorkerRegisteraton.class);
    public String falkonServerIP = null;
    public String falkonWorkerIP = null;

    public FalkonWorkerRegisteraton(String _threadName, String _falkonServerIP, String _falkonWorkerIP) {
        super(_threadName);
        falkonServerIP = _falkonServerIP;
        falkonWorkerIP = _falkonWorkerIP;
    }

    public void run() {
        CommandExecutable ce = (new FalkonExecFactory()).getCmdExec("worker");
        ConnInfo ci = new ConnInfo(falkonWorkerIP, Constant.VIRTUAL_MACHINE_ACCOUNT, Constant.VIRTUAL_MACHINE_KEY_PATH, null);
        SSHResult result = ce.connect(ci);
        if (!result.isSuccess()) {
            Exception exception = result.getError();
            try {
                throw exception;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        result = ce.execute(Constant.FALKON_WORKER_REGISTERATION_COMMAND + " " + falkonServerIP);
        /*if(result!=null){
            ce.disconnect();
        }*/
    }
}

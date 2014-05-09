package com.cattles.schedulingmanagementservice.falkon.falkonframeworkoperation;

import com.cattles.schedulingmanagementservice.falkon.commandexecutor.FalkonExecFactory;
import com.cattles.schedulingmanagementservice.ssh.CommandExecutable;
import com.cattles.schedulingmanagementservice.ssh.ConnInfo;
import com.cattles.schedulingmanagementservice.ssh.SSHResult;
import com.cattles.util.Constant;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonWorkerDeregisteraton extends Thread {
    private static Logger logger = Logger.getLogger(FalkonWorkerDeregisteraton.class);
    public String falkonServerIP = null;
    public String falkonWorkerIP = null;

    public FalkonWorkerDeregisteraton(String _threadName, String _falkonServerIP, String _falkonWorkerIP) {
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
        result = ce.execute(Constant.FALKON_WORKER_DEREGISTERATION_COMMAND);
        if (result != null) {
            ce.disconnect();
        }
        logger.info("Finished deregistering the workers****************************");
    }
}

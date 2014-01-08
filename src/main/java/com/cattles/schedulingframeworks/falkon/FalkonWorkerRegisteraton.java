package com.cattles.schedulingframeworks.falkon;

import com.cattles.schedulingframeworks.falkon.common.ExecuteCommand;
import com.cattles.util.Constant;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/8/14
 * Time: 11:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonWorkerRegisteraton extends Thread {
    private static Logger logger = Logger.getLogger(FalkonWorkerRegisteraton.class);
    public String falkonServerIP=null;
    public String falkonWorkerIP=null;
    public FalkonWorkerRegisteraton(String _threadName,String _falkonServerIP,String _falkonWorkerIP){
        super(_threadName);
        falkonServerIP=_falkonServerIP;
        falkonWorkerIP=_falkonWorkerIP;
    }
    public void run(){
        ExecuteCommand executeCommand= new ExecuteCommand(falkonWorkerIP, Constant.VIRTUAL_MACHINE_ACCOUNT,Constant.VIRTUAL_MACHINE_PASSWORD);
        try {
            executeCommand.execShell("sh /usr/local/falkon.r174/cattles/startWorker.sh "+falkonServerIP);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            logger.info(e.getMessage());
        }
    }
}

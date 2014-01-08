package com.cattles.schedulingframeworks.falkon;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.schedulingframeworks.falkon.common.ExecuteCommand;
import com.cattles.util.Constant;
import com.cattles.vmManagement.VMInfo;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/8/14
 * Time: 1:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonServerInitialization extends Thread{
    private static Logger logger = Logger.getLogger(FalkonServer.class);
    VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();
    String falkonServerIP=null;
    public FalkonServerInitialization(String _threadName, String _falkonServerIP){
        super(_threadName);
        falkonServerIP=_falkonServerIP;
    }
    public void run(){
        ExecuteCommand executeCommand=new ExecuteCommand(falkonServerIP, Constant.VIRTUAL_MACHINE_ACCOUNT,Constant.VIRTUAL_MACHINE_PASSWORD);
        try {
            executeCommand.execShell("sh /usr/local/falkon.r174/cattles/startService.sh");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            logger.info(e.getMessage());
        }
    }
}

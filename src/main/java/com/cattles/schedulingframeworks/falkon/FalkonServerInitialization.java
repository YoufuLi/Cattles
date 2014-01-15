package com.cattles.schedulingframeworks.falkon;

import com.cattles.schedulingframeworks.falkon.commandexecutor.FalkonExecFactory;
import com.cattles.util.ssh.CommandExecutable;
import com.cattles.util.ssh.ConnInfo;
import com.cattles.util.ssh.SSHResult;
import com.cattles.util.Constant;
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
    String falkonServerIP=null;
    public FalkonServerInitialization(String _threadName, String _falkonServerIP){
        super(_threadName);
        falkonServerIP=_falkonServerIP;
    }
    public void run(){
        CommandExecutable ce = (new FalkonExecFactory()).getCmdExec("server");
        ConnInfo ci = new ConnInfo(falkonServerIP, Constant.VIRTUAL_MACHINE_ACCOUNT, Constant.VIRTUAL_MACHINE_KEY_PATH,null);
        SSHResult result = ce.connect(ci);
        if(!result.isSuccess()){
            Exception exception = result.getError();
            try {
                throw exception;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        result = ce.execute(Constant.FALKON_SERVICE_INTIALIZATION_COMMAND);
    }
}

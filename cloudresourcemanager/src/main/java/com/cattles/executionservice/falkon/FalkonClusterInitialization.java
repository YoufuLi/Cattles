package com.cattles.executionservice.falkon;

import com.cattles.schedulingmanagementservice.falkon.commandexecutor.FalkonExecFactory;
import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.util.Constant;
import com.cattles.schedulingmanagementservice.ssh.CommandExecutable;
import com.cattles.schedulingmanagementservice.ssh.ConnInfo;
import com.cattles.schedulingmanagementservice.ssh.SSHResult;
import com.cattles.virtualMachineManagement.VirtualMachineInformation;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonClusterInitialization extends Thread {
    private static Logger logger = Logger.getLogger(FalkonClusterInitialization.class);
    VirtualMachineResourcePool virtualMachineResourcePool = VirtualMachineResourcePool.getResourcePool();
    public String serverID;
    public ArrayList<String> nodeIDList;

    public FalkonClusterInitialization(String _serverID, ArrayList<String> _nodeIDList) {
        serverID = _serverID;
        nodeIDList = _nodeIDList;
    }

    public void run() {
        logger.info("Begin to Initialize Falkon service**************");
        VirtualMachineInformation falkonServer = virtualMachineResourcePool.getVMWithID(serverID);
        CommandExecutable ce = (new FalkonExecFactory()).getCmdExec("cluster");
        ConnInfo ci = new ConnInfo(falkonServer.getVmPublicIpAddress(), Constant.VIRTUAL_MACHINE_ACCOUNT, Constant.VIRTUAL_MACHINE_KEY_PATH, null);
        SSHResult result = ce.connect(ci);
        if (!result.isSuccess()) {
            Exception exception = result.getError();
            try {
                throw exception;
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        result = ce.execute(Constant.FALKON_SERVICE_INTIALIZATION_COMMAND);

        logger.info("Finish initializing the Falkon service**************");
        if (result != null) {
            //ce.disconnect();
            logger.info("Finish initializing the Falkon service**************");
            for (String workerID : nodeIDList) {
                VirtualMachineInformation falkonWorker = virtualMachineResourcePool.getVMWithID(workerID);
                logger.info("registering worker " + workerID + " to server!");
                FalkonWorkerRegisteraton falkonWorkerRegisteraton = new FalkonWorkerRegisteraton(falkonWorker.getVmID(), falkonServer.getVmPublicIpAddress(), falkonWorker.getVmPublicIpAddress());
                falkonWorkerRegisteraton.start();
            }
        }

    }
}

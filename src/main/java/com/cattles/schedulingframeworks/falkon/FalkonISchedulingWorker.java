package com.cattles.schedulingframeworks.falkon;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.schedulingframeworks.interfaces.ISchedulingWorker;
import com.cattles.virtualMachineManagement.VMInfo;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonISchedulingWorker implements ISchedulingWorker {
    private static Logger logger = Logger.getLogger(FalkonISchedulingWorker.class);
    VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();

    public void register2Server(String serverID, ArrayList<String> nodeIDList){
        //get the vm information according to the serverID, which is also the ID of a virtual machine.
        VMInfo falkonServer=virtualMachineResourcePool.getVMWithID(serverID);
        logger.info("Registering to server*************************"+System.currentTimeMillis()+"******************************");
        for (String workerID:nodeIDList){
            VMInfo falkonWorker=virtualMachineResourcePool.getVMWithID(workerID);
            logger.info("registering worker "+workerID+" to server!");
            FalkonWorkerRegisteraton falkonWorkerRegisteraton=new FalkonWorkerRegisteraton(falkonWorker.getVmID(),falkonServer.getVmPublicIpAddress(),falkonWorker.getVmPublicIpAddress());
            falkonWorkerRegisteraton.start();
            /*ExecuteCommand executeCommand= new ExecuteCommand(falkonWorker.getVmPublicIpAddress(), Constant.VIRTUAL_MACHINE_ACCOUNT,Constant.VIRTUAL_MACHINE_PASSWORD);
            try {
                executeCommand.execShell("sh /usr/local/falkon.r174/cattles/startWorker.sh "+falkonServer.getVmPublicIpAddress());
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                logger.info(e.getMessage());
            }*/
        }
    }
    public void deregisterFromServer(String serverID, ArrayList<String> nodeIDList){
        //String serverIP=virtualMachineResourcePool.getVMWithID(serverID).getVmPublicIpAddress();
        //get the vm information according to the serverID, which is also the ID of a virtual machine.
        logger.info("Begin to deregister workers:*********************"+System.currentTimeMillis()+"******************");
        VMInfo falkonServer=virtualMachineResourcePool.getVMWithID(serverID);
        for (String workerID:nodeIDList){
            VMInfo falkonWorker=virtualMachineResourcePool.getVMWithID(workerID);
            logger.info("deregistering worker "+workerID+" from server!");
            FalkonWorkerDeregisteraton falkonWorkerDeregisteraton=new FalkonWorkerDeregisteraton(falkonWorker.getVmID(),falkonServer.getVmPublicIpAddress(),falkonWorker.getVmPublicIpAddress());
            falkonWorkerDeregisteraton.start();
        }
        logger.info("Finished deregistering the workers:*******************"+System.currentTimeMillis()+"****************************");
    }
}

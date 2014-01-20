package com.cattles.schedulingframeworks.falkon;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.schedulingframeworks.interfaces.IServerBiz;
import com.cattles.virtualMachineManagement.VMInfo;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonIServer implements IServerBiz {
    private static Logger logger = Logger.getLogger(FalkonIServer.class);
    VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();

    /**
     * Initialize the cluster according to provided cluster
     *
     * @param serverID
     */
    @Override
    public void startServer(String serverID) {
        //get the vm information according to the serverID, which is also the ID of a virtual machine.
        VMInfo falkonServer=virtualMachineResourcePool.getVMWithID(serverID);
        logger.info("Initializing the falkon server!");
        FalkonServerInitialization falkonServerInitialization=new FalkonServerInitialization(falkonServer.getVmID(),falkonServer.getVmPublicIpAddress());
        falkonServerInitialization.start();
    }

    /**
     * terminate the cluster according to provided cluster
     *
     * @param serverID
     */
    @Override
    public void stopServer(String serverID) {
        VMInfo falkonServer=virtualMachineResourcePool.getVMWithID(serverID);
        logger.info("Stopping falkon service");
        FalkonServerStop falkonServerStop=new FalkonServerStop(falkonServer.getVmID(),falkonServer.getVmPublicIpAddress());
        falkonServerStop.start();
    }
}

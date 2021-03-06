package com.cattles.executionservice.falkon;

import com.cattles.executionservice.ExecutionServiceConfiguration;
import com.cattles.executionservice.interfaces.ISchedulingServer;
import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.virtualMachineManagement.VirtualMachineInformation;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonISchedulingServer implements ISchedulingServer {
    private static Logger logger = Logger.getLogger(FalkonISchedulingServer.class);
    VirtualMachineResourcePool virtualMachineResourcePool = VirtualMachineResourcePool.getResourcePool();

    /**
     * Initialize the cluster according to provided cluster
     *
     * @param serverID
     */
    @Override
    public boolean startServer(String serverID, ExecutionServiceConfiguration configuration) {
        //get the vm information according to the serverID, which is also the ID of a virtual machine.
        VirtualMachineInformation falkonServer = virtualMachineResourcePool.getVMWithID(serverID);
        logger.info("Initializing the falkon server!");
        FalkonServerInitialization falkonServerInitialization = new FalkonServerInitialization(falkonServer.getVmID(), falkonServer.getVmPublicIpAddress());
        falkonServerInitialization.start();
        return true;
    }

    /**
     * terminate the cluster according to provided cluster
     *
     * @param serverID
     */
    @Override
    public boolean stopServer(String serverID) {
        VirtualMachineInformation falkonServer = virtualMachineResourcePool.getVMWithID(serverID);
        logger.info("Stopping falkon service");
        FalkonServerStop falkonServerStop = new FalkonServerStop(falkonServer.getVmID(), falkonServer.getVmPublicIpAddress());
        falkonServerStop.start();
        return true;
    }
}

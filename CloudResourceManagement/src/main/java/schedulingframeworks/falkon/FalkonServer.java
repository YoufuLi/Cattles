package schedulingframeworks.falkon;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.schedulingframeworks.interfaces.ISchedulingServerBiz;
import com.cattles.virtualMachineManagement.VMInfo;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/27/13
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonServer implements ISchedulingServerBiz {
    private static Logger logger = Logger.getLogger(FalkonServer.class);
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

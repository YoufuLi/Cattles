package com.cattles.schedulingframeworks.falkon;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.ssh.CommandExecutable;
import com.cattles.ssh.ConnInfo;
import com.cattles.ssh.SSHResult;
import com.cattles.ssh.jsch.CmdExecFactory;
import com.cattles.vmManagement.VMInfo;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/27/13
 * Time: 4:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonServer {
    private static Logger logger = Logger.getLogger(FalkonServer.class);
    VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();
    public void startFalkonService(String serverID) {
        //get the vm information according to the serverID, which is also the ID of a virtual machine.
        VMInfo falkonServer=virtualMachineResourcePool.getVMWithID(serverID);
    }
    public void stopFalkonServie(String serverID){

    }
}

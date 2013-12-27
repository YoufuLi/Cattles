package com.cattles.schedulingframeworks.falkon;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/27/13
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonWorker {
    VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();
    public void register2Server(String serverID, ArrayList<String> nodeIDList){

    }
    public void deregisterFromServer(String serverID, ArrayList<String> nodeIDList){
        String serverIP=virtualMachineResourcePool.getVMWithID(serverID).getVmPublicIpAddress();

    }
}

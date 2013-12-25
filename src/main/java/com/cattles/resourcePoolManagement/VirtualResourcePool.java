package com.cattles.resourcePoolManagement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cattles.util.Constant;
import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmManagement.VMInfo;

public class VirtualResourcePool {
    VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();

    /**
     * fetch the required VM list
     * @param vmNum
     * @return
     */
    public ArrayList<VMInfo> fetchVMList(int vmNum){
        ArrayList<VMInfo> fetchVMs=new ArrayList<VMInfo>();
        ArrayList<VMInfo> availableVMList=virtualMachineResourcePool.getVMWithState(Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);
        //there are enough resource in the resource pool
        if(availableVMList.size()>=vmNum){
            fetchVMs.addAll(availableVMList.subList(0,vmNum));

        }else {
            int applySize=Math.abs(vmNum-availableVMList.size());
            ArrayList<VMInfo> applyVMList=virtualMachineResourcePool.applyVMs(applySize);
            fetchVMs.addAll(availableVMList);
            fetchVMs.addAll(applyVMList);
        }
        //TODO:update VMs' state
        return fetchVMs;
    }

}

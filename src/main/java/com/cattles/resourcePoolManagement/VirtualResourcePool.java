package com.cattles.resourcePoolManagement;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cattles.util.Constant;
import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmManagement.VMInfo;

public class VirtualResourcePool {
    VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();
    public VirtualCluster createCluster(int _clusterSize){
        VirtualCluster virtualCluster=new VirtualCluster();
        ArrayList<VMInfo> availableVMList=virtualMachineResourcePool.getVMWithState(Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);
        if(availableVMList.size()>=_clusterSize){

        }else {
            int requestSize=Math.abs(_clusterSize-availableVMList.size());
            ArrayList<VMInfo> requestVMList=virtualMachineResourcePool.requestVMs(requestSize);
        }
        return virtualCluster;
    }

}

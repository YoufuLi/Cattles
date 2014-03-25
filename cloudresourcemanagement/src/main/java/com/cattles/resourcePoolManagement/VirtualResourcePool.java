package com.cattles.resourcePoolManagement;

import com.cattles.util.Constant;
import com.cattles.virtualMachineManagement.VirtualMachineInformation;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class VirtualResourcePool {
    VirtualMachineResourcePool virtualMachineResourcePool = VirtualMachineResourcePool.getResourcePool();
    private static Logger logger = Logger.getLogger(VirtualResourcePool.class);

    /**
     * fetch the required VM list
     *
     * @param vmNum
     * @return
     */
    public ArrayList<VirtualMachineInformation> fetchVMList(int vmNum) {
        ArrayList<VirtualMachineInformation> fetchVMs = new ArrayList<VirtualMachineInformation>();
        ArrayList<VirtualMachineInformation> availableVMList = virtualMachineResourcePool.getVMWithState(Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);
        //there are enough resource in the resource pool
        logger.info("available vm size:" + availableVMList.size());
        if (availableVMList.size() >= vmNum) {
            fetchVMs.addAll(availableVMList.subList(0, vmNum));
        } else {
            int applySize = Math.abs(vmNum - availableVMList.size());
            ArrayList<VirtualMachineInformation> applyVMList = virtualMachineResourcePool.applyVMs(applySize);
            fetchVMs.addAll(availableVMList);
            fetchVMs.addAll(applyVMList);
        }
        //TODO:update VMs' state
        ArrayList<String> fetchVMsIDList = new ArrayList<String>();//the ID list of fetched VMs
        for (int i = 0; i < fetchVMs.size(); i++) {
            fetchVMsIDList.add(fetchVMs.get(i).getVmID());
        }
        virtualMachineResourcePool.modifyVMsState(fetchVMsIDList, Constant.VIRTUAL_MACHINES_STATE_BUSY);
        return fetchVMs;
    }

    public boolean deregisterVMs(ArrayList<String> vmIDList) {
        for (String vmID : vmIDList) {
            logger.info("deregistering virtual machines:" + vmID);
        }
        virtualMachineResourcePool.modifyVMsState(vmIDList, Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);
        return true;
    }

}

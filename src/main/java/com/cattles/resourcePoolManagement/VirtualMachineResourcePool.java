package com.cattles.resourcePoolManagement;

import com.cattles.vmManagement.VMInfo;

import java.util.ArrayList;

public class VirtualMachineResourcePool {
    private static VirtualMachineResourcePool vmResourcePool = null;
    XMLOperationVirtualMachine xmlOperationVirtualMachine=XMLOperationVirtualMachine.getXmlOperationVirtualMachine();
    private VirtualMachineResourcePool(){

    }
    public static synchronized VirtualMachineResourcePool getResourcePool(){
        if (vmResourcePool==null){
            vmResourcePool=new VirtualMachineResourcePool();
        }
        return vmResourcePool;
    }

    /**
     * get all the virtual machines listed in the VirtualMachines.xml
     * @return
     */
    public ArrayList<VMInfo> getVMResourceList() {
        ArrayList<VMInfo> VMResourceList= xmlOperationVirtualMachine.getAllVMs();
        return VMResourceList;
    }

    /**
     * get the virtual machines with specified state.
     * @param _state
     * @return
     */
    public ArrayList<VMInfo> getVMWithState(String _state){
        ArrayList<VMInfo> vmInfoArrayList=xmlOperationVirtualMachine.getVMsWithState(_state);
        return vmInfoArrayList;
    }

    public int checkPoolSize() {
        int vmCount=xmlOperationVirtualMachine.getVMCount();
        return vmCount;
    }
    public ArrayList<VMInfo> requestVMs(int _vmNum) {
        ArrayList<VMInfo> requestVMList=new ArrayList<VMInfo>();
        return requestVMList;
    }
    public void modidyVMState(String _vmID, String _state){
        xmlOperationVirtualMachine.modifyVMState(_vmID,_state);
    }
}

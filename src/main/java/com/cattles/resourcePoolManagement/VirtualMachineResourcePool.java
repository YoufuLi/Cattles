package com.cattles.resourcePoolManagement;

import com.cattles.util.XMLOperationVirtualMachine;
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
    public ArrayList<VMInfo> getVMResourceList() {
        ArrayList<VMInfo> VMResourceList= xmlOperationVirtualMachine.getAllVMs();
        return VMResourceList;
    }

    public ArrayList<VMInfo> getVMWithState(String _state){
        ArrayList<VMInfo> vmInfoArrayList=xmlOperationVirtualMachine.getVMsWithState(_state);
        return vmInfoArrayList;
    }

    public int checkPoolSize() {
        int vmCount=xmlOperationVirtualMachine.getVMCount();
        return vmCount;
    }
    public void requestVMs(int _vmNum) {

    }
}

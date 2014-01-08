package com.cattles.resourcePoolManagement;

import com.cattles.vmManagement.VMInfo;
import com.cattles.vmManagement.VMOperation;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class VirtualMachineResourcePool {
    private static VirtualMachineResourcePool vmResourcePool = null;
    private static Logger logger = Logger.getLogger(VirtualMachineResourcePool.class);
    XMLOperationVirtualMachine xmlOperationVirtualMachine=XMLOperationVirtualMachine.getXmlOperationVirtualMachine();
    VMOperation vmOperation=new VMOperation();
    private VirtualMachineResourcePool(){

    }
    public static synchronized VirtualMachineResourcePool getResourcePool(){
        if (vmResourcePool==null){
            vmResourcePool=new VirtualMachineResourcePool();
        }
        return vmResourcePool;
    }

    public ArrayList<VMInfo> initialization(int poolSize){
        ArrayList<VMInfo> vmInfoArrayList=new ArrayList<VMInfo>();
        logger.info("Initializing the Resource Pool!");
        vmInfoArrayList=applyVMs(poolSize);
        return vmInfoArrayList;
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

    public VMInfo getVMWithID(String _vmID){
        VMInfo vmInfo=new VMInfo();
        vmInfo=xmlOperationVirtualMachine.getVMByID(_vmID);
        return vmInfo;
    }

    /**
     * when the VMs number in the resource pool is not enough, use this method to apply VMs from the underlying Cloud Computing platform
     * @param _vmNum
     * @return
     */
    public ArrayList<VMInfo> applyVMs(int _vmNum) {
        ArrayList<VMInfo> applyVMList=new ArrayList<VMInfo>();
        //apply VMs from the underlying Cloud Computing platform
        try {
            logger.info("Applying virtual resources from the underlying Cloud Computing platform!");
            applyVMList= vmOperation.createInstances(_vmNum);
            xmlOperationVirtualMachine.addVMs(applyVMList);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return applyVMList;
    }

    /**
     * Use the method to modify the state of the specified VM
     * @param _vmID
     * @param _state
     */
    public boolean modidyVMState(String _vmID, String _state){
        boolean success=false;
        success=xmlOperationVirtualMachine.modifyVMState(_vmID,_state);
        return success;
    }

    /**
     * use this method to modify a batch of VMs' state
     * @param _vmIDList
     * @param _state
     * @return
     */
    public boolean modifyVMsState(ArrayList<String> _vmIDList, String _state){
        boolean success=false;
        success=xmlOperationVirtualMachine.modifyVMsState(_vmIDList,_state);
        return success;
    }
}

package com.cattles.resourcePoolManagement;

import com.cattles.virtualMachineManagement.IVirtualMachineOperationimpl;
import com.cattles.virtualMachineManagement.VirtualMachineInformation;
import org.apache.log4j.Logger;

import java.util.ArrayList;

public class VirtualMachineResourcePool {
    private static Logger logger = Logger.getLogger(VirtualMachineResourcePool.class);
    private static VirtualMachineResourcePool vmResourcePool = null;
    XMLOperationVirtualMachine xmlOperationVirtualMachine = XMLOperationVirtualMachine.getXmlOperationVirtualMachine();
    IVirtualMachineOperationimpl vmOperation = new IVirtualMachineOperationimpl();

    private VirtualMachineResourcePool() {

    }

    public static synchronized VirtualMachineResourcePool getResourcePool() {
        if (vmResourcePool == null) {
            vmResourcePool = new VirtualMachineResourcePool();
        }
        return vmResourcePool;
    }

    public ArrayList<VirtualMachineInformation> initialization(int poolSize) {
        ArrayList<VirtualMachineInformation> vmInfoArrayList = new ArrayList<VirtualMachineInformation>();
        logger.info("Initializing the Resource Pool!");
        vmInfoArrayList = applyVMs(poolSize);
        return vmInfoArrayList;
    }

    /**
     * get all the virtual machines listed in the VirtualMachines.xml
     *
     * @return
     */
    public ArrayList<VirtualMachineInformation> getVMResourceList() {
        ArrayList<VirtualMachineInformation> VMResourceList = xmlOperationVirtualMachine.getAllVMs();
        return VMResourceList;
    }

    /**
     * get the virtual machines with specified state.
     *
     * @param _state
     * @return
     */
    public ArrayList<VirtualMachineInformation> getVMWithState(String _state) {
        ArrayList<VirtualMachineInformation> vmInfoArrayList = xmlOperationVirtualMachine.getVMsWithState(_state);
        return vmInfoArrayList;
    }

    public int checkPoolSize() {
        int vmCount = xmlOperationVirtualMachine.getVMCount();
        return vmCount;
    }

    public VirtualMachineInformation getVMWithID(String _vmID) {
        VirtualMachineInformation vmInfo = new VirtualMachineInformation();
        vmInfo = xmlOperationVirtualMachine.getVMByID(_vmID);
        return vmInfo;
    }

    /**
     * when the VMs number in the resource pool is not enough, use this method to apply VMs from the underlying Cloud Computing platform
     *
     * @param _vmNum
     * @return
     */
    public ArrayList<VirtualMachineInformation> applyVMs(int _vmNum) {
        ArrayList<VirtualMachineInformation> applyVMList = new ArrayList<VirtualMachineInformation>();
        //apply VMs from the underlying Cloud Computing platform
        try {
            logger.info("Applying virtual resources from the underlying Cloud Computing platform!");
            applyVMList = vmOperation.createInstances(_vmNum);
            this.addVMs(applyVMList);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return applyVMList;
    }

    public void addVMs(ArrayList<VirtualMachineInformation> VMList) {
        xmlOperationVirtualMachine.addVMs(VMList);
    }

    /**
     * Use the method to modify the state of the specified VM
     *
     * @param _vmID
     * @param _state
     */
    public boolean modidyVMState(String _vmID, String _state) {
        boolean success = false;
        success = xmlOperationVirtualMachine.modifyVMState(_vmID, _state);
        return success;
    }

    /**
     * use this method to modify a batch of VMs' state
     *
     * @param _vmIDList
     * @param _state
     * @return
     */
    public boolean modifyVMsState(ArrayList<String> _vmIDList, String _state) {
        boolean success = false;
        success = xmlOperationVirtualMachine.modifyVMsState(_vmIDList, _state);
        return success;
    }
}

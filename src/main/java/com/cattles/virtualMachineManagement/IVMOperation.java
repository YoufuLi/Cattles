package com.cattles.virtualMachineManagement;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class IVMOperation implements com.cattles.cloudplatforms.interfaces.IVMOperation {
     com.cattles.cloudplatforms.interfaces.IVMOperation vmOperation=VMOperationFactory.vmOperation();

    /**
     * Used to create certain number of VMs.
     *
     * @param vmNumber
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VMInfo> createInstances(int vmNumber) throws Exception {
        ArrayList<VMInfo> vmInfoList=vmOperation.createInstances(vmNumber);
        return vmInfoList;
    }

    /**
     * used to launch one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    @Override
    public VMInfo launchInstance(VMInfo _VMInfo) throws Exception {

        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to launch a list of instances
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VMInfo> launchInstances(ArrayList<VMInfo> vmList) throws Exception {
        ArrayList<VMInfo> vmInfoList=vmOperation.launchInstances(vmList);
        return vmInfoList;
    }

    /**
     * used to shutdown one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean shutdownInstance(VMInfo _VMInfo) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to shutdown a list of instances
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public boolean shutdownInstances(ArrayList<VMInfo> vmList) throws Exception {
        boolean success=vmOperation.shutdownInstances(vmList);
        return success;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to reboot one instance
     *
     * @param _VMInfo
     * @throws Exception
     */
    @Override
    public VMInfo rebootInstance(VMInfo _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to reboot a list of instances
     *
     * @param vmList
     * @throws Exception
     */
    @Override
    public ArrayList<VMInfo> rebootInstances(ArrayList<VMInfo> vmList) throws Exception {
        ArrayList<VMInfo> vmInfoList=vmOperation.rebootInstances(vmList);
        return vmInfoList;
    }

    /**
     * Used to destory vms according to the vmList
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public boolean destroyInstances(ArrayList<VMInfo> vmList) throws Exception {
        boolean success=vmOperation.destroyInstances(vmList);
        return success;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

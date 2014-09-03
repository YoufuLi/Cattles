package com.cattles.cloudplatforms.opennebula;

import com.cattles.cloudplatforms.interfaces.IVirtualMachineOperation;

import com.cattles.virtualMachineManagement.VirtualMachineInformation;

import java.util.ArrayList;


/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class OpenNebulaVMOperationImpl implements IVirtualMachineOperation {

    @Override
    public ArrayList<VirtualMachineInformation> createInstances(int vmNumber) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualMachineInformation launchInstance(VirtualMachineInformation _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualMachineInformation> launchInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shutdownInstance(VirtualMachineInformation _VMInfo) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shutdownInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualMachineInformation rebootInstance(VirtualMachineInformation _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualMachineInformation> rebootInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean destroyInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

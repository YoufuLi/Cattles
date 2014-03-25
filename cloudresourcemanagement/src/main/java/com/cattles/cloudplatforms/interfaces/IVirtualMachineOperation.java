package com.cattles.cloudplatforms.interfaces;

import com.cattles.virtualMachineManagement.VirtualMachineInformation;

import java.util.ArrayList;

/**
 * Used to declare the vm operations that supported by platforms
 *
 * @author youfuli
 */
public interface IVirtualMachineOperation {

    /**
     * Used to create certain number of VMs.
     *
     * @param vmNumber
     * @return
     * @throws Exception
     */
    public ArrayList<VirtualMachineInformation> createInstances(int vmNumber) throws Exception;

    /**
     * used to launch one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    public VirtualMachineInformation launchInstance(VirtualMachineInformation _VMInfo) throws Exception;

    /**
     * used to launch a list of instances
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    public ArrayList<VirtualMachineInformation> launchInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception;

    /**
     * used to shutdown one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    public boolean shutdownInstance(VirtualMachineInformation _VMInfo) throws Exception;

    /**
     * used to shutdown a list of instances
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    public boolean shutdownInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception;

    /**
     * used to reboot one instance
     *
     * @param _VMInfo
     * @throws Exception
     */
    public VirtualMachineInformation rebootInstance(VirtualMachineInformation _VMInfo) throws Exception;

    /**
     * used to reboot a list of instances
     *
     * @param vmList
     * @throws Exception
     */
    public ArrayList<VirtualMachineInformation> rebootInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception;

    /**
     * Used to destroy vms according to the vmList
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    public boolean destroyInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception;
}

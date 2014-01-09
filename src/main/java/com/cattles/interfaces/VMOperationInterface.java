package com.cattles.interfaces;

import com.cattles.vmManagement.VMInfo;

import java.util.ArrayList;

/**
 * Used to declare the vm operations that supported by platforms
 * @author youfuli
 *
 */
public interface VMOperationInterface {

	/**
	 * Used to create certain number of VMs.
	 * @param vmNumber
	 * @return
	 * @throws Exception
	 */
	public ArrayList<VMInfo> createInstances(int vmNumber) throws Exception;

    /**
     * used to launch one instance
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    public VMInfo launchInstance(VMInfo _VMInfo) throws Exception;
    /**
     * used to launch a list of instances
     * @param vmList
     * @return
     * @throws Exception
     */
    public ArrayList<VMInfo> launchInstances(ArrayList<VMInfo> vmList) throws Exception;

    /**
     * used to shutdown one instance
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    public boolean shutdownInstance(VMInfo _VMInfo) throws Exception;
    /**
     * used to shutdown a list of instances
     * @param vmList
     * @return
     * @throws Exception
     */
    public boolean shutdownInstances(ArrayList<VMInfo> vmList) throws Exception;

    /**
     * used to reboot one instance
     * @param _VMInfo
     * @throws Exception
     */
    public VMInfo rebootInstance(VMInfo _VMInfo) throws Exception;
    /**
     * used to reboot a list of instances
     * @param vmList
     * @throws Exception
     */
    public ArrayList<VMInfo> rebootInstances(ArrayList<VMInfo> vmList) throws Exception;

    /**
	 * Used to destroy vms according to the vmList
	 * @param vmList
	 * @return
	 * @throws Exception
	 */
	public boolean destroyInstances(ArrayList<VMInfo> vmList) throws Exception;
}

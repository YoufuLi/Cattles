package com.cattles.interfaces;

import com.cattles.vmManagement.VMInfo;

import java.awt.List;

/**
 * Used to declare the vm operations that supported by platforms
 * @author youfuli
 *
 */
public interface VMOperationInterface {

    /**
     * Used to create one virtual machine
     * @return
     * @throws Exception
     */
    public VMInfo createInstance() throws Exception;
	/**
	 * Used to create certain number of VMs.
	 * @param vmNumber
	 * @return
	 * @throws Exception
	 */
	public List createInstances(int vmNumber) throws Exception;

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
    public VMInfo launchInstances(List vmList) throws Exception;

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
    public boolean shutdownInstances(List vmList) throws Exception;

    /**
     * used to reboot one instance
     * @param _VMInfo
     * @throws Exception
     */
    public List rebootInstance(VMInfo _VMInfo) throws Exception;
    /**
     * used to reboot a list of instances
     * @param vmList
     * @throws Exception
     */
    public List rebootInstances(List vmList) throws Exception;

    /**
	 * Used to destory vms according to the vmList
	 * @param vmList
	 * @return
	 * @throws Exception
	 */
	public boolean destoryInstances(List vmList) throws Exception;
}

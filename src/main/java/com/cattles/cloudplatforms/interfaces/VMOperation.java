package com.cattles.cloudplatforms.interfaces;

import java.awt.List;

/**
 * Used to declare the vm operations that supported by platforms
 * @author youfuli
 *
 */
public interface VMOperation {
	/**
	 * Used to create certain number VMs.
	 * @param vmNumber
	 * @return
	 * @throws Exception
	 */
	public List createVMs(int vmNumber) throws Exception;
	
	public Boolean startVMs(List vmList) throws Exception;
	public Boolean shutdownVMs(List vmList) throws Exception;
	public void rebootVMs(List vmList) throws Exception;
	/**
	 * Used to destory vms according to the vmList
	 * @param vmList
	 * @return
	 * @throws Exception
	 */
	public Boolean destoryVMs(List vmList) throws Exception;
}

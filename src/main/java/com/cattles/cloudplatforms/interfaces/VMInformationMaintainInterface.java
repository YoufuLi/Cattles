package com.cattles.cloudplatforms.interfaces;

import com.cattles.virtualMachineManagement.VMInfo;

/**
 * Used to declare the vm operations that supported by platforms
 * @author youfuli
 *
 */
public interface VMInformationMaintainInterface {
    /**
     * use cloud platform API to get the last VM information.
     * @param vmID
     * @return
     */
    public VMInfo queryVMInformation(String vmID);
}

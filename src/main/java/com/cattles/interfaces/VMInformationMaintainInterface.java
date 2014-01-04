package com.cattles.interfaces;

import com.cattles.vmManagement.VMInfo;

import java.util.ArrayList;

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

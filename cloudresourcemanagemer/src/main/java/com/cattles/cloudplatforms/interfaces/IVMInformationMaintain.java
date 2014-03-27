package com.cattles.cloudplatforms.interfaces;

import com.cattles.virtualMachineManagement.VirtualMachineInformation;

import java.util.ArrayList;

/**
 * Used to declare the vm operations that supported by platforms
 *
 * @author youfuli
 */
public interface IVMInformationMaintain {
    public ArrayList<VirtualMachineInformation> getInstanceList();

    /**
     * use cloud platform API to get the last VM information.
     *
     * @param vmID
     * @return
     */
    public VirtualMachineInformation queryVMInformation(String vmID);
}

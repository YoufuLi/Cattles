package com.cattles.cloudplatforms.amazonec2;

import com.cattles.interfaces.VMInformationMaintainInterface;
import com.cattles.vmManagement.VMInfo;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/4/14
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class VMInformationMaintainImpl implements VMInformationMaintainInterface {
    /**
     * use cloud platform API to get the last VM information.
     *
     * @param vmID
     * @return
     */
    @Override
    public VMInfo queryVMInformation(String vmID) {
        VMInfo vmInfo=new VMInfo();

        return vmInfo;
    }
}

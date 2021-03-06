package com.cattles.virtualMachineManagement;

import com.cattles.cloudplatforms.amazonec2.EC2VMOperationImpl;
import com.cattles.cloudplatforms.interfaces.IVirtualMachineOperation;
import com.cattles.cloudplatforms.opennebula.OpenNebulaVMOperationImpl;
import com.cattles.cloudplatforms.openstack.OpenStackVMOperationImpl;
import com.cattles.util.Constant;
import com.cattles.util.PlatformConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class VMOperationFactory {
    public static IVirtualMachineOperation vmOperation() {
        PlatformConfiguration platformConfiguration = PlatformConfiguration.getPlatformConfiguration();
        if (platformConfiguration.getPlatformName().equalsIgnoreCase(Constant.AMAZON_EC2_PLATFORM_NAME)) {
            return new EC2VMOperationImpl();
        } else if (platformConfiguration.getPlatformName().equalsIgnoreCase(Constant.OPENNEBULA_PLATFORM_NAME)) {
            return new OpenNebulaVMOperationImpl();
        } else if (platformConfiguration.getPlatformName().equalsIgnoreCase(Constant.OPENSTACK_PLATFORM_NAME)){
            return new OpenStackVMOperationImpl();
        } else {
            return null;
        }
    }
}

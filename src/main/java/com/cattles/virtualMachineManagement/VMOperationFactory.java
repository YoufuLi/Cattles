package com.cattles.virtualMachineManagement;

import com.cattles.cloudplatforms.amazonec2.EC2VMOperationImpl;
import com.cattles.cloudplatforms.opennebula.OpenNebulaIVMOperationImpl;
import com.cattles.cloudplatforms.interfaces.IVMOperationBiz;
import com.cattles.util.Constant;
import com.cattles.util.PlatformConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class VMOperationFactory {
    public static IVMOperationBiz vmOperation(){
        PlatformConfiguration platformConfiguration=PlatformConfiguration.getPlatformConfiguration();
        if (platformConfiguration.getPlatformName().equalsIgnoreCase(Constant.AMAZON_EC2_PLATFORM_NAME)){
            return new EC2VMOperationImpl();
        }else if (platformConfiguration.getPlatformName().equalsIgnoreCase(Constant.OPENNEBULA_PLATFORM_NAME)){
            return new OpenNebulaIVMOperationImpl();
        }else{
            return null;
        }
    }
}

package com.cattles.vmManagement;

import com.cattles.cloudplatforms.amazonec2.EC2VMOperationImpl;
import com.cattles.cloudplatforms.opennebula.OpenNebulaVMOperationImpl;
import com.cattles.interfaces.VMOperationInterface;
import com.cattles.util.XMLOperationPlatform;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/18/13
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class VMOperationFactory {
    public static VMOperationInterface vmOperation(){
        XMLOperationPlatform xmlOperationPlatform=XMLOperationPlatform.getXmlOperationPlatform();
        if (xmlOperationPlatform.getPlatformName().equals("EC2")){
            return new EC2VMOperationImpl();
        }else if (xmlOperationPlatform.getPlatformName().equals("OpenNebula")){
            return new OpenNebulaVMOperationImpl();
        }else{
            return null;
        }
    }
}

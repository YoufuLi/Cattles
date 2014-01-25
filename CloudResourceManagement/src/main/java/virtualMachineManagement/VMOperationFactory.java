package virtualMachineManagement;

import com.cattles.cloudplatforms.amazonec2.EC2VMOperationImpl;
import com.cattles.cloudplatforms.interfaces.IVMOperation;
import com.cattles.cloudplatforms.opennebula.OpenNebulaIVMOperationImpl;
import com.cattles.util.Constant;
import com.cattles.util.PlatformConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/18/13
 * Time: 1:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class VMOperationFactory {
    public static IVMOperation vmOperation(){
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

package virtualClusterProvision;

import com.cattles.util.Constant;
import com.cattles.util.XMLOperationSchedulingFramework;
import com.cattles.virtualClusterProvision.interfaces.IVirtualClusterProvision;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/15/14
 * Time: 4:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class VirtualClusterProvisionFactory {
    private static Logger log = Logger.getLogger(VirtualClusterProvisionFactory.class);
    public static IVirtualClusterProvision virtualClusterProvision(){
        XMLOperationSchedulingFramework xmlOperationSchedulingFramework=XMLOperationSchedulingFramework.getXmlOperationPlatform();
        if(xmlOperationSchedulingFramework.getFrameworkName().equalsIgnoreCase(Constant.FALKON_FRAMEWORK_NAME)){
            log.info(Constant.FALKON_FRAMEWORK_NAME);
            return new FalkonClusterProvisionImpl();

        }else if(xmlOperationSchedulingFramework.getFrameworkName().equalsIgnoreCase(Constant.GEARMAN_FRAMEWORK_NAME)){
            return new GearmanClusterProvisionImpl();
        }
        return null;
    }
}

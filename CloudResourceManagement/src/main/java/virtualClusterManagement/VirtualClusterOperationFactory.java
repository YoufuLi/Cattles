package virtualClusterManagement;

import com.cattles.virtualClusterManagement.falkonCluster.FalkonClusterOperationImplI;
import com.cattles.virtualClusterManagement.gearmanCluster.GearmanClusterOperationImplI;
import com.cattles.virtualClusterManagement.interfaces.IVirtualClusterOperationBiz;
import com.cattles.util.Constant;
import com.cattles.util.XMLOperationSchedulingFramework;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/26/13
 * Time: 4:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class VirtualClusterOperationFactory {
    private static Logger log = Logger.getLogger(VirtualClusterOperationFactory.class);
    public static IVirtualClusterOperationBiz virtualClusterOperation(){
        XMLOperationSchedulingFramework xmlOperationSchedulingFramework=XMLOperationSchedulingFramework.getXmlOperationPlatform();
        if(xmlOperationSchedulingFramework.getFrameworkName().equalsIgnoreCase(Constant.FALKON_FRAMEWORK_NAME)){
            log.info(Constant.FALKON_FRAMEWORK_NAME);
            return new FalkonClusterOperationImplI();

        }else if(xmlOperationSchedulingFramework.getFrameworkName().equalsIgnoreCase(Constant.GEARMAN_FRAMEWORK_NAME)){
            return new GearmanClusterOperationImplI();
        }
        return null;
    }
}

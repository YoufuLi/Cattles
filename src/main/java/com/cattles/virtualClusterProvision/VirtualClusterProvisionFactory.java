package com.cattles.virtualClusterProvision;

import com.cattles.util.Constant;
import com.cattles.util.XMLOperationSchedulingFramework;
import com.cattles.virtualClusterManagement.falkonCluster.FalkonClusterOperationImpl;
import com.cattles.virtualClusterManagement.gearmanCluster.GearmanClusterOperationImpl;
import com.cattles.virtualClusterManagement.interfaces.VirtualClusterOperationInterface;
import com.cattles.virtualClusterProvision.interfaces.VirtualClusterProvisionInterface;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class VirtualClusterProvisionFactory {
    private static Logger log = Logger.getLogger(VirtualClusterProvisionFactory.class);
    public static VirtualClusterProvisionInterface virtualClusterProvision(){
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

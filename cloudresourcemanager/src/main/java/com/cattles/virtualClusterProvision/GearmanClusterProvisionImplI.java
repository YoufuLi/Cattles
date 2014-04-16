package com.cattles.virtualClusterProvision;

import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.virtualClusterManagement.VirtualCluster;
import com.cattles.virtualClusterManagement.XMLOperationCluster;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class GearmanClusterProvisionImplI implements com.cattles.virtualClusterProvision.interfaces.IVirtualClusterProvision {
    private static Logger logger = Logger.getLogger(GearmanClusterProvisionImplI.class);
    XMLOperationCluster xmlOperationCluster = XMLOperationCluster.getXmlOperationCluster();
    VirtualResourcePool virtualResourcePool = new VirtualResourcePool();

    /**
     * provision cluster to upper layer
     *
     * @param _clusterSize
     * @return
     */
    @Override
    public VirtualCluster clusterProvision(int _clusterSize) {
        return null;
    }

    /**
     * upper layer can invoke this method to release the idle cluster
     *
     * @param virtualCluster
     */
    @Override
    public void releaseCluster(VirtualCluster virtualCluster) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

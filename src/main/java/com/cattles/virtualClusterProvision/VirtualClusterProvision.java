package com.cattles.virtualClusterProvision;

import com.cattles.virtualClusterManagement.VirtualCluster;
import com.cattles.virtualClusterManagement.VirtualClusterOperationFactory;
import com.cattles.virtualClusterManagement.interfaces.VirtualClusterOperationInterface;
import com.cattles.virtualClusterProvision.interfaces.VirtualClusterProvisionInterface;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class VirtualClusterProvision implements VirtualClusterProvisionInterface {
    private static Logger logger = Logger.getLogger(VirtualClusterProvision.class);
    VirtualClusterProvisionInterface virtualClusterProvision= VirtualClusterProvisionFactory.virtualClusterProvision();
    /**
     * provision cluster to upper layer
     *
     * @param _clusterSize
     * @return
     */
    @Override
    public VirtualCluster clusterProvision(int _clusterSize) {
        return virtualClusterProvision.clusterProvision(_clusterSize);  //To change body of implemented methods use File | Settings | File Templates.
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
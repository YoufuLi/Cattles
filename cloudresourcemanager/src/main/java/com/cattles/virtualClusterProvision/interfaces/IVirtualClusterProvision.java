package com.cattles.virtualClusterProvision.interfaces;

import com.cattles.virtualClusterManagement.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public interface IVirtualClusterProvision {
    /**
     * provision cluster to upper layer
     *
     * @param _clusterSize
     * @return
     */
    public VirtualCluster clusterProvision(int _clusterSize);

    /**
     * upper layer can invoke this method to release the idle cluster
     *
     * @param virtualCluster
     */
    public void releaseCluster(VirtualCluster virtualCluster);
}

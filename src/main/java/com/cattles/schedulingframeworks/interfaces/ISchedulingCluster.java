package com.cattles.schedulingframeworks.interfaces;

import com.cattles.virtualClusterManagement.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public interface ISchedulingCluster {
    /**
     * Initialize the cluster according to provided cluster
     * @param virtualCluster
     */
    public void initializeCluster(VirtualCluster virtualCluster);

    /**
     * terminate the cluster according to provided cluster
     * @param virtualCluster
     */
    public void terminateCluster(VirtualCluster virtualCluster);
}

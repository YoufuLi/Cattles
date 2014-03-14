package com.cattles.schedulingframeworks.interfaces;

import com.cattles.schedulingframeworks.SchedulingConfiguration;
import com.cattles.schedulingframeworks.SchedulingInformation;
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
     * @param configuration
     */
    public SchedulingInformation initializeCluster(VirtualCluster virtualCluster, SchedulingConfiguration configuration);

    /**
     * terminate the cluster according to provided cluster
     * @param virtualCluster
     */
    public boolean terminateCluster(VirtualCluster virtualCluster);
}

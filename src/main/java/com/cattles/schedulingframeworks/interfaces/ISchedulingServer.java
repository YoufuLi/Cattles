package com.cattles.schedulingframeworks.interfaces;

import com.cattles.schedulingframeworks.SchedulingConfiguration;
import com.cattles.virtualClusterManagement.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public interface ISchedulingServer {
    /**
     * Initialize the cluster according to provided cluster
     * @param serverID
     * @param configuration
     */
    public boolean startServer(String serverID, SchedulingConfiguration configuration);

    /**
     * terminate the cluster according to provided cluster
     * @param serverID
     */
    public boolean stopServer(String serverID);
}

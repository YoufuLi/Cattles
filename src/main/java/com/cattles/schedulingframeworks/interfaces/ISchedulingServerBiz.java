package com.cattles.schedulingframeworks.interfaces;

import com.cattles.virtualClusterManagement.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public interface ISchedulingServerBiz {
    /**
     * Initialize the cluster according to provided cluster
     * @param serverID
     */
    public void startServer(String serverID);

    /**
     * terminate the cluster according to provided cluster
     * @param serverID
     */
    public void stopServer(String serverID);
}

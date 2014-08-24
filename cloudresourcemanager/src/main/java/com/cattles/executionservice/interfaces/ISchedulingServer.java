package com.cattles.executionservice.interfaces;

import com.cattles.executionservice.ExecutionServiceConfiguration;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public interface ISchedulingServer {
    /**
     * Initialize the cluster according to provided cluster
     *
     * @param serverID
     * @param configuration
     */
    public boolean startServer(String serverID, ExecutionServiceConfiguration configuration);

    /**
     * terminate the cluster according to provided cluster
     *
     * @param serverID
     */
    public boolean stopServer(String serverID);
}

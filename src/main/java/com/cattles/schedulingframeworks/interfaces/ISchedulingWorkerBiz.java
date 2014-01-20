package com.cattles.schedulingframeworks.interfaces;

import com.cattles.virtualClusterManagement.VirtualCluster;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public interface ISchedulingWorkerBiz {
    /**
     * Initialize the server according to provided serverID
     * @param serverID
     * @param nodeIDList
     */
    public void register2Server(String serverID, ArrayList<String> nodeIDList);

    /**
     * deregister the worker from server
     * @param serverID
     * @param nodeIDList
     */
    public void deregisterFromServer(String serverID, ArrayList<String> nodeIDList);
}

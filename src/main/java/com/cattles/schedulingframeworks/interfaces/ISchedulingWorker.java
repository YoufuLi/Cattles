package com.cattles.schedulingframeworks.interfaces;

import com.cattles.schedulingframeworks.SchedulingConfiguration;
import com.cattles.virtualClusterManagement.VirtualCluster;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public interface ISchedulingWorker {
    /**
     * Initialize the server according to provided serverID
     * @param serverID
     * @param nodeIDList
     * @param configuration
     */
    public boolean register2Server(String serverID, ArrayList<String> nodeIDList, SchedulingConfiguration configuration);

    /**
     * deregister the worker from server
     * @param serverID
     * @param nodeIDList
     */
    public boolean deregisterFromServer(String serverID, ArrayList<String> nodeIDList);
}

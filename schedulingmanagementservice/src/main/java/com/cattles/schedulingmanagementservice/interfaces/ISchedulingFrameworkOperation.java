package com.cattles.schedulingmanagementservice.interfaces;

import com.cattles.schedulingmanagementservice.SchedulingFrameworkInformation;
import com.cattles.schedulingmanagementservice.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 3/25/14
 * Time: 6:53 PM
 * defined to standardize the supported operations upon scheduling framework
 */
public interface ISchedulingFrameworkOperation {
    /**
     * used to initialize the scheduling service of the scheduling framework (eg. start falkon server and worker, including registration)
     * @param information
     * @return
     * @throws Exception
     */
    public SchedulingFrameworkInformation initializeSchedulingService(SchedulingFrameworkInformation information) throws Exception;

    /**
     * defined to support the auto-scale operation of scheduling cluster--add the provided virtual cluster to the scheduling cluster
     * @param information
     * @param virtualCluster
     * @return
     * @throws Exception
     */
    public boolean increaseSchedulingClusterSize(SchedulingFrameworkInformation information, VirtualCluster virtualCluster) throws Exception;

    /**
     * defined to support the auto-scale operation of scheduling cluster--decrease the specified number of nodes from the scheduling cluster
     * @param information
     * @param size
     * @return
     * @throws Exception
     */
    public boolean decreaseSchedulingClusterSize(SchedulingFrameworkInformation information, int size) throws Exception;

    /**
     * used to terminate the scheduling service in the scheduling cluster (eg. deregister falkon workers from server, shutdown falkon service, etc.)
     * @param information
     * @return
     * @throws Exception
     */
    public boolean terminateSchedulingService(SchedulingFrameworkInformation information) throws Exception;
}

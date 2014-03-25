package com.cattles.schedulingmanagementservice.interfaces;

import com.cattles.schedulingmanagementservice.SchedulingFrameworkInformation;
import com.cattles.schedulingmanagementservice.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 3/25/14
 * Time: 6:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISchedulingFrameworkOperation {
    public SchedulingFrameworkInformation initializeSchedulingService(SchedulingFrameworkInformation information) throws Exception;
    public boolean increaseSchedulingClusterSize(SchedulingFrameworkInformation information, VirtualCluster virtualCluster) throws Exception;
    public boolean decreaseSchedulingClusterSize(SchedulingFrameworkInformation information, int size) throws Exception;
    public boolean terminateSchedulingService(SchedulingFrameworkInformation information) throws Exception;
}

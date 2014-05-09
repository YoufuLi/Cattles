package com.cattles.schedulingmanagementservice.falkon.falkonframeworkoperation;

import com.cattles.schedulingmanagementservice.SchedulingFrameworkInformation;
import com.cattles.schedulingmanagementservice.VirtualCluster;
import com.cattles.schedulingmanagementservice.interfaces.ISchedulingFrameworkOperation;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 5/9/14
 * Time: 7:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonFrameworkOperationImpl implements ISchedulingFrameworkOperation{
    @Override
    public SchedulingFrameworkInformation initializeSchedulingService(SchedulingFrameworkInformation information) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean increaseSchedulingClusterSize(SchedulingFrameworkInformation information, VirtualCluster virtualCluster) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean decreaseSchedulingClusterSize(SchedulingFrameworkInformation information, int size) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean terminateSchedulingService(SchedulingFrameworkInformation information) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

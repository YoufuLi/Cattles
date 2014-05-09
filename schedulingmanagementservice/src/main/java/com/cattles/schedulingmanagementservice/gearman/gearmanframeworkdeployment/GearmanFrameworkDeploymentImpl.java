package com.cattles.schedulingmanagementservice.gearman.gearmanframeworkdeployment;

import com.cattles.schedulingmanagementservice.SchedulingFrameworkConfiguration;
import com.cattles.schedulingmanagementservice.SchedulingFrameworkInformation;
import com.cattles.schedulingmanagementservice.VirtualCluster;
import com.cattles.schedulingmanagementservice.interfaces.ISchedulingFrameworkDeployment;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 5/9/14
 * Time: 7:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class GearmanFrameworkDeploymentImpl implements ISchedulingFrameworkDeployment {
    @Override
    public SchedulingFrameworkInformation deploySchedulingFramework(VirtualCluster virtualCluster, SchedulingFrameworkConfiguration configuration) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SchedulingFrameworkInformation modifySchedulingFramework(VirtualCluster virtualCluster, SchedulingFrameworkInformation information, SchedulingFrameworkConfiguration configuration) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean revokeSchedulingFrameworkDeployment(VirtualCluster virtualCluster, SchedulingFrameworkInformation information) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

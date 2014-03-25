package com.cattles.schedulingmanagementservice.interfaces;

import com.cattles.schedulingmanagementservice.SchedulingFrameworkInformation;
import com.cattles.schedulingmanagementservice.SchedulingFrameworkConfiguration;
import com.cattles.schedulingmanagementservice.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Defined to complete the deployment of user-specified scheduling framework and its
 */
public interface ISchedulingFrameworkDeployment {
    /**
     * Deploy the scheduling framework to the specified virtual cluster
     *
     * @param virtualCluster
     * @param configuration
     */
    public SchedulingFrameworkInformation deploySchedulingFramework(VirtualCluster virtualCluster, SchedulingFrameworkConfiguration configuration) throws Exception;

    /**
     * modify the scheduling framework in the specified virtual cluster
     *
     * @param virtualCluster
     * @param information
     * @param configuration
     * @return
     */
    public SchedulingFrameworkInformation modifySchedulingFramework(VirtualCluster virtualCluster, SchedulingFrameworkInformation information, SchedulingFrameworkConfiguration configuration) throws Exception;

    /**
     * revoke the scheduling framework in the specified cluster
     *
     * @param virtualCluster
     * @param information
     */
    public boolean revokeSchedulingFrameworkDeployment(VirtualCluster virtualCluster, SchedulingFrameworkInformation information) throws Exception;
}

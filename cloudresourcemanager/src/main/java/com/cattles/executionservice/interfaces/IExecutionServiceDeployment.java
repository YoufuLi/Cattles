package com.cattles.executionservice.interfaces;

import com.cattles.executionservice.ExecutionServiceConfiguration;
import com.cattles.executionservice.ExecutionServiceInformation;
import com.cattles.virtualClusterManagement.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public interface IExecutionServiceDeployment {
    /**
     * Deploy the execution service to provided virtual cluster
     *
     * @param virtualCluster
     * @param configuration
     */
    public ExecutionServiceInformation deployExecutionService(VirtualCluster virtualCluster, ExecutionServiceConfiguration configuration) throws Exception;

    /**
     * modify the execution service in provided virtual cluster
     *
     * @param virtualCluster
     * @param information
     * @param configuration
     * @return
     */
    public ExecutionServiceInformation modifyExecutionService(VirtualCluster virtualCluster, ExecutionServiceInformation information, ExecutionServiceConfiguration configuration) throws Exception;

    /**
     * revoke the execution service in provided cluster
     *
     * @param virtualCluster
     * @param information
     */
    public boolean revokeExecutionServiceDeployment(VirtualCluster virtualCluster, ExecutionServiceInformation information) throws Exception;
}

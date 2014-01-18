package com.cattles.virtualClusterProvision.interfaces;

import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.schedulingframeworks.falkon.FalkonClusterInitialization;
import com.cattles.schedulingframeworks.falkon.FalkonServer;
import com.cattles.schedulingframeworks.falkon.FalkonWorker;
import com.cattles.util.Constant;
import com.cattles.virtualClusterManagement.VirtualCluster;
import com.cattles.virtualClusterManagement.XMLOperationCluster;
import com.cattles.virtualClusterManagement.interfaces.VirtualClusterOperationInterface;
import com.cattles.virtualMachineManagement.VMInfo;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/15/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface VirtualClusterProvisionInterface {
    /**
     * provision cluster to upper layer
     * @param _clusterSize
     * @return
     */
    public VirtualCluster clusterProvision(int _clusterSize);

    /**
     * upper layer can invoke this method to release the idle cluster
     * @param virtualCluster
     */
    public void releaseCluster(VirtualCluster virtualCluster);
}

package com.cattles.interfaces;

import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.util.Constant;
import com.cattles.util.Tool;
import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmManagement.VMInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/26/13
 * Time: 4:43 PM
 * To change this template use File | Settings | File Templates.
 */
public interface VirtualClusterOperationInterface {
    public VirtualCluster clusterProvision(int _clusterSize);

    /**
     * fetch a list of VMs from the resource pool, then generate a virtual machine cluster.
     * add the virtual machine cluster to the VirtualCluster.xml
     * @param _clusterSize
     * @return
     */
    public VirtualCluster createCluster(int _clusterSize);

    /**
     * Use the provided virtual machine list to generate a virtual cluster
     * @param VMList
     * @return
     */
    public VirtualCluster generateCluster(ArrayList<VMInfo> VMList);
    public boolean addNodes(String _clusterID, ArrayList<String> _nodeIDList);

    public boolean removeNodes(String _clusterID, ArrayList<String> _nodeIDList);

    public boolean modifyServerID(String _clusterID,String _serverID);

    /**
     * Modify the state of the specified cluster which ID is _clusterID
     * @param _clusterID
     * @param _state
     * @return
     */
    public boolean modifyClusterState(String _clusterID, String _state);

    /**
     * get the size of a cluster, which is consist of the num of nodes and the server node
     * @param _clusterID
     * @return
     */
    public int getClusterSize(String _clusterID);

    public ArrayList<VirtualCluster> getAllClusters();

    public ArrayList<VirtualCluster> getClustersWithState(String _state);

    public VirtualCluster getClusterWithID(String _clusterID);

    /**
     * invoke the method in XMLOperationCluster to delete a cluster.
     * We also provide a method to delete a list of Clusters
     * @param _clusterID
     * @return
     */
    public boolean deleteCluster(String _clusterID);
}

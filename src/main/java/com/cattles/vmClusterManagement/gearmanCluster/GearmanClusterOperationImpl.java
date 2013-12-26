package com.cattles.vmClusterManagement.gearmanCluster;

import com.cattles.interfaces.VirtualClusterOperationInterface;
import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmManagement.VMInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/26/13
 * Time: 4:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class GearmanClusterOperationImpl implements VirtualClusterOperationInterface {
    @Override
    public VirtualCluster clusterProvision(int _clusterSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * fetch a list of VMs from the resource pool, then generate a virtual machine cluster.
     * add the virtual machine cluster to the VirtualCluster.xml
     *
     * @param _clusterSize
     * @return
     */
    @Override
    public VirtualCluster createCluster(int _clusterSize) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Use the provided virtual machine list to generate a virtual cluster
     *
     * @param VMList
     * @return
     */
    @Override
    public VirtualCluster generateCluster(ArrayList<VMInfo> VMList) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean addNodes(String _clusterID, ArrayList<String> _nodeIDList) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeNodes(String _clusterID, ArrayList<String> _nodeIDList) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean modifyServerID(String _clusterID, String _serverID) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Modify the state of the specified cluster which ID is _clusterID
     *
     * @param _clusterID
     * @param _state
     * @return
     */
    @Override
    public boolean modifyClusterState(String _clusterID, String _state) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * get the size of a cluster, which is consist of the num of nodes and the server node
     *
     * @param _clusterID
     * @return
     */
    @Override
    public int getClusterSize(String _clusterID) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualCluster> getAllClusters() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualCluster> getClustersWithState(String _state) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualCluster getClusterWithID(String _clusterID) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * invoke the method in XMLOperationCluster to delete a cluster.
     * We also provide a method to delete a list of Clusters
     *
     * @param _clusterID
     * @return
     */
    @Override
    public boolean deleteCluster(String _clusterID) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

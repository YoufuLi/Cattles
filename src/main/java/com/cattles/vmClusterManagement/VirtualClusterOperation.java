package com.cattles.vmClusterManagement;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/23/13
 * Time: 10:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class VirtualClusterOperation {
    //TODO:update the operations upon to cluster management
    public VirtualCluster createCluster(int _clusterSize){
        VirtualCluster virtualCluster=new VirtualCluster();
        return virtualCluster;
    }

    public boolean deleteCluster(String _clusterID){
        boolean success=false;
        return success;
    }

    public boolean addNodes(String _clusterID, int _nodeNum) {
        boolean success=false;
        return success;
    }

    public boolean removeNodes(String _clusterID, int _nodeNum) {
        boolean success=false;
        return success;
    }

    public VirtualCluster modifyServerID(String _clusterID){
        VirtualCluster virtualCluster=new VirtualCluster();
        return virtualCluster;
    }

    public boolean modifyClusterState(String _clusterID){
        boolean success=false;
        return success;
    }

    public int getClusterSize(String _clusterID){
        int clusterSize=0;
        return clusterSize;
    }

    public ArrayList<VirtualCluster> getAllClusters(){
        ArrayList<VirtualCluster> virtualClusters=new ArrayList<VirtualCluster>();
        return virtualClusters;
    }

    public ArrayList<VirtualCluster> getClustersWithState(){
        ArrayList<VirtualCluster> virtualClusters=new ArrayList<VirtualCluster>();
        return virtualClusters;
    }

    public VirtualCluster getClusterWithID(String _clusterID){
        VirtualCluster virtualCluster=new VirtualCluster();
        return virtualCluster;
    }
}

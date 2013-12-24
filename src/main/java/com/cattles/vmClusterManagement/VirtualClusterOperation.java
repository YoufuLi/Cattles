package com.cattles.vmClusterManagement;

import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.util.Constant;

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
    XMLOperationCluster xmlOperationCluster=XMLOperationCluster.getXmlOperationCluster();
    VirtualCluster virtualCluster;
    public VirtualCluster createCluster(int _clusterSize){
        //check if there are any standby cluster,
        ArrayList<VirtualCluster> virtualClusterList=xmlOperationCluster.getClustersWithState(Constant.VIRTUAL_CLUSTER_STATE_STANDBY);
        if(virtualClusterList.size()>=1){
            //calculate the absolute value of standby cluster size and the required num, then select the smallest absolute value
            int minABS=Math.abs(virtualClusterList.get(0).getClusterSize()-_clusterSize);
            int flag=0;
            for(int i=1;i<virtualClusterList.size();i++){
                if(minABS>Math.abs(virtualClusterList.get(i).getClusterSize()-_clusterSize)){
                    minABS=Math.abs(virtualClusterList.get(i).getClusterSize()-_clusterSize);
                    flag=i;
                }
            }
            virtualCluster=virtualClusterList.get(flag);
            //set the "standby" state of cluster to "activated"
            xmlOperationCluster.modifyClusterState(virtualCluster.getClusterID(),Constant.VIRTUAL_CLUSTER_STATE_STANDBY);
        }else{
            VirtualResourcePool virtualResourcePool=new VirtualResourcePool();
            virtualCluster=virtualResourcePool.createCluster(_clusterSize);
            xmlOperationCluster.addCluster(virtualCluster);
        }
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

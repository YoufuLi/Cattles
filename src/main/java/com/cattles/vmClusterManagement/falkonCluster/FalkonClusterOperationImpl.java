package com.cattles.vmClusterManagement.falkonCluster;

import com.cattles.interfaces.VirtualClusterOperationInterface;
import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.util.Constant;
import com.cattles.util.Tool;
import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmClusterManagement.XMLOperationCluster;
import com.cattles.vmManagement.VMInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/26/13
 * Time: 4:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonClusterOperationImpl implements VirtualClusterOperationInterface {
    //TODO:update the operations upon to cluster management
    XMLOperationCluster xmlOperationCluster=XMLOperationCluster.getXmlOperationCluster();
    VirtualCluster virtualCluster;
    public VirtualCluster clusterProvision(int _clusterSize){
        //check if there are any standby cluster,
        ArrayList<VirtualCluster> standbyClusterList=xmlOperationCluster.getClustersWithState(Constant.VIRTUAL_CLUSTER_STATE_STANDBY);
        if(standbyClusterList.size()>=1){
            //calculate the absolute value of standby cluster size and the required num, then select the smallest absolute value
            int minABS=Math.abs(standbyClusterList.get(0).getClusterSize()-_clusterSize);
            int flag=0;
            for(int i=1;i<standbyClusterList.size();i++){
                if(minABS>Math.abs(standbyClusterList.get(i).getClusterSize()-_clusterSize)){
                    minABS=Math.abs(standbyClusterList.get(i).getClusterSize()-_clusterSize);
                    flag=i;
                }
            }
            virtualCluster=standbyClusterList.get(flag);
            //TODO:check if the num of nodes equals to the required, if not add more nodes , or remove the redundant nodes
            if(virtualCluster.getClusterSize()!=_clusterSize){
                if(virtualCluster.getClusterSize()>_clusterSize){
                    //TODO: Deregister the node from the cluster

                    //TODO: Remove the redundant nodes


                }else{
                    //TODO: Fetch a list of VMs and add to the cluster, then do the node registration
                }
            }

        }else{
            virtualCluster=this.createCluster(_clusterSize);
        }
        //update the cluster state from "standby" to "activated"
        xmlOperationCluster.modifyClusterState(virtualCluster.getClusterID(),Constant.VIRTUAL_CLUSTER_STATE_ACTIVATED);
        return virtualCluster;
    }

    /**
     * fetch a list of VMs from the resource pool, then generate a virtual machine cluster.
     * add the virtual machine cluster to the VirtualCluster.xml
     * @param _clusterSize
     * @return
     */
    public VirtualCluster createCluster(int _clusterSize){
        VirtualCluster virtualCluster=new VirtualCluster();
        VirtualResourcePool virtualResourcePool=new VirtualResourcePool();
        ArrayList<VMInfo> virtualMachineList=virtualResourcePool.fetchVMList(_clusterSize);
        //TODO: use a method to generate a cluster based to the VMs list
        virtualCluster=this.generateCluster(virtualMachineList);
        xmlOperationCluster.addCluster(virtualCluster);
        return virtualCluster;
    }

    /**
     * Use the provided virtual machine list to generate a virtual cluster
     * @param VMList
     * @return
     */
    public VirtualCluster generateCluster(ArrayList<VMInfo> VMList){
        VirtualCluster virtualCluster=new VirtualCluster();
        if(VMList.size()>=1){
            String clusterID= Tool.genUUID();
            String clusterState=Constant.VIRTUAL_CLUSTER_STATE_STANDBY;
            int clusterSize=VMList.size();
            String clusterServerID=VMList.get(0).getVmID();
            ArrayList<String> nodesIDList=null;
            for(int i=1;i<VMList.size();i++){
                nodesIDList.add(VMList.get(i).getVmID());
            }
            virtualCluster.setClusterID(clusterID);
            virtualCluster.setClusterState(clusterState);
            virtualCluster.setClusterSize(clusterSize);
            virtualCluster.setClusterServerID(clusterServerID);
            virtualCluster.setNodesIDList(nodesIDList);
        }
        return virtualCluster;
    }
    public boolean addNodes(String _clusterID, ArrayList<String> _nodeIDList) {
        boolean success=false;
        success=xmlOperationCluster.addNodes(_clusterID,_nodeIDList);
        return success;
    }

    public boolean removeNodes(String _clusterID, ArrayList<String> _nodeIDList) {
        boolean success=false;
        success=xmlOperationCluster.removeNodes(_clusterID,_nodeIDList);
        return success;
    }

    public boolean modifyServerID(String _clusterID,String _serverID){
        boolean success=false;
        success=xmlOperationCluster.modifyServerID(_clusterID,_serverID);
        return success;
    }

    /**
     * Modify the state of the specified cluster which ID is _clusterID
     * @param _clusterID
     * @param _state
     * @return
     */
    public boolean modifyClusterState(String _clusterID, String _state){
        boolean success=false;
        xmlOperationCluster.modifyClusterState(_clusterID,_state);
        return success;
    }

    /**
     * get the size of a cluster, which is consist of the num of nodes and the server node
     * @param _clusterID
     * @return
     */
    public int getClusterSize(String _clusterID){
        int clusterSize=0;
        clusterSize=xmlOperationCluster.getClusterSize(_clusterID);
        return clusterSize;
    }

    public ArrayList<VirtualCluster> getAllClusters(){
        ArrayList<VirtualCluster> virtualClusters=new ArrayList<VirtualCluster>();
        virtualClusters=xmlOperationCluster.getAllClusters();
        return virtualClusters;
    }

    public ArrayList<VirtualCluster> getClustersWithState(String _state){
        ArrayList<VirtualCluster> virtualClusters=new ArrayList<VirtualCluster>();
        virtualClusters=xmlOperationCluster.getClustersWithState(_state);
        return virtualClusters;
    }

    public VirtualCluster getClusterWithID(String _clusterID){
        VirtualCluster virtualCluster=new VirtualCluster();
        virtualCluster=xmlOperationCluster.getClusterWithID(_clusterID);
        return virtualCluster;
    }

    /**
     * invoke the method in XMLOperationCluster to delete a cluster.
     * We also provide a method to delete a list of Clusters
     * @param _clusterID
     * @return
     */
    public boolean deleteCluster(String _clusterID){
        boolean success=xmlOperationCluster.deleteCluster(_clusterID);
        return success;
    }
}

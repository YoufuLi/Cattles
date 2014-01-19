package com.cattles.virtualClusterManagement.falkonCluster;

import com.cattles.virtualClusterManagement.interfaces.VirtualClusterOperationInterface;
import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.schedulingframeworks.falkon.FalkonClusterInitialization;
import com.cattles.schedulingframeworks.falkon.FalkonServer;
import com.cattles.schedulingframeworks.falkon.FalkonWorker;
import com.cattles.util.Constant;
import com.cattles.util.Tool;
import com.cattles.virtualClusterManagement.VirtualCluster;
import com.cattles.virtualClusterManagement.XMLOperationCluster;
import com.cattles.virtualMachineManagement.VMInfo;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonClusterOperationImpl implements VirtualClusterOperationInterface {
    //TODO:update the operations upon to cluster management
    private static Logger logger = Logger.getLogger(FalkonClusterOperationImpl.class);
    XMLOperationCluster xmlOperationCluster=XMLOperationCluster.getXmlOperationCluster();
    VirtualResourcePool virtualResourcePool=new VirtualResourcePool();
    /**
     * use getClustersWithState(Constant.VIRTUAL_CLUSTER_STATE_STANDBY) and getClustersWithType(Constant.FALKON_FRAMEWORK_NAME) to find the standby falkon cluster
     * @return
     */
    @Override
    public ArrayList<VirtualCluster> getStandbyCluster(){
        ArrayList<VirtualCluster> standbyFalkonClusterList=new ArrayList<VirtualCluster>();
        ArrayList<VirtualCluster> standbyClusterList=xmlOperationCluster.getClustersWithState(Constant.VIRTUAL_CLUSTER_STATE_STANDBY);
        ArrayList<VirtualCluster> falkonClusterList=xmlOperationCluster.getClustersWithType(Constant.FALKON_FRAMEWORK_NAME);
        for(int i=0;i<falkonClusterList.size();i++){
            for (int j=0;j<standbyClusterList.size();j++){
                if(falkonClusterList.get(i).getClusterID().equals(standbyClusterList.get(j).getClusterID())){
                    standbyFalkonClusterList.add(falkonClusterList.get(i));
                }
            }
        }
        return standbyFalkonClusterList;
    }

    /**
     * launch a falkon cluster, including falkon service start and falkon worker registration
     * @param virtualCluster
     */
    @Override
    public void launchCluster(VirtualCluster virtualCluster){
        String serverID=virtualCluster.getClusterServerID();
        ArrayList<String> nodeIDList=virtualCluster.getNodesIDList();
        FalkonClusterInitialization falkonClusterInitialization=new FalkonClusterInitialization(serverID,nodeIDList);
        falkonClusterInitialization.run();
    }

    /**
     * fetch a list of VMs from the resource pool, then generate a virtual machine cluster.
     * add the virtual machine cluster to the VirtualCluster.xml
     * @param _clusterSize
     * @return
     */
    public VirtualCluster createCluster(int _clusterSize){
        VirtualCluster virtualCluster=new VirtualCluster();
        ArrayList<VMInfo> virtualMachineList=virtualResourcePool.fetchVMList(_clusterSize);
        logger.info("fetched "+virtualMachineList.size()+" virtual machines");
        for (VMInfo vmInfo:virtualMachineList){
            logger.info("the vm ID is: "+vmInfo.getVmID());
        }
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
            ArrayList<String> nodesIDList=new ArrayList<String>();
            for(int i=1;i<VMList.size();i++){
                nodesIDList.add(VMList.get(i).getVmID());
            }
            virtualCluster.setClusterID(clusterID);
            virtualCluster.setClusterType(Constant.FALKON_FRAMEWORK_NAME);
            virtualCluster.setClusterState(clusterState);
            virtualCluster.setClusterSize(clusterSize);
            virtualCluster.setClusterServerID(clusterServerID);
            virtualCluster.setNodesIDList(nodesIDList);
        }
        else {
            logger.info("we did not get enough virtual machines to generate a cluster");
        }
        logger.info("the virtual cluster ID is :"+virtualCluster.getClusterID());
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

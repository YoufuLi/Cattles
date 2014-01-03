package com.cattles.vmClusterManagement.falkonCluster;

import com.cattles.interfaces.VirtualClusterOperationInterface;
import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.schedulingframeworks.falkon.FalkonServer;
import com.cattles.schedulingframeworks.falkon.FalkonWorker;
import com.cattles.util.Constant;
import com.cattles.util.Tool;
import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmClusterManagement.XMLOperationCluster;
import com.cattles.vmManagement.VMInfo;
import org.apache.log4j.Logger;

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
    private static Logger logger = Logger.getLogger(FalkonClusterOperationImpl.class);
    XMLOperationCluster xmlOperationCluster=XMLOperationCluster.getXmlOperationCluster();
    VirtualResourcePool virtualResourcePool=new VirtualResourcePool();
    FalkonWorker falkonWorker=new FalkonWorker();
    FalkonServer falkonServer=new FalkonServer();
    public VirtualCluster clusterProvision(int _clusterSize){
        //check if there are any standby cluster,
        VirtualCluster virtualCluster;
        ArrayList<VirtualCluster> standbyFalkonClusterList=this.getStandbyFalkonCluster();
        logger.info("get "+standbyFalkonClusterList.size()+" standby cluster, and choosing one appropriate from them.");
        if(standbyFalkonClusterList.size()>=1){
            //calculate the absolute value of standby cluster size and the required num, then select the smallest absolute value
            int minABS=Math.abs(standbyFalkonClusterList.get(0).getClusterSize()-_clusterSize);
            int flag=0;
            for(int i=1;i<standbyFalkonClusterList.size();i++){
                if(minABS>Math.abs(standbyFalkonClusterList.get(i).getClusterSize()-_clusterSize)){
                    minABS=Math.abs(standbyFalkonClusterList.get(i).getClusterSize()-_clusterSize);
                    flag=i;
                }
            }
            virtualCluster=standbyFalkonClusterList.get(flag);
            //check if the num of nodes equals to the required, if not add more nodes , or remove the redundant nodes
            if(virtualCluster.getClusterSize()!=_clusterSize){
                if(virtualCluster.getClusterSize()>_clusterSize){
                    int deregisterNum=virtualCluster.getClusterSize()-_clusterSize;
                    logger.info("the request cluster size is "+_clusterSize+", and the size of the standby cluster is "+virtualCluster.getClusterSize()
                            +". We need to deregister "+deregisterNum+" nodes from the cluster.");
                    //Deregister the node from the cluster
                    ArrayList<String> deregisterNodeIDList=new ArrayList<String>();
                    for(int j=0;j<deregisterNum;j++){
                        deregisterNodeIDList.add(virtualCluster.getNodesIDList().get(j));
                    }
                    logger.info("deregister nodes number:"+deregisterNodeIDList.size());
                    falkonWorker.deregisterFromServer(virtualCluster.getClusterServerID(),deregisterNodeIDList);
                    //Remove the redundant nodes

                    this.removeNodes(virtualCluster.getClusterID(),deregisterNodeIDList);
                }else{
                    //TODO: Fetch a list of VMs and add to the cluster, then do the node registration
                    int requestVMsNum=_clusterSize-virtualCluster.getClusterSize();
                    logger.info("fetching "+requestVMsNum+" nodes from the virtual resource pool");
                    ArrayList<VMInfo> virtualMachineList=virtualResourcePool.fetchVMList(requestVMsNum);
                    ArrayList<String> registerNodeIDList=new ArrayList<String>();
                    for(int j=0;j<virtualMachineList.size();j++){
                        registerNodeIDList.add(virtualMachineList.get(j).getVmID());
                    }
                    this.addNodes(virtualCluster.getClusterID(),registerNodeIDList);
                    falkonWorker.register2Server(virtualCluster.getClusterServerID(),registerNodeIDList);
                }
            }

        }else{
            virtualCluster=this.createCluster(_clusterSize);
            //TODO:the registeration of workers
            this.launchFalkonCluster(virtualCluster);
        }
        //update the cluster state from "standby" to "activated"
        xmlOperationCluster.modifyClusterState(virtualCluster.getClusterID(),Constant.VIRTUAL_CLUSTER_STATE_ACTIVATED);
        return virtualCluster;
    }

    /**
     * use getClustersWithState(Constant.VIRTUAL_CLUSTER_STATE_STANDBY) and getClustersWithType(Constant.FALKON_FRAMEWORK_NAME) to find the standby falkon cluster
     * @return
     */
    public ArrayList<VirtualCluster> getStandbyFalkonCluster(){
        ArrayList<VirtualCluster> standbyFalkonClusterList=new ArrayList<VirtualCluster>();
        ArrayList<VirtualCluster> standbyClusterList=xmlOperationCluster.getClustersWithState(Constant.VIRTUAL_CLUSTER_STATE_STANDBY);
        ArrayList<VirtualCluster> falkonClusterList=xmlOperationCluster.getClustersWithType(Constant.FALKON_FRAMEWORK_NAME);
        logger.info("standby:"+standbyClusterList.size());
        logger.info("falkon:"+falkonClusterList.size());
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
    public void launchFalkonCluster(VirtualCluster virtualCluster){
        String serverID=virtualCluster.getClusterServerID();
        ArrayList<String> nodeIDList=virtualCluster.getNodesIDList();
        falkonServer.startFalkonService(serverID);
        falkonWorker.register2Server(serverID,nodeIDList);
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

package virtualClusterProvision;

import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.schedulingframeworks.falkon.FalkonIServer;
import com.cattles.schedulingframeworks.falkon.FalkonIWorker;
import com.cattles.util.Constant;
import com.cattles.virtualClusterManagement.IVirtualClusterOperation;
import com.cattles.virtualClusterManagement.VirtualCluster;
import com.cattles.virtualClusterManagement.XMLOperationCluster;
import com.cattles.virtualClusterProvision.interfaces.IVirtualClusterProvisionBiz;
import com.cattles.virtualMachineManagement.VMInfo;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/15/14
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonClusterProvisionImpl implements IVirtualClusterProvisionBiz {
    private static Logger logger = Logger.getLogger(FalkonClusterProvisionImpl.class);
    XMLOperationCluster xmlOperationCluster=XMLOperationCluster.getXmlOperationCluster();
    VirtualResourcePool virtualResourcePool=new VirtualResourcePool();
    FalkonIWorker falkonWorker=new FalkonIWorker();
    FalkonIServer falkonServer=new FalkonIServer();
    IVirtualClusterOperation falkonClusterOperation=new IVirtualClusterOperation();
    /**
     * provision cluster to upper layer
     *
     * @param _clusterSize
     * @return
     */
    @Override
    public VirtualCluster clusterProvision(int _clusterSize){
        //check if there are any standby cluster,
        VirtualCluster virtualCluster;
        ArrayList<VirtualCluster> standbyFalkonClusterList=falkonClusterOperation.getStandbyCluster();
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
                    virtualResourcePool.deregisterVMs(deregisterNodeIDList);
                    //Remove the redundant nodes
                    falkonClusterOperation.removeNodes(virtualCluster.getClusterID(), deregisterNodeIDList);
                }else{
                    //TODO: Fetch a list of VMs and add to the cluster, then do the node registration
                    int requestVMsNum=_clusterSize-virtualCluster.getClusterSize();
                    logger.info("fetching "+requestVMsNum+" nodes from the virtual resource pool");
                    ArrayList<VMInfo> virtualMachineList=virtualResourcePool.fetchVMList(requestVMsNum);
                    ArrayList<String> registerNodeIDList=new ArrayList<String>();
                    for(int j=0;j<virtualMachineList.size();j++){
                        registerNodeIDList.add(virtualMachineList.get(j).getVmID());
                    }
                    falkonClusterOperation.addNodes(virtualCluster.getClusterID(), registerNodeIDList);
                    falkonWorker.register2Server(virtualCluster.getClusterServerID(),registerNodeIDList);
                }
            }

        }else{
            virtualCluster=falkonClusterOperation.createCluster(_clusterSize);
            falkonClusterOperation.launchCluster(virtualCluster);
        }
        //update the cluster state from "standby" to "activated"
        logger.info("modifying cluster state------------------------------------");
        xmlOperationCluster.modifyClusterState(virtualCluster.getClusterID(), Constant.VIRTUAL_CLUSTER_STATE_ACTIVATED);
        return virtualCluster;
    }

    /**
     * upper layer can invoke this method to release the idle cluster
     *
     * @param virtualCluster
     */
    @Override
    public void releaseCluster(VirtualCluster virtualCluster) {
        String clusterID=virtualCluster.getClusterID();
        falkonClusterOperation.modifyClusterState(clusterID,Constant.VIRTUAL_CLUSTER_STATE_STANDBY);
    }
}

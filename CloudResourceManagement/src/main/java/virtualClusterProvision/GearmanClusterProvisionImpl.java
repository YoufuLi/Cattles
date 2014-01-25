package virtualClusterProvision;

import com.cattles.resourcePoolManagement.VirtualResourcePool;
import com.cattles.virtualClusterManagement.VirtualCluster;
import com.cattles.virtualClusterManagement.XMLOperationCluster;
import com.cattles.virtualClusterProvision.interfaces.IVirtualClusterProvision;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/15/14
 * Time: 3:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class GearmanClusterProvisionImpl implements IVirtualClusterProvision {
    private static Logger logger = Logger.getLogger(GearmanClusterProvisionImpl.class);
    XMLOperationCluster xmlOperationCluster=XMLOperationCluster.getXmlOperationCluster();
    VirtualResourcePool virtualResourcePool=new VirtualResourcePool();
    /**
     * provision cluster to upper layer
     *
     * @param _clusterSize
     * @return
     */
    @Override
    public VirtualCluster clusterProvision(int _clusterSize){
        return null;
    }

    /**
     * upper layer can invoke this method to release the idle cluster
     *
     * @param virtualCluster
     */
    @Override
    public void releaseCluster(VirtualCluster virtualCluster) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}

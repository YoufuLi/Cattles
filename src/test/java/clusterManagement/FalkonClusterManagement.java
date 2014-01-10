package clusterManagement;

import com.cattles.schedulingframeworks.falkon.FalkonServer;
import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmClusterManagement.VirtualClusterOperation;
import com.cattles.vmClusterManagement.VirtualClusterOperationFactory;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/27/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonClusterManagement {
    private static Logger logger = Logger.getLogger(FalkonClusterManagement.class);
    public static void main(String[] args){
        logger.info("Begin to start cluster provision:***********"+System.currentTimeMillis()+"**************");
        VirtualClusterOperation virtualClusterOperation=new VirtualClusterOperation();
        VirtualCluster virtualCluster=virtualClusterOperation.clusterProvision(3);
        System.out.println(virtualCluster.getClusterID());
    }
}

package clusterManagement;

import com.cattles.virtualClusterManagement.VirtualCluster;
import com.cattles.virtualClusterProvision.interfaces.IVirtualClusterProvision;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonClusterManagement {
    private static Logger logger = Logger.getLogger(FalkonClusterManagement.class);
    public static void main(String[] args){
        logger.info("Begin to start cluster provision:***********"+System.currentTimeMillis()+"**************");
        IVirtualClusterProvision virtualClusterProvision=new com.cattles.virtualClusterProvision.IVirtualClusterProvision();
        VirtualCluster virtualCluster=virtualClusterProvision.clusterProvision(2);
        System.out.println(virtualCluster.getClusterID());
    }
}

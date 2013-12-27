package clusterManagement;

import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmClusterManagement.VirtualClusterOperation;
import com.cattles.vmClusterManagement.VirtualClusterOperationFactory;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/27/13
 * Time: 5:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class FalkonClusterManagement {
    public static void main(String args){
        VirtualClusterOperation virtualClusterOperation=new VirtualClusterOperation();
        VirtualCluster virtualCluster=virtualClusterOperation.clusterProvision(2);
        System.out.println(virtualCluster.getClusterID());
    }
}

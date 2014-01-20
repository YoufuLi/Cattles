package schedulingframeworks.interfaces;

import com.cattles.virtualClusterManagement.VirtualCluster;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/15/14
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ClusterInterface {
    /**
     * Initialize the cluster according to provided cluster
     * @param virtualCluster
     */
    public void initializeCluster(VirtualCluster virtualCluster);

    /**
     * terminate the cluster according to provided cluster
     * @param virtualCluster
     */
    public void terminateCluster(VirtualCluster virtualCluster);
}

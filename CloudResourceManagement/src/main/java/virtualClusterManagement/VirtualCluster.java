package virtualClusterManagement;

import java.util.ArrayList;

public class VirtualCluster {
	String clusterID; //the id of a cluster
	String clusterType; // identify whether the cluster is a "falkon" cluster, "gearman" cluster or the other cluster
    String clusterState;  //if the cluster is in "standby" state or in "activated" state
    int clusterSize;   //cluster size, the number of worker
	String clusterServerID;    //the server ID in this cluster
    ArrayList<String> nodesIDList;

    public String getClusterID() {
        return clusterID;
    }

    public void setClusterID(String clusterID) {
        this.clusterID = clusterID;
    }

    public String getClusterType() {
        return clusterType;
    }

    public void setClusterType(String clusterType) {
        this.clusterType = clusterType;
    }

    public String getClusterState() {
        return clusterState;
    }

    public void setClusterState(String clusterState) {
        this.clusterState = clusterState;
    }

    public int getClusterSize() {
        return clusterSize;
    }

    public void setClusterSize(int clusterSize) {
        this.clusterSize = clusterSize;
    }

    public String getClusterServerID() {
        return clusterServerID;
    }

    public void setClusterServerID(String clusterServerID) {
        this.clusterServerID = clusterServerID;
    }

    public ArrayList<String> getNodesIDList() {
        return nodesIDList;
    }

    public void setNodesIDList(ArrayList<String> nodesIDList) {
        this.nodesIDList = nodesIDList;
    }
}

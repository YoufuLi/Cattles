package com.cattles.resourcePoolManagement;

import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmManagement.VMInfo;

import java.util.LinkedList;
import java.util.List;

public class ResourcePool {
    private static ResourcePool resourcePool = null;
    public List<VMInfo> VMResourceList= new LinkedList<VMInfo>();
    public List<VirtualCluster> VMClusterList=new LinkedList<VirtualCluster>();
    private ResourcePool(){

    }
    public static synchronized ResourcePool getResourcePool(){
        if (resourcePool==null){
            resourcePool=new ResourcePool();
        }
        return resourcePool;
    }
    public List<VMInfo> getVMResourceList() {
        return VMResourceList;
    }

    public void setVMResourceList(List<VMInfo> VMResourceList) {
        this.VMResourceList = VMResourceList;
    }

    public List<VirtualCluster> getVMClusterList() {
        return VMClusterList;
    }

    public void setVMClusterList(List<VirtualCluster> VMClusterList) {
        this.VMClusterList = VMClusterList;
    }
    public void checkRemainVMs() {

    }
    public void requestVMs() {

    }
    public void addVMResource(){

    }
    public void addCluster(){

    }
}

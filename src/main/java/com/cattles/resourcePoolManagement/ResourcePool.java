package com.cattles.resourcePoolManagement;

import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmManagement.VMInfo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ResourcePool {
    private static ResourcePool resourcePool = null;
    private ResourcePool(){

    }
    public static synchronized ResourcePool getResourcePool(){
        if (resourcePool==null){
            resourcePool=new ResourcePool();
        }
        return resourcePool;
    }
    public ArrayList<VMInfo> getVMResourceList() {
        ArrayList<VMInfo> VMResourceList=new ArrayList<VMInfo>();
        return VMResourceList;
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

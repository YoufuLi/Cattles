package com.cattles.resourcePoolManagement;

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

}

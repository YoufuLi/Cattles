package com.cattles.util;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/18/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlatformConfiguration {
    private static PlatformConfiguration platformConfiguration;
    private PlatformConfiguration(){}
    public static synchronized PlatformConfiguration getPlatformConfiguration(){
        if(platformConfiguration==null){
            platformConfiguration=new PlatformConfiguration();
        }
        return platformConfiguration;
    }
    public String getPlatformName(){
        String platformName="EC2";
        return platformName;
    }
}

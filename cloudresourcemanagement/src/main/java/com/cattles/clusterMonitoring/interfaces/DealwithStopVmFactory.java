package com.cattles.clusterMonitoring.interfaces;

import com.cattles.interfaces.DealwithStopVm;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/16/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class DealwithStopVmFactory {
    public static DealwithStopVm getInstance(String s){
        DealwithStopVm a= new DealwithStopVm() {
            @Override
            public int stopVmType(String vmIp) throws Exception {
                return 0;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean dealwithSotpServer(String serviceIp) throws Exception {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean dealwithStopWorker(String workerIp) throws Exception {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        return a;
    }
}

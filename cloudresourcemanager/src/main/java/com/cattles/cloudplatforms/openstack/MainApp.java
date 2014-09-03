package com.cattles.cloudplatforms.openstack;

import com.cattles.virtualMachineManagement.VirtualMachineInformation;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 9/3/14
 * Time: 4:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class MainApp {
    public static void main(String[] args){
        OpenStackVMOperationImpl openStackVMOperation=new OpenStackVMOperationImpl();
        JCloudsNova jcloudsNova = new JCloudsNova();
        try {
            /*System.out.println();
            System.out.println("******************************Servers***************************************");
            System.out.println();
            jcloudsNova.listServers();
            System.out.println();
            System.out.println("******************************Images***************************************");
            System.out.println();
            jcloudsNova.listImages();
            System.out.println();
            System.out.println("******************************Flavors***************************************");
            System.out.println();
            jcloudsNova.listFlavors();*/


            //Thread.sleep(5000);
            //openStackVMOperation.createInstances(1);
            ArrayList<VirtualMachineInformation> vmList=new ArrayList<VirtualMachineInformation>();
            VirtualMachineInformation vmInfo=new VirtualMachineInformation();
            vmInfo.setVmID("5f377b61-a16f-4099-88b2-26eecd829bd1");
            VirtualMachineInformation vmInfo1=new VirtualMachineInformation();
            vmInfo1.setVmID("0685a5bd-07e5-42b0-a28a-2719dfdca4ef");
            vmList.add(vmInfo);
            vmList.add(vmInfo1);
            openStackVMOperation.shutdownInstances(vmList);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

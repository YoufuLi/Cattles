package com.cattles.cloudplatforms.openstack;

import com.cattles.cloudplatforms.interfaces.IVirtualMachineOperation;
import com.cattles.util.Constant;
import com.cattles.virtualMachineManagement.VirtualMachineInformation;
import com.google.common.io.Closeables;
import org.apache.log4j.Logger;
import org.jclouds.compute.ComputeService;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.RebootType;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 8/24/14
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenStackVMOperationImpl implements IVirtualMachineOperation {
    private static Logger logger = Logger.getLogger(OpenStackVMOperationImpl.class);

    public OpenStackConfigOperation openStackConfigOperation=new OpenStackConfigOperation();
    private final NovaApi novaApi=openStackConfigOperation.initNovaApi();
    private final String zone="RegionOne";
    public ComputeService computeService=openStackConfigOperation.initComputeService();

    @Override
    public ArrayList<VirtualMachineInformation> createInstances(int vmNumber) throws Exception {
        ArrayList<VirtualMachineInformation> instanceList = new ArrayList<VirtualMachineInformation>();
        ServerApi serverApi= novaApi.getServerApiForZone(zone);
        CreateServerOptions options = new CreateServerOptions().availabilityZone("nova").networks("613f9b74-1fc9-460b-ae15-b6cef678a249");
        for(int i=0;i< vmNumber;i++){
            logger.info("here");
            ServerCreated serverCreated= serverApi.create("test01","b72e4c21-deb0-43e3-b010-9d354566b35f","2",options);
            System.out.println("#######################################lalal#########################################");
            System.out.print("server name:"+serverCreated.getName());
            Server serverDetails=serverApi.get(serverCreated.getId());
            VirtualMachineInformation virtualMachineInformation=new VirtualMachineInformation();
            virtualMachineInformation.setVmID(serverDetails.getId());
            virtualMachineInformation.setVmState(Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);
            virtualMachineInformation.setVmHostname(serverDetails.getName());
            virtualMachineInformation.setVmPort("");
            virtualMachineInformation.setVmKeyName(serverDetails.getKeyName());
            virtualMachineInformation.setVmPrivateIpAddress(serverDetails.getAccessIPv4());
            virtualMachineInformation.setVmPublicIpAddress(serverDetails.getAccessIPv4());
            virtualMachineInformation.setVmType(serverDetails.getFlavor().getName());

            //the information above is not correct, need to be adjusted
            instanceList.add(virtualMachineInformation);
        }
        Closeables.close(novaApi, true);
        return instanceList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualMachineInformation launchInstance(VirtualMachineInformation _VMInfo) throws Exception {
        VirtualMachineInformation virtualMachineInformation=new VirtualMachineInformation();
        ServerApi serverApi= novaApi.getServerApiForZone(zone);
        serverApi.start(_VMInfo.getVmID());
        return virtualMachineInformation;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualMachineInformation> launchInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shutdownInstance(VirtualMachineInformation _VMInfo) throws Exception {
        ServerApi serverApi= novaApi.getServerApiForZone(zone);
        serverApi.stop(_VMInfo.getVmID());
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shutdownInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        ServerApi serverApi= novaApi.getServerApiForZone(zone);
        for (VirtualMachineInformation vmInfo:vmList){
            serverApi.stop(vmInfo.getVmID());
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualMachineInformation rebootInstance(VirtualMachineInformation _VMInfo) throws Exception {
        ServerApi serverApi= novaApi.getServerApiForZone(zone);
        serverApi.reboot(_VMInfo.getVmID(), RebootType.SOFT);
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualMachineInformation> rebootInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        ServerApi serverApi= novaApi.getServerApiForZone(zone);
        for (VirtualMachineInformation vmInfo:vmList){
            serverApi.reboot(vmInfo.getVmID(),RebootType.SOFT);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean destroyInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        ServerApi serverApi= novaApi.getServerApiForZone(zone);
        for (VirtualMachineInformation vmInfo:vmList){
            serverApi.delete(vmInfo.getVmID());
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Set<String> getZones(){
        Set<String> zones=novaApi.getConfiguredZones();
        return  zones;
    }
    public static void main(String[] args){
        OpenStackVMOperationImpl openStackVMOperation=new OpenStackVMOperationImpl();
        /*VirtualMachineInformation virtualMachineInformation=new VirtualMachineInformation();
        virtualMachineInformation.setVmID("605855cf-46dd-4c14-8608-8bf5ac9cea73");
        virtualMachineInformation.setVmHostname("test");
        virtualMachineInformation.setVmPrivateIpAddress("192.168.100.10");*/
        try {
            openStackVMOperation.createInstances(1);
            //openStackVMOperation.shutdownInstance(virtualMachineInformation);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

package com.cattles.cloudplatforms.openstack;

import com.cattles.cloudplatforms.interfaces.IVirtualMachineOperation;
import com.cattles.util.Constant;
import com.cattles.virtualMachineManagement.VirtualMachineInformation;
import com.google.common.io.Closeables;
import org.apache.log4j.Logger;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.openstack.nova.v2_0.NovaApi;

import org.jclouds.openstack.nova.v2_0.compute.options.NovaTemplateOptions;
import org.jclouds.openstack.nova.v2_0.domain.ServerCreated;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;
import org.jclouds.openstack.nova.v2_0.options.CreateServerOptions;


import java.io.Closeable;
import java.io.IOException;
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
    private final Set<String> zones=novaApi.getConfiguredZones();
    public ComputeService computeService=openStackConfigOperation.initComputeService();

    @Override
    public ArrayList<VirtualMachineInformation> createInstances(int vmNumber) throws Exception {
        ArrayList<VirtualMachineInformation> instanceList = new ArrayList<VirtualMachineInformation>();
        //Template template=computeService.templateBuilder().imageId("a3cb063c-3c08-4020-8c06-07ac48a32a5e").build();
        Template template=computeService.templateBuilder().build();
        template.getOptions().as(NovaTemplateOptions.class).securityGroupNames();
        //ServerApi serverApi= novaApi.getServerApiForZone("GrizzlyDemo");
        //CreateServerOptions options = new CreateServerOptions().availabilityZone("GrizzlyDemo");

        //ServerCreated serverCreated= serverApi.create("");
        Set<? extends NodeMetadata> nodes=computeService.createNodesInGroup("default",vmNumber,template);

        for(NodeMetadata node:nodes){
            VirtualMachineInformation virtualMachineInformation=new VirtualMachineInformation();
            virtualMachineInformation.setVmID(node.getId());
            virtualMachineInformation.setVmState(Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);
            virtualMachineInformation.setVmHostname(node.getHostname());
            virtualMachineInformation.setVmPort(String.valueOf(node.getLoginPort()));
            virtualMachineInformation.setVmPrivateIpAddress(node.getPrivateAddresses().toString());
            virtualMachineInformation.setVmPublicIpAddress(node.getPublicAddresses().toString());
            virtualMachineInformation.setVmType(node.getType().toString());

            //the information above is not correct, need to be adjusted
            instanceList.add(virtualMachineInformation);
        }
        Closeables.close(novaApi,true);
        return instanceList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualMachineInformation launchInstance(VirtualMachineInformation _VMInfo) throws Exception {
        VirtualMachineInformation virtualMachineInformation=new VirtualMachineInformation();

        return virtualMachineInformation;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualMachineInformation> launchInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shutdownInstance(VirtualMachineInformation _VMInfo) throws Exception {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shutdownInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualMachineInformation rebootInstance(VirtualMachineInformation _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualMachineInformation> rebootInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean destroyInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public static void main(String[] args){
        OpenStackVMOperationImpl openStackVMOperation=new OpenStackVMOperationImpl();
        try {
            openStackVMOperation.createInstances(1);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

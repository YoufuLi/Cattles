package com.cattles.cloudplatforms.amazonec2;

import com.cattles.cloudplatforms.interfaces.IVirtualMachineOperation;
import com.cattles.cloudplatforms.openstack.OpenStackConfigOperation;
import com.cattles.util.Constant;
import com.cattles.virtualMachineManagement.VirtualMachineInformation;
import org.apache.log4j.Logger;
import org.openstack.keystone.KeystoneClient;
import org.openstack.keystone.api.Authenticate;
import org.openstack.keystone.api.ListTenants;
import org.openstack.keystone.model.Access;
import org.openstack.keystone.model.Tenants;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.FlavorsCore;
import org.openstack.nova.api.ImagesCore;
import org.openstack.nova.api.ServersCore;
import org.openstack.nova.api.extensions.KeyPairsExtension;
import org.openstack.nova.api.extensions.StartStopServerExtension;
import org.openstack.nova.model.*;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 8/24/14
 * Time: 11:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenStackVMOperationImpl_backup implements IVirtualMachineOperation {
    private static Logger logger = Logger.getLogger(OpenStackVMOperationImpl_backup.class);

    KeystoneClient keystone = new KeystoneClient(OpenStackConfigOperation.KEYSTONE_AUTH_URL);
    //access with unscoped token
    Access access = keystone.execute(Authenticate.withPasswordCredentials(OpenStackConfigOperation.KEYSTONE_USERNAME, OpenStackConfigOperation.KEYSTONE_PASSWORD));

    Tenants tenants = keystone.execute(new ListTenants());

    @Override
    public ArrayList<VirtualMachineInformation> createInstances(int vmNumber) throws Exception {
        ArrayList<VirtualMachineInformation> instanceList = new ArrayList<VirtualMachineInformation>();

        for (int i=0;i<vmNumber;i++){
            VirtualMachineInformation vmInstance=new VirtualMachineInformation();


            //use the token in the following requests
            keystone.setToken(access.getToken().getId());

            if(tenants.getList().size() > 0) {

                access = keystone.execute(Authenticate.withToken(access.getToken().getId()).withTenantId(tenants.getList().get(0).getId()));

                //NovaClient novaClient = new NovaClient(KeystoneUtils.findEndpointURL(access.getServiceCatalog(), "compute", null, "public"), access.getToken().getId());
                NovaClient novaClient = new NovaClient(OpenStackConfigOperation.NOVA_ENDPOINT.concat(tenants.getList().get(0).getId()), access.getToken().getId());
                //novaClient.enableLogging(java.util.logging.Logger.getLogger("nova"), 100 * 1024);

                KeyPairs keysPairs = novaClient.execute(KeyPairsExtension.listKeyPairs());

                Images images = novaClient.execute(ImagesCore.listImages());

                Flavors flavors = novaClient.execute(FlavorsCore.listFlavors());

                ServerForCreate serverForCreate = new ServerForCreate();
                serverForCreate.setName("nicholas");

                serverForCreate.setFlavorRef(flavors.getList().get(0).getId());
                serverForCreate.setImageRef(images.getList().get(1).getId());
                serverForCreate.setKeyName(keysPairs.getList().get(0).getName());
                serverForCreate.getSecurityGroups().add(new ServerForCreate.SecurityGroup("default"));
                //serverForCreate.getSecurityGroups().add(new ServerForCreate.SecurityGroup(securityGroup.getName()));

                Server server = novaClient.execute(ServersCore.createServer(serverForCreate));
                System.out.println(server);


                vmInstance.setVmID(server.getId());
                vmInstance.setVmState(Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);
                vmInstance.setVmType("");
                vmInstance.setVmPrivateIpAddress(server.getAccessIPv6());
                vmInstance.setVmPublicIpAddress(server.getAccessIPv4());
                vmInstance.setVmKeyName(server.getKeyName());
                instanceList.add(vmInstance);
                logger.info("here here");

            } else {
                System.out.println("No tenants found!");
            }
        }
        return instanceList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualMachineInformation launchInstance(VirtualMachineInformation _VMInfo) throws Exception {
        VirtualMachineInformation virtualMachineInformation=new VirtualMachineInformation();
        NovaClient novaClient = new NovaClient(OpenStackConfigOperation.NOVA_ENDPOINT.concat(tenants.getList().get(0).getId()), access.getToken().getId());
        StartStopServerExtension startStopServerExtension=new StartStopServerExtension();
        novaClient.execute(startStopServerExtension.start(_VMInfo.getVmID()));
        return virtualMachineInformation;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualMachineInformation> launchInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shutdownInstance(VirtualMachineInformation _VMInfo) throws Exception {
        NovaClient novaClient = new NovaClient(OpenStackConfigOperation.NOVA_ENDPOINT.concat(tenants.getList().get(0).getId()), access.getToken().getId());
        StartStopServerExtension startStopServerExtension=new StartStopServerExtension();
        novaClient.execute(startStopServerExtension.stop(_VMInfo.getVmID()));
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shutdownInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public VirtualMachineInformation rebootInstance(VirtualMachineInformation _VMInfo) throws Exception {
        NovaClient novaClient = new NovaClient(OpenStackConfigOperation.NOVA_ENDPOINT.concat(tenants.getList().get(0).getId()), access.getToken().getId());
        novaClient.execute(ServersCore.reboot(_VMInfo.getVmID(),"reboot"));
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ArrayList<VirtualMachineInformation> rebootInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean destroyInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        NovaClient novaClient = new NovaClient(OpenStackConfigOperation.NOVA_ENDPOINT.concat(tenants.getList().get(0).getId()), access.getToken().getId());
        for (VirtualMachineInformation vmInfo : vmList) {
            novaClient.execute(ServersCore.deleteServer(vmInfo.getVmID()));
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
    public static void main(String[] args){
        OpenStackVMOperationImpl_backup openStackVMOperation=new OpenStackVMOperationImpl_backup();
        try {
            openStackVMOperation.createInstances(1);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}

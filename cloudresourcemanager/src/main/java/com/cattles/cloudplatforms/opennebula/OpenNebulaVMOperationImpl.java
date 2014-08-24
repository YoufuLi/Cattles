package com.cattles.cloudplatforms.opennebula;

import com.cattles.cloudplatforms.interfaces.IVirtualMachineOperation;
import com.cattles.exception.OpennebulaException;
import com.cattles.util.Constant;
import com.cattles.virtualMachineManagement.VirtualMachineInformation;
import org.apache.log4j.Logger;
import org.opennebula.client.Client;
import org.opennebula.client.OneResponse;
import org.opennebula.client.host.Host;
import org.opennebula.client.host.HostPool;
import org.opennebula.client.vm.VirtualMachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class OpenNebulaVMOperationImpl implements IVirtualMachineOperation {
    private static Logger logger = Logger.getLogger(OpenNebulaVMOperationImpl.class);
    OpenNebulaConfigOperation opennebulaConfig = new OpenNebulaConfigOperation();
    // Initialize variables.

    // Create the AmazonEC2Client object so we can call various APIs.
    Client opennebulaClient = opennebulaConfig.initOpenNebulaClient();

    /**
     * Used to create certain number of VMs.
     *
     * @param vmNumber
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VirtualMachineInformation> createInstances(int vmNumber) throws Exception {
        ArrayList<VirtualMachineInformation> instanceList = new ArrayList<VirtualMachineInformation>();
        String vmTemplate =
                "NAME     = vm_from_java    CPU = 0.1    MEMORY = 64\n"
                        + "DISK     = [\n"
                        + "\tsource   = \"/root/images/ttylinux.img\",\n"
                        + "\ttarget   = \"hda\",\n"
                        + "\treadonly = \"no\" ]\n"
                        + "# NIC     = [ NETWORK = \"Non existing network\" ]\n"
                        + "FEATURES = [ acpi=\"no\" ]";

        // 检查系统中是否有足够的资源，返回hostid
        int cpu=1;
        int memory=512;
        List<String> hostIdList = OpenNebulaVMOperationImpl.checkSystemResource(opennebulaClient,
                cpu, memory, vmNumber);
        if (hostIdList == null) {
            throw new OpennebulaException(
                    vmNumber + " Vm have not create Finsh!resource not enough,please Add host!");
        }
        logger.info("System have Resource enough,we begin create vm!");
        // We can create a representation for the new VM, using the returned
        // VM-ID
        OneResponse oneResponse = null;
        for (String hostId:hostIdList) {
            oneResponse = VirtualMachine.allocate(opennebulaClient, vmTemplate);


            if (oneResponse.isError()) {
                throw new OpennebulaException(oneResponse.getErrorMessage());
            }
            VirtualMachineInformation vmInstance = new VirtualMachineInformation();
            // The response message is the new VM's ID
            vmInstance.setVmID(oneResponse.getMessage());


            logger.info("ok, ID " + vmInstance.getVmID() + ".");
            VirtualMachine vm = new VirtualMachine(Integer.parseInt(vmInstance.getVmID()), opennebulaClient);
            logger.info("Trying to deploy the new VM... ");
            oneResponse = vm.hold();
            oneResponse = vm.info();
            if (oneResponse.isError()) {
                throw new OpennebulaException(oneResponse.getErrorMessage());

            }
            vmInstance.setVmState(Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);

            //vmInstance.setVmType(instance.getInstanceType());
            //vmInstance.setVmPrivateIpAddress(instance.getPrivateIpAddress());
            //vmInstance.setVmPublicIpAddress(instance.getPublicIpAddress());
            //vmInstance.setVmKeyName(instance.getKeyName());
            instanceList.add(vmInstance);
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to launch one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    @Override
    public VirtualMachineInformation launchInstance(VirtualMachineInformation _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to launch a list of instances
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VirtualMachineInformation> launchInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        ArrayList<VirtualMachineInformation> instanceList = new ArrayList<VirtualMachineInformation>();
        for (VirtualMachineInformation vmInfo : vmList) {
            VirtualMachine vm = new VirtualMachine(Integer.parseInt(vmInfo.getVmID()), opennebulaClient);
            OneResponse or = vm.deploy(Integer.parseInt(vmInfo.getVmID()));
            if (or.isError()) {
                throw new OpennebulaException(or.getErrorMessage());
            }
        }
        return instanceList;
    }

    /**
     * used to shutdown one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean shutdownInstance(VirtualMachineInformation _VMInfo) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to shutdown a list of instances
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public boolean shutdownInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        for (VirtualMachineInformation vmInfo : vmList) {
            VirtualMachine vm = new VirtualMachine(Integer.parseInt(vmInfo.getVmID()), opennebulaClient);
            OneResponse or = vm.shutdown();
            if (or.isError()) {
                throw new OpennebulaException(or.getErrorMessage());
            }
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to reboot one instance
     *
     * @param _VMInfo
     * @throws Exception
     */
    @Override
    public VirtualMachineInformation rebootInstance(VirtualMachineInformation _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to reboot a list of instances
     *
     * @param vmList
     * @throws Exception
     */
    @Override
    public ArrayList<VirtualMachineInformation> rebootInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        ArrayList<VirtualMachineInformation> instanceList = new ArrayList<VirtualMachineInformation>();
        for (VirtualMachineInformation vmInfo : vmList) {
            VirtualMachine vm = new VirtualMachine(Integer.parseInt(vmInfo.getVmID()), opennebulaClient);
            OneResponse or = vm.restart();
            if (or.isError()) {
                throw new OpennebulaException(or.getErrorMessage());
            }
        }
        return instanceList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Used to destroy vms according to the vmList
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public boolean destroyInstances(ArrayList<VirtualMachineInformation> vmList) throws Exception {
        for (VirtualMachineInformation vmInfo : vmList) {
            VirtualMachine vm = new VirtualMachine(Integer.parseInt(vmInfo.getVmID()), opennebulaClient);
            OneResponse or = vm.finalizeVM();
            if (or.isError()) {
                throw new OpennebulaException(or.getErrorMessage());
            }
        }
        return true;
    }

    public static List<String> checkSystemResource(Client oneClient, int cpu,
                                                   int memory, int vmNum) throws OpennebulaException {
        // 保存有开启vm，有足够资源host的id
        List<String> idList = new ArrayList<String>();
        HostPool hp = new HostPool(oneClient);
        OneResponse or = hp.info();
        if (or.isError()) {
            throw new OpennebulaException(or.getErrorMessage());
        }

        // 系统中可以开启vm数目，用来检查是否可以有开启请求数目vm的资源
        int idNum = 0;
        for (Host host : hp) {
            String id = host.getId();
            int max_cpu = Integer.parseInt(host.xpath("HOST_SHARE/MAX_CPU"));
            int max_mem = Integer.parseInt(host.xpath("HOST_SHARE/MAX_MEM"));
            int cpu_usage = Integer
                    .parseInt(host.xpath("HOST_SHARE/CPU_USAGE"));
            int mem_usage = Integer
                    .parseInt(host.xpath("HOST_SHARE/MEM_USAGE"));
            logger.info("host " + id + " max cpu is" + max_cpu);
            logger.info("vm create request cpu is " + cpu);
            logger.info("host used cpu is " + cpu_usage);
            logger.info("host max memory is " + max_mem);
            logger.info("vm create request memory is " + memory);
            logger.info("host used memory is " + mem_usage);
            int cpuNum = (max_cpu - cpu_usage) / cpu;
            int memNum = (max_mem - mem_usage) / memory;
            int num = Math.min(cpuNum, memNum);
            for (int i = 0; i < num; i++) {
                idList.add(id);
                idNum++;
                if (idNum == vmNum) {
                    break;
                }
            }

            if (idNum == vmNum) {
                break;
            }
        }

        if (idNum < vmNum) {
            return null;
        }
        return idList;
    }
}

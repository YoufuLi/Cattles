package com.cattles.cloudplatforms.amazonec2;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;
import com.cattles.cloudplatforms.interfaces.VMInformationMaintainInterface;
import com.cattles.util.Constant;
import com.cattles.virtualMachineManagement.VMInfo;
import org.apache.log4j.Logger;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/4/14
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class VMInformationMaintainImpl implements VMInformationMaintainInterface {
    private static org.apache.log4j.Logger logger = Logger.getLogger(VMInformationMaintainImpl.class);
    EC2ConfigOperation ec2Config = new EC2ConfigOperation();
    // Initialize variables.

    // Create the AmazonEC2Client object so we can call various APIs.
    AmazonEC2 ec2 = ec2Config.initAmazonEC2();

    public ArrayList<VMInfo> getInstanceList(){
        ArrayList<VMInfo> vmInfoArrayList=new ArrayList<VMInfo>();
        try
        {
            DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
            java.util.List<Reservation> reservations = describeInstancesResult.getReservations();
            logger.info("You have " + reservations.size() + " reservations.");
            for (Reservation reservation : reservations)
            {
                logger.info(" " + reservation.getReservationId());
                java.util.List<Instance> instances = reservation.getInstances();
                logger.info("You have " + instances.size() + " instances.");
                for (Instance instance : instances)
                {
                    VMInfo vmInfo=new VMInfo();
                    vmInfo.setVmID(instance.getInstanceId());
                    //TODO:the virtual machine state should not be set here,update in the next version.
                    vmInfo.setVmState(Constant.VIRTUAL_MACHINES_STATE_AVAILABLE);
                    vmInfo.setVmType(instance.getInstanceType());
                    vmInfo.setVmPrivateIpAddress(instance.getPrivateIpAddress());
                    vmInfo.setVmPublicIpAddress(instance.getPublicIpAddress());
                    vmInfo.setVmKeyName(instance.getKeyName());
                    vmInfoArrayList.add(vmInfo);
                    /*System.out.println(instance.getInstanceId());
                    System.out.println(instance.getInstanceType());
                    System.out.println(instance.getPrivateIpAddress());
                    System.out.println(instance.getPublicIpAddress());
                    System.out.println(instance.getKeyName());
                    //System.out.println(instance.getState());
                    //System.out.println(" " + instance.getInstanceId() + " " + instance.getInstanceType() + " " + instance.getPrivateIpAddress() + " " + instance.getPublicIpAddress() + " key: " + instance.getKeyName() + " " + instance.getState().getName());
                    */
                }
            }
        } catch (AmazonServiceException ase)
        {
            logger.info("Caught Exception: " + ase.getMessage());
        }
        return vmInfoArrayList;
    }
    /**
     * use cloud platform API to get the last VM information.
     *
     * @param vmID
     * @return
     */
    @Override
    public VMInfo queryVMInformation(String vmID) {
        VMInfo vmInfo=new VMInfo();
        try
        {
            DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
            java.util.List<Reservation> reservations = describeInstancesResult.getReservations();
            for (Reservation reservation : reservations)
            {
                java.util.List<Instance> instances = reservation.getInstances();
                for (Instance instance : instances)
                {
                    if (instance.getInstanceId().equalsIgnoreCase(vmID)){
                        vmInfo.setVmID(instance.getInstanceId());
                        vmInfo.setVmType(instance.getInstanceType());
                        vmInfo.setVmPrivateIpAddress(instance.getPrivateIpAddress());
                        vmInfo.setVmPublicIpAddress(instance.getPublicIpAddress());
                        vmInfo.setVmKeyName(instance.getKeyName());
                    }
                }
            }
        } catch (AmazonServiceException ase)
        {
            logger.info("Caught Exception: " + ase.getMessage());
        }
        return vmInfo;
    }
}

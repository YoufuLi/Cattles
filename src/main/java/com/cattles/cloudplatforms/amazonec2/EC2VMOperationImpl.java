package com.cattles.cloudplatforms.amazonec2;

import java.util.ArrayList;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.*;
import com.cattles.interfaces.VMOperationInterface;
import com.cattles.vmManagement.VMInfo;

public class EC2VMOperationImpl implements VMOperationInterface {

    EC2ConfigOperation ec2Config = new EC2ConfigOperation();
    // Initialize variables.

    // Create the AmazonEC2Client object so we can call various APIs.
    AmazonEC2 ec2 = ec2Config.initAmazonEC2();
    public void getInstanceList(){
        try
        {
            DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
            java.util.List<Reservation> reservations = describeInstancesResult.getReservations();
            System.out.println("You have " + reservations.size() + " reservations.");
            for (Reservation reservation : reservations)
            {
                System.out.println(" " + reservation.getReservationId());
                java.util.List<Instance> instances = reservation.getInstances();
                System.out.println("You have " + instances.size() + " instances.");
                for (Instance instance : instances)
                {
                    System.out.println(instance.getInstanceId());
                    System.out.println(instance.getInstanceType());
                    System.out.println(instance.getPrivateIpAddress());
                    System.out.println(instance.getPublicIpAddress());
                    System.out.println(instance.getKeyName());
                    //System.out.println(instance.getState());
                    //System.out.println(" " + instance.getInstanceId() + " " + instance.getInstanceType() + " " + instance.getPrivateIpAddress() + " " + instance.getPublicIpAddress() + " key: " + instance.getKeyName() + " " + instance.getState().getName());
                }
            }
        } catch (AmazonServiceException ase)
        {
            System.out.println("Caught Exception: " + ase.getMessage());
        }
    }

    /**
     * Used to create certain number of VMs.
     *
     * @param vmNumber
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VMInfo> createInstances(int vmNumber) throws Exception {
        ArrayList<VMInfo> instanceList = new ArrayList<VMInfo>();
        try
        {
            RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                    .withInstanceType("m1.small")
                    .withImageId("emi-1C8C3ADF")
                    .withMinCount(1)
                    .withMaxCount(vmNumber)
                    .withKeyName("nicholas-key")
                    ;
            RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);
            Reservation reservation = runInstances.getReservation();
            System.out.println(" " + reservation.getReservationId());
            java.util.List<Instance> instances = reservation.getInstances();
            System.out.println("You have " + instances.size() + " instances.");
            for (Instance instance : instances)
            {
                VMInfo vmInstance=new VMInfo();
                vmInstance.setVmID(instance.getInstanceId());
                vmInstance.setVmType(instance.getInstanceType());
                vmInstance.setVmPrivateIpAddress(instance.getPrivateIpAddress());
                vmInstance.setVmPublicIpAddress(instance.getPublicIpAddress());
                vmInstance.setVmKeyName(instance.getKeyName());
                instanceList.add(vmInstance);
                //System.out.println(" " + instance.getInstanceId() + " " + instance.getInstanceType() + " " + instance.getPrivateIpAddress() + " " + instance.getPublicIpAddress() + " key: " + instance.getKeyName() + " " + instance.getState().getName());
            }
        } catch (AmazonServiceException ase)
        {
            System.out.println("Caught Exception: " + ase.getMessage());
        }
        return instanceList;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to launch one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    @Override
    public VMInfo launchInstance(VMInfo _VMInfo) throws Exception {

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
    public ArrayList<VMInfo> launchInstances(ArrayList<VMInfo> vmList) throws Exception {
        ArrayList<VMInfo> instanceList = new ArrayList<VMInfo>();
        VMInfo vmInstance=new VMInfo();
        // TODO Auto-generated method stub
        //============================================================================================//
        //=================================== Terminating any Instances ==============================//
        //============================================================================================//
        try {
            // Terminate instances.
            ArrayList<String> instanceIds = new ArrayList<String>();
            for(VMInfo vmInfo: vmList){
                instanceIds.add(vmInfo.getVmID());
            }
            StartInstancesRequest startRequest = new StartInstancesRequest().withInstanceIds(instanceIds);
            StartInstancesResult startInstancesResult=ec2.startInstances(startRequest);
        } catch (AmazonServiceException e) {
            // Write out any exceptions that may have occurred.
            System.out.println("Error starting instances");
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Response Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Request ID: " + e.getRequestId());
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to shutdown one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean shutdownInstance(VMInfo _VMInfo) throws Exception {
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
    public boolean shutdownInstances(ArrayList<VMInfo> vmList) throws Exception {
        // TODO Auto-generated method stub
        //============================================================================================//
        //=================================== Shutting down any Instances ==============================//
        //============================================================================================//
        try {
            // Terminate instances.
            ArrayList<String> instanceIds = new ArrayList<String>();
            for(VMInfo vmInfo: vmList){
                instanceIds.add(vmInfo.getVmID());
            }
            StopInstancesRequest stopRequest = new StopInstancesRequest().withInstanceIds(instanceIds);

            System.out.println(instanceIds.size());
            StopInstancesResult stopResult=ec2.stopInstances(stopRequest);
            System.out.println(stopResult.toString());
            System.out.println(stopResult.getStoppingInstances().size());
        } catch (AmazonServiceException e) {
            // Write out any exceptions that may have occurred.
            System.out.println("Error stopping instances");
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Response Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Request ID: " + e.getRequestId());
        }
        return false;
    }

    /**
     * used to reboot one instance
     *
     * @param _VMInfo
     * @throws Exception
     */
    @Override
    public VMInfo rebootInstance(VMInfo _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to reboot a list of instances
     *
     * @param vmList
     * @throws Exception
     */
    @Override
    public ArrayList<VMInfo> rebootInstances(ArrayList<VMInfo> vmList) throws Exception {
        //============================================================================================//
        //=================================== reboot any Instances ==============================//
        //============================================================================================//
        try {
            // Terminate instances.
            ArrayList<String> instanceIds = new ArrayList<String>();
            for(VMInfo vmInfo: vmList){
                instanceIds.add(vmInfo.getVmID());
            }
            RebootInstancesRequest rebootRequest = new RebootInstancesRequest();
            rebootRequest.setInstanceIds(instanceIds);
            ec2.rebootInstances(rebootRequest);
        } catch (AmazonServiceException e) {
            // Write out any exceptions that may have occurred.
            System.out.println("Error rebooting instances");
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Response Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Request ID: " + e.getRequestId());
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    /**
     * Used to destroy vms according to the vmList
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public boolean destroyInstances(ArrayList<VMInfo> vmList) throws Exception {
        //============================================================================================//
        //=================================== Destroy any Instances ==============================//
        //============================================================================================//
        try {
            // Terminate instances.
            ArrayList<String> instanceIds = new ArrayList<String>();
            for(VMInfo vmInfo: vmList){
                instanceIds.add(vmInfo.getVmID());
            }
            TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest(instanceIds);
            ec2.terminateInstances(terminateRequest);
        } catch (AmazonServiceException e) {
            // Write out any exceptions that may have occurred.
            System.out.println("Error terminating instances");
            System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Response Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Request ID: " + e.getRequestId());
        }
        return false;
    }


    public static void main(String[] args) throws Exception {
        EC2VMOperationImpl ec2VMOperation=new EC2VMOperationImpl();
        /*ArrayList<VMInfo> vmList=new ArrayList<VMInfo>();
        VMInfo vmInfo1=new VMInfo();
        vmInfo1.setVmID("i-52994123");
        VMInfo vmInfo2=new VMInfo();
        vmInfo2.setVmID("i-4C944459");
        vmList.add(vmInfo1);
        vmList.add(vmInfo2);
        try {
            //ec2VMOperation.createInstances(1);
            //ec2VMOperation.getInstanceList();
            //ec2VMOperation.launchInstances(vmList);
            //ec2VMOperation.rebootInstances(vmList);
            ec2VMOperation.shutdownInstances(vmList);
        }catch (Exception e){
            System.out.println("error");
        }*/
        ec2VMOperation.getInstanceList();

    }
}

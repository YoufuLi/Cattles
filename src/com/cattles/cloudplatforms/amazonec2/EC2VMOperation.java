package com.cattles.cloudplatforms.amazonec2;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.TerminateInstancesRequest;
import com.cattles.cloudplatforms.interfaces.*;
public class EC2VMOperation implements VMOperation{
	
	EC2ConfigOperation ec2Config=new EC2ConfigOperation();
	AWSCredentials credentials = ec2Config.initAWSCredentials();
	// Initialize variables.
	ArrayList<String> instanceIds = new ArrayList<String>();
	// Create the AmazonEC2Client object so we can call various APIs.
	AmazonEC2 ec2 = new AmazonEC2Client(credentials);
	@Override
	public List createVMs(int vmNumber) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean startVMs(List vmList) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean shutdownVMs(List vmList) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void rebootVMs(List vmList) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean destoryVMs(List vmList) throws Exception {
		// TODO Auto-generated method stub
    	//============================================================================================//
    	//=================================== Terminating any Instances ==============================// 
    	//============================================================================================//
        try {
        	// Terminate instances.
        	TerminateInstancesRequest terminateRequest = new TerminateInstancesRequest(instanceIds);
        	ec2.terminateInstances(terminateRequest);
    	} catch (AmazonServiceException e) {
    		// Write out any exceptions that may have occurred.
            System.out.println("Error terminating instances");
    		System.out.println("Caught Exception: " + e.getMessage());
            System.out.println("Reponse Status Code: " + e.getStatusCode());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Request ID: " + e.getRequestId());
        }
		return null;
	}

}

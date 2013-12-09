package com.cattles.cloudplatforms.amazonec2;

import java.io.IOException;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;

public class EC2ConfigOperation {
	public AWSCredentials initAWSCredentials(){
		AWSCredentials credentials = null;
		try {
			credentials = new PropertiesCredentials(
			           	InlineTaggingCodeSampleApp.class.getResourceAsStream("AwsCredentials.properties"));
		} catch (IOException e1) {
			System.out.println("Credentials were not properly entered into AwsCredentials.properties.");
			System.out.println(e1.getMessage());
			System.exit(-1);
		}
		return credentials;
	}

}

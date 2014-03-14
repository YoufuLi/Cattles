package com.cattles.cloudplatforms.amazonec2;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;

public class EC2ConfigOperation {
    static String EC2_ACCESS_KEY="URPLJMDCFI82GR7JVDUVY";
    static String EC2_SECRET_KEY="93GSMdieMAoo9q6Cljajt0KTc6v9yUKVgkdoLhGj";
    // 这个信息也可以从eucarc文件中找到，
    static String EC2_END_POINT ="http://149.165.146.135:8773/services/Eucalyptus";
	public AmazonEC2 initAmazonEC2(){
        AmazonEC2 ec2;
        AWSCredentials myCredential = new BasicAWSCredentials(EC2_ACCESS_KEY, EC2_SECRET_KEY);
        ec2 = new AmazonEC2Client(myCredential);
        ec2.setEndpoint(EC2_END_POINT);
        return ec2;
	}

}

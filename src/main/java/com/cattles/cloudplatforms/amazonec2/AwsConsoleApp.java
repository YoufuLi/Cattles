package com.cattles.cloudplatforms.amazonec2;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/16/13
 * Time: 10:32 PM
 * To change this template use File | Settings | File Templates.
 */
// 首先导入必要的类库
import java.util.*;
import com.amazonaws.*;
import com.amazonaws.auth.*;
import com.amazonaws.services.ec2.*;
import com.amazonaws.services.ec2.model.*;
import com.amazonaws.services.ec2.model.DescribeAvailabilityZonesResult;
import com.amazonaws.services.ec2.model.DescribeInstancesResult;
import com.amazonaws.services.ec2.model.Instance;
import com.amazonaws.services.ec2.model.Reservation;

// 定义一个类
public class AwsConsoleApp
{

    // 必要的登录信息
// 如果要用来测试AWS公有云服务，需要使用AWS的帐号信息
// 如果要用来测试Eucalyptus私有云服务，可以从eucarc文件中找到这些帐号信息
    static String EC2_ACCESS_KEY="URPLJMDCFI82GR7JVDUVY";
    static String EC2_SECRET_KEY="93GSMdieMAoo9q6Cljajt0KTc6v9yUKVgkdoLhGj";
    // 这个信息也可以从eucarc文件中找到，
    static String EC2_END_POINT ="http://149.165.146.135:8773/services/Eucalyptus";
    static AmazonEC2 ec2;

    // 创建一个AWSCredentials类的实例，包含登录信息
// 设定访问AWS（Eucalyptus）云的端点
    private static void init() throws Exception
    {
        AWSCredentials myCredential = new BasicAWSCredentials(EC2_ACCESS_KEY, EC2_SECRET_KEY);
        ec2 = new AmazonEC2Client(myCredential);
        ec2.setEndpoint(EC2_END_POINT);
    }

    // 测试方法
    public static void main(String[] args) throws Exception
    {
// 初始化登录信息和访问端点
        init();

// 获得所有的可用域列表
        try
        {
            DescribeAvailabilityZonesResult availabilityZonesResult = ec2.describeAvailabilityZones();
            List<AvailabilityZone> zones = availabilityZonesResult.getAvailabilityZones();
            System.out.println("You have access to " + zones.size() + " availability zones.");
            for(AvailabilityZone zone : zones)
            {
                System.out.println(" "+ zone.getZoneName());
            }
        } catch (AmazonServiceException ase)
        {
            System.out.println("Caught Exception: " + ase.getMessage());
        }

// 获得所有的映像列表
        try
        {
            DescribeImagesResult imagesResult = ec2.describeImages();
            List<Image> images = imagesResult.getImages();
            System.out.println("You have access to " + images.size() + " images.");
            for(Image image : images)
            {
                System.out.println(" " + image.getImageId() +" " + image.getImageType() +" " + image.getName());
            }
        } catch (AmazonServiceException ase)
        {
            System.out.println("Caught Exception: " + ase.getMessage());
        }

// 创建两个虚拟机实例
        try
        {
            RunInstancesRequest runInstancesRequest = new RunInstancesRequest()
                    .withInstanceType("m1.small")
                    .withImageId("emi-1C8C3ADF")
                    .withMinCount(1)
                    .withMaxCount(2)
                    .withKeyName("nicholas-key")
                    ;
            RunInstancesResult runInstances = ec2.runInstances(runInstancesRequest);
            System.out.println(runInstances.toString());
        } catch (AmazonServiceException ase)
        {
            System.out.println("Caught Exception: " + ase.getMessage());
        }

// 获得所有的虚拟机列表
        try
        {
            DescribeInstancesResult describeInstancesResult = ec2.describeInstances();
            List<Reservation> reservations = describeInstancesResult.getReservations();
            System.out.println("You have " + reservations.size() + " reservations.");
            for (Reservation reservation : reservations)
            {
                System.out.println(" " + reservation.getReservationId());
                List<Instance> instances = reservation.getInstances();
                for (Instance instance : instances)
                {
                    System.out.println(" " + instance.getInstanceId() + " " + instance.getInstanceType() + " " + instance.getPrivateIpAddress() + " " + instance.getPublicIpAddress() + " key: " + instance.getKeyName() + " " + instance.getState().getName());
                }
            }
        } catch (AmazonServiceException ase)
        {
            System.out.println("Caught Exception: " + ase.getMessage());
        }

// 杀死一个虚拟机实例
        try
        {
            List instancesToTerminate = new ArrayList();
            instancesToTerminate.add("ID_of_the_Instance");
            TerminateInstancesRequest term = new TerminateInstancesRequest();
            term.setInstanceIds(instancesToTerminate);
            ec2.terminateInstances(term);
        } catch (AmazonServiceException ase)
        {
            System.out.println("Caught Exception: " + ase.getMessage());
        }

    }
}

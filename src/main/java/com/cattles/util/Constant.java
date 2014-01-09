package com.cattles.util;

/**
 * 
 * @author YoufuLi 功能：常量声明类
 */
public class Constant {

	//the path of VirtualMachines.xml
    public static final String VIRTUAL_MACHINES_XML_PATH="resource/VirtualMachines.xml";
    //the path of VirtualCluster.xml
    public static final String VIRTUAL_CLUSTERS_XML_PATH="resource/VirtualClusters.xml";
	//the path of platform configuration file
	public static final String PLATFORM_CONF_PATH = "conf/platform_conf.xml";
    //the path of scheduling framework configuration file
    public static final String SCHEDULING_FRAMEWORK_CONF_PATH="conf/schedulingframework_conf.xml";
    //the state of cluster: activated, standby
    public static final String VIRTUAL_CLUSTER_STATE_ACTIVATED="activated";
    public static final String VIRTUAL_CLUSTER_STATE_STANDBY="standby";
    //the state of virtual machine: idle, busy
    public static final String VIRTUAL_MACHINES_STATE_AVAILABLE="available";
    public static final String VIRTUAL_MACHINES_STATE_BUSY="busy";

    //platform name
    public static final String AMAZON_EC2_PLATFORM_NAME="ec2";
    public static final String OPENNEBULA_PLATFORM_NAME="opennebula";
    public static final String EUCALYPTUS_PLATFORM_NAME="eucalyptus";
    public static final String NIMBUS_PLATFORM_NAME="nimbus";
    public static final String OPENSTACK_PLATFORM_NAME="openstack";

    //scheduling framework name
    public static final String FALKON_FRAMEWORK_NAME="falkon";
    public static final String GEARMAN_FRAMEWORK_NAME="gearman";

    //the account information of virtual machine
    public static final String VIRTUAL_MACHINE_ACCOUNT="youfuli";
    public static final String VIRTUAL_MACHINE_PASSWORD="lz";

    //various command
    public static final String FALKON_SERVICE_INTIALIZATION_COMMAND="sh /usr/local/falkon.r174/cattles/startService.sh";
    public static final String FALKON_SERVICE_CLOSE_COMMAND="sh /usr/local/falkon.r174/cattles/stopService.sh falkon";
    public static final String FALKON_WORKER_REGISTERATION_COMMAND="sh /usr/local/falkon.r174/cattles/startWorker.sh";
    public static final String FALKON_WORKER_DEREGISTERATION_COMMAND="sh /usr/local/falkon.r174/cattles/stopWorker.sh falkon";
}

package com.cattles.util;

import com.cattles.cloudplatforms.opennebula.FalkonUserRequest;
import com.cattles.clusterMonitoring.Node;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    //the state of cluster: activated, standby
    public static final String VIRTUAL_CLUSTER_STATE_ACTIVATED="activated";
    public static final String VIRTUAL_CLUSTER_STATE_STANDBY="standby";
    //the state of virtual machine: idle, busy
    public static final String VIRTUAL_MACHINES_STATE_AVAILABLE="available";
    public static final String VIRTUAL_MACHINES_STATE_BUSY="busy";

}

package com.cattles.cloudplatforms;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cattles.cloudplatforms.opennebula.FalkonUserRequest;

import com.cattles.heartbeat.Node;;

/**
 * 
 * @author xiongrong 功能：管理器中常量声明类
 */
public class Constant {

	// nimbus平台名称
	public static final String NIMBUS_PLATFORM_NAME = "nimbus";

	// opennebula平台名称
	public static final String OPENNEBULA_PLATFORM_NAME = "opennebula";

	// opennebula的用户名和密码
	public static final String OPENNEBULA_USER_PASSWORD = "opennebulaUserAndPW";
	
	//opennebula的URL地址
	public static final String OPENNEBULA_URL = "opennebulaURL";
	
	// 配置文件路径
	public static final String CONFIG_FILE_PATH = "config/managerConfig.config";

	// 配置文件managerConfig.properties中platform的key键
	public static final String PLATFORM_KEY = "platform";

	// 配置文件managerconfig.properties中falkon服务器配置文件的key键
	public static final String FALKON_SERVICE_CONFIG_FILE_KEY = "falkonserviceconfigfile";

	// opennebula启动虚拟机集群的配置文件路径
	public static final String VM_CONFIG_FILE_PATH = "vmconfigfilepath";

	// 池子里面虚拟机的数目
	public static final String POOL_MAX_VM_NUM = "poolMaxVmNum";

	// vm池子是否初始化，初始化后的nebula平台下的vm名字
	public static final String VM_POOL_IS_INIT_VMNAME_NEBULA = "VM_0";

	// vm中user名称，front－end可以ssh连接上vm的用户名key键
	public static final String VM_USER_NAME = "vmUserName";

	// 配置文件managerconfig.properties中工作流nfs挂载路径key键
	public static final String NFS_MOUNT_PATH_KEY = "nfsMountPath";

	// 配置文件managerconfig.properties中工作流nfs挂载ip的key键
	public static final String NFS_MOUNT_IP_KEY = "nfsMountIP";
	
	public static final String FALKON_SERVICE_PORT = "falkonServicePort";
	
	//配置文件managerconfig.conf中heartbeatServer的key键
	public static final String HEARTBEAT_SERVER = "heartbeatServer";

	//配置文件managerconfig.conf中heartbeatport的key键
	public static final String HEARTBEAT_PORT = "heartbeatPort";
	
	//开启的集群是否是falkon集群的标志性文件名称
	public static final String FALKON_CLUSTER_FLAG_FILE = "falkonclusterFlagFile";
	
	//保存falkon是否提交过，一个falkon service检测线程只能运行一次，key是service IP，value是FalkonUserRequest，请求vm数目和检测线程是否运行
	public static Map<String,FalkonUserRequest> falkonStateThreadMap = Collections.synchronizedMap(new HashMap<String,FalkonUserRequest>()); 
	
	//保存开启虚拟机的配置文件的相关信息，包括cpu、memory、nic、整个文件等
	public static Map<String,String> VMConfigFileMap = Collections.synchronizedMap(new HashMap<String,String>());
	
	//保存受监控机子心跳的map，key键是监控机子的ip，value是监控机子的系统信息
	public static Map<String, Node> nodeMap = Collections.synchronizedMap(new HashMap<String, Node>());
	
	//保存service和对应worker的信息
	public static Map<String, List<String>> serviceWorkerMap = Collections.synchronizedMap(new HashMap<String, List<String>>());
	
	//保存新的和旧的falkon server映射，处理server虚拟机停止情况
	public static Map<String,String> oldAndNewServerMap = Collections.synchronizedMap(new HashMap<String,String>());
}

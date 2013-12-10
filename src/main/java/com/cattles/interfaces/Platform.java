package com.cattles.interfaces;

import java.util.List;

import com.cattles.resourcePoolManagement.ResourcePool;
import com.cattles.vmManagement.*;

public interface Platform {
	
	/**
	 * author xiongrong
	 * 功能：在已经存在service的情况下，创建工作流需要的虚拟机资源
	 * 参数workerNum:需要创建的worker数目
	 * 参数cloudType：创建worker的云平台
	 * 参数serviceIp：已知的falkon服务器的ip地址
	 * 参数port：falkon服务器的端口
	 * 返回ResponseInfo
	 */
	public ResponseInfo createWorkerCluster(int workerNum,String serviceIP, String port,ResourcePool rp) throws Exception;
	
	/**
	 * author:xiong rong
	 * 功能：在没有service的情况下，创建工作流需要的虚拟机资源
	 * 参数workerNum:需要创建的worker数目
	 * 返回VmInfo：只要service创建成功后，就返回创建的service的虚拟机ip和hostname，worker再创建。
	 */
	public VMInfo createCluster(int workerNum, ResourcePool rp) throws Exception;
	
	/**
	 * 
	 * author:xiong rong
	 * 功能：创建虚拟机
	 * @param workerNum
	 */
	public boolean createPlatform(int workerNum, String uuid) throws Exception;
	
	/**
	 * 
	 * author:xiong rong
	 * 功能：管理器服务在启动时创建池子中的虚拟机，每隔一段时间进行检查，当平台中vm个数少于配置文件的个数时进行再创建，
	 * 保持系统中有配置文件中的虚拟机的数目
  	 * 参数uuid：标志，确保vm是managerservice创建
	 * 参数resourceNum：创建的资源数目
	 * 参数resourceList：传递资源列表
	 * 返回List:返回管理器资源池中vm数组
	 */
	public List<Object> genPoolVm(String uuid,int resourceNum,List<Object> resourceList) throws Exception;
	
	/**
	 * 
	 * author:xiong rong
	 * 功能：判断是否已经初始化了虚拟机池子
	 * 返回boolean：初始化过，返回true，否则返回false
	 */
	public boolean isInitPool() throws Exception;
	
	/**
	 * author:xiong rong
 	 * 功能：停止检查falkon service状态的线程
	 * 参数serviceIP:停止service的ip地址
	 * 返回boolean：返回是否成功
	 */
	public boolean stopCheckServiceStateThread(String serviceIP) throws Exception;
}

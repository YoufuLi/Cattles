/**
 * 文件名：CheckVmOperate.java
 * 创建时间：Aug 18, 2011
 * 创建者：xiong rong
 */
package com.cattles.interfaces;

import java.util.List;

import com.cattles.schedulingframeworks.falkon.*;

/**
 * @author xiongrong
 * 用法：检查vm操作的接口
 */
public interface CheckVmOperate {

	/**
	 * author:xiong rong
	 * 功能：检查vm中nfs挂载
	 * @param vmList
	 */
	public boolean checkNfsMount(List<Object> vmList);
	
	/**
	 * author:xiong rong
	 * 功能：检查虚拟机是否注册到server
	 * 参数fwn:计算worker数目类
	 * 参数vmList：检查的vm列表
	 * 参数serviceIp:注册server的ip地址
	 * 参数port：注册server的端口
	 * 参数workerNum：注册到server的worker个数
	 */
	public boolean checkVmRegeister(CalWorkerNum fwn,
			List<Object> vmList, String serviceIp, String port,
			int workerNum) throws Exception ;
	
	/**
	 * author:xiong rong
	 * 功能：检查falkon service是否启动 参数serviceIp:falkon service ip
	 * 参数port：falkon service运行的端口号
	 * @param serviceIp
	 */
	public boolean checkBootServiceComm(String serviceIp,
			String port); 
}

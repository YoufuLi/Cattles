/**
 * 文件名：DealwithStopVm.java
 * 创建时间：Aug 19, 2011
 * 创建者：xiong rong
 */
package com.cattles.cloudplatforms.interfaces;


/**
 * @author xiong rong
 * 处理平台中心跳停止的vm
 *
 */
public interface DealwithStopVm {

	/**
	 * 
	 * author:xiong rong
	 * 功能：得到心跳停止vm的类型，worker还是server
	 * @param args
	 * 参数vmIp：vm的Ip地址
	 * 返回int：1为server，2为worker
	 */
	public  int stopVmType(String vmIp) throws Exception;
	
	/**
	 * author:xiong rong
	 * 功能：处理停止的server
	 * @param args
	 * 参数：falkon服务器的ip地址
	 * 返回boolean:处理是否成功
	 * @throws Exception 
	 */
	public boolean dealwithSotpServer(String serviceIp) throws Exception;
	
	/**
	 * author:xiong rong
	 * 功能：处理停止的worker
	 * @param args
	 * 参数workerIp：worker的Ip地址
	 * 返回boolean:处理是否成功
	 */
	public boolean dealwithStopWorker(String workerIp) throws Exception;
}

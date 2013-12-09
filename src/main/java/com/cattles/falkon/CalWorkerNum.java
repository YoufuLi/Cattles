package com.cattles.falkon;

import java.util.Map;

/**
 * 
 * @author xiong rong
 * 用法：计算worker数目接口
 */
public interface CalWorkerNum {
	
	/**
	 * 
	 * author:xiong rong
	 * 功能：计算运行任务的worker数目
	 * 参数serviceURI：falkon service地址 
	 */
	public Map<String,String> getFalkonInfo();
}

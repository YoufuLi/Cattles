/**
 * 文件名：FalkonUserRequest.java
 * 创建时间：Sep 6, 2011
 * 创建者：xiong rong
 */
package com.cattles.cloudplatforms.opennebula;

/**
 * @author xiong rong
 * 用法：保存用户请求vm个数和falkon监控任务数得到worker数目的线程是否运行
 *
 */
public class FalkonUserRequest {

	//请求vm个数
	private int requestVmNum;
	
	//falkon监控任务数得到worker数目的线程是否运行
	private boolean isRun;

	public int getRequestVmNum() {
		return requestVmNum;
	}

	public void setRequestVmNum(int requestVmNum) {
		this.requestVmNum = requestVmNum;
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}
}

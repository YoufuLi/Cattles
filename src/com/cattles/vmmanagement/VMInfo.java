package com.cattles.vmmanagement;

public class VMInfo {
	/**
	 * identify if the vm is available
	 */
	String vmState;
	/**
	 * the ip or other address of vm
	 */
	String vmAddress;
	/**
	 * the port of the vm
	 */
	String vmPort;
	/**
	 * the hostname of the vm
	 */
	private String vmhostname;
	public String getVmState() {
		return vmState;
	}
	public void setVmState(String vmState) {
		this.vmState = vmState;
	}
	public String getVmAddress() {
		return vmAddress;
	}
	public void setVmAddress(String vmAddress) {
		this.vmAddress = vmAddress;
	}
	public String getVmPort() {
		return vmPort;
	}
	public void setVmPort(String vmPort) {
		this.vmPort = vmPort;
	}
	public String getVmhostname() {
		return vmhostname;
	}
	public void setVmhostname(String vmhostname) {
		this.vmhostname = vmhostname;
	}
	
}

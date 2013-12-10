package com.cattles.vmManagement;

public class VMInfo {
    /**
     * the id of vm
     */
    String vmID;
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
	private String vmHostname;

    public String getVmID() {
        return vmID;
    }

    public void setVmID(String vmID) {
        this.vmID = vmID;
    }

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

    public String getVmHostname() {
        return vmHostname;
    }

    public void setVmHostname(String vmHostname) {
        this.vmHostname = vmHostname;
    }
}

package com.cattles.virtualMachineManagement;

public class VirtualMachineInformation {
    /**
     * the id of vm
     */
    String vmID;
    /**
     * such as m1.small
     */
    String vmType;
    /**
     * identify if the vm is available
     */
    String vmState;
    /**
     * the ip or other address of vm
     */
    String vmPublicIpAddress;
    String vmPrivateIpAddress;
    /**
     * the user key used to create this instance
     */
    String vmKeyName;
    /**
     * the port of the vm
     */
    String vmPort;
    /**
     * the hostname of the vm
     */
    String vmHostname;

    public String getVmID() {
        return vmID;
    }

    public void setVmID(String vmID) {
        this.vmID = vmID;
    }

    public String getVmType() {
        return vmType;
    }

    public void setVmType(String vmType) {
        this.vmType = vmType;
    }

    public String getVmState() {
        return vmState;
    }

    public void setVmState(String vmState) {
        this.vmState = vmState;
    }

    public String getVmPublicIpAddress() {
        return vmPublicIpAddress;
    }

    public void setVmPublicIpAddress(String vmPublicIpAddress) {
        this.vmPublicIpAddress = vmPublicIpAddress;
    }

    public String getVmPrivateIpAddress() {
        return vmPrivateIpAddress;
    }

    public void setVmPrivateIpAddress(String vmPrivateIpAddress) {
        this.vmPrivateIpAddress = vmPrivateIpAddress;
    }

    public String getVmKeyName() {
        return vmKeyName;
    }

    public void setVmKeyName(String vmKeyName) {
        this.vmKeyName = vmKeyName;
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

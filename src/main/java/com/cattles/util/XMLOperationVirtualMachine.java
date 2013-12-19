package com.cattles.util;

import com.cattles.vmClusterManagement.VirtualCluster;
import com.cattles.vmManagement.VMInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/18/13
 * Time: 3:36 PM
 * read and modify the VirtualMachine.xml.
 */
public class XMLOperationVirtualMachine {
    public Document xmlDocument;
    private static XMLOperationVirtualMachine xmlOperationVirtualMachine = null;
    private XMLOperationVirtualMachine(){

    }
    public static synchronized XMLOperationVirtualMachine getXmlOperationVirtualMachine(){
        if (xmlOperationVirtualMachine==null){
            xmlOperationVirtualMachine=new XMLOperationVirtualMachine();
            xmlOperationVirtualMachine.init();
        }
        return xmlOperationVirtualMachine;
    }
    private void init() {
        // TODO Auto-generated method stub
        try{
            DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            this.xmlDocument=builder.parse(Constant.VIRTUAL_MACHINES_XML_PATH);
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println(e.getMessage());
        } catch (SAXException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * get all the virtual machines in the resource pool
     * @return vmInfoList
     */
    public ArrayList<VMInfo> getAllVMs(){
        ArrayList<VMInfo> vmInfoList=new ArrayList<VMInfo>();
        VMInfo vmInfo=new VMInfo();
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        System.out.println("nodes:"+virtualMachines.getNodeName());
        NodeList virtualMachine=virtualMachines.getChildNodes();
        System.out.println("virtual machine:"+virtualMachine.getLength());
        for (int i = 0; i < virtualMachine.getLength(); i++) {
            if(virtualMachine.item(i).getNodeType()==Node.ELEMENT_NODE){
                NodeList vmInfoNodeList = virtualMachine.item(i).getChildNodes();
                vmInfo.setVmID(vmInfoNodeList.item(1).getTextContent());
                vmInfo.setVmType(vmInfoNodeList.item(3).getTextContent());
                vmInfo.setVmState(vmInfoNodeList.item(5).getTextContent());
                vmInfo.setVmPublicIpAddress(vmInfoNodeList.item(7).getTextContent());
                vmInfo.setVmPrivateIpAddress(vmInfoNodeList.item(9).getTextContent());
                vmInfo.setVmKeyName(vmInfoNodeList.item(11).getTextContent());
                vmInfo.setVmPort(vmInfoNodeList.item(13).getTextContent());
                vmInfo.setVmHostname(vmInfoNodeList.item(15).getTextContent());
                vmInfoList.add(vmInfo);
                /*System.out.println("vminfo:"+vmInfoNodeList.getLength());
                for (int k=0;k<vmInfoNodeList.getLength();k++){
                    System.out.println(k+":"+vmInfoNodeList.item(k).getTextContent());
                } */
            }

        }
        return vmInfoList;
    }

    /**
     * get a vm instance with specified vmID
     * @param _vmID
     * @return vmInfo
     */
    public VMInfo getVMByID(String _vmID){
        VMInfo vmInfo=new VMInfo();
        return vmInfo;
    }

    /**
     * add a vm to the resource pool
     * @param _vmInfo
     */
    public void addVM(VMInfo _vmInfo){

    }

    /**
     * add a list of VMs to the resource pool
     * @param _vmInfoList
     */
    public void addVMs(ArrayList<VMInfo> _vmInfoList){
        for(VMInfo vmInfo:_vmInfoList){
            addVM(vmInfo);
        }
    }

    /**
     * delete a VM according to the vmID
     * @param _vmID
     * @return
     */
    public boolean deleteVM(String _vmID){
        boolean success=false;
        return success;
    }

    /**
     * delete a list of VMs according the vmID list
     * @param _vmIDList
     * @return
     */
    public boolean deleteVMs(ArrayList<String> _vmIDList){
        boolean success=false;
        for (String vmID:_vmIDList){
            success=deleteVM(vmID);
        }
        return success;
    }

    /**
     * modify a VM with the provided vmInfo
     * @param _vmInfo
     */
    public void modifyVM(VMInfo _vmInfo){

    }

    /**
     *
     * @param _vmInfoList
     */
    public void modifyVMs(ArrayList<VMInfo> _vmInfoList){
        for(VMInfo vmInfo:_vmInfoList){
            modifyVM(vmInfo);
        }
    }
    public static void main(String[] args){
        XMLOperationVirtualMachine xmlOperationVirtualMachine1=XMLOperationVirtualMachine.getXmlOperationVirtualMachine();
        xmlOperationVirtualMachine1.getAllVMs();
    }
}

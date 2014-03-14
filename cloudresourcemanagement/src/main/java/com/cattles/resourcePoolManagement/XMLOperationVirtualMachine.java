package com.cattles.resourcePoolManagement;

import com.cattles.util.Constant;
import com.cattles.virtualMachineManagement.VMInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
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
            xmlDocument.normalize();
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
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        //System.out.println("nodes:"+virtualMachines.getNodeName());
        NodeList virtualMachineList=virtualMachines.getChildNodes();
        //System.out.println("virtual machine:"+virtualMachine.getLength());
        for (int i = 0; i < virtualMachineList.getLength(); i++) {
            NodeList vmInfoNodeList = virtualMachineList.item(i).getChildNodes();
            VMInfo vmInfo=new VMInfo();
            vmInfo.setVmID(vmInfoNodeList.item(0).getTextContent());
            vmInfo.setVmType(vmInfoNodeList.item(1).getTextContent());
            vmInfo.setVmState(vmInfoNodeList.item(2).getTextContent());
            vmInfo.setVmPublicIpAddress(vmInfoNodeList.item(3).getTextContent());
            vmInfo.setVmPrivateIpAddress(vmInfoNodeList.item(4).getTextContent());
            vmInfo.setVmKeyName(vmInfoNodeList.item(5).getTextContent());
            vmInfo.setVmPort(vmInfoNodeList.item(6).getTextContent());
            vmInfo.setVmHostname(vmInfoNodeList.item(7).getTextContent());
            vmInfoList.add(vmInfo);
            //System.out.println(vmInfo.getVmHostname()+vmInfo.getVmID()+vmInfo.getVmKeyName()+vmInfo.getVmPort()+vmInfo.getVmPrivateIpAddress()+vmInfo.getVmPublicIpAddress()+vmInfo.getVmState()+vmInfo.getVmType());
                /*System.out.println("vminfo:"+vmInfoNodeList.getLength());
                for (int k=0;k<vmInfoNodeList.getLength();k++){
                    System.out.println(k+":"+vmInfoNodeList.item(k).getTextContent());
                } */

        }
        return vmInfoList;
    }

    /**
     * get the list of VMs according to the state: "available","busy"
     * @param _state
     * @return
     */
    public ArrayList<VMInfo> getVMsWithState(String _state){
        ArrayList<VMInfo> vmInfoList=new ArrayList<VMInfo>();
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        NodeList virtualMachineList=virtualMachines.getChildNodes();
        for (int i = 0; i < virtualMachineList.getLength(); i++) {
            NodeList vmInfoNodeList = virtualMachineList.item(i).getChildNodes();
            if(vmInfoNodeList.item(2).getTextContent().equals(_state)){
                VMInfo vmInfo=new VMInfo();
                vmInfo.setVmID(vmInfoNodeList.item(0).getTextContent());
                vmInfo.setVmType(vmInfoNodeList.item(1).getTextContent());
                vmInfo.setVmState(vmInfoNodeList.item(2).getTextContent());
                vmInfo.setVmPublicIpAddress(vmInfoNodeList.item(3).getTextContent());
                vmInfo.setVmPrivateIpAddress(vmInfoNodeList.item(4).getTextContent());
                vmInfo.setVmKeyName(vmInfoNodeList.item(5).getTextContent());
                vmInfo.setVmPort(vmInfoNodeList.item(6).getTextContent());
                vmInfo.setVmHostname(vmInfoNodeList.item(7).getTextContent());
                vmInfoList.add(vmInfo);
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
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        NodeList virtualMachineList=virtualMachines.getChildNodes();
        for (int i = 0; i < virtualMachineList.getLength(); i++) {
            NodeList vmInfoNodeList = virtualMachineList.item(i).getChildNodes();
            if(vmInfoNodeList.item(0).getTextContent().equals(_vmID)){
                vmInfo.setVmID(vmInfoNodeList.item(0).getTextContent());
                vmInfo.setVmType(vmInfoNodeList.item(1).getTextContent());
                vmInfo.setVmState(vmInfoNodeList.item(2).getTextContent());
                vmInfo.setVmPublicIpAddress(vmInfoNodeList.item(3).getTextContent());
                vmInfo.setVmPrivateIpAddress(vmInfoNodeList.item(4).getTextContent());
                vmInfo.setVmKeyName(vmInfoNodeList.item(5).getTextContent());
                vmInfo.setVmPort(vmInfoNodeList.item(6).getTextContent());
                vmInfo.setVmHostname(vmInfoNodeList.item(7).getTextContent());
            }

        }
        return vmInfo;
    }

    /**
     * add a vm to the resource pool
     * @param _vmInfo
     */
    public void addVM(VMInfo _vmInfo){
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        Node vmInfoNode=xmlDocument.createElement("VirtualMachine");
        Node vmID=xmlDocument.createElement("vmID");
        Node vmType=xmlDocument.createElement("vmType");
        Node vmState=xmlDocument.createElement("vmState");
        Node vmPublicIpAddress=xmlDocument.createElement("vmPublicIpAddress");
        Node vmPrivateIpAddress=xmlDocument.createElement("vmPrivateIpAddress");
        Node vmKeyName=xmlDocument.createElement("vmKeyName");
        Node vmPort=xmlDocument.createElement("vmPort");
        Node vmHostname=xmlDocument.createElement("vmHostname");

        vmID.setTextContent(_vmInfo.getVmID());
        vmType.setTextContent(_vmInfo.getVmType());
        vmState.setTextContent(_vmInfo.getVmState());
        vmPublicIpAddress.setTextContent(_vmInfo.getVmPublicIpAddress());
        vmPrivateIpAddress.setTextContent(_vmInfo.getVmPrivateIpAddress());
        vmKeyName.setTextContent(_vmInfo.getVmKeyName());
        vmPort.setTextContent(_vmInfo.getVmPort());
        vmHostname.setTextContent(_vmInfo.getVmHostname());

        vmInfoNode.appendChild(vmID);
        vmInfoNode.appendChild(vmType);
        vmInfoNode.appendChild(vmState);
        vmInfoNode.appendChild(vmPublicIpAddress);
        vmInfoNode.appendChild(vmPrivateIpAddress);
        vmInfoNode.appendChild(vmKeyName);
        vmInfoNode.appendChild(vmPort);
        vmInfoNode.appendChild(vmHostname);
        virtualMachines.appendChild(vmInfoNode);
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_MACHINES_XML_PATH));
            transformer.transform(source, result);
        }catch (TransformerConfigurationException e){
            System.out.println(e.getMessage());
        }catch (TransformerException e){
            System.out.println(e.getMessage());
        }
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
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        NodeList virtualMachineList=virtualMachines.getChildNodes();
        for (int i = 0; i < virtualMachineList.getLength(); i++) {
            NodeList vmInfoNodeList = virtualMachineList.item(i).getChildNodes();
            if(vmInfoNodeList.item(0).getTextContent().equals(_vmID)){
                //xmlDocument.getChildNodes().item(0).removeChild(xmlDocument.getChildNodes().item(0).getChildNodes().item(i));
                virtualMachines.removeChild(virtualMachineList.item(i));
            }
        }
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_MACHINES_XML_PATH));
            transformer.transform(source, result);
            success=true;
        }catch (TransformerConfigurationException e){
            System.out.println(e.getMessage());
        }catch (TransformerException e){
            System.out.println(e.getMessage());
        }
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
     * locate the vm location, and modify the state of a vm.
     * @param _vmID
     * @param _state
     */
    public boolean modifyVMState(String _vmID, String _state){
        boolean success=false;
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        NodeList virtualMachineList=virtualMachines.getChildNodes();
        for (int i = 0; i < virtualMachineList.getLength(); i++) {
            NodeList vmInfoNodeList = virtualMachineList.item(i).getChildNodes();
            if(vmInfoNodeList.item(0).getTextContent().equals(_vmID)){
                vmInfoNodeList.item(2).setTextContent(_state);
            }
        }
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_MACHINES_XML_PATH));
            transformer.transform(source, result);
            success=true;
        }catch (TransformerConfigurationException e){
            System.out.println(e.getMessage());
        }catch (TransformerException e){
            System.out.println(e.getMessage());
        }
        return success;
    }

    /**
     * modify a list of VMs according the vmID list
     * @param _vmIDList
     * @return
     */
    public boolean modifyVMsState(ArrayList<String> _vmIDList, String _state){
        boolean success=false;
        for (String vmID:_vmIDList){
            success=modifyVMState(vmID, _state);
        }
        return success;
    }
    /**
     * modify a VM with the provided vmInfo
     * @param _vmInfo
     */
    public void modifyVM(VMInfo _vmInfo){
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        NodeList virtualMachineList=virtualMachines.getChildNodes();
        for (int i = 0; i < virtualMachineList.getLength(); i++) {
            NodeList vmInfoNodeList = virtualMachineList.item(i).getChildNodes();
            if(vmInfoNodeList.item(0).getTextContent().equals(_vmInfo.getVmID())){
                vmInfoNodeList.item(1).setTextContent(_vmInfo.getVmType());
                vmInfoNodeList.item(2).setTextContent(_vmInfo.getVmState());
                vmInfoNodeList.item(3).setTextContent(_vmInfo.getVmPublicIpAddress());
                vmInfoNodeList.item(4).setTextContent(_vmInfo.getVmPrivateIpAddress());
                vmInfoNodeList.item(5).setTextContent(_vmInfo.getVmKeyName());
                vmInfoNodeList.item(6).setTextContent(_vmInfo.getVmPort());
                vmInfoNodeList.item(7).setTextContent(_vmInfo.getVmHostname());
                /*xmlDocument.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(1).setTextContent(_vmInfo.getVmType());
                xmlDocument.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(2).setTextContent(_vmInfo.getVmState());
                xmlDocument.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(3).setTextContent(_vmInfo.getVmPublicIpAddress());
                xmlDocument.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(4).setTextContent(_vmInfo.getVmPrivateIpAddress());
                xmlDocument.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(5).setTextContent(_vmInfo.getVmKeyName());
                xmlDocument.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(6).setTextContent(_vmInfo.getVmPort());
                xmlDocument.getChildNodes().item(0).getChildNodes().item(i).getChildNodes().item(7).setTextContent(_vmInfo.getVmHostname());*/
            }
        }
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_MACHINES_XML_PATH));
            transformer.transform(source, result);
        }catch (TransformerConfigurationException e){
            System.out.println(e.getMessage());
        }catch (TransformerException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * call modifyVM() method to modify a list of virtual machines
     * @param _vmInfoList
     */
    public void modifyVMs(ArrayList<VMInfo> _vmInfoList){
        for(VMInfo vmInfo:_vmInfoList){
            modifyVM(vmInfo);
        }
    }

    /**
     * get the count of the VMs listed in the VirtualMachines.xml
     * @return
     */
    public int getVMCount(){
        int vmCount=0;
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        NodeList virtualMachineList=virtualMachines.getChildNodes();
        vmCount=virtualMachineList.getLength();
        return vmCount;
    }
    public static void main(String[] args){
        XMLOperationVirtualMachine xmlOperationVirtualMachine=XMLOperationVirtualMachine.getXmlOperationVirtualMachine();

        //xmlOperationVirtualMachine.getAllVMs();

        //VMInfo vmInfo=xmlOperationVirtualMachine.getVMByID("i-ba4534d");
        //System.out.println(vmInfo.getVmHostname());

        //add a list of VMs
        /*ArrayList<VMInfo> vmInfoArrayList=new ArrayList<VMInfo>();
        VMInfo vmInfo=new VMInfo();
        for(int i=0;i<2;i++){
            vmInfo.setVmID("i-ba4534d00");
            vmInfo.setVmType("type");
            vmInfo.setVmState("available");
            vmInfo.setVmPublicIpAddress("public");
            vmInfo.setVmPrivateIpAddress("private");
            vmInfo.setVmKeyName("key");
            vmInfo.setVmPort("port");
            vmInfo.setVmHostname("hostname");
            vmInfoArrayList.add(vmInfo);
        }
        xmlOperationVirtualMachine.addVMs(vmInfoArrayList);*/

        //add a single vm
        VMInfo vmInfo=new VMInfo();
        vmInfo.setVmID("i-ba4534r");
        vmInfo.setVmType("type");
        vmInfo.setVmState("using");
        vmInfo.setVmPublicIpAddress("public");
        vmInfo.setVmPrivateIpAddress("private");
        vmInfo.setVmKeyName("key");
        vmInfo.setVmPort("port");
        vmInfo.setVmHostname("hostname");
        xmlOperationVirtualMachine.addVM(vmInfo);

        //delete a single vm
        //xmlOperationVirtualMachine.deleteVM("i-ba4534d00");

        //delete a list of VMs
        /*ArrayList<String> vmIDList=new ArrayList<String>();
        vmIDList.add("i-ba4534d00");
        vmIDList.add("i-ba4534d");
        xmlOperationVirtualMachine.deleteVMs(vmIDList);*/

        //modify a single vm
        /*VMInfo vmInfo=new VMInfo();
        vmInfo.setVmID("i-ba4534d");
        vmInfo.setVmType("type1");
        vmInfo.setVmState("state1");
        vmInfo.setVmPublicIpAddress("public1");
        vmInfo.setVmPrivateIpAddress("private1");
        vmInfo.setVmKeyName("key1");
        vmInfo.setVmPort("port1");
        vmInfo.setVmHostname("hostname1");
        xmlOperationVirtualMachine.modifyVM(vmInfo);*/

        /*//modify a list of VMs
        ArrayList<VMInfo> vmInfoArrayList=new ArrayList<VMInfo>();
        VMInfo vmInfo=new VMInfo();
        VMInfo vmInfo00=new VMInfo();
        vmInfo.setVmID("i-ba4534d");
        vmInfo.setVmType("type");
        vmInfo.setVmState("state");
        vmInfo.setVmPublicIpAddress("public");
        vmInfo.setVmPrivateIpAddress("private");
        vmInfo.setVmKeyName("key");
        vmInfo.setVmPort("port");
        vmInfo.setVmHostname("hostname");
        vmInfoArrayList.add(vmInfo);
        vmInfo00.setVmID("i-ba4534d00");
        vmInfo00.setVmType("type00");
        vmInfo00.setVmState("state00");
        vmInfo00.setVmPublicIpAddress("public00");
        vmInfo00.setVmPrivateIpAddress("private00");
        vmInfo00.setVmKeyName("key00");
        vmInfo00.setVmPort("port00");
        vmInfo00.setVmHostname("hostname00");
        vmInfoArrayList.add(vmInfo00);
        xmlOperationVirtualMachine.modifyVMs(vmInfoArrayList);*/
    }
}

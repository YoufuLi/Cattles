package com.cattles.util;

import com.cattles.vmManagement.VMInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/18/13
 * Time: 3:36 PM
 * read and modify the VirtualClusters.xml
 */
public class XMLOperationCluster {
    public Document xmlDocument;
    private static XMLOperationCluster xmlOperationCluster = null;
    private XMLOperationCluster(){

    }
    public static synchronized XMLOperationCluster getXmlOperationCluster(){
        if (xmlOperationCluster==null){
            xmlOperationCluster=new XMLOperationCluster();
            xmlOperationCluster.init();
        }
        return xmlOperationCluster;
    }
    private void init() {
        // TODO Auto-generated method stub
        try{
            DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            this.xmlDocument=builder.parse(Constant.VIRTUAL_CLUSTERS_XML_PATH);
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
        VMInfo vmInfo=new VMInfo();
        Node virtualMachines = xmlDocument.getChildNodes().item(0);
        //System.out.println("nodes:"+virtualMachines.getNodeName());
        NodeList virtualMachineList=virtualMachines.getChildNodes();
        //System.out.println("virtual machine:"+virtualMachine.getLength());
        for (int i = 0; i < virtualMachineList.getLength(); i++) {
            NodeList vmInfoNodeList = virtualMachineList.item(i).getChildNodes();
            vmInfo.setVmID(vmInfoNodeList.item(0).getTextContent());
            vmInfo.setVmType(vmInfoNodeList.item(1).getTextContent());
            vmInfo.setVmState(vmInfoNodeList.item(2).getTextContent());
            vmInfo.setVmPublicIpAddress(vmInfoNodeList.item(3).getTextContent());
            vmInfo.setVmPrivateIpAddress(vmInfoNodeList.item(4).getTextContent());
            vmInfo.setVmKeyName(vmInfoNodeList.item(5).getTextContent());
            vmInfo.setVmPort(vmInfoNodeList.item(6).getTextContent());
            vmInfo.setVmHostname(vmInfoNodeList.item(7).getTextContent());
            vmInfoList.add(vmInfo);
            System.out.println(vmInfo.getVmHostname()+vmInfo.getVmID()+vmInfo.getVmKeyName()+vmInfo.getVmPort()+vmInfo.getVmPrivateIpAddress()+vmInfo.getVmPublicIpAddress()+vmInfo.getVmState()+vmInfo.getVmType());
                /*System.out.println("vminfo:"+vmInfoNodeList.getLength());
                for (int k=0;k<vmInfoNodeList.getLength();k++){
                    System.out.println(k+":"+vmInfoNodeList.item(k).getTextContent());
                } */

        }
        return vmInfoList;
    }
}

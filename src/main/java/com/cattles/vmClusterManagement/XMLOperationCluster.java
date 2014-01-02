package com.cattles.vmClusterManagement;

import com.cattles.util.Constant;
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

    /**
     * initialize the xml operation object
     */
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
     * add a cluster to the VirtualClusters.xml
     * @param _virtualCluster
     * @return
     */
    public boolean addCluster(VirtualCluster _virtualCluster){
        boolean success=false;
        Node virtualClusters = xmlDocument.getChildNodes().item(0);
        Node virtualClusterNode=xmlDocument.createElement("VirtualCluster");
        Node clusterID=xmlDocument.createElement("clusterID");
        Node clusterType=xmlDocument.createElement("clusterType");
        Node clusterState=xmlDocument.createElement("clusterState");
        Node clusterSize=xmlDocument.createElement("clusterSize");
        Node serverID=xmlDocument.createElement("serverID");
        Node nodes=xmlDocument.createElement("nodes");
        clusterID.setTextContent(_virtualCluster.getClusterID());
        clusterType.setTextContent(_virtualCluster.getClusterType());
        clusterState.setTextContent(_virtualCluster.getClusterState());
        clusterSize.setTextContent(String.valueOf(_virtualCluster.getClusterSize()));
        serverID.setTextContent(_virtualCluster.getClusterServerID());
        for(int nodeNum=0;nodeNum<_virtualCluster.getNodesIDList().size();nodeNum++){
            Node node= xmlDocument.createElement("node");
            node.setTextContent(_virtualCluster.getNodesIDList().get(nodeNum));
            nodes.appendChild(node);
        }

        virtualClusterNode.appendChild(clusterID);
        virtualClusterNode.appendChild(clusterType);
        virtualClusterNode.appendChild(clusterState);
        virtualClusterNode.appendChild(clusterSize);
        virtualClusterNode.appendChild(serverID);
        virtualClusterNode.appendChild(nodes);
        virtualClusters.appendChild(virtualClusterNode);

        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_CLUSTERS_XML_PATH));
            transformer.transform(source, result);
        }catch (TransformerConfigurationException e){
            System.out.println(e.getMessage());
        }catch (TransformerException e){
            System.out.println(e.getMessage());
        }
        return success;
    }

    /**
     * call addCluster() method to add a list of clusters
     * @param _virtualClusterArrayList
     * @return
     */
    public boolean addClusters(ArrayList<VirtualCluster> _virtualClusterArrayList){
        boolean success=false;
        for(VirtualCluster virtualCluster:_virtualClusterArrayList){
            success=addCluster(virtualCluster);
        }
        return success;
    }

    /**
     * delete a cluster from the VirtualClusters.xml
     * @param _clusterID
     * @return
     */
    public boolean deleteCluster(String _clusterID){
        boolean success=false;
        Node virtualClusters = xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClusters.getChildNodes();
        for (int i = 0; i < virtualClusterList.getLength(); i++) {
            NodeList virtualClusterInfoList = virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(0).getTextContent().equals(_clusterID)){
                //xmlDocument.getChildNodes().item(0).removeChild(xmlDocument.getChildNodes().item(0).getChildNodes().item(i));
                virtualClusters.removeChild(virtualClusterList.item(i));
            }
        }
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_CLUSTERS_XML_PATH));
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
     * call the deleteCluster() method to delete a list of clusters
     * @param _clusterIDList
     * @return
     */
    public boolean deleteClusters(ArrayList<String> _clusterIDList){
        boolean success=false;
        for (String clusterID:_clusterIDList){
            success=deleteCluster(clusterID);
        }
        return success;
    }

    /**
     * add a node to the VirtualClusters.xml
     * @param _clusterID
     * @param _nodeID
     * @return
     */
    public boolean addNode(String _clusterID,String _nodeID){
        boolean success=false;
        Node virtualClusters = xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClusters.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(0).getTextContent().equals(_clusterID)){
                Node node=xmlDocument.createElement("node");
                node.setTextContent(_nodeID);
                virtualClusterInfoList.item(5).appendChild(node);
                //modify the cluster size after adding a node
                int clusterSize= Integer.parseInt(virtualClusterInfoList.item(3).getTextContent());
                clusterSize++;
                virtualClusterInfoList.item(3).setTextContent(String.valueOf(clusterSize));
            }
        }

        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_CLUSTERS_XML_PATH));
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
     * call addNode() method to add a list of nodes
     * @param _clusterID
     * @param _nodeIDList
     * @return
     */
    public boolean addNodes(String _clusterID, ArrayList<String> _nodeIDList){
        boolean success=false;
        for (String nodeID:_nodeIDList){
            success=addNode(_clusterID, nodeID);
        }
        return success;
    }

    /**
     * remove a node from the VirtualClusters.xml
     * @param _clusterID
     * @param _nodeID
     * @return
     */
    public boolean removeNode(String _clusterID,String _nodeID){
        boolean success=false;
        Node virtualClusters = xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClusters.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(0).getTextContent().equals(_clusterID)){
                NodeList nodeList=virtualClusterInfoList.item(5).getChildNodes();
                for(int j=0;j<nodeList.getLength();j++){
                    if(nodeList.item(j).getTextContent().equals(_nodeID)){
                        virtualClusterInfoList.item(5).removeChild(nodeList.item(j));
                        //modify the cluster size after adding a node
                        int clusterSize= Integer.parseInt(virtualClusterInfoList.item(3).getTextContent());
                        clusterSize--;
                        virtualClusterInfoList.item(3).setTextContent(String.valueOf(clusterSize));
                    }
                }
            }
        }

        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_CLUSTERS_XML_PATH));
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
     * call removeNode() method to remove a list of nodes
     * @param _clusterID
     * @param _nodeIDList
     * @return
     */
    public boolean removeNodes(String _clusterID,ArrayList<String> _nodeIDList){
        boolean success=false;
        for(String nodeID:_nodeIDList){
            success=removeNode(_clusterID, nodeID);
        }
        return success;
    }

    /**
     * modify the serverID parameter according to the _clusterID
     * @param _clusterID
     * @return
     */
    public boolean modifyServerID(String _clusterID,String _serverID){
        boolean success=false;
        Node virtualClusters = xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClusters.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(0).getTextContent().equals(_clusterID)){
                virtualClusterInfoList.item(4).setTextContent(_serverID);
            }
        }

        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_CLUSTERS_XML_PATH));
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
     * modify the state of cluster, such as "activated", "standby"
     * @param _clusterID
     * @param _clusterState
     * @return
     */
    public boolean modifyClusterState(String _clusterID,String _clusterState){
        boolean success=false;
        Node virtualClusters = xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClusters.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(0).getTextContent().equals(_clusterID)){
                virtualClusterInfoList.item(2).setTextContent(_clusterState);
            }
        }

        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = tFactory.newTransformer();
            DOMSource source = new DOMSource(xmlDocument);
            StreamResult result = new StreamResult(new java.io.File(Constant.VIRTUAL_CLUSTERS_XML_PATH));
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
     * get the cluster size, which is the number of nodes,except the server node
     * @param _clusterID
     * @return
     */
    public int getClusterSize(String _clusterID){
        int clusterSize=0;
        Node virtualClusters = xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClusters.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(0).getTextContent().equals(_clusterID)){
                clusterSize=Integer.parseInt(virtualClusterInfoList.item(3).getTextContent());
            }
        }
        return clusterSize;
    }

    /**
     * get all the clusters in the VirtualClusters.xml
     * @return
     */
    public ArrayList<VirtualCluster> getAllClusters(){
        ArrayList<VirtualCluster> virtualClusters=new ArrayList<VirtualCluster>();
        Node virtualClustersXML=xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClustersXML.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            VirtualCluster virtualCluster=new VirtualCluster();
            virtualCluster.setClusterID(virtualClusterInfoList.item(0).getTextContent());
            virtualCluster.setClusterType(virtualClusterInfoList.item(1).getTextContent());
            virtualCluster.setClusterState(virtualClusterInfoList.item(2).getTextContent());
            virtualCluster.setClusterSize(Integer.parseInt(virtualClusterInfoList.item(3).getTextContent()));
            virtualCluster.setClusterServerID(virtualClusterInfoList.item(4).getTextContent());
            NodeList nodeList=virtualClusterInfoList.item(5).getChildNodes();
            ArrayList<String> nodes=new ArrayList<String>();
            for(int j=0;j<nodeList.getLength();j++){
                nodes.add(nodeList.item(j).getTextContent());
            }
            virtualCluster.setNodesIDList(nodes);
            virtualClusters.add(virtualCluster);
        }
        return virtualClusters;
    }

    /**
     * get a cluster according to the clusterID specified
     * @param _clusterID
     * @return
     */
    public VirtualCluster getClusterWithID(String _clusterID){
        VirtualCluster virtualCluster=new VirtualCluster();
        Node virtualClustersXML=xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClustersXML.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(0).getTextContent().equals(_clusterID)){
                virtualCluster.setClusterID(virtualClusterInfoList.item(0).getTextContent());
                virtualCluster.setClusterType(virtualClusterInfoList.item(1).getTextContent());
                virtualCluster.setClusterState(virtualClusterInfoList.item(2).getTextContent());
                virtualCluster.setClusterSize(Integer.parseInt(virtualClusterInfoList.item(3).getTextContent()));
                virtualCluster.setClusterServerID(virtualClusterInfoList.item(4).getTextContent());
                NodeList nodeList=virtualClusterInfoList.item(5).getChildNodes();
                ArrayList<String> nodes=new ArrayList<String>();
                for(int j=0;j<nodeList.getLength();j++){
                    nodes.add(nodeList.item(j).getTextContent());
                }
                virtualCluster.setNodesIDList(nodes);
            }
        }
        return virtualCluster;
    }

    /**
     * get the cluster list according to the cluster type(falkon, gearman)
     * @param _clusterType
     * @return
     */
    public ArrayList<VirtualCluster> getClustersWithType(String _clusterType){
        ArrayList<VirtualCluster> virtualClusters=new ArrayList<VirtualCluster>();
        Node virtualClustersXML=xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClustersXML.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(1).getTextContent().equals(_clusterType)){
                VirtualCluster virtualCluster=new VirtualCluster();
                virtualCluster.setClusterID(virtualClusterInfoList.item(0).getTextContent());
                virtualCluster.setClusterType(virtualClusterInfoList.item(1).getTextContent());
                virtualCluster.setClusterState(virtualClusterInfoList.item(2).getTextContent());
                virtualCluster.setClusterSize(Integer.parseInt(virtualClusterInfoList.item(3).getTextContent()));
                virtualCluster.setClusterServerID(virtualClusterInfoList.item(4).getTextContent());
                NodeList nodeList=virtualClusterInfoList.item(5).getChildNodes();
                ArrayList<String> nodes=new ArrayList<String>();
                for(int j=0;j<nodeList.getLength();j++){
                    nodes.add(nodeList.item(j).getTextContent());
                }
                virtualCluster.setNodesIDList(nodes);
                virtualClusters.add(virtualCluster);
            }
        }
        return virtualClusters;
    }

    /**
     * get the cluster list according to the cluster state(activated, standby)
     * @param _state
     * @return
     */
    public ArrayList<VirtualCluster> getClustersWithState(String _state){
        ArrayList<VirtualCluster> virtualClusters=new ArrayList<VirtualCluster>();

        Node virtualClustersXML=xmlDocument.getChildNodes().item(0);
        NodeList virtualClusterList=virtualClustersXML.getChildNodes();
        for (int i=0;i<virtualClusterList.getLength();i++){
            NodeList virtualClusterInfoList=virtualClusterList.item(i).getChildNodes();
            if(virtualClusterInfoList.item(2).getTextContent().equals(_state)){
                VirtualCluster virtualCluster=new VirtualCluster();
                virtualCluster.setClusterID(virtualClusterInfoList.item(0).getTextContent());
                virtualCluster.setClusterType(virtualClusterInfoList.item(1).getTextContent());
                virtualCluster.setClusterState(virtualClusterInfoList.item(2).getTextContent());
                virtualCluster.setClusterSize(Integer.parseInt(virtualClusterInfoList.item(3).getTextContent()));
                virtualCluster.setClusterServerID(virtualClusterInfoList.item(4).getTextContent());
                NodeList nodeList=virtualClusterInfoList.item(5).getChildNodes();
                ArrayList<String> nodes=new ArrayList<String>();
                for(int j=0;j<nodeList.getLength();j++){
                    nodes.add(nodeList.item(j).getTextContent());
                }
                virtualCluster.setNodesIDList(nodes);
                virtualClusters.add(virtualCluster);
            }
        }
        return virtualClusters;
    }


    public static void main(String[] args){
        XMLOperationCluster xmlOperationCluster=XMLOperationCluster.getXmlOperationCluster();

        //add a cluster
        /*VirtualCluster virtualCluster=new VirtualCluster();
        virtualCluster.setClusterID("ti4325cs");
        virtualCluster.setClusterState("activated");
        virtualCluster.setClusterSize(2);
        virtualCluster.setClusterServerID("i-ba4534d");
        ArrayList<String> nodeList=new ArrayList<String>();
        nodeList.add("i-sf453wr");
        nodeList.add("i-yu4536i");
        virtualCluster.setNodesIDList(nodeList);
        xmlOperationCluster.addCluster(virtualCluster);*/

        //add a list of clusters
        /*ArrayList<VirtualCluster> virtualClusterArrayList=new ArrayList<VirtualCluster>();
        for(int i=0;i<2;i++){
            VirtualCluster virtualCluster=new VirtualCluster();
            virtualCluster.setClusterID("ti4325cs");
            virtualCluster.setClusterState("activated");
            virtualCluster.setClusterSize(2);
            virtualCluster.setClusterServerID("i-ba4534d");
            ArrayList<String> nodeList=new ArrayList<String>();
            nodeList.add("i-ba4534d");
            nodeList.add("i-ba4534d");
            virtualCluster.setNodesIDList(nodeList);
            virtualClusterArrayList.add(virtualCluster);
        }
        xmlOperationCluster.addClusters(virtualClusterArrayList);*/

        //delete a cluster
        //xmlOperationCluster.deleteCluster("ti4325cs");

        //delete a list of clusters
        /*ArrayList<String> clusterIDList=new ArrayList<String>();
        clusterIDList.add("ti4325cs");
        clusterIDList.add("ti4325cs");
        xmlOperationCluster.deleteClusters(clusterIDList);*/

        //add a node
        /*String clusterID="ti4325cs";
        String nodeID="node-idsgst" ;
        xmlOperationCluster.addNode(clusterID,nodeID);*/

        //add a list of nodes
        /*String clusterID="ti4325cs";
        ArrayList<String> nodeIDList=new ArrayList<String>();
        String nodeID="node-idsgst";
        String nodeID2="node-222222";
        nodeIDList.add(nodeID);
        nodeIDList.add(nodeID2);
        xmlOperationCluster.addNodes(clusterID,nodeIDList);*/

        //remove a node
        /*String clusterID="ti4325cs";
        String nodeID="node-idsgst" ;
        xmlOperationCluster.removeNode(clusterID,nodeID);*/

        //remove a list of nodes
        /*String clusterID="ti4325cs";
        ArrayList<String> nodeIDList=new ArrayList<String>();
        String nodeID="node-idsgst";
        String nodeID2="node-222222";
        nodeIDList.add(nodeID);
        nodeIDList.add(nodeID2);
        xmlOperationCluster.removeNodes(clusterID,nodeIDList);*/

        //modify serverID
        /*String clusterID="ti4325cs";
        String serverID="server-idsgst" ;
        xmlOperationCluster.modifyServerID(clusterID,serverID);*/

        //modify clusterState
        /*String clusterID="ti4325cs";
        String clusterState="standby" ;
        xmlOperationCluster.modifyClusterState(clusterID,clusterState);*/

        //get clusters with the state
        /*String state="activated";
        ArrayList<VirtualCluster> virtualClusterArrayList=new ArrayList<VirtualCluster>();
        virtualClusterArrayList=xmlOperationCluster.getClustersWithState(state);
        System.out.println(virtualClusterArrayList.get(1).getClusterID());*/

        //get cluster with the clusterID
        /*String clusterID="ti4325cs";
        VirtualCluster virtualCluster=xmlOperationCluster.getClusterWithID(clusterID);
        System.out.println(virtualCluster.getClusterID());*/

        //get all clusters
        /*ArrayList<VirtualCluster> virtualClusterArrayList=new ArrayList<VirtualCluster>();
        virtualClusterArrayList=xmlOperationCluster.getAllClusters();
        System.out.println(virtualClusterArrayList.get(3).getClusterID()); */

        //get cluster size
        System.out.println(xmlOperationCluster.getClusterSize("ti4325cs"));
    }
}

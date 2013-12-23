package com.cattles.util;


import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/18/13
 * Time: 3:44 PM
 * read the platform_conf.xml
 */
public class XMLOperationPlatform {
    public Document xmlDocument;
    private static XMLOperationPlatform xmlOperationPlatform = null;
    private XMLOperationPlatform(){

    }
    public static synchronized XMLOperationPlatform getXmlOperationPlatform(){
        if (xmlOperationPlatform==null){
            xmlOperationPlatform=new XMLOperationPlatform();
            xmlOperationPlatform.init();
        }
        return xmlOperationPlatform;
    }
    private void init() {
        // TODO Auto-generated method stub
        try{
            DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            this.xmlDocument=builder.parse(Constant.PLATFORM_CONF_PATH);
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
     * get the platform name from the platform_conf file.
     * tagName platform_name
     * @return vmInfoList
     */
    public String getPlatformName(){
        String platformName="";
        NodeList platform_nameList=xmlDocument.getElementsByTagName("platform_name");
        if(platform_nameList.getLength()>=1){
            platformName=platform_nameList.item(0).getTextContent();
        }
        return platformName;
    }

    public int getPoolInitializationSize(){
        int initializationSize=6;
        NodeList initializationSize_List=xmlDocument.getElementsByTagName("poolInitializationSize");
        if(initializationSize_List.getLength()>=1){
            initializationSize=Integer.parseInt(initializationSize_List.item(0).getTextContent());
        }
        return initializationSize;
    }

    public static void main(String[] args){
        XMLOperationPlatform xmlOperationPlatform=XMLOperationPlatform.getXmlOperationPlatform();
        System.out.println(xmlOperationPlatform.getPlatformName());
    }
}

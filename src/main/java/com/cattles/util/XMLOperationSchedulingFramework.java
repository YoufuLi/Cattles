package com.cattles.util;


import org.apache.log4j.Logger;
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
public class XMLOperationSchedulingFramework {
    private static Logger logger = Logger.getLogger(XMLOperationSchedulingFramework.class);
    public Document xmlDocument;
    private static XMLOperationSchedulingFramework xmlOperationSchedulingFramework = null;
    private XMLOperationSchedulingFramework(){

    }
    public static synchronized XMLOperationSchedulingFramework getXmlOperationPlatform(){
        if (xmlOperationSchedulingFramework==null){
            xmlOperationSchedulingFramework=new XMLOperationSchedulingFramework();
            xmlOperationSchedulingFramework.init();
        }
        return xmlOperationSchedulingFramework;
    }
    private void init() {
        // TODO Auto-generated method stub
        try{
            DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
            DocumentBuilder builder=factory.newDocumentBuilder();
            this.xmlDocument=builder.parse(Constant.SCHEDULING_FRAMEWORK_CONF_PATH);
            xmlDocument.normalize();
            logger.info("initialize successfully!");
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
    public String getFrameworkName(){
        String frameworkName="";
        NodeList framework_nameList=xmlDocument.getElementsByTagName("framework_name");
        if(framework_nameList.getLength()>=1){
            frameworkName=framework_nameList.item(0).getTextContent();
        }
        return frameworkName;
    }

    public static void main(String[] args){
        XMLOperationSchedulingFramework xmlOperationSchedulingFramework=XMLOperationSchedulingFramework.getXmlOperationPlatform();
        System.out.println(xmlOperationSchedulingFramework.getFrameworkName());
    }
}

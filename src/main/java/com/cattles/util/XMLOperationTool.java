package com.cattles.util;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.FileOutputStream; 
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory; 
import javax.xml.parsers.ParserConfigurationException; 
import javax.xml.transform.OutputKeys; 
import javax.xml.transform.Transformer; 
import javax.xml.transform.TransformerConfigurationException; 
import javax.xml.transform.TransformerException; 
import javax.xml.transform.TransformerFactory; 
import javax.xml.transform.dom.DOMSource; 
import javax.xml.transform.stream.StreamResult; 
import org.w3c.dom.Document; 
import org.w3c.dom.Element; 
import org.w3c.dom.Node; 
import org.w3c.dom.NodeList; 
import org.xml.sax.SAXException; 

import com.cattles.vmManagement.*;


/** 
 * 
 * @author YoufuLi
 * DOM生成与解析XML文档 
 */ 

public class XMLOperationTool {
	public String xmlFilePath;
	public Document xmlDocument;
	private void init() {
		// TODO Auto-generated method stub
		try{
			DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
			DocumentBuilder builder=factory.newDocumentBuilder();
			this.xmlDocument=builder.newDocument();
		}
		catch (ParserConfigurationException e) {
			// TODO: handle exception
            System.out.println(e.getMessage());
		}
	}
	public List<VMInfo> readVMResourceXML(){
		List<VMInfo> vmResourceList=null;
		return vmResourceList;
	}
	public void writeVMResourceXML(){

	}
	public Boolean updateVMState(String IPAddress){
		if(true){
			return true;
		}
		else {
			return false;
		}
	}
}
/*class DomDemo {
	private Document document; 
	private String fileName; 
	public void init() { 
		try { 
			DocumentBuilderFactory factory = DocumentBuilderFactory 
					.newInstance(); 
			DocumentBuilder builder = factory.newDocumentBuilder(); 
			this.document = builder.newDocument(); 
		} catch (ParserConfigurationException e) { 
			System.out.println(e.getMessage()); 
		} 
	} 
	public void createXml(String fileName) { 
		Element root = this.document.createElement("employees"); 
		this.document.appendChild(root); 
		Element employee = this.document.createElement("employee"); 
		Element name = this.document.createElement("name"); 
		name.appendChild(this.document.createTextNode("丁宏亮")); 
		employee.appendChild(name); 
		Element sex = this.document.createElement("sex"); 
		sex.appendChild(this.document.createTextNode("m")); 
		employee.appendChild(sex); 
		Element age = this.document.createElement("age"); 
		age.appendChild(this.document.createTextNode("30")); 
		employee.appendChild(age); 
		root.appendChild(employee); 
		TransformerFactory tf = TransformerFactory.newInstance(); 
		try { 
			Transformer transformer = tf.newTransformer(); 
			DOMSource source = new DOMSource(document); 
			transformer.setOutputProperty(OutputKeys.ENCODING, "gb2312"); 
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); 
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName)); 
			StreamResult result = new StreamResult(pw); 
			transformer.transform(source, result); 
			System.out.println("生成XML文件成功!"); 
		} catch (TransformerConfigurationException e) { 
			System.out.println(e.getMessage()); 
		} catch (IllegalArgumentException e) { 
			System.out.println(e.getMessage()); 
		} catch (FileNotFoundException e) { 
			System.out.println(e.getMessage()); 
		} catch (TransformerException e) { 
			System.out.println(e.getMessage()); 
		} 
	} 
	public void parserXml(String fileName) { 
		try { 
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance(); 
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document document = db.parse(fileName); 
			NodeList employees = document.getChildNodes(); 
			for (int i = 0; i < employees.getLength(); i++) { 
				Node employee = employees.item(i); 
				NodeList employeeInfo = employee.getChildNodes(); 
				for (int j = 0; j < employeeInfo.getLength(); j++) { 
					Node node = employeeInfo.item(j); 
					NodeList employeeMeta = node.getChildNodes(); 
					for (int k = 0; k < employeeMeta.getLength(); k++) { 
						System.out.println(employeeMeta.item(k).getNodeName() 
								+ ":" + employeeMeta.item(k).getTextContent()); 
					} 
				} 
			} 
			System.out.println("解析完毕"); 
		} catch (FileNotFoundException e) { 
			System.out.println(e.getMessage()); 
		} catch (ParserConfigurationException e) { 
			System.out.println(e.getMessage()); 
		} catch (SAXException e) { 
			System.out.println(e.getMessage()); 
		} catch (IOException e) { 
			System.out.println(e.getMessage()); 
		} 
	} 
}*/




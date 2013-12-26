/**
 * 文件名：FalkonWorkerDeRegistration.java
 * 创建时间：Nov 24, 2011
 * 创建者：xiong rong
 */
package com.cattles.schedulingframeworks.falkon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;

import javax.xml.rpc.Stub;

import org.apache.axis.message.addressing.Address;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.GenericPortal.services.core.WS.impl.GPConstants;
import org.globus.GenericPortal.stubs.Factory.CreateResource;
import org.globus.GenericPortal.stubs.Factory.CreateResourceResponse;
import org.globus.GenericPortal.stubs.Factory.FactoryPortType;
import org.globus.GenericPortal.stubs.Factory.service.FactoryServiceAddressingLocator;
import org.globus.GenericPortal.stubs.GPService_instance.GPPortType;
import org.globus.GenericPortal.stubs.GPService_instance.WorkerDeRegistration;
import org.globus.GenericPortal.stubs.GPService_instance.WorkerDeRegistrationResponse;
import org.globus.GenericPortal.stubs.GPService_instance.service.GPServiceAddressingLocator;
import org.globus.axis.util.Util;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.encoding.DeserializationException;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.encoding.SerializationException;
import org.globus.wsrf.security.Constants;
import org.globus.wsrf.utils.AddressingUtils;
import org.xml.sax.InputSource;

import com.cattles.schedulingframeworks.falkon.common.StopWatch;
import com.cattles.schedulingframeworks.falkon.common.WorkQueue;


/**
 * @author xiong rong
 *
 */
public class FalkonWorkerDeRegistration {


	public static final Log logger = LogFactory.getLog(FalkonMonitor.class);

	private static final String WORKER_GRAM_VERSION = "v0.2.0";

	private static String CLIENT_DESC = null;

	static {
		Util.registerTransport();
	}

	private String fileEPR = null;
	private String serviceURI;
	private EndpointReferenceType homeEPR;

	GPPortType ap;

	WorkQueue threadQ;
	StopWatch lt;

	long elapsedTime;

	public FalkonWorkerDeRegistration(String serviceURI) {
		this.lt = new StopWatch();
		this.serviceURI = serviceURI;
	}

	public String FALKON_WORKER_HOME = null;

	public EndpointReferenceType getEPR(ResourceKey key) throws Exception {
		String instanceURI = homeEPR.getAddress().toString();
		return (EndpointReferenceType) AddressingUtils.createEndpointReference(
				instanceURI, key);
	}

	public EndpointReferenceType getEPR(FileInputStream fis) throws Exception, DeserializationException {
		return (EndpointReferenceType) ObjectDeserializer.deserialize(
				new InputSource(fis), EndpointReferenceType.class);
	}

	public EndpointReferenceType getEPR(String serviceURI) throws Exception {
		EndpointReferenceType epr = new EndpointReferenceType();
		epr.setAddress(new Address(serviceURI));
		return epr;
	}

	public GPPortType getGPPortType(EndpointReferenceType epr) throws Exception {
		GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
		return instanceLocator.getGPPortTypePort(epr);

	}

	public GPPortType getGPPortType() throws Exception {
		if (homeEPR == null) {
			throw new Exception(
					"homeEPR == null, probably EPR was not correctly read from file");
		} else {

			GPServiceAddressingLocator instanceLocator = new GPServiceAddressingLocator();
			return instanceLocator.getGPPortTypePort(homeEPR);
		}

	}

	public boolean createWorkerResource(String factoryURI, String eprFilename) throws SerializationException {
		
		// static final Object EPR_FILENAME = "epr.txt";
		FactoryServiceAddressingLocator factoryLocator = new FactoryServiceAddressingLocator();

		try {

			EndpointReferenceType factoryEPR, instanceEPR;
			FactoryPortType apFactory;

			// Get factory portType
			factoryEPR = new EndpointReferenceType();
			System.out.println("~~~~~~~~~~~~~~~~~~~" + factoryURI);
			factoryEPR.setAddress(new Address(factoryURI));
			apFactory = factoryLocator.getFactoryPortTypePort(factoryEPR);

			if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists()) {
				logger.debug("Setting appropriate security from file '"
						+ CLIENT_DESC + "'!");
				((Stub) apFactory)._setProperty(
						Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);

			}

			CreateResourceResponse createResponse = apFactory
					.createResource(new CreateResource());
			instanceEPR = createResponse.getEndpointReference();

			String endpointString = ObjectSerializer.toString(instanceEPR,
					GPConstants.RESOURCE_REFERENCE);
			FileWriter fileWriter = new FileWriter(eprFilename);
			BufferedWriter bfWriter = new BufferedWriter(fileWriter);
			bfWriter.write(endpointString);
			bfWriter.close();
			logger.info("Endpoint reference written to file " + eprFilename);
			return true;
		} catch (Exception e) {
			logger.error("" + e);
			return false;
		}
	}

	private boolean readWorkerResource(String fileEPR) throws Exception, DeserializationException {

		// fileEPR = new String(args[ctr]);
		boolean exists = (new File(fileEPR)).exists();
		if (exists) {

			// Get endpoint reference of WS-Resource from file
			FileInputStream fis = new FileInputStream(fileEPR);

			homeEPR = (EndpointReferenceType) ObjectDeserializer.deserialize(
					new InputSource(fis), EndpointReferenceType.class);

			if (homeEPR == null) {
				throw new Exception(
						"parseArgs(): homeEPR == null, probably EPR was not correctly read from file");
			} else {

				logger.info("parseArgs(): homeEPR is set correctly");
			}

			fis.close();
			return true;
		} else {
			return false;
		}
	}

	public void deregster(String machID) throws Exception, SerializationException, DeserializationException {
		logger.debug("WORKERS-GRAM: " + WORKER_GRAM_VERSION);
		StopWatch sw = new StopWatch();
		lt.start();

		if (fileEPR == null)
			fileEPR = "WorkerEPR.txt";

		logger.debug("WORKERS: Creating worker resource...");
		if (createWorkerResource(serviceURI, fileEPR)) {
			logger.debug("WORKERS: Created worker resource... saved in '"
					+ fileEPR + "'");
		} else
			throw new Exception(
					"main_run(): createWorkerResource() failed, could not create worker resource...");

		if (readWorkerResource(fileEPR)) {
			logger.debug("WORKERS: initialized worker reasource state from '"
					+ fileEPR + "'");

		} else
			throw new Exception(
					"main_run(): readWorkerResource() failed, could not read worker resource from '"
							+ fileEPR + "'");

		if (homeEPR == null) {
			throw new Exception(
					"main_run(): homeEPR == null, something is wrong");
		} else {

			logger.debug("main_run(): homeEPR is OK");
		}

		sw.start();
		// Get PortType
		ap = getGPPortType();// = instanceLocator

		if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists()) {
			logger.debug("Setting appropriate security from file '"
					+ CLIENT_DESC + "'");
			((Stub) ap)._setProperty(Constants.CLIENT_DESCRIPTOR_FILE,
					CLIENT_DESC);

		}

		sw.stop();
		logger.debug("WORKER: Get PortType (" + sw.getElapsedTime() + "ms)");

		logger.info("WORKER:getGPPortType(): " + sw.getElapsedTime() + " ms");
		sw.reset();

		WorkerDeRegistrationResponse mwsr = this.workerDeReg(machID);
		System.out.println(mwsr.isValid());
	}
	
	public WorkerDeRegistrationResponse workerDeReg(String machID) {
		WorkerDeRegistration sr = new WorkerDeRegistration();
		sr.setMachID(machID);
		sr.setService(false);
		
		logger.debug("Retrieving GPWS WorkerDeRegistration...");

		try {

			WorkerDeRegistrationResponse wdrr = ap.workerDeRegistration(sr);
			return wdrr;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * author:xiong rong
	 * 功能：
	 * @param args
	 */
	public static void main(String[] args) throws DeserializationException, SerializationException {
		String serviceURI = "http://172.16.254.110:50001/wsrf/services/GenericPortal/core/WS/GPFactoryService";
		FalkonWorkerDeRegistration fwn = new FalkonWorkerDeRegistration(serviceURI);
		String machID = "172.16.254.198:50100";
		try {
			fwn.deregster(machID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

//		try {
//			List<String> workIps = fwn.getWorkIp();
//			for(String workIp : workIps){
//				System.out.println(workIp);
//			}
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}

	}



}

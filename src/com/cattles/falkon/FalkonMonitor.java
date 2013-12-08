package com.cattles.falkon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import org.globus.GenericPortal.stubs.GPService_instance.MonitorWorkerState;
import org.globus.GenericPortal.stubs.GPService_instance.MonitorWorkerStateResponse;
import org.globus.GenericPortal.stubs.GPService_instance.WorkerDeRegistration;
import org.globus.GenericPortal.stubs.GPService_instance.service.GPServiceAddressingLocator;
import org.globus.axis.util.Util;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.encoding.ObjectDeserializer;
import org.globus.wsrf.encoding.ObjectSerializer;
import org.globus.wsrf.security.Constants;
import org.globus.wsrf.utils.AddressingUtils;
import org.xml.sax.InputSource;

import com.cattles.cloudplatforms.Constant;
import com.cattles.falkon.common.StopWatch;
import com.cattles.falkon.common.WorkQueue;


public class FalkonMonitor {

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

	public FalkonMonitor(String serviceURI) {
		this.lt = new StopWatch();
		this.serviceURI = serviceURI;
	}

	public String FALKON_WORKER_HOME = null;

	public EndpointReferenceType getEPR(ResourceKey key) throws Exception {
		String instanceURI = homeEPR.getAddress().toString();
		return (EndpointReferenceType) AddressingUtils.createEndpointReference(
				instanceURI, key);
	}

	public EndpointReferenceType getEPR(FileInputStream fis) throws Exception {
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

	public boolean createWorkerResource(String factoryURI, String eprFilename) {
		
		// static final Object EPR_FILENAME = "epr.txt";
		FactoryServiceAddressingLocator factoryLocator = new FactoryServiceAddressingLocator();

		try {

			EndpointReferenceType factoryEPR, instanceEPR;
			FactoryPortType apFactory;

			// Get factory portType
			factoryEPR = new EndpointReferenceType();
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

	private boolean readWorkerResource(String fileEPR) throws Exception {

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

	public List<String> getWorkIp() throws Exception {
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

		
		List<String> tmpWorkIpList = null;	
		List<String> workIpList = new ArrayList<String>();	
		MonitorWorkerStateResponse mwsr = this.getMonitorWorkerState();
		String[] strArr = mwsr.getFreeWorkers();

		if(strArr != null){
			tmpWorkIpList = this.getStr(strArr);
			workIpList.addAll(tmpWorkIpList);
		}
		strArr = mwsr.getPendWorkers();
		if(strArr != null){
			tmpWorkIpList = this.getStr(strArr);
			workIpList.addAll(tmpWorkIpList);
		}
		strArr = mwsr.getBusyWorkers();
		if(strArr != null){
			tmpWorkIpList = this.getStr(strArr);
			workIpList.addAll(tmpWorkIpList);
		}
		return workIpList;
	}
	
	public List<String> getMachIDList() throws Exception {
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

		
		List<String> machIDList = new ArrayList<String>();	
		MonitorWorkerStateResponse mwsr = this.getMonitorWorkerState();
		String[] strArr = mwsr.getFreeWorkers();
		if(strArr != null){
			machIDList.addAll(Arrays.asList(strArr));
		}
		strArr = mwsr.getPendWorkers();
		if(strArr != null){
			machIDList.addAll(Arrays.asList(strArr));
		}
		strArr = mwsr.getBusyWorkers();
		if(strArr != null){
			machIDList.addAll(Arrays.asList(strArr));
		}
		return machIDList;
	}

	/**
	 * 
	 * author:xiong rong
	 * 功能：对workerIp信息进行处理得到ip地址，去除端口号
	 * @param args
	 */
	public List<String> getStr(String[] strArr){
		List<String> workIplist = new ArrayList<String>();
		for(String str : strArr){
			String[] s = str.split(":");
			workIplist.add(s[0]);
		}
		return workIplist;
	}
	
	public MonitorWorkerStateResponse getMonitorWorkerState() {

		MonitorWorkerState stat = new MonitorWorkerState("");
		logger.debug("Retrieving GPWS MonitorWorkerState...");

		try {

			MonitorWorkerStateResponse sr = ap.monitorWorkerState(stat);
			return sr;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * author:xiong rong
	 * 功能： 通过service的ip地址更新serviceWorkerMap,只要falkon service
	 * 或者falkon worker改变都需要及时更新
	 * @param args
	 */
	public void updateMap(String serviceURI,String serviceIP) throws Exception {
		List<String> workList = this.getWorkIp();
		if (workList != null) {
			if (Constant.serviceWorkerMap.containsKey(serviceIP)) {
				Constant.serviceWorkerMap.remove(serviceIP);
			}
			Constant.serviceWorkerMap.put(serviceIP, workList);
		}
	}
	public static void main(String[] args) {
		String serviceURI = "http://172.16.254.110:50001/wsrf/services/GenericPortal/core/WS/GPFactoryService";
		FalkonMonitor fwn = new FalkonMonitor(serviceURI);
		try {
			List<String> workIps = fwn.getMachIDList();
			for(String workIp : workIps){
				System.out.println(workIp);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}
}

package com.cattles.falkon;

//SVN v0.8.1
/*
 * This file is licensed under the terms of the Globus Toolkit Public License
 * v3, found at http://www.globus.org/toolkit/legal/4.0/license-v3.html.
 * 
 * This notice must appear in redistributions of this file, with or without
 * modification.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
import org.globus.GenericPortal.stubs.GPService_instance.Status;
import org.globus.GenericPortal.stubs.GPService_instance.StatusResponse;
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

import com.cattles.falkon.common.Notification;
import com.cattles.falkon.common.StopWatch;
import com.cattles.falkon.common.WorkQueue;


public class FalkonWorkerNum implements CalWorkerNum {

	private int numWorkers; 
	
	private int host2Allocate;

	public static final Log logger = LogFactory.getLog(FalkonWorkerNum.class);

	public static final String WORKER_GRAM_VERSION = "v0.2.0";

	public Map<String, String> provisionerConf = Collections
			.synchronizedMap(new HashMap<String, String>());
	
	public Map<String, String> falkonInfoHM = Collections
	.synchronizedMap(new HashMap<String, String>());

	public int provisionerConfigInterval = 60; // default value in sec
	public String provisionerConfigFile = null;

	public String drpLogFileName = new String("drp-status.txt");

	// 读取ProvConfig文件
	private synchronized boolean readProvConfig() throws Exception {

		StopWatch sw = new StopWatch();
		sw.start();
		logger.debug("readProvisionerConfig()...");

		try {
			BufferedReader in = new BufferedReader(new FileReader(
					provisionerConfigFile));

			String str;
			while ((str = in.readLine()) != null) {
				if (str.startsWith("#")) {
					logger.debug("readProvisionerConfig(): ignoring comment "
							+ str);

				} else {

					String tokens[] = str.split("=");
					if (tokens.length == 2) {
						provisionerConf.put(tokens[0], tokens[1]);

					}

					else {

						logger.debug("readProvisionerConfig(): reading Provisioner config file error at "
								+ str + "... ignoring");
					}
				}

			}

			interpretProvisionerConfig();
			in.close();

			sw.stop();
			logger.debug(System.currentTimeMillis()
					+ ": WORKERS:readProvisionerConfig(): "
					+ sw.getElapsedTime() + " ms");
			sw.reset();
			return true;

		} catch (IOException e) {
			logger.error("readProvisionerConfig(): reading Provisioner config file "
					+ provisionerConfigFile + " failed: " + e);
			e.printStackTrace();
			return false;

		}
		// return false;
	}

	// 读取provisioner配置文件
	private boolean readProvisionerConfig() {
		try {

			if (provisionerConfigFile == null) {
				return false;
			} else {

				readProvConfig();

				try {
					URL serviceURL = new URL(serviceURI);
					InetAddress addr = InetAddress.getByName(serviceURL
							.getHost());
					String serviceIP = addr.getHostAddress();

					BufferedWriter out = new BufferedWriter(new FileWriter(
							falkonServiceFileName));
					out.write(serviceIP + "\n");
					out.flush();
					out.close();

				} catch (Exception e) {
					e.printStackTrace();
				}

//				int period = provisionerConfigInterval * 1000; // repeat every
//																// sec.
//				int delay = period; // delay for 5 sec.
//				final Timer timer = new Timer();
//
//				// 每隔5s读取、解析配置文件
//				timer.scheduleAtFixedRate(new TimerTask() {
//					public void run() {
//						try {
//							if (readProvConfig() == false)
//								timer.cancel();
//
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//
//					}
//				}, delay, period);

			}

			return true;

		} catch (Exception eee) {
			eee.printStackTrace();
			return false;
		}

	}

	final Object _MIN_NUM_EXECUTORS = "MinNumExecutors";
	final Object _MAX_NUM_EXECUTORS = "MaxNumExecutors";
	final Object _EXECUTORS_PER_HOST = "ExecutorsPerHost";
	final Object _MIN_RESOURCE_ALLOCATION_TIME_MIN = "MinResourceAllocationTime_min";
	final Object _MAX_RESOURCE_ALLOCATION_TIME_MIN = "MaxResourceAllocationTime_min";
	final Object _HOST_TYPE = "HostType";
	final Object _ALLOCATION_STRATEGY = "AllocationStrategy";
	final Object _MIN_NUM_HOSTS_PER_ALLOCATION = "MinNumHostsPerAllocation";
	final Object _MAX_NUM_HOSTS_PER_ALLOCATION = "MaxNumHostsPerAllocation";
	final Object _DEALLOCATION_IDLE_TIME = "DeAllocationIdleTime_sec";
	final Object _FALKON_SERVICE_URI = "FalkonServiceURI";
	final Object _EPR_FILE_NAME = "EPR_FileName";
	final Object _FALKON_SERVICE_NAME_FILE_NAME = "FalkonServiceName_FileName";
	final Object _FALKON_STATE_POLL_TIME_SEC = "FalkonStatePollTime_sec";
	final Object _GRAM4_LOCATION = "GRAM4_Location";
	final Object _GRAM4_FACTORY_TYPE = "GRAM4_FactoryType";
	final Object _PROJECT = "Project";
	final Object _EXECUTOR_SCRIPT = "ExecutorScript";
	final Object _FALKON_HOME = "FALKON_HOME";
	final Object _SECURITY_FILE = "SecurityFile";
	final Object _DEBUG = "DEBUG";
	final Object _DIPERF = "DIPERF";
	final Object _DRP_LOG_FILE_NAME = "DRP_Log";

	// drpLogFileName

	public String allocationStrategy = "one-at-a-time";
	public String GRAM4_factory_type = "PBS"; //

	public int minNumHostsPerAllocation = 1;
	public int maxNumHostsPerAllocation = 1;

	// 解析provisioner配置文件，初始化信息
	private void interpretProvisionerConfig() {
		String sTemp = null;

		sTemp = (String) provisionerConf.get(_EPR_FILE_NAME);
		if (sTemp != null) {
			try {

				File file = new File(sTemp);
				fileEPR = new String(file.getCanonicalPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		sTemp = (String) provisionerConf.get(_FALKON_SERVICE_NAME_FILE_NAME);
		if (sTemp != null) {
			try {

				File file = new File(sTemp);
				falkonServiceFileName = new String(file.getCanonicalPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		sTemp = (String) provisionerConf.get(_MAX_RESOURCE_ALLOCATION_TIME_MIN);
		if (sTemp != null) {
			maxWallTime = Integer.parseInt(sTemp); // in minutes
		}

		sTemp = (String) provisionerConf.get(_MIN_RESOURCE_ALLOCATION_TIME_MIN);
		if (sTemp != null) {
			minWallTime = Integer.parseInt(sTemp); // in minutes
		}

		sTemp = (String) provisionerConf.get(_DEALLOCATION_IDLE_TIME);
		if (sTemp != null) {
			idleTime = Integer.parseInt(sTemp); // in minutes
		}

		sTemp = (String) provisionerConf.get(_FALKON_STATE_POLL_TIME_SEC);
		if (sTemp != null) {
			pollTime = Long.parseLong(sTemp) * 1000; // in minutes
		}

		sTemp = (String) provisionerConf.get(_MIN_NUM_EXECUTORS);
		if (sTemp != null) {
			minAllocatedWorkers = Integer.parseInt(sTemp);
		}

		sTemp = (String) provisionerConf.get(_EXECUTORS_PER_HOST);
		if (sTemp != null) {
			MAX_NUM_WORKERS_PER_HOST = Integer.parseInt(sTemp);
		}

		sTemp = (String) provisionerConf.get(_EXECUTOR_SCRIPT);
		if (sTemp != null) {
			try {
				if (falkon_home == null) {

					File file = new File(sTemp);
					executable = new String(file.getCanonicalPath());
				} else {
					executable = new String(falkon_home + "/worker/" + sTemp);

				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		sTemp = (String) provisionerConf.get(_PROJECT);
		if (sTemp != null) {
			project = new String(sTemp);
		}

		sTemp = (String) provisionerConf.get(_HOST_TYPE);
		if (sTemp != null) {
			hostType = new String(sTemp);
		}

		sTemp = (String) provisionerConf.get(_MAX_NUM_EXECUTORS);
		if (sTemp != null) {
			hostCount = Integer.parseInt(sTemp); // in minutes
		}

		sTemp = (String) provisionerConf.get(_GRAM4_LOCATION);
		if (sTemp != null) {
			contact = new String(sTemp);
		}

		sTemp = (String) provisionerConf.get(_DEBUG);
		if (sTemp != null) {
			if (sTemp.contentEquals(new StringBuffer("true"))) {
				DEBUG = true;
			} else {
				DEBUG = false;
			}
			// DEBUG = Boolean.parseBoolean(sTemp);
		}

		sTemp = (String) provisionerConf.get(_DIPERF);
		if (sTemp != null) {
			if (sTemp.contentEquals(new StringBuffer("true"))) {
				DIPERF = true;
			} else {
				DIPERF = false;
			}

			// DIPERF = Boolean.parseBoolean(sTemp);
		}

		sTemp = (String) provisionerConf.get(_SECURITY_FILE);
		if (sTemp != null) {

			try {

				File file = new File(sTemp);
				CLIENT_DESC = new String(file.getCanonicalPath());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		sTemp = (String) provisionerConf.get(_DRP_LOG_FILE_NAME);
		if (sTemp != null) {
			try {
				drpLogFileName = sTemp;

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		sTemp = (String) provisionerConf.get(_ALLOCATION_STRATEGY);
		if (sTemp != null) {
			allocationStrategy = new String(sTemp);
		}

		sTemp = (String) provisionerConf.get(_GRAM4_FACTORY_TYPE);
		if (sTemp != null) {
			GRAM4_factory_type = new String(sTemp);
		}

		sTemp = (String) provisionerConf.get(_MIN_NUM_HOSTS_PER_ALLOCATION);
		if (sTemp != null) {
			minNumHostsPerAllocation = Integer.parseInt(sTemp);
		}

		sTemp = (String) provisionerConf.get(_MAX_NUM_HOSTS_PER_ALLOCATION);
		if (sTemp != null) {
			maxNumHostsPerAllocation = Integer.parseInt(sTemp);
		}

	}

	public static String CLIENT_DESC = null;

	static {
		Util.registerTransport();
	}

	public String executable = "";
	public String falkon_home = "";
	public String project = "default";
	public int maxWallTime = 60 * 1 * 24; // in min
	public int minWallTime = 60 * 1 * 24; // in min
	public int idleTime = 600000; // in ms
	public long pollTime = 1000; // in ms
	public String hostType = "ia32-compute";
	public int hostCount = 48;
	public String contact = "tg-grid1.uc.teragrid.org";

	public String machID;

	public boolean DEBUG;
	public boolean DIPERF;

	public int activeNotifications; // # of concurent notifications
	public int MAX_NUM_NOTIFICATIONS;
	public int activeThreads; // # of concurent threads accross all the jobs
	public int MAX_NUM_THREADS;
	public int queueLength; // # of individual tasks (image ROI) scheduled to
							// take place

	public int LIFETIME; // time in ms that the worker should accept work

	public Notification workerNot;
	private int SO_TIMEOUT;

	public String fileEPR = null;
	public String falkonServiceFileName = null;
	public String serviceURI;
	public EndpointReferenceType homeEPR;
	public String homeURI;

	public Random rand;

	public String scratchDisk;

	GPPortType ap;

	WorkQueue threadQ;
	StopWatch lt;
	public int jobID;
	public boolean INTERACTIVE;
	public boolean STANDALONE;
	public String DESC_FILE;
	public int JOB_SIZE;

	public int NUM_JOBS;
	public int ROI_Height;
	public int ROI_Width;

	public BufferedReader inBufRead = null;

	public int numTasksFailed = 0;
	// public int numImagesStacked = 0;
	public Random randIndex;

	public String[] data;

	long elapsedTime;

	public int allocatedWorkers = 0;
	public int minAllocatedWorkers = 0;
	public int MAX_NUM_WORKERS_PER_HOST = 1;

	public FalkonWorkerNum(String serviceURI) {
		this.ROI_Height = 100;
		this.ROI_Width = 100;
		this.randIndex = new Random();

		this.machID = "localhost:0";
		this.scratchDisk = "";

		this.rand = new Random();
		this.DEBUG = false;
		this.DIPERF = false;
		this.activeNotifications = 0;
		this.activeThreads = 0;
		this.MAX_NUM_THREADS = 10;
		this.MAX_NUM_NOTIFICATIONS = 1000000; // some large #, if setting lower,
												// there needs a way to enforce
												// it...
		this.queueLength = 0;
		this.SO_TIMEOUT = 0; // receive will block forever...
		this.LIFETIME = 0;
		// this.LIFETIME_STACK = 0;

//		this.workerNot = new Notification(this.SO_TIMEOUT, this.DEBUG);
//		logger.info("Notification initialized on port: "
//				+ this.workerNot.recvPort);
		this.threadQ = new WorkQueue();
		this.lt = new StopWatch();
		this.jobID = 0;
		this.INTERACTIVE = false;
		this.elapsedTime = 0;
		this.STANDALONE = false;
		this.serviceURI = serviceURI;
	}

	public long getElapsedTime() {
		return elapsedTime;

	}

	public void incElapsedTime(long t) {
		elapsedTime += t;

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
		logger.debug("createWorkerResource() started... ");
		FactoryServiceAddressingLocator factoryLocator = new FactoryServiceAddressingLocator();

		try {

			EndpointReferenceType factoryEPR, instanceEPR;
			FactoryPortType apFactory;

			// Get factory portType
			logger.debug("createWorkerResource(): new EndpointReferenceType()... ");
			factoryEPR = new EndpointReferenceType();
			logger.debug("createWorkerResource(): factoryEPR.setAddress(new Address(factoryURI))... ");
			factoryEPR.setAddress(new Address(factoryURI));
			logger.debug("createWorkerResource(): factoryLocator.getFactoryPortTypePort(factoryEPR)... ");
			apFactory = factoryLocator.getFactoryPortTypePort(factoryEPR);

			if (CLIENT_DESC != null && (new File(CLIENT_DESC)).exists()) {
				logger.debug("Setting appropriate security from file '"
						+ CLIENT_DESC + "'!");
				((Stub) apFactory)._setProperty(
						Constants.CLIENT_DESCRIPTOR_FILE, CLIENT_DESC);

			}

			// Create resource and get endpoint reference of WS-Resource
			logger.debug("createWorkerResource(): apFactory.createResource(new CreateResource())... ");
			CreateResourceResponse createResponse = apFactory
					.createResource(new CreateResource());
			logger.debug("createWorkerResource(): createResponse.getEndpointReference()... ");
			instanceEPR = createResponse.getEndpointReference();

			logger.debug("createWorkerResource(): ObjectSerializer.toString(instanceEPR,GPConstants.RESOURCE_REFERENCE)... ");
			String endpointString = ObjectSerializer.toString(instanceEPR,
					GPConstants.RESOURCE_REFERENCE);

			logger.debug("createWorkerResource(): new FileWriter(eprFilename)... ");
			FileWriter fileWriter = new FileWriter(eprFilename);
			logger.debug("createWorkerResource(): new BufferedWriter(fileWriter)... ");
			BufferedWriter bfWriter = new BufferedWriter(fileWriter);
			logger.debug("createWorkerResource(): bfWriter.write(endpointString)... ");
			bfWriter.write(endpointString);
			logger.debug("createWorkerResource(): bfWriter.close()... ");
			bfWriter.close();
			logger.debug("Endpoint reference written to file " + eprFilename);
			logger.debug("createWorkerResource() ended... ");
			return true;
		} catch (Exception e) {
			logger.error("" + e);
			return false;
		}
	}

	public boolean readWorkerResource(String fileEPR) throws Exception, DeserializationException {

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

				logger.debug("parseArgs(): homeEPR is set correctly");
			}

			fis.close();
			return true;
		} else {
			// throw new Exception("File '" + fileEPR + "' does not exist.");
			return false;

		}
	}

	public synchronized void incAllocatedWorkers(int i) {
		logger.debug("Current allocated workers: " + allocatedWorkers);
		allocatedWorkers += i;
		logger.debug("New allocated workers: " + allocatedWorkers);

	}

	public synchronized void decAllocatedWorkers(int i) {
		allocatedWorkers -= i;

	}

	public synchronized int getAllocatedWorkers() {
		return allocatedWorkers;

	}

	public int getNewAllocation(int AllocatedWorkers, int RegisteredWorkers,
			int FreeWorkers, int BusyWorkers, int newWorkers, int oldWorkers,
			int minWorkers, int maxWorkers, int workersPerNode, int queueLength) {
		return (int) Math.max(
				0,
				Math.ceil(Math.max(Math.min(Math.max(
						Math.max(minWorkers - AllocatedWorkers, 0), queueLength
								- FreeWorkers), Math.max(maxWorkers
						- (AllocatedWorkers), 0)), 0)
						* 1.0 / workersPerNode)
						- Math.max(0, AllocatedWorkers - RegisteredWorkers));
	}

	boolean one_at_a_time = true;
	boolean additive = false;
	boolean exponential = false;
	boolean all_at_a_time = false;

	public Map<String, String> main_run() throws Exception, SerializationException, DeserializationException {
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

		logger.debug("WORKER:getGPPortType(): " + sw.getElapsedTime() + " ms");
		sw.reset();

		Status stat = new Status();

		/*
		 * <xsd:element name="message" type="xsd:string"/> <xsd:element
		 * name="drpAllocation" type="xsd:string"/> <xsd:element
		 * name="drpMinResc" type="xsd:int"/> <xsd:element name="drpMaxResc"
		 * type="xsd:int"/> <xsd:element name="drpMinTime" type="xsd:int"/>
		 * <xsd:element name="drpMaxTime" type="xsd:int"/> <xsd:element
		 * name="drpIdle" type="xsd:int"/> <xsd:element name="resourceAllocated"
		 * type="xsd:int"/>
		 */
		stat.setMessage("");

		if (one_at_a_time) {
			stat.setDrpAllocation("One-at-a-Time");
		} else if (additive) {
			stat.setDrpAllocation("Additive");
		} else if (exponential) {
			stat.setDrpAllocation("Exponential");
		} else if (all_at_a_time) {
			stat.setDrpAllocation("All-at-Once");
		} else {
			stat.setDrpAllocation("Unkown");
		}

		stat.setDrpMinResc(minAllocatedWorkers);
		stat.setDrpMaxResc(hostCount);
		stat.setDrpMinTime(minWallTime);
		stat.setDrpMaxTime(maxWallTime);
		stat.setDrpIdle(idleTime);

		logger.info("####################### WorkerRunGram started....");

		// int executors2AllocateInFuture = 0;
		int lastNumWorkers = 0;
		logger.debug("Time_sec qLength activeTask allocatedWorkers numWorkers freeWorkers busyWorkers newWorkers deregisteredWorkers numThreads host2Allocate");

		int host2Allocate = 0;

		logger.debug("Retrieving GPWS status...");
		StatusResponse sr = ap.status(stat);
		
		if (sr.isValid()) {

			int qLength = sr.getQueueLength();
			int activeTask = sr.getActiveTasks();
			int numWorkers = sr.getNumWorkers();

			int busyWorkers = sr.getBusyWorkers();
			int newWorkers = sr.getNewWorkers();
			int deregisteredWorkers = sr.getDeregisteredWorkers();
			
//			int newQLength = qLength - lastQLength;
			
			deregisteredWorkers = (lastNumWorkers - numWorkers) + newWorkers;

			decAllocatedWorkers(deregisteredWorkers);

			// new formula, to make allocation less aggregsive
			host2Allocate = getNewAllocation(getAllocatedWorkers(), numWorkers,
					numWorkers - busyWorkers, busyWorkers, newWorkers,
					deregisteredWorkers, minAllocatedWorkers, hostCount,
					MAX_NUM_WORKERS_PER_HOST, (int) Math.floor(qLength / 10));
			lastNumWorkers = numWorkers;

			logger.info(lt.getElapsedTime() * 1.0 / 1000.0 + " " + qLength
					+ " " + activeTask + " " + getAllocatedWorkers() + " "
					+ numWorkers + " " + (numWorkers - busyWorkers) + " "
					+ busyWorkers + " " + newWorkers + " "
					+ deregisteredWorkers + " " + Thread.activeCount() + " "
					+ host2Allocate);
			falkonInfoHM.put("qLength", String.valueOf(qLength));
			falkonInfoHM.put("numWorkers", String.valueOf(numWorkers));
			falkonInfoHM.put("busyWorkers", String.valueOf(busyWorkers));
			falkonInfoHM.put("newWorkers", String.valueOf(newWorkers));
			falkonInfoHM.put("deregisteredWorkers", String.valueOf(deregisteredWorkers));
			falkonInfoHM.put("host2Allocate", String.valueOf(host2Allocate));
		} else {
			logger.debug("State was not valid");
		}
		return falkonInfoHM;
	}

	public Map<String, String> getFalkonInfo() {
		try {
			StopWatch all = new StopWatch();
			StopWatch sw = new StopWatch();
			all.start();

			FalkonWorkerNum workers = new FalkonWorkerNum(serviceURI);
			sw.start();
			sw.stop();
			logger.debug("WORKERS:parseArgs(): " + sw.getElapsedTime() + " ms");
			sw.reset();

			sw.start();
			workers.provisionerConfigFile = "config/Provisioner.config";
			File file = new File(workers.provisionerConfigFile);
			logger.debug("$$$$$$" + file.getCanonicalPath());
			boolean exists = (new File(workers.provisionerConfigFile)).exists();
			if (exists) {
				// OK
				workers.readProvisionerConfig();
			} else {
				logger.debug("Config file '"
						+ workers.provisionerConfigFile
						+ "' does not exist, using either default values or other command line arguements.");
			}
			sw.stop();
			logger.debug("WORKERS:readProvisionerConfig(): "
					+ sw.getElapsedTime() + " ms");
			sw.reset();

            try {
                falkonInfoHM = workers.main_run();
            } catch (SerializationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (DeserializationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            all.stop();

			logger.debug("WORKERS:WorkTime: " + all.getElapsedTime() + " ms");
		} catch (Exception e) {
			logger.error("WORKERS: Fatal ERROR: " + e + "... exiting");
			e.printStackTrace();
		}
		return falkonInfoHM;
		
	}

	public void setHost2Allocate(int host2Allocate) {
		this.host2Allocate = host2Allocate;
	}

	public static void main(String []args){
		
		String serviceURI =
		 "http://172.16.254.213:50001/wsrf/services/GenericPortal/core/WS/GPFactoryService";
		FalkonWorkerNum fwn = new FalkonWorkerNum(serviceURI);
		Map<String, String> fm = fwn.getFalkonInfo();
		System.out.println(fm.get("numWorkers"));
		System.out.println(fm.get("busyWorkers"));
		System.out.println(fm.get("newWorkers"));
		System.out.println(fm.get("deregisteredWorkers"));
		System.out.println(fm.get("host2Allocate"));
		try {
            try {
                System.out.println("" + fwn.main_run());
            } catch (SerializationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (DeserializationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

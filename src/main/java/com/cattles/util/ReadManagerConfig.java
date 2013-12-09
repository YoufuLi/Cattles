/**
 * 文件名：ReadManagerConfig.java
 * 创建时间：Aug 2, 2011
 * 创建者：xiong rong
 */
package com.cattles.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cattles.cloudplatforms.Constant;

/**
 * @author cloudian
 * 
 */
public class ReadManagerConfig {

	public static final Log logger = LogFactory.getLog(ReadManagerConfig.class);
	public static String managerConfigFile = Constant.CONFIG_FILE_PATH;
	public static Map<String, String> manageConf = Collections
			.synchronizedMap(new HashMap<String, String>());

	public static String platform = null;
	public static String falkonserviceconfigfile = null;
	public static String opennebulaUserAndPW = null;
	public static String opennebulaURL = null;
	public static String vmconfigfilepath = null;
	public static int poolMaxVmNum = 0;
	public static String vmUserName = null;
	public static String nfsMountPath = null;
	public static String nfsMountIP = null;
	public static String falkonServicePort = null;
	public static int vmBootTime = 0;
	public static int workerRegisterTime = 0;
	public static String heartbeatServer = null;
	public static int heartbeatPort = 0;

	private static synchronized boolean readProvConfig() throws Exception {

		logger.debug("readManageConfig()...");

		try {
			BufferedReader in = new BufferedReader(new FileReader(
					managerConfigFile));

			String str;
			while ((str = in.readLine()) != null) {
				if (str.startsWith("#")) {
					logger.debug("readManageConfig(): ignoring comment "
							+ str);

				} else {

					String tokens[] = str.split("=");
					if (tokens.length == 2) {
						manageConf.put(tokens[0], tokens[1]);

					}

					else {

						logger.debug("readManageConfig(): reading manage config file error at "
								+ str + "... ignoring");
					}
				}

			}
			interpretProvisionerConfig();
			in.close();

			return true;

		} catch (IOException e) {
			logger.error("readManagerConfigFile(): reading manage config file "
					+ managerConfigFile + " failed: " + e);
			e.printStackTrace();
			return false;

		}
		// return false;
	}

	// 解析provisioner配置文件，初始化信息
	private static void interpretProvisionerConfig() {
		String sTemp = null;

		sTemp = (String) manageConf.get(Constant.PLATFORM_KEY);
		if (sTemp != null) {
			platform = sTemp;
		}

		sTemp = (String) manageConf.get(Constant.FALKON_SERVICE_CONFIG_FILE_KEY);
		if (sTemp != null) {
			falkonserviceconfigfile = sTemp;
		}

		sTemp = (String) manageConf.get(Constant.OPENNEBULA_USER_PASSWORD);
		if (sTemp != null) {
			opennebulaUserAndPW = sTemp;
		}

		sTemp = (String) manageConf.get(Constant.OPENNEBULA_URL);
		if (sTemp != null) {
			opennebulaURL = sTemp;
		}

		sTemp = (String) manageConf.get(Constant.VM_CONFIG_FILE_PATH);
		if (sTemp != null) {
			vmconfigfilepath = sTemp;
		}

		sTemp = (String) manageConf.get(Constant.POOL_MAX_VM_NUM);
		if (sTemp != null) {
			poolMaxVmNum = Integer.parseInt(sTemp);
		}

		sTemp = (String) manageConf.get(Constant.VM_USER_NAME);
		if (sTemp != null) {
			vmUserName = sTemp;
		}

		sTemp = (String) manageConf.get(Constant.NFS_MOUNT_PATH_KEY);
		if (sTemp != null) {
			nfsMountPath = sTemp;
		}

		sTemp = (String) manageConf.get(Constant.NFS_MOUNT_IP_KEY);
		if (sTemp != null) {
			nfsMountIP = sTemp;

		}

		sTemp = (String) manageConf.get(Constant.FALKON_SERVICE_PORT);
		if (sTemp != null) {
			falkonServicePort = sTemp;
		}
		
		sTemp = (String) manageConf.get(Constant.HEARTBEAT_SERVER);
		if (sTemp != null) {
			heartbeatServer = sTemp;
		}
		
		sTemp = (String) manageConf.get(Constant.HEARTBEAT_PORT);
		if (sTemp != null) {
			heartbeatPort = Integer.parseInt(sTemp);
		}
		
	}

	public static void getConfigInfo() {
		try {
			File file = new File(managerConfigFile);
			System.out.println("$$$$$$" + file.getCanonicalPath());
			boolean exists = (new File(managerConfigFile)).exists();
			if (exists) {
				// OK
				readManageConfig();
			} else {
				logger.info("Config file '"
						+ managerConfigFile
						+ "' does not exist, using either default values or other command line arguements.");
			}
		} catch (Exception e) {
			logger.error("Fatal ERROR: " + e + "... exiting");
			e.printStackTrace();
		}

	}

	private static boolean readManageConfig() {
		try {

			if (managerConfigFile == null) {
				return false;
			} else {

				readProvConfig();

			}

			return true;

		} catch (Exception eee) {
			eee.printStackTrace();
			return false;
		}

	}
	
//	public static void main(String[] args){
//		
//	}

}

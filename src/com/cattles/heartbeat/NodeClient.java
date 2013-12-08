package com.cattles.heartbeat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author xiongrong
 * 用法：节点客户端，发送心跳包，检查服务器是否停止
 */
public class NodeClient implements Runnable {
	
	public static final Log logger = LogFactory.getLog(NodeClient.class);

	private static NodeClient client = null;

//	private boolean stoped = false;

	private int interval = 5000;

	private NodeClient() {
	}

	public static NodeClient getNodeClient() {
		if (client == null) {
			client = new NodeClient();
		}
		return client;
	}

	public void run() {
		// while (!stoped) {
		try {
			NodeSender.send();
			synchronized (this) {
				this.wait(interval);
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage());
		}
		// }
	}

	public void destroy() {
//		stoped = true;
		synchronized (this) {
			this.notify();
		}
	}

	public static void main(String[] args) {
		Thread t = new Thread(new NodeClient());
		t.start();
	}

}
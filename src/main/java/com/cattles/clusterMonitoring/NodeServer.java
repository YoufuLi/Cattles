/**
 * 
 */
package com.cattles.clusterMonitoring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.ServerSocket;
import java.net.Socket;

//import com.cattles.cloudplatforms.factory.DealwithStopVmFactory;

/**
 * @author xiongrong 用法：监控节点服务器，侦听节点心跳
 */
public class NodeServer extends Thread {

	public static final Log logger = LogFactory.getLog(NodeServer.class);

	// 当前的连接数和工作线程数
	static int workThreadNum = 0;
	static int socketConnect = 0;

	private ServerSocket serverSocket;

	// 服务的IP
	// private static final String SERVER = "172.16.254.180";

	// 端口
	// private static final int PORT = 60001;
	public NodeServer(){
		//确保ctrl＋c的时候会关闭serverSocket
		Runtime.getRuntime().addShutdownHook(this);
	}

	public void run() {
		// 绑定端口，并开始侦听用户心跳包
		/*serverSocket = startListenUserReport(ReadManagerConfig.heartbeatPort);
		if (serverSocket == null) {
			logger.error("server socket error!");
			return;
		}
		// 等待用户心跳包请求
		while (true) {
			Socket socket = null;
			try {
				socketConnect = socketConnect + 1;
				// 接收客服端的连接
				socket = serverSocket.accept();
				// 为该连接创建一个工作线程
				Thread workThread = new Thread(new Handler(socket));
				// 启动工作线程
				workThread.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	}

	/**
	 * 创建一个ServerSocket来侦听用户心跳包请求
	 * 
	 */
	public ServerSocket startListenUserReport(int port) {
		/*try {
			ServerSocket serverSocket = new ServerSocket();
			if (!serverSocket.getReuseAddress()) {
				serverSocket.setReuseAddress(true);
			}
			serverSocket.bind(new InetSocketAddress(
					ReadManagerConfig.heartbeatServer, port));
			logger.info("we start moniter user's heartbeat in "
					+ serverSocket.getLocalSocketAddress());
			return serverSocket;
		} catch (IOException e) {
			logger.error("port " + port + " have used!");
			if (serverSocket != null) {
				if (!serverSocket.isClosed()) {
					try {
						serverSocket.close();
					} catch (IOException e1) {
						logger.error(e1.getMessage());
					}
				}
			}
		}*/
		return serverSocket;
	}

	/**
	 * @author xiongrong 用法：工作线程类
	 */
	class Handler implements Runnable {
		private Socket socket;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			/*Node node = null;
			try {
				workThreadNum = workThreadNum + 1;
				logger.info(workThreadNum + " connect:"
						+ socket.getInetAddress() + ":" + socket.getPort());
				while (true) {
					InputStream in = socket.getInputStream();
					byte[] bin = new byte[300];
					int length = 0;
					while ((length = in.read(bin)) != -1) {
						logger.debug("get byte length:" + length);
						node = Persistence.getBytes(bin);
						logger.debug("node ip is：" + node.getIp());
						logger.debug("node free cpu is：" + node.getFree_cpu());
						logger.debug("node df is：" + node.getDf());
						logger.debug("node used df is：" + node.getUsed_df());
						logger.debug("node free df is：" + node.getFree_df());
						logger.debug("node ram is：" + node.getRam());
						logger.debug("node used ram is：" + node.getUsed_ram());
						logger.debug("node free ram is：" + node.getFree_ram());
						// 用于检测socket网络远端是否断开
						socket.sendUrgentData(0xFF);
					}

//					if (Constant.nodeMap.containsKey(node.getIp()))
//						Constant.nodeMap.remove(node.getIp());
					Constant.nodeMap.put(node.getIp(), node);

					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				//对停止心跳的处理
				if(node != null){
					try {
						//删除内存中该节点的记录
						Constant.nodeMap.remove(node.getIp());
						DealwithStopVm dsv = DealwithStopVmFactory.getInstance(ReadManagerConfig.platform);
						int type = dsv.stopVmType(node.getIp());
						logger.info("###################" + node.getIp() + " vm have stoped!");
						if(type == 1){
							logger.info("Now,we dealwith falkon server " + node.getIp() + " who have stoped!");
							dsv.dealwithSotpServer(node.getIp());
						}
						if(type == 2){
							logger.info("Now,we dealwith falkon worker " + node.getIp() + " who have stoped!");
							dsv.dealwithStopWorker(node.getIp());
						}
						logger.info("###################Dealwith " + node.getIp() + " Finshed!");
						//删除内存中该节点的记录
						//Constant.nodeMap.remove(node.getIp());
					} catch (OpennebulaException e1) {
						logger.error(e1.getMessage());
					} catch (Exception e2) {
						logger.error(e2.getMessage());
					}
				}
				
//				if (node != null)
//					Constant.nodeMap.remove(node.getIp());
//				logger.info("user have disconnect!");
			} finally {
				if (socket != null) {
					try {
						// 关闭socket
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} */
		}
	}

	/**
	 * 
	 * author:xiong rong 功能： 查询一个用户是否在线
	 * 
	 * @param name
	 */
	public boolean isAlive(String name) {
		//return Constant.nodeMap.containsKey(name);
        return false;
	}

	public static void main(String arg[]) {
		NodeServer server = new NodeServer();
		System.out.println("..............start server.................");
		server.start();
	}
}

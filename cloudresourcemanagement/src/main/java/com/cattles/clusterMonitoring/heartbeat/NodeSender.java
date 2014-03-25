package com.cattles.clusterMonitoring.heartbeat;

import java.net.Socket;

/**
 * @author xiongrong
 *         用法：发送数据包
 */
public class NodeSender {

    static Socket server;

    /**
     * 子节点向服务器发送本机信息
     *
     * @throws Exception
     */
    public static void send() throws Exception {
        /*System.out.println(InetAddress.getLocalHost().getHostAddress());
		String serverIp = ReadManagerConfig.heartbeatServer;
		int port = ReadManagerConfig.heartbeatPort;
		server = new Socket(serverIp, port);
		while (true) {
			OutputStream out = server.getOutputStream();
			byte[] bs = Persistence.toBytes();
			System.out.println("发生数字长度：" + bs.length);
			out.write(bs);
			Thread.sleep(5000);
		} */
    }
}

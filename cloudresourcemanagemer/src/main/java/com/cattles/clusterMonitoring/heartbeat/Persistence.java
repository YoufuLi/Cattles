/**
 *
 */
package com.cattles.clusterMonitoring.heartbeat;

import java.io.*;

/**
 * @author xiongrong 用法：得到节点机器信息
 */
public class Persistence {

    /**
     * author:xiong rong
     * 功能：调用linux系统信息
     * 参数command：执行的linux命令
     */
    private static String execCommand(String command) {
        StringBuffer buffer = null;
        try {
            Process process = Runtime.getRuntime().exec(command);
            InputStreamReader ir = new InputStreamReader(
                    process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String line;
            buffer = new StringBuffer();
            while ((line = input.readLine()) != null) {
                buffer.append(line);
                buffer.append("\n");
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("IOException " + e.getMessage());
        }

        return buffer.toString();
    }

    /**
     * author:xiong rong
     * 功能：把节点信息保存到内存
     */
    public static byte[] toBytes() {

        Node node = new Node();
        node.setType(1);

        String cpuInfo = execCommand("top -n 1 -b");
        String dfInfo = execCommand("df -k");
        String ramInfo = execCommand("free -k");
        String ipInfo = execCommand("ifconfig");

        String[] ipArray = ipInfo.split("\n");
        int containEthernet = 0;

        for (int i = 0; i < ipArray.length; i++) {
            if (ipArray[i].contains("Link encap:Ethernet")) {
                containEthernet = i;
                break;
            }
        }

        String ip = ipArray[containEthernet + 1].split(":")[1].split(" ")[0];
        node.setIp(ip);

        String[] cpuArray = cpuInfo.split("\n");
        String cpu = cpuArray[2].split(",")[3];
        cpu = cpu.substring(1, cpu.indexOf("%"));
        node.setFree_cpu(Float.valueOf(cpu) / 100);

        String[] dfArray = dfInfo.split("\n");
        long total_df = 0, used_df = 0, free_df = 0;

        for (int j = 0; j < dfArray.length; j++) {
            if (dfArray[j].contains("/dev")) {
                dfArray[j] = dfArray[j].replaceAll(" +", " ");
                total_df += Long.valueOf(dfArray[j].split(" ")[1]);
                used_df += Long.valueOf(dfArray[j].split(" ")[2]);
                free_df += Long.valueOf(dfArray[j].split(" ")[3]);
            }
        }

        node.setDf(total_df);
        node.setUsed_df(used_df);
        node.setFree_df(free_df);

        String[] ramArray = ramInfo.split("\n");
        long total_ram = 0, used_ram = 0, free_ram = 0;
        ramArray[1] = ramArray[1].replaceAll(" +", " ");
        total_ram = Long.valueOf(ramArray[1].split(" ")[1]);
        used_ram = Long.valueOf(ramArray[1].split(" ")[2]);
        free_ram = Long.valueOf(ramArray[1].split(" ")[3]);

        node.setRam(total_ram);
        node.setUsed_ram(used_ram);
        node.setFree_ram(free_ram);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oout = new ObjectOutputStream(out);
            oout.writeObject(node);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * author:xiong rong
     * 功能：内存中的信息转换为节点对象
     */
    public static Node getBytes(byte[] bs) {
        Node node = null;
        try {
            ByteArrayInputStream byteIn = new ByteArrayInputStream(bs);
            ObjectInputStream in = new ObjectInputStream(byteIn);
            node = (Node) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return node;
    }
}

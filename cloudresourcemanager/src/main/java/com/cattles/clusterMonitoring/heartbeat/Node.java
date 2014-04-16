package com.cattles.clusterMonitoring.heartbeat;

import java.io.Serializable;

/**
 * @author xiongrong
 *         用法：节点主机描述类
 */
public class Node implements Serializable {

    private static final long serialVersionUID = 1L;
    //节点机子ip
    private String ip;
    private int connnection;
    private int type;
    //节点机子总硬盘大小
    private long df;
    //节点机子已使用硬盘大小
    private long used_df;
    //节点机子空闲硬盘大小
    private long free_df;
    //节点机子内存大小
    private long ram;
    //节点机子内存大小
    private long used_ram;
    //节点空闲的内存大小
    private long free_ram;
    //节点机子空闲cpu
    private float free_cpu;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getConnnection() {
        return connnection;
    }

    public void setConnnection(int connnection) {
        this.connnection = connnection;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getDf() {
        return df;
    }

    public void setDf(long df) {
        this.df = df;
    }

    public long getUsed_df() {
        return used_df;
    }

    public void setUsed_df(long used_df) {
        this.used_df = used_df;
    }

    public long getFree_df() {
        return free_df;
    }

    public void setFree_df(long free_df) {
        this.free_df = free_df;
    }

    public long getRam() {
        return ram;
    }

    public void setRam(long ram) {
        this.ram = ram;
    }

    public long getFree_ram() {
        return free_ram;
    }

    public void setFree_ram(long free_ram) {
        this.free_ram = free_ram;
    }

    public long getUsed_ram() {
        return used_ram;
    }

    public void setUsed_ram(long used_ram) {
        this.used_ram = used_ram;
    }

    public float getFree_cpu() {
        return free_cpu;
    }

    public void setFree_cpu(float free_cpu) {
        this.free_cpu = free_cpu;
    }
}
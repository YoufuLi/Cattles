package com.cattles.schedulingframeworks.falkon.common;

import com.cattles.ssh.CommandExecutable;
import com.cattles.ssh.ConnInfo;
import com.cattles.ssh.SSHResult;
import com.cattles.ssh.jsch.CmdExecFactory;
import com.cattles.ssh.jsch.JschUserInfo;
import com.jcraft.jsch.*;
import org.apache.log4j.Logger;

/**
 * 在远程主机上执行安装
 * @author zhuyq
 *
 */
public class ExecuteCommand {
	
	private Logger logger = Logger.getLogger(ExecuteCommand.class);
	
	protected String ipAddress;
	protected String username;
	protected String passwd;
	protected String filePath;
	
	protected Session jschSession;//
	protected JSch jsch;//jsch对象
	
	public ExecuteCommand(String ipAddress, String username, String passwd){
		this(ipAddress, username, passwd, null);
	}
	
	public ExecuteCommand(String ipAddress, String username, String passwd, String filePath){
		this.ipAddress = ipAddress;
		this.username = username;
		this.passwd = passwd;
		this.filePath = filePath;
	}
	
	/**
	 * 执行安装
	 * @return
	 * @throws Exception
	 */
	public SSHResult execute() throws Exception {
		
		CommandExecutable ce = (new CmdExecFactory()).getCmdExec();
		ConnInfo ci = new ConnInfo(ipAddress, username, passwd);
		SSHResult result = ce.connect(ci);
		if(!result.isSuccess()){
			Exception exception = result.getError();
			throw exception;
		}
		
		result = ce.execute("dpkg -i "+filePath);
		
		logger.info(result.getSysOut());
		return result;
	}
	
	/**
	 * 执行命令
	 */
	public SSHResult execShell(String command) throws Exception {
		logger.info("start execu command!");
		CommandExecutable ce = (new CmdExecFactory()).getCmdExec();
		ConnInfo ci = new ConnInfo(ipAddress, username, passwd);
		SSHResult result = ce.connect(ci);
		if(!result.isSuccess()){
			Exception exception = result.getError();
			throw exception;
		}
		if(command.length() == 0){
			logger.info("node "+ipAddress+" no command exec!");
		}else{
//			initSession(ci);//初始化
			result = ce.execute(command);
			logger.info("node "+ipAddress+" exec '"+command+"' finish!");
			ce.disconnect();
		}
		return result;
	}
	
	
	protected void initSession(ConnInfo connInfo) throws JSchException{
		if(jsch == null){
			jsch = new JSch();
		}
		if(jschSession == null || !jschSession.isConnected()){
			jschSession  = jsch.getSession(connInfo.getUser(), connInfo.getHost(), connInfo.getPort());
			jschSession.setUserInfo(new JschUserInfo(jsch,connInfo));
		}
	}
	
	/**
	 * 找到falkon service进程，并结束进程
	 * @param proc
	 * @throws com.jcraft.jsch.JSchException
	 */
	public void killServiceProc(String proc) throws JSchException {
		Channel channel1 = jschSession.openChannel("exec");
		((ChannelExec)channel1).setCommand("ps ax|grep "+proc+"|grep -v grep|awk '{print $1}'|xargs kill -s 9");
		channel1.connect();
	}

    public static void main(String[] args){
        ExecuteCommand executeCommand=new ExecuteCommand("192.168.145.130","youfuli","lz");
        try {
            //executeCommand.execShell("falkon-service-stdout.sh 50001 ${FALKON_CONFIG}/Falkon.config");
            executeCommand.execShell("sh /usr/local/falkon.r174/cattles/startService.sh");
            //executeCommand.execShell("sh /usr/local/falkon.r174/cattles/stopService.sh");
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
	
}

package schedulingframeworks.falkon.commandexecutor;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.util.ssh.CommandExecutable;
import com.cattles.util.ssh.ConnInfo;
import com.cattles.util.ssh.SSHResult;
import com.cattles.util.ssh.jsch.JschUserInfo;
import com.cattles.virtualMachineManagement.VMInfo;
import com.jcraft.jsch.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;

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
    protected String passphrase;
	
	protected Session jschSession;//
	protected JSch jsch;//jsch对象
	
	public ExecuteCommand(String ipAddress, String username, String passwd){
		this(ipAddress, username, passwd, null);
	}
	
	/*public ExecuteCommand(String ipAddress, String username, String passwd, String filePath){
		this.ipAddress = ipAddress;
		this.username = username;
		this.passwd = passwd;
		this.filePath = filePath;
	} */
    public ExecuteCommand(String ipAddress, String username, String keyPath, String passphrase){
        this.ipAddress = ipAddress;
        this.username = username;
        this.filePath = keyPath;
        this.passphrase=passphrase;

    }
	
	/**
	 * 执行命令
	 */
	public SSHResult execShell(String command) throws Exception {
		logger.info("start to execute command!");
            CommandExecutable ce = (new FalkonExecFactory()).getCmdExec();
		ConnInfo ci = new ConnInfo(ipAddress, username, filePath,passphrase);
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
        VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();
        ArrayList<VMInfo> vmInfoArrayList=virtualMachineResourcePool.getVMResourceList();
        System.out.println("vm nubmer:"+vmInfoArrayList.size());
        for (VMInfo vmInfo:vmInfoArrayList){
            String ipAddress=vmInfo.getVmPublicIpAddress();
            ExecuteCommand executeCommand=new ExecuteCommand(ipAddress,"ubuntu","/home/youfuli/Documents/fg239/nicholas-key.pem",null);
            try {
                //executeCommand.execShell("falkon-service-stdout.sh 50001 ${FALKON_CONFIG}/Falkon.config");
                //executeCommand.execShell("sh /usr/local/falkon.r174/cattles/startWorker.sh 192.168.145.130");
                //executeCommand.execShell("sh /usr/local/falkon.r174/cattles/startService.sh");
                executeCommand.execShell("sh /home/ubuntu/software/falkon.r174/cattles/stopService.sh falkon");
                //executeCommand.execShell("sh /usr/local/falkon.r174/cattles/stopWorker.sh falkon");
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        System.out.println("finish");


    }
	
}

package com.cattles.schedulingmanagementservice.falkon.commandexecutor;

import com.cattles.schedulingmanagementservice.ssh.*;
import com.cattles.schedulingmanagementservice.ssh.jsch.JschSCP;
import com.cattles.schedulingmanagementservice.ssh.jsch.JschUserInfo;
import com.jcraft.jsch.*;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class FalkonServerExecutor extends BaseCommandExecutor {
    private Logger logger = Logger.getLogger(FalkonServerExecutor.class);

    private Session sshSession;
    private volatile static JSch jsch;

    private void initJsch() {
        synchronized (FalkonClusterExecutor.class) {
            if (jsch == null) {
                jsch = new JSch();
            }
        }
    }

    private synchronized void initSession(ConnInfo connInfo) throws JSchException {
        if (sshSession == null) {
            sshSession = jsch.getSession(connInfo.getUser(), connInfo.getHost(), connInfo.getPort());
            sshSession.setUserInfo(new JschUserInfo(jsch, connInfo));
        }
    }

    public SSHResult execute(String command) {
        SSHResult result = new SSHResult(command);
        boolean success = true;
        try {
            Channel channel = sshSession.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);
            //need to modify
            ByteArrayOutputStream errStream = new ByteArrayOutputStream();
            ((ChannelExec) channel).setErrStream(errStream);
            InputStream in = channel.getInputStream();
            InputStream errIn = ((ChannelExec) channel).getErrStream();

            channel.connect();
//	        StringBuilder sb = new StringBuilder();
            byte[] tmp = new byte[1024];
            byte[] errByte = new byte[1024];
            while (true) {
                while (in.available() > 0) {
                    int i = in.read(tmp, 0, 1024);
                    if (i < 0) break;
                    String msg = new String(tmp, 0, i);
                    if (!getMonitors().isEmpty()) {
                        for (SSHMonitor m : getMonitors()) {
                            m.info(msg);
                        }
                    }
                    logger.info(msg);
                    if (msg.contains("config file /home/ubuntu/software/")) {
                        logger.info("Falkon server creation");
                    }
                    if (msg.contains("[26]")) {
                        result.append(msg);
                        return result;
                    }
                    result.append(msg);
                }
                while (errIn.available() > 0) {
                    int j = errIn.read(errByte, 0, 1024);
                    if (j < 0) break;
                    String errMsg = new String(errByte, 0, j);
                    if (!getMonitors().isEmpty()) {
                        for (SSHMonitor m : getMonitors()) {
                            m.info(errMsg);
                        }
                    }
                    logger.error(errMsg);
                    result.append(errMsg);
                    success = false;
                }

                if (channel.isClosed()) {
                    result.setCmdExitCode(channel.getExitStatus());
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (Exception ee) {
                }
            }
            channel.disconnect();
            //Check the command execute successful or not
            if (!success || result.getCmdExitCode() != 0) {
                result.setSuccess(false);
                result.setError(new SSHException("Execute command failed!"));
                result.append("Execute command failed!\n");
            } else {
                result.setSuccess(true);
            }
        } catch (JSchException e) {
            SSHResult.makeFailedResult(result, e, "Execute command failed!\n");
        } catch (IOException e) {
            SSHResult.makeFailedResult(result, e, "Execute command failed, Io error!\n");
        }
        return result;
    }

    public SSHResult connect(ConnInfo info) {
        SSHResult result = new SSHResult();
        result.setCommand("Connecting to host " + info.getHost());
        if (jsch == null) {
            initJsch();
        }
        try {
            if (sshSession == null) initSession(info);
            sshSession.connect();
            result.setSuccess(true);
            result.append("Connect to host " + info.getHost() + " successful!\n");
        } catch (JSchException e) {
            SSHResult.makeFailedResult(result, e, "Connect to host " + info.getHost() + " failed!\n");
        }
        return result;
    }

    public SSHResult disconnect() {
        SSHResult result = new SSHResult("Disconnect from host " + sshSession.getHost());
        sshSession.disconnect();
        result.setSuccess(true);
        return result;
    }

    public SSHResult scp(String origin, String dest, String option) {
        if (option == null) option = "";
        return JschSCP.SCP(sshSession, origin, dest, option, getMonitors());
    }
}

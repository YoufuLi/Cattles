package com.cattles.ssh;

public interface CommandExecutable {
	public SSHResult execute(String command);
	public SSHResult connect(ConnInfo info);
	public SSHResult disconnect();
	/**
	 * monitor the step of ssh operation
	 * @param monitor
	 */
	public void addMonitor(SSHMonitor monitor);
	
	public SSHResult scp(String origin, String dest, String option);
}

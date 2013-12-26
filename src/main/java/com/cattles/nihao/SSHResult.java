package com.cattles.nihao;

/**
 * Return the ssh result
 *
 * @author Tom
 */
public class SSHResult {
    private StringBuffer sysOut = new StringBuffer("");
    private Exception error;
    private boolean success;
    private int cmdExitCode;
    /**
     * means the current command
     */
    private String command;

    public SSHResult(String command) {
        this.command = command;
    }

    public SSHResult() {
    }

    public String getSysOut() {
        return sysOut.toString();
    }

    public Exception getError() {
        return error;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getCmdExitCode() {
        return cmdExitCode;
    }

    public void setCmdExitCode(int cmdExitCode) {
        this.cmdExitCode = cmdExitCode;
    }

    public void append(String msg) {
        this.sysOut.append(msg);
    }

    public static void makeFailedResult(SSHResult r, Exception e, String failedMsg) {
        r.setError(e);
        r.setSuccess(false);
        r.append(failedMsg);
    }
}
package com.cattles.util.ssh;

public class SSHException extends Exception {

    public SSHException() {
    }

    public SSHException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSHException(String message) {
        super(message);
    }

}
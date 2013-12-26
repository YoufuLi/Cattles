package com.cattles.nihao;

public class ConnInfo {

    public enum AuthType {
        PASS, KEY
    }

    private String host;
    private int port = 22;
    private String password;
    private String keyPath;
    private String user;
    private AuthType authType;
    private String passphrase;

    public ConnInfo() {
    }

    /**
     * use the password when connect remote
     *
     * @param host
     * @param user
     * @param password
     */
    public ConnInfo(String host, String user, String password) {
        this.host = host;
        this.password = password;
        this.user = user;
        this.authType = AuthType.PASS;
    }

    /**
     * use the pubkey when connect remote
     *
     * @param host
     * @param user
     * @param keyPath
     * @param passphrase
     */
    public ConnInfo(String host, String user, String keyPath, String passphrase) {
        this.host = host;
        this.user = user;
        this.keyPath = keyPath;
        this.passphrase = passphrase;
        this.authType = AuthType.KEY;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }

    public AuthType getAuthType() {
        return authType;
    }

    public void setAuthType(AuthType authType) {
        this.authType = authType;
    }

    public String getPassphrase() {
        return passphrase;
    }

    public void setPassphrase(String passphrase) {
        this.passphrase = passphrase;
    }


}
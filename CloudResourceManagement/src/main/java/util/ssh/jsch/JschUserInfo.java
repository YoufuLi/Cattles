package util.ssh.jsch;

import com.cattles.util.ssh.ConnInfo;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.UserInfo;

public class JschUserInfo implements UserInfo {

    private String password;
    private String passphrase;

    public JschUserInfo(JSch jsch, ConnInfo connInfo) throws JSchException {
        switch (connInfo.getAuthType()) {
            case PASS:
                this.password = connInfo.getPassword();
                break;
            case KEY:
                jsch.addIdentity(connInfo.getKeyPath());
                this.passphrase = connInfo.getPassphrase();
                break;
        }
    }

    @Override
    public String getPassphrase() {

        return this.passphrase;
    }

    @Override
    public String getPassword() {

        return this.password;
    }

    @Override
    public boolean promptPassphrase(String arg0) {

        return true;
    }

    @Override
    public boolean promptPassword(String arg0) {

        return true;
    }

    @Override
    public boolean promptYesNo(String arg0) {

        return true;
    }

    @Override
    public void showMessage(String arg0) {


    }

}
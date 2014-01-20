package util.ssh.profile;

import java.util.ArrayList;

/**
 * It's a sets that contain many install or configure commands(scripts)
 *
 * @author Tom
 */
public class Profile {
    private String profileName;
    private int priority;
    private ArrayList<Command> commands;
    private int status;
    private String dependency;

    public String getProfileName() {
        return profileName;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ArrayList<Command> getCommands() {
        return commands;
    }

    public void setCommands(ArrayList<Command> commands) {
        this.commands = commands;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

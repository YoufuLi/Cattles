package com.cattles.util.ssh;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCommandExecutor implements CommandExecutable {
    protected List<SSHMonitor> monitors = new ArrayList<SSHMonitor>();

    public void addMonitor(SSHMonitor monitor) {
        monitors.add(monitor);
    }

    protected List<SSHMonitor> getMonitors() {
        return monitors;
    }

}
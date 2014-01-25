package cloudplatforms.opennebula;

import com.cattles.cloudplatforms.interfaces.IVMOperation;
import com.cattles.virtualMachineManagement.VMInfo;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 12/18/13
 * Time: 2:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenNebulaVMOperationImpl implements IVMOperation {
    /**
     * Used to create certain number of VMs.
     *
     * @param vmNumber
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VMInfo> createInstances(int vmNumber) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to launch one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    @Override
    public VMInfo launchInstance(VMInfo _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to launch a list of instances
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<VMInfo> launchInstances(ArrayList<VMInfo> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to shutdown one instance
     *
     * @param _VMInfo
     * @return
     * @throws Exception
     */
    @Override
    public boolean shutdownInstance(VMInfo _VMInfo) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to shutdown a list of instances
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public boolean shutdownInstances(ArrayList<VMInfo> vmList) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to reboot one instance
     *
     * @param _VMInfo
     * @throws Exception
     */
    @Override
    public VMInfo rebootInstance(VMInfo _VMInfo) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * used to reboot a list of instances
     *
     * @param vmList
     * @throws Exception
     */
    @Override
    public ArrayList<VMInfo> rebootInstances(ArrayList<VMInfo> vmList) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Used to destroy vms according to the vmList
     *
     * @param vmList
     * @return
     * @throws Exception
     */
    @Override
    public boolean destroyInstances(ArrayList<VMInfo> vmList) throws Exception {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}

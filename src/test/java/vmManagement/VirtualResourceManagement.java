package vmManagement;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.virtualMachineManagement.VMInfo;
import com.cattles.virtualMachineManagement.IVMOperation;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class VirtualResourceManagement {

    public static void main(String[] args){
        VirtualResourceManagement virtualResourceManagement=new VirtualResourceManagement();
        VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();
        virtualMachineResourcePool.initialization(2);
    }
    public static void shutdownVMs(ArrayList<VMInfo> vmInfoArrayList){
        IVMOperation vmOperation=new IVMOperation();
        try {
            vmOperation.shutdownInstances(vmInfoArrayList);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}

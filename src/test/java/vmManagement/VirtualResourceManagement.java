package vmManagement;

import com.cattles.cloudplatforms.amazonec2.VMInformationMaintainImpl;
import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;
import com.cattles.vmManagement.VMInfo;
import com.cattles.vmManagement.VMOperation;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/8/14
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class VirtualResourceManagement {

    public static void main(String[] args){
        VirtualResourceManagement virtualResourceManagement=new VirtualResourceManagement();
        VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();
        //virtualMachineResourcePool.initialization(10);
        VMInformationMaintainImpl vmInformationMaintain=new VMInformationMaintainImpl();
        ArrayList<VMInfo> vmInfoArrayList=vmInformationMaintain.getInstanceList();
        //virtualMachineResourcePool.addVMs(vmInfoArrayList);
        virtualResourceManagement.shutdownVMs(vmInfoArrayList);


    }
    public static void shutdownVMs(ArrayList<VMInfo> vmInfoArrayList){
        VMOperation vmOperation=new VMOperation();
        try {
            vmOperation.shutdownInstances(vmInfoArrayList);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}

package vmManagement;

import com.cattles.resourcePoolManagement.VirtualMachineResourcePool;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 1/8/14
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class VirtualResourceManagement {

    public void main(String[] args){
        VirtualMachineResourcePool virtualMachineResourcePool=VirtualMachineResourcePool.getResourcePool();
        virtualMachineResourcePool.initialization(10);
    }

}

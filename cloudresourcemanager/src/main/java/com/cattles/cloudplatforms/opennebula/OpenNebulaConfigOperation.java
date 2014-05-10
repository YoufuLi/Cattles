package com.cattles.cloudplatforms.opennebula;

import org.opennebula.client.Client;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * <p/>
 * To change this template use File | Settings | File Templates.
 */
public class OpenNebulaConfigOperation {
    static String OPENNEBULAACCOUNT="oneadmin:password";
    static String OPENNEBULA_END_POINT="http://172.16.254.101:2633/RPC2";
    public Client initOpenNebulaClient() {
        Client oneClient;

        try {
            oneClient = new Client(OPENNEBULAACCOUNT,
                    OPENNEBULA_END_POINT);
            return oneClient;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}

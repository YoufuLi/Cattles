package com.cattles.cloudplatforms.openstack;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 8/31/14
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
import org.openstack.keystone.KeystoneClient;
import org.openstack.keystone.api.Authenticate;
import org.openstack.keystone.api.ListTenants;
import org.openstack.keystone.model.Access;
import org.openstack.keystone.model.Authentication;
import org.openstack.keystone.model.Authentication.PasswordCredentials;
import org.openstack.keystone.model.Authentication.Token;
import org.openstack.keystone.model.Tenants;
import org.openstack.nova.NovaClient;
import org.openstack.nova.api.ServersCore;
import org.openstack.nova.model.Server;
import org.openstack.nova.model.Servers;

import java.util.logging.Logger;

public class NovaListServers {

    /**
     * @param args
     */
    public static void main(String[] args) {
        KeystoneClient keystone = new KeystoneClient(OpenStackConfiguration.KEYSTONE_AUTH_URL);
        Authentication authentication = new Authentication();
        PasswordCredentials passwordCredentials = new PasswordCredentials();
        passwordCredentials.setUsername(OpenStackConfiguration.KEYSTONE_USERNAME);
        passwordCredentials.setPassword(OpenStackConfiguration.KEYSTONE_PASSWORD);
        authentication.setPasswordCredentials(passwordCredentials);

        //access with unscoped token
        Access access = keystone.execute(new Authenticate(authentication));

        //use the token in the following requests
        keystone.setToken(access.getToken().getId());

        Tenants tenants = keystone.execute(new ListTenants());

        //try to exchange token using the first tenant
        if(tenants.getList().size() > 0) {

            authentication = new Authentication();
            Token token = new Token();
            token.setId(access.getToken().getId());
            authentication.setToken(token);
            authentication.setTenantId(tenants.getList().get(0).getId());

            access = keystone.execute(new Authenticate(authentication));

            //NovaClient novaClient = new NovaClient(KeystoneUtils.findEndpointURL(access.getServiceCatalog(), "compute", null, "public"), access.getToken().getId());
            NovaClient novaClient = new NovaClient(OpenStackConfiguration.NOVA_ENDPOINT.concat(tenants.getList().get(0).getId()), access.getToken().getId());
            novaClient.enableLogging(Logger.getLogger("nova"), 100 * 1024);

            Servers servers = novaClient.execute(ServersCore.listServers(true));
            for(Server server : servers) {
                System.out.println(server);
            }

        } else {
            System.out.println("No tenants found!");
        }

    }

}

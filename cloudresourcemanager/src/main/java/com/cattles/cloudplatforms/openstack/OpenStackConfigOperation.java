package com.cattles.cloudplatforms.openstack;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.sshj.config.SshjSshClientModule;
/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * Date: 8/30/14
 * Time: 9:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class OpenStackConfigOperation {
    public NovaApi novaApi;

    public static final String KEYSTONE_AUTH_URL = "http://192.168.100.51:5000/v2.0/";

    public static final String KEYSTONE_USERNAME = "admin";

    public static final String KEYSTONE_PASSWORD = "admin_pass";

    public static final String OS_TENANT_NAME="admin";

    public static final String NOVA_ENDPOINT = "http://192.168.100.51:8774/v2.0";

    public static final String CEILOMETER_ENDPOINT = "";

    public NovaApi initNovaApi(){
        Iterable<Module> modules = ImmutableSet.<Module>of(new SLF4JLoggingModule());

        String provider = "openstack-nova";
        String identity = "admin:admin"; // tenantName:userName
        String credential = "admin_pass";
        String endpoint=this.KEYSTONE_AUTH_URL;

        novaApi = ContextBuilder.newBuilder(provider)
                .endpoint(endpoint)
                .credentials(identity, credential)
                .modules(modules)
                .buildApi(NovaApi.class);
        return novaApi;
    }

    public ComputeService initComputeService(){
        String provider = "openstack-nova";
        String identity = "admin:admin"; // tenantName:userName
        String credential = "admin_pass";
        String endpoint=this.KEYSTONE_AUTH_URL;

        // example of injecting a ssh implementation
        Iterable<Module> modules = ImmutableSet.<Module> of(
                new SshjSshClientModule(),
                new SLF4JLoggingModule());
        ComputeServiceContext computeServiceContext = ContextBuilder.newBuilder(provider)
                .endpoint(endpoint)
                .credentials(identity, credential)
                .modules(modules)
                .buildView(ComputeServiceContext.class);
        return computeServiceContext.getComputeService();
    }
}

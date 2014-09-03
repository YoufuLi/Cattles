package com.cattles.cloudplatforms.openstack;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.io.Closeables;
import com.google.inject.Module;
import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Flavor;
import org.jclouds.openstack.nova.v2_0.domain.Image;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.extensions.AvailabilityZoneApi;
import org.jclouds.openstack.nova.v2_0.features.FlavorApi;
import org.jclouds.openstack.nova.v2_0.features.ImageApi;
import org.jclouds.openstack.nova.v2_0.features.ServerApi;

import java.io.Closeable;
import java.io.IOException;
import java.util.Set;

public class JCloudsNova implements Closeable {
    public OpenStackConfigOperation openStackConfigOperation=new OpenStackConfigOperation();
    private final NovaApi novaApi=openStackConfigOperation.initNovaApi();
    private final Set<String> zones=novaApi.getConfiguredZones();

    public static void main(String[] args) throws IOException {
        JCloudsNova jcloudsNova = new JCloudsNova();

        try {
            jcloudsNova.listServers();
            jcloudsNova.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jcloudsNova.close();
        }
        System.out.println("nihao");
    }

    public void listServers() {
        for (String zone : zones) {
            ServerApi serverApi = novaApi.getServerApiForZone(zone);
            System.out.println("Servers in " + zone);
            if(serverApi.listInDetail().size()>0){
                for (Server server : serverApi.listInDetail().concat()) {
                    System.out.println(zone+"*#*#*#" + server);
                }
            }
        }
    }
    public void listImages(){
        ImageApi imageApi=novaApi.getImageApiForZone("GrizzlyDemo");
        for (Image image : imageApi.listInDetail().concat()) {
            System.out.println("    " +image);
        }
    }
    public void listFlavors(){
        FlavorApi flavorApi=novaApi.getFlavorApiForZone("GrizzlyDemo");
        for (Flavor flavor : flavorApi.listInDetail().concat()) {
            System.out.println("    " +flavor);
        }
    }

    public void close() throws IOException {
        Closeables.close(novaApi, true);
    }


}

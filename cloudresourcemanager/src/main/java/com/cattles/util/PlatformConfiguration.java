package com.cattles.util;

/**
 * Created with IntelliJ IDEA.
 * User: youfuli
 * To change this template use File | Settings | File Templates.
 */
public class PlatformConfiguration {
    private static PlatformConfiguration platformConfiguration;

    private PlatformConfiguration() {
    }

    XMLOperationPlatform xmlOperationPlatform = XMLOperationPlatform.getXmlOperationPlatform();

    public static synchronized PlatformConfiguration getPlatformConfiguration() {
        if (platformConfiguration == null) {
            platformConfiguration = new PlatformConfiguration();
        }
        return platformConfiguration;
    }

    public String getPlatformName() {
        String platformName = xmlOperationPlatform.getPlatformName();
        return platformName;
    }
}

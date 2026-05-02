package net.flamgop.vulkanic.core;

import org.lwjgl.vulkan.VkPhysicalDeviceProperties;

import java.util.UUID;

public record VulkanicPhysicalDeviceProperties(
        ApiVersion apiVersion,
        ApiVersion driverVersion,
        int vendorID,
        int deviceID,
        VulkanicDeviceType deviceType,
        String deviceName,
        UUID pipelineCacheUUID,
        VulkanicPhysicalDeviceLimits limits,
        VulkanicPhysicalDeviceSparseProperties sparseProperties
) {
    public VulkanicPhysicalDeviceProperties(VkPhysicalDeviceProperties source) {
        this(
                new ApiVersion(source.apiVersion()),
                new ApiVersion(source.driverVersion()),
                source.vendorID(),
                source.deviceID(),
                VulkanicDeviceType.valueOf(source.deviceType()),
                source.deviceNameString(),
                new UUID(source.pipelineCacheUUID().getLong(), source.pipelineCacheUUID().getLong()),
                new VulkanicPhysicalDeviceLimits(source.limits()),
                new VulkanicPhysicalDeviceSparseProperties(source.sparseProperties())
        );
    }
}

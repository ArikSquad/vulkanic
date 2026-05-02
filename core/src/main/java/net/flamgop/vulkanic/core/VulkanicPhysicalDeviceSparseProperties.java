package net.flamgop.vulkanic.core;

import org.lwjgl.vulkan.VkPhysicalDeviceSparseProperties;

public record VulkanicPhysicalDeviceSparseProperties(
        boolean residencyStandard2DBlockShape,
        boolean residencyStandard2DMultisampleBlockShape,
        boolean residencyStandard3DBlockShape,
        boolean residencyAlignedMipSize,
        boolean residencyNonResidentStrict
) {
    public VulkanicPhysicalDeviceSparseProperties(VkPhysicalDeviceSparseProperties source) {
        this(
                source.residencyStandard2DBlockShape(),
                source.residencyStandard2DMultisampleBlockShape(),
                source.residencyStandard3DBlockShape(),
                source.residencyAlignedMipSize(),
                source.residencyNonResidentStrict()
        );
    }
}

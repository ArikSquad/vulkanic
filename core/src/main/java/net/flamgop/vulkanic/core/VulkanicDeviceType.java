package net.flamgop.vulkanic.core;

import org.jetbrains.annotations.NotNull;
import org.lwjgl.vulkan.VK10;

import java.util.HashMap;
import java.util.Map;

public enum VulkanicDeviceType {
    OTHER(VK10.VK_PHYSICAL_DEVICE_TYPE_OTHER),
    INTEGRATED_GPU(VK10.VK_PHYSICAL_DEVICE_TYPE_INTEGRATED_GPU),
    DISCRETE_GPU(VK10.VK_PHYSICAL_DEVICE_TYPE_DISCRETE_GPU),
    VIRTUAL_GPU(VK10.VK_PHYSICAL_DEVICE_TYPE_VIRTUAL_GPU),
    CPU(VK10.VK_PHYSICAL_DEVICE_TYPE_CPU)
    ;
    private static final Map<Integer, VulkanicDeviceType> LOOKUP = new HashMap<>();
    static {
        for (VulkanicDeviceType type : values()) {
            LOOKUP.put(type.qualifier, type);
        }
    }

    private final int qualifier;
    VulkanicDeviceType(int qualifier) {
        this.qualifier = qualifier;
    }

    public int qualifier() {
        return qualifier;
    }

    public static @NotNull VulkanicDeviceType valueOf(int value) {
        return LOOKUP.get(value);
    }
}

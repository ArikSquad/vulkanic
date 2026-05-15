package net.flamgop.vulkanic.swapchain;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.*;

public enum VulkanicSwapchainCreateFlag implements Bitmaskable<Integer> {
    SPLIT_INSTANCE_BIND_REGIONS_KHR(KHRSwapchain.VK_SWAPCHAIN_CREATE_SPLIT_INSTANCE_BIND_REGIONS_BIT_KHR), // 1.1/VK_KHR_device_group
    PROTECTED_KHR(KHRSwapchain.VK_SWAPCHAIN_CREATE_PROTECTED_BIT_KHR), // 1.1

    MUTABLE_FORMAT_KHR(KHRSwapchainMutableFormat.VK_SWAPCHAIN_CREATE_MUTABLE_FORMAT_BIT_KHR),

    PRESENT_TIMING_EXT(EXTPresentTiming.VK_SWAPCHAIN_CREATE_PRESENT_TIMING_BIT_EXT),

    PRESENT_ID_2_KHR(KHRPresentId2.VK_SWAPCHAIN_CREATE_PRESENT_ID_2_BIT_KHR),

    PRESENT_WAIT_2_KHR(KHRPresentWait2.VK_SWAPCHAIN_CREATE_PRESENT_WAIT_2_BIT_KHR),

    DEFERRED_MEMORY_ALLOCATION_KHR(KHRSwapchainMaintenance1.VK_SWAPCHAIN_CREATE_DEFERRED_MEMORY_ALLOCATION_BIT_KHR)
    ;

    private final int flag;
    VulkanicSwapchainCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}

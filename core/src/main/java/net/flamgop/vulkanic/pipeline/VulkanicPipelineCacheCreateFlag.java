package net.flamgop.vulkanic.pipeline;

import net.flamgop.vulkanic.util.Bitmaskable;
import org.lwjgl.vulkan.KHRMaintenance8;
import org.lwjgl.vulkan.VK13;

public enum VulkanicPipelineCacheCreateFlag implements Bitmaskable<Integer> {
    EXTERNALLY_SYNCHRONIZED(VK13.VK_PIPELINE_CACHE_CREATE_EXTERNALLY_SYNCHRONIZED_BIT), // also EXTPipelineCreationCacheControl if no support for VK13

    INTERNALLY_SYNCHRONIZED_MERGE_KHR(KHRMaintenance8.VK_PIPELINE_CACHE_CREATE_INTERNALLY_SYNCHRONIZED_MERGE_BIT_KHR),
    ;

    private final int flag;
    VulkanicPipelineCacheCreateFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public Integer flag() {
        return flag;
    }
}

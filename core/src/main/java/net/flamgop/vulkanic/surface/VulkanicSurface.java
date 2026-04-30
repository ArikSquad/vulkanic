package net.flamgop.vulkanic.surface;

import net.flamgop.vulkanic.core.VulkanicInstance;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.lwjgl.vulkan.KHRSurface;

public class VulkanicSurface implements AutoCloseable {

    private final VulkanicInstance instance;
    private final long handle;

    /// @param instance the [VulkanicInstance] that created this surface
    /// @param handle the Vulkan handle of this surface
    public VulkanicSurface(VulkanicInstance instance, long handle) {
        this.instance = instance;
        this.handle = handle;
    }

    @ApiStatus.Internal
    @Contract(pure = true)
    public long handle() {
        return handle;
    }

    @Override
    public void close() {
        KHRSurface.vkDestroySurfaceKHR(instance.handle(), handle, null);
    }
}

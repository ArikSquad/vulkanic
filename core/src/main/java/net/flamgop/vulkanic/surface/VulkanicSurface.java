package net.flamgop.vulkanic.surface;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;

public interface VulkanicSurface extends AutoCloseable {
    @ApiStatus.Internal
    @Contract(pure = true)
    long handle();

    @Override
    void close();
}

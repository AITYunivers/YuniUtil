package io.github.yunivers.yuniutil.util;

import net.minecraft.util.math.Vec3d;

@SuppressWarnings("unused")
public class QueuedCollisionCheck
{
    public final Vec3d from;
    public final Vec3d to;

    public QueuedCollisionCheck(Vec3d vec3d, Vec3d vec3d2)
    {
        this.from = vec3d;
        this.to = vec3d2;
    }

    public Vec3d from()
    {
        return this.from;
    }

    public Vec3d to()
    {
        return this.to;
    }
}

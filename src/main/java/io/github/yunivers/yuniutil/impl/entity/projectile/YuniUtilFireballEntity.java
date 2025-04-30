package io.github.yunivers.yuniutil.impl.entity.projectile;

import net.minecraft.util.hit.HitResult;
import net.modificationstation.stationapi.api.util.Util;
import org.spongepowered.asm.mixin.Unique;

public interface YuniUtilFireballEntity
{
    @Unique
    default void yuniutil$onHitEntity(HitResult hit)
    {
        Util.assertImpl();
    }
}

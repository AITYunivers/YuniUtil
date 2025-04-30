package io.github.yunivers.yuniutil.mixin.entity;

import io.github.yunivers.yuniutil.impl.entity.YuniUtilEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin implements YuniUtilEntity
{
    @Shadow public int fireTicks;

    @Override
    @Unique
    public boolean yuniutil$isFireImmune()
    {
        return false;
    }

    @Override
    @Unique
    public void yuniutil$setOnFire(int seconds)
    {
        if (yuniutil$isFireImmune())
            return;

        int newFireTicks = seconds * 20;
        if (fireTicks < newFireTicks)
            this.fireTicks = newFireTicks;
    }

    @Inject(
        method = "setOnFire",
        at = @At("HEAD"),
        cancellable = true
    )
    public void yuniutil$setOnFire_checkIfImmune(CallbackInfo ci)
    {
        if (yuniutil$isFireImmune())
            ci.cancel();
    }
}

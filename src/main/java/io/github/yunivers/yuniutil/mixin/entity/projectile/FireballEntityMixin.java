package io.github.yunivers.yuniutil.mixin.entity.projectile;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.yunivers.yuniutil.impl.entity.projectile.YuniUtilFireballEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireballEntity.class)
public abstract class FireballEntityMixin extends Entity implements YuniUtilFireballEntity
{
    @Shadow public LivingEntity owner;

    public FireballEntityMixin(World world)
    {
        super(world);
    }

    @WrapOperation(
        method = "tick",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/World;isRemote:Z",
            opcode = Opcodes.GETFIELD
        )
    )
    public boolean yuniutil$tick_removeOldLogic(World instance, Operation<Boolean> original)
    {
        return true;
    }

    @WrapOperation(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/projectile/FireballEntity;markDead()V",
            ordinal = 1
        )
    )
    public void yuniutil$tick_callNewLogic(FireballEntity instance, Operation<Void> original, @Local(ordinal = 0) HitResult hit)
    {
        yuniutil$onHitEntity(hit);
    }

    @Unique
    @Override
    public void yuniutil$onHitEntity(HitResult hit)
    {
        if (!world.isRemote)
        {
            if (hit.entity != null)
                hit.entity.damage(owner, 0);

            world.createExplosion(null, this.x, this.y, this.z, 1.0F, true);
        }

        markDead();
    }
}

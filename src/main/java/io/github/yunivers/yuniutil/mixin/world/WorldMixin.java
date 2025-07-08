package io.github.yunivers.yuniutil.mixin.world;

import io.github.yunivers.yuniutil.impl.world.YuniUtilWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Random;

@Mixin(World.class)
public abstract class WorldMixin implements YuniUtilWorld
{
    @Shadow public Random random;
    @Shadow public abstract long getSeed();

    @Override
    public Random yuniutil$setRandomSeed(int x, int z, int seed)
    {
        long l = (long)x * 341873128712L + (long)z * 132897987541L + this.getSeed() + seed;
        this.random.setSeed(l);
        return this.random;
    }
}

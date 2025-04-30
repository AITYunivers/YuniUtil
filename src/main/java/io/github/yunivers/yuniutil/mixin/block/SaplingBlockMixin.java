package io.github.yunivers.yuniutil.mixin.block;

import net.minecraft.block.SaplingBlock;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.StationBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.Random;

/*
    TAKEN FROM https://github.com/ModificationStation/StationAPI/pull/170
    Will remove when merged into StationAPI
 */
@Mixin(SaplingBlock.class)
public abstract class SaplingBlockMixin implements StationBlock {
    @Unique
    private static final Random RANDOM = new Random();

    @Shadow
    public abstract void generate(World world, int x, int y, int z, Random random);

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state) {
        if (!world.isRemote) {
            generate(world, x, y, z, RANDOM);
        }
        return true;
    }
}

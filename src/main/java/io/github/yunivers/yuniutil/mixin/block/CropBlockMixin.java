package io.github.yunivers.yuniutil.mixin.block;

import net.minecraft.block.CropBlock;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.StationBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/*
    TAKEN FROM https://github.com/ModificationStation/StationAPI/pull/170
    Will remove when merged into StationAPI
 */
@Mixin(CropBlock.class)
public abstract class CropBlockMixin implements StationBlock {

    @Shadow
    public abstract void applyFullGrowth(World world, int i, int j, int k);

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state) {
        if (!world.isRemote) {
            applyFullGrowth(world, x, y, z); // Full grows crop.
        }
        return true;
    }
}
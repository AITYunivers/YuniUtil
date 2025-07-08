package io.github.yunivers.yuniutil.impl.world.biome.source;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.util.Util;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public interface YuniUtilBiomeSource
{
    default boolean yuniutil$areBiomesViable(int x, int y, int z, List<Biome> biomes)
    {
        return Util.assertImpl();
    }

    default BlockPos yuniutil$getBiomeViablePos(int x, int y, int z, List<Biome> biomes, Random random)
    {
        return Util.assertImpl();
    }
}

package io.github.yunivers.yuniutil.impl.world;

import net.minecraft.world.biome.Biome;
import net.modificationstation.stationapi.api.util.Util;

import java.util.List;
import java.util.Random;

public interface YuniUtilWorld
{
    @SuppressWarnings("unused")
    default Random yuniutil$setRandomSeed(int x, int z, int seed)
    {
        return Util.assertImpl();
    }
}

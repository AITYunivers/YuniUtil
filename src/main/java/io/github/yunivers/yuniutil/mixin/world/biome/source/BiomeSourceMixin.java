package io.github.yunivers.yuniutil.mixin.world.biome.source;

import io.github.yunivers.yuniutil.impl.world.biome.source.YuniUtilBiomeSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.modificationstation.stationapi.api.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Random;

@Mixin(BiomeSource.class)
public abstract class BiomeSourceMixin implements YuniUtilBiomeSource
{
    @Shadow public Biome[] biomes;
    @Shadow public abstract Biome[] getBiomesInArea(int x, int z, int width, int depth);

    @Override
    public boolean yuniutil$areBiomesViable(int x, int y, int z, List<Biome> biomes)
    {
        int n4 = x - z >> 2;
        int n5 = y - z >> 2;
        int n6 = x + z >> 2;
        int n7 = y + z >> 2;
        int n8 = n6 - n4 + 1;
        int n9 = n7 - n5 + 1;
        this.biomes = this.getBiomesInArea(n4, n5, n8, n9);
        for (int i = 0; i < n8 * n9; ++i)
        {
            Biome biomeGenBase = this.biomes[i];
            if (biomes.contains(biomeGenBase))
                continue;
            return false;
        }
        return true;
    }

    @Override
    public BlockPos yuniutil$getBiomeViablePos(int x, int y, int z, List<Biome> biomes, Random random)
    {
        int n4 = x - z >> 2;
        int n5 = y - z >> 2;
        int n6 = x + z >> 2;
        int n7 = y + z >> 2;
        int n8 = n6 - n4 + 1;
        int n9 = n7 - n5 + 1;
        this.biomes = this.getBiomesInArea(n4, n5, n8, n9);
        BlockPos chunkPosition = null;
        int n10 = 0;
        for (int i = 0; i < this.biomes.length; ++i)
        {
            int n11 = n4 + i % n8 << 2;
            int n12 = n5 + i / n8 << 2;
            Biome biomeGenBase = this.biomes[i];
            if (!biomes.contains(biomeGenBase) || chunkPosition != null && random.nextInt(n10 + 1) != 0)
                continue;
            chunkPosition = new BlockPos(n11, 0, n12);
            ++n10;
        }
        return chunkPosition;
    }
}

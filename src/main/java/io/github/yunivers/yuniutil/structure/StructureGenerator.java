package io.github.yunivers.yuniutil.structure;

import io.github.yunivers.yuniutil.world.chunk.ChunkCoordIntPair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkSource;
import net.minecraft.world.gen.carver.Carver;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public abstract class StructureGenerator extends Carver
{
    protected HashMap<Long, StructureStart> coordMap = new HashMap<>();
    protected World world;

    public void carve(ChunkSource source, World world, int chunkX, int chunkZ, byte[] blocks)
    {
        this.world = world;
        super.carve(source, world, chunkX, chunkZ, blocks);
    }

    protected void carve(World world, int startChunkX, int startChunkZ, int chunkX, int chunkZ, byte[] blocks)
    {
        this.world = world;
        long coordHash = ChunkCoordIntPair.chunkXZ2Int(startChunkX, startChunkZ);
        if (this.coordMap.containsKey(coordHash))
            return;
        this.random.nextInt();
        if (this.canSpawnStructureAtCoords(startChunkX, startChunkZ))
        {
            StructureStart start = this.getStructureStart(startChunkX, startChunkZ);
            //YuniUtil.LOGGER.info("Generated Structure at ({}, {}, {})", startChunkX * 16 + 8, 65, startChunkZ * 16 + 8);
            this.coordMap.put(coordHash, start);
        }
    }

    public boolean generateStructuresInChunk(World world, Random random, int chunkX, int chunkZ)
    {
        int x = (chunkX << 4) + 8;
        int z = (chunkZ << 4) + 8;
        boolean generated = false;
        for (StructureStart start : this.coordMap.values())
        {
            if (!start.isSizeableStructure() || !start.getBoundingBox().isInsideStructureBB(x, z, x + 15, z + 15))
                continue;
            start.generateStructure(world, random, new StructureBoundingBox(x, z, x + 15, z + 15));
            generated = true;
        }
        return generated;
    }

    public boolean positionInsideStructure(int x, int y, int z)
    {
        for (StructureStart start : this.coordMap.values())
        {
            if (!start.isSizeableStructure() || !start.getBoundingBox().isInsideStructureBB(x, z, x, z))
                continue;
            for (StructureComponent component : start.getComponents())
                if (component.getBoundingBox().isVecInside(x, y, z))
                    return true;
        }
        return false;
    }

    /// Used by Eyes of Ender in modern to locate Strongholds
    public BlockPos getLocatorPosition(World world, int xOffset, int yOffset, int zOffset)
    {
        this.world = world;
        this.random.setSeed(world.getSeed());
        long xRandom = this.random.nextLong();
        long zRandom = this.random.nextLong();
        long xSeedCalc = (long)(xOffset >> 4) * xRandom;
        long zSeedCalc = (long)(zOffset >> 4) * zRandom;
        this.random.setSeed(xSeedCalc ^ zSeedCalc ^ world.getSeed());

        this.carve(world, xOffset >> 4, zOffset >> 4, 0, 0, null);

        double minDistance = Double.MAX_VALUE;
        BlockPos closestStructureStart = null;
        for (StructureStart start : this.coordMap.values())
        {
            if (!start.isSizeableStructure())
                continue;
            BlockPos structureStart = start.getComponents().get(0).getCenterBlock();
            int x = structureStart.x - xOffset;
            int y = structureStart.y - yOffset;
            int z = structureStart.z - zOffset;
            double distance = x + x * y * y + z * z;
            if (distance >= minDistance)
                continue;
            minDistance = distance;
            closestStructureStart = structureStart;
        }
        if (closestStructureStart != null)
            return closestStructureStart;
        List<BlockPos> origins = this.getStructureOrigins();
        if (origins != null)
        {
            BlockPos closestOrigin = null;
            for (BlockPos origin : origins)
            {
                int x = origin.x - xOffset;
                int y = origin.y - yOffset;
                int z = origin.z - zOffset;
                double distance = x + x * y * y + z * z;
                if (distance >= minDistance)
                    continue;
                minDistance = distance;
                closestOrigin = origin;
            }
            return closestOrigin;
        }
        return null;
    }

    // Used by Strongholds
    protected List<BlockPos> getStructureOrigins()
    {
        return null;
    }

    protected abstract boolean canSpawnStructureAtCoords(int chunkX, int chunkZ);

    protected abstract StructureStart getStructureStart(int chunkX, int chunkZ);
}

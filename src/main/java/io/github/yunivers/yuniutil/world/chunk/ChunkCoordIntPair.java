package io.github.yunivers.yuniutil.world.chunk;

import net.minecraft.util.math.BlockPos;

@SuppressWarnings("unused")
public class ChunkCoordIntPair
{
    public final int chunkXPos;
    public final int chunkZPos;

    public ChunkCoordIntPair(int chunkX, int chunkY)
    {
        this.chunkXPos = chunkX;
        this.chunkZPos = chunkY;
    }

    public static long chunkXZ2Int(int chunkX, int chunkZ)
    {
        return (long)chunkX & 0xFFFFFFFFL | ((long)chunkZ & 0xFFFFFFFFL) << 32;
    }

    public int hashCode()
    {
        long hash = ChunkCoordIntPair.chunkXZ2Int(this.chunkXPos, this.chunkZPos);
        int hashLeft = (int)hash;
        int hashRight = (int)(hash >> 32);
        return hashLeft ^ hashRight;
    }

    public boolean equals(ChunkCoordIntPair other)
    {
        return other.chunkXPos == this.chunkXPos && other.chunkZPos == this.chunkZPos;
    }

    public int getCenterX()
    {
        return (this.chunkXPos << 4) + 8;
    }

    public int getCenterZ()
    {
        return (this.chunkZPos << 4) + 8;
    }

    public BlockPos toBlockPos(int y)
    {
        return new BlockPos(this.getCenterX(), y, this.getCenterZ());
    }

    public String toString() {
        return "[" + this.chunkXPos + ", " + this.chunkZPos + "]";
    }
}

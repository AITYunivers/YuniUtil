package io.github.yunivers.yuniutil.structure;

@SuppressWarnings({"unused", "BooleanMethodIsAlwaysInverted"})
public class StructureBoundingBox
{
    public int minX;
    public int minY;
    public int minZ;
    public int maxX;
    public int maxY;
    public int maxZ;

    public StructureBoundingBox()
    {

    }

    public static StructureBoundingBox getNewBoundingBox()
    {
        return new StructureBoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    public static StructureBoundingBox getComponentToAddBoundingBox(int x, int y, int z, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int type)
    {
        return switch (type)
        {
            case 2 ->
                    new StructureBoundingBox(x + minX, y + minY, z - maxZ + 1 + minZ, x + maxX - 1 + minX, y + maxY - 1 + minY, z + minZ);
            case 0, 3 ->
                    new StructureBoundingBox(x + minX, y + minY, z + minZ, x + maxX - 1 + minX, y + maxY - 1 + minY, z + maxZ - 1 + minZ);
            case 1 ->
                    new StructureBoundingBox(x - maxZ + 1 + minZ, y + minY, z + minX, x + minZ, y + maxY - 1 + minY, z + maxX - 1 + minX);
            default ->
                    new StructureBoundingBox(x + minZ, y + minY, z + minX, x + maxZ - 1 + minZ, y + maxY - 1 + minY, z + maxX - 1 + minX);
        };
    }

    public StructureBoundingBox(StructureBoundingBox structureBoundingBox)
    {
        this.minX = structureBoundingBox.minX;
        this.minY = structureBoundingBox.minY;
        this.minZ = structureBoundingBox.minZ;
        this.maxX = structureBoundingBox.maxX;
        this.maxY = structureBoundingBox.maxY;
        this.maxZ = structureBoundingBox.maxZ;
    }

    public StructureBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
    {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }

    public StructureBoundingBox(int minX, int minZ, int maxX, int maxZ)
    {
        this.minX = minX;
        this.minY = 0;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = 65536;
        this.maxZ = maxZ;
    }

    public boolean intersectsWith(StructureBoundingBox other)
    {
        return this.maxX >= other.minX && this.minX <= other.maxX && this.maxZ >= other.minZ && this.minZ <= other.maxZ && this.maxY >= other.minY && this.minY <= other.maxY;
    }

    public boolean isInsideStructureBB(int minX, int minZ, int maxX, int maxZ)
    {
        return this.maxX >= minX && this.minX <= maxX && this.maxZ >= minZ && this.minZ <= maxZ;
    }

    public void resizeBoundingBoxTo(StructureBoundingBox other)
    {
        this.minX = Math.min(this.minX, other.minX);
        this.minY = Math.min(this.minY, other.minY);
        this.minZ = Math.min(this.minZ, other.minZ);
        this.maxX = Math.max(this.maxX, other.maxX);
        this.maxY = Math.max(this.maxY, other.maxY);
        this.maxZ = Math.max(this.maxZ, other.maxZ);
    }

    public void offset(int x, int y, int z)
    {
        this.minX += x;
        this.minY += y;
        this.minZ += z;
        this.maxX += x;
        this.maxY += y;
        this.maxZ += z;
    }

    public boolean isVecInside(int x, int y, int z)
    {
        return x >= this.minX && x <= this.maxX && z >= this.minZ && z <= this.maxZ && y >= this.minY && y <= this.maxY;
    }

    public int getXSize()
    {
        return this.maxX - this.minX + 1;
    }

    public int getYSize()
    {
        return this.maxY - this.minY + 1;
    }

    public int getZSize()
    {
        return this.maxZ - this.minZ + 1;
    }

    public int getCenterX()
    {
        return this.minX + (this.maxX - this.minX + 1) / 2;
    }

    public int getCenterY()
    {
        return this.minY + (this.maxY - this.minY + 1) / 2;
    }

    public int getCenterZ()
    {
        return this.minZ + (this.maxZ - this.minZ + 1) / 2;
    }

    public String toString()
    {
        return "(" + this.minX + ", " + this.minY + ", " + this.minZ + "; " + this.maxX + ", " + this.maxY + ", " + this.maxZ + ")";
    }
}

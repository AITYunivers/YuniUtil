package io.github.yunivers.yuniutil.structure;

import io.github.yunivers.yuniutil.util.WeightedRandom;
import lombok.Getter;
import net.minecraft.block.*;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.DoorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

@SuppressWarnings("unused")
public abstract class StructureComponent
{
    @Getter
    protected StructureBoundingBox boundingBox;
    protected int coordBaseMode;
    @Getter
    protected int id;

    protected StructureComponent(int id)
    {
        this.id = id;
        this.coordBaseMode = -1;
    }

    public void buildComponent(StructureComponent connectionComponent, List<StructureComponent> components, Random random)
    {

    }

    public abstract boolean addComponentParts(World world, Random random, StructureBoundingBox box);

    public static StructureComponent getIntersectingStructureComponent(List<StructureComponent> components, StructureBoundingBox box)
    {
        for (StructureComponent structureComponent : components)
        {
            if (structureComponent.getBoundingBox() == null || !structureComponent.getBoundingBox().intersectsWith(box))
                continue;
            return structureComponent;
        }
        return null;
    }

    public BlockPos getCenterBlock()
    {
        return new BlockPos(this.boundingBox.getCenterX(), this.boundingBox.getCenterY(), this.boundingBox.getCenterZ());
    }

    protected boolean isLiquidInStructureBoundingBox(World world, StructureBoundingBox box)
    {
        int block;
        int n2;
        int n3;
        int n4 = Math.max(this.boundingBox.minX - 1, box.minX);
        int n5 = Math.max(this.boundingBox.minY - 1, box.minY);
        int n6 = Math.max(this.boundingBox.minZ - 1, box.minZ);
        int n7 = Math.min(this.boundingBox.maxX + 1, box.maxX);
        int n8 = Math.min(this.boundingBox.maxY + 1, box.maxY);
        int n9 = Math.min(this.boundingBox.maxZ + 1, box.maxZ);
        for (n3 = n4; n3 <= n7; ++n3)
        {
            for (n2 = n6; n2 <= n9; ++n2)
            {
                block = world.getBlockId(n3, n5, n2);
                if (block > 0 && Block.BLOCKS[block].material.isFluid())
                    return true;
                block = world.getBlockId(n3, n8, n2);
                if (block <= 0 || !Block.BLOCKS[block].material.isFluid())
                    continue;
                return true;
            }
        }
        for (n3 = n4; n3 <= n7; ++n3)
        {
            for (n2 = n5; n2 <= n8; ++n2)
            {
                block = world.getBlockId(n3, n2, n6);
                if (block > 0 && Block.BLOCKS[block].material.isFluid())
                    return true;
                block = world.getBlockId(n3, n2, n9);
                if (block <= 0 || !Block.BLOCKS[block].material.isFluid())
                    continue;
                return true;
            }
        }
        for (n3 = n6; n3 <= n9; ++n3)
        {
            for (n2 = n5; n2 <= n8; ++n2)
            {
                block = world.getBlockId(n4, n2, n3);
                if (block > 0 && Block.BLOCKS[block].material.isFluid())
                    return true;
                block = world.getBlockId(n7, n2, n3);
                if (block <= 0 || !Block.BLOCKS[block].material.isFluid())
                    continue;
                return true;
            }
        }
        return false;
    }

    protected int getXWithOffset(int x, int y)
    {
        return switch (this.coordBaseMode)
        {
            case 0, 2 -> this.boundingBox.minX + x;
            case 1 -> this.boundingBox.maxX - y;
            case 3 -> this.boundingBox.minX + y;
            default -> x;
        };
    }

    protected int getYWithOffset(int y)
    {
        if (this.coordBaseMode == -1)
            return y;
        return y + this.boundingBox.minY;
    }

    protected int getZWithOffset(int x, int z)
    {
        return switch (this.coordBaseMode)
        {
            case 2 -> this.boundingBox.maxZ - z;
            case 0 -> this.boundingBox.minZ + z;
            case 1, 3 -> this.boundingBox.minZ + x;
            default -> z;
        };
    }

    protected int getRotatedMeta(int block, int meta)
    {
        if (Block.BLOCKS[block] instanceof RailBlock)
        {
            if (this.coordBaseMode == 1 || this.coordBaseMode == 3)
            {
                if (meta == 1)
                    return 0;
                return 1;
            }
        }
        else if (Block.BLOCKS[block] instanceof DoorBlock)
        {
            if (this.coordBaseMode == 0)
            {
                if (meta == 0)
                    return 2;
                if (meta == 2)
                    return 0;
            }
            else
            {
                if (this.coordBaseMode == 1)
                    return meta + 1 & 3;
                if (this.coordBaseMode == 3)
                    return meta + 3 & 3;
            }
        }
        else if (Block.BLOCKS[block] instanceof StairsBlock)
        {
            if (this.coordBaseMode == 0)
            {
                if (meta == 2)
                    return 3;
                if (meta == 3)
                    return 2;
            }
            else if (this.coordBaseMode == 1)
            {
                if (meta == 0)
                    return 2;
                if (meta == 1)
                    return 3;
                if (meta == 2)
                    return 0;
                if (meta == 3)
                    return 1;
            }
            else if (this.coordBaseMode == 3)
            {
                if (meta == 0)
                    return 2;
                if (meta == 1)
                    return 3;
                if (meta == 2)
                    return 1;
                if (meta == 3)
                    return 0;
            }
        }
        else if (Block.BLOCKS[block] instanceof LadderBlock)
        {
            if (this.coordBaseMode == 0)
            {
                if (meta == 2)
                    return 3;
                if (meta == 3)
                    return 2;
            }
            else if (this.coordBaseMode == 1)
            {
                if (meta == 2)
                    return 4;
                if (meta == 3)
                    return 5;
                if (meta == 4)
                    return 2;
                if (meta == 5)
                    return 3;
            }
            else if (this.coordBaseMode == 3)
            {
                if (meta == 2)
                    return 5;
                if (meta == 3)
                    return 4;
                if (meta == 4)
                    return 2;
                if (meta == 5)
                    return 3;
            }
        }
        else if (Block.BLOCKS[block] instanceof ButtonBlock)
        {
            if (this.coordBaseMode == 0)
            {
                if (meta == 3)
                    return 4;
                if (meta == 4)
                    return 3;
            }
            else if (this.coordBaseMode == 1)
            {
                if (meta == 3)
                    return 1;
                if (meta == 4)
                    return 2;
                if (meta == 2)
                    return 3;
                if (meta == 1)
                    return 4;
            }
            else if (this.coordBaseMode == 3)
            {
                if (meta == 3)
                    return 2;
                if (meta == 4)
                    return 1;
                if (meta == 2)
                    return 3;
                if (meta == 1)
                    return 4;
            }
        }
        return meta;
    }

    protected void placeBlockAtCurrentPosition(World world, int block, int meta, int xOffset, int yOffset, int zOffset, StructureBoundingBox box)
    {
        int z = this.getZWithOffset(xOffset, zOffset);
        int y = this.getYWithOffset(yOffset);
        int x = this.getXWithOffset(xOffset, zOffset);
        if (box.isVecInside(x, y, z))
            world.setBlock(x, y, z, block, meta);
    }

    protected int getBlockIdAtCurrentPosition(World world, int xOffset, int yOffset, int zOffset, StructureBoundingBox box)
    {
        int z = this.getZWithOffset(xOffset, zOffset);
        int y = this.getYWithOffset(yOffset);
        int x = this.getXWithOffset(xOffset, zOffset);
        if (box.isVecInside(x, y, z))
            return world.getBlockId(x, y, z);
        return 0;
    }

    protected void fillWithBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int edgeBlock, int innerBlock, boolean ignoreAir)
    {
        for (int y = minY; y <= maxY; ++y)
            for (int x = minX; x <= maxX; ++x)
                for (int z = minZ; z <= maxZ; ++z)
                {
                    if (ignoreAir && this.getBlockIdAtCurrentPosition(world, x, y, z, box) == 0)
                        continue;
                    if (y == minY || y == maxY || x == minX || x == maxX || z == minZ || z == maxZ)
                    {
                        this.placeBlockAtCurrentPosition(world, edgeBlock, 0, x, y, z, box);
                        continue;
                    }
                    this.placeBlockAtCurrentPosition(world, innerBlock, 0, x, y, z, box);
                }
    }

    protected void fillWithRandomizedBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, boolean ignoreAir, Random random, StructurePieceBlockSelector selector)
    {
        for (int y = minY; y <= maxY; ++y)
            for (int x = minX; x <= maxX; ++x)
                for (int z = minZ; z <= maxZ; ++z)
                {
                    if (ignoreAir && this.getBlockIdAtCurrentPosition(world, x, y, z, box) == 0)
                        continue;
                    selector.selectBlocks(random, x, y, z, y == minY || y == maxY || x == minX || x == maxX || z == minZ || z == maxZ);
                    this.placeBlockAtCurrentPosition(world, selector.getSelectedBlockId(), selector.getSelectedBlockMetaData(), x, y, z, box);
                }
    }

    protected void randomlyFillWithBlocks(World world, StructureBoundingBox box, Random random, float chance, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int edgeBlock, int innerBlock, boolean ignoreAir)
    {
        for (int y = minY; y <= maxY; ++y)
            for (int x = minX; x <= maxX; ++x)
                for (int z = minZ; z <= maxZ; ++z)
                {
                    if (random.nextFloat() > chance || ignoreAir && this.getBlockIdAtCurrentPosition(world, x, y, z, box) == 0)
                        continue;
                    if (y == minY || y == maxY || x == minX || x == maxX || z == minZ || z == maxZ)
                    {
                        this.placeBlockAtCurrentPosition(world, edgeBlock, 0, x, y, z, box);
                        continue;
                    }
                    this.placeBlockAtCurrentPosition(world, innerBlock, 0, x, y, z, box);
                }
    }

    protected void randomlyPlaceBlock(World world, StructureBoundingBox box, Random random, float chance, int x, int y, int z, int block, int meta)
    {
        if (random.nextFloat() < chance)
            this.placeBlockAtCurrentPosition(world, block, meta, x, y, z, box);
    }

    protected void randomlyRareFillWithBlocks(World world, StructureBoundingBox box, int minX, int minY, int minZ, int maxX, int maxY, int maxZ, int block, boolean ignoreAir)
    {
        float width = maxX - minX + 1;
        float height = maxY - minY + 1;
        float length = maxZ - minZ + 1;
        float centerX = (float)minX + width / 2.0f;
        float centerZ = (float)minZ + length / 2.0f;
        for (int y = minY; y <= maxY; ++y)
        {
            float f6 = (float)(y - minY) / height;
            for (int x = minX; x <= maxX; ++x)
            {
                float f7 = ((float)x - centerX) / (width * 0.5f);
                for (int z = minZ; z <= maxZ; ++z)
                {
                    float f9 = ((float)z - centerZ) / (length * 0.5f);
                    float distance = f7 * f7 + f6 * f6 + f9 * f9;
                    if (ignoreAir && this.getBlockIdAtCurrentPosition(world, x, y, z, box) == 0 || distance > 1.05f)
                        continue;
                    this.placeBlockAtCurrentPosition(world, block, 0, x, y, z, box);
                }
            }
        }
    }

    protected void clearCurrentPositionBlocksUpwards(World world, int xOffset, int yOffset, int zOffset, StructureBoundingBox box)
    {
        int z = this.getZWithOffset(xOffset, zOffset);
        int y = this.getYWithOffset(yOffset);
        int x = this.getXWithOffset(xOffset, zOffset);
        if (!box.isVecInside(x, y, z))
            return;
        while (!world.isAir(x, y, z) && y < world.getHeight())
        {
            world.setBlock(x, y, z, 0, 0);
            y++;
        }
    }

    protected void fillCurrentPositionBlocksDownwards(World world, int block, int meta, int xOffset, int yOffset, int zOffset, StructureBoundingBox box)
    {
        int z = this.getZWithOffset(xOffset, zOffset);
        int y = this.getYWithOffset(yOffset);
        int x = this.getXWithOffset(xOffset, zOffset);
        if (!box.isVecInside(x, y, z))
            return;
        while ((world.isAir(x, y, z) || Block.BLOCKS[world.getBlockId(x, y, z)].material.isFluid()) && y > 1)
        {
            world.setBlock(x, y, z, block, meta);
            y--;
        }
    }

    protected void createTreasureChestAtCurrentPosition(World world, StructureBoundingBox box, Random random, int xOffset, int yOffset, int zOffset, StructurePieceTreasure[] treasures, int count)
    {
        int z = this.getZWithOffset(xOffset, zOffset);
        int y = this.getYWithOffset(yOffset);
        int x = this.getXWithOffset(xOffset, zOffset);
        if (box.isVecInside(x, y, z) && world.getBlockId(x, y, z) != Block.CHEST.id)
        {
            world.setBlock(x, y, z, Block.CHEST.id);
            ChestBlockEntity tileEntityChest = (ChestBlockEntity) world.getBlockEntity(x, y, z);
            if (tileEntityChest != null)
                StructureComponent.fillTreasureChestWithLoot(random, treasures, tileEntityChest, count);
        }
    }

    private static void fillTreasureChestWithLoot(Random random, StructurePieceTreasure[] treasures, ChestBlockEntity chest, int count)
    {
        for (int i = 0; i < count; ++i)
        {
            StructurePieceTreasure structurePieceTreasure = (StructurePieceTreasure)WeightedRandom.getRandomWeight(random, treasures);
            int itemCount = structurePieceTreasure.minCount + random.nextInt(structurePieceTreasure.maxCount - structurePieceTreasure.minCount + 1);
            if (Item.ITEMS[structurePieceTreasure.itemId].getMaxCount() >= itemCount)
            {
                chest.setStack(random.nextInt(chest.size()), new ItemStack(structurePieceTreasure.itemId, itemCount, structurePieceTreasure.damage));
                continue;
            }
            for (int j = 0; j < itemCount; ++j)
                chest.setStack(random.nextInt(chest.size()), new ItemStack(structurePieceTreasure.itemId, 1, structurePieceTreasure.damage));
        }
    }

    protected void placeDoorAtCurrentPosition(World world, StructureBoundingBox structureBoundingBox, Random random, int xOffset, int yOffset, int zOffset, int facing)
    {
        int z = this.getZWithOffset(xOffset, zOffset);
        int y = this.getYWithOffset(yOffset);
        int x = this.getXWithOffset(xOffset, zOffset);
        if (structureBoundingBox.isVecInside(x, y, z))
            ((DoorItem)Item.WOODEN_DOOR).yuniutil$placeDoorBlock(world, x, y, z, facing, Block.DOOR);
    }
}

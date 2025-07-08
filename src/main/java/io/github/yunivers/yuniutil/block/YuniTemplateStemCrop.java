package io.github.yunivers.yuniutil.block;

import io.github.yunivers.yuniutil.YuniUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.template.block.TemplatePlantBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Random;

@SuppressWarnings("unused")
public abstract class YuniTemplateStemCrop extends TemplatePlantBlock
{
    private final Block baseBlock;

    public YuniTemplateStemCrop(int id, Block block)
    {
        super(id, 0);
        this.baseBlock = block;
        setTickRandomly(true);
        setBoundingBox(0.375f, 0.0f, 0.375f, 0.625f, 0.25f, 0.625f);
    }

    public YuniTemplateStemCrop(Identifier identifier, Block block)
    {
        super(identifier, 0);
        this.baseBlock = block;
        setTickRandomly(true);
        setBoundingBox(0.375f, 0.0f, 0.375f, 0.625f, 0.25f, 0.625f);
    }

    @Override
    protected boolean canPlantOnTop(int id)
    {
        return id == Block.FARMLAND.id;
    }

    @Override
    public void onTick(World world, int x, int y, int z, Random random)
    {
        super.onTick(world, x, y, z, random);
        float f = this.getGrowthChance(world, x, y, z);
        if (world.getLightLevel(x, y + 1, z) >= 9 && random.nextInt((int)(25.0f / f) + 1) == 0)
        {
            int meta = world.getBlockMeta(x, y, z);
            if (meta < 7)
                world.setBlockMeta(x, y, z, meta + 1);
            else
            {
                if (world.getBlockId(x - 1, y, z) == this.baseBlock.id)
                    return;
                if (world.getBlockId(x + 1, y, z) == this.baseBlock.id)
                    return;
                if (world.getBlockId(x, y, z - 1) == this.baseBlock.id)
                    return;
                if (world.getBlockId(x, y, z + 1) == this.baseBlock.id)
                    return;
                int randomSide = random.nextInt(4);
                int placeAtX = switch (randomSide)
                {
                    case 0 -> x - 1;
                    case 1 -> x + 1;
                    default -> x;
                };
                int placeAtZ = switch (randomSide)
                {
                    case 2 -> z - 1;
                    case 3 -> z + 1;
                    default -> z;
                };
                if (world.getBlockId(placeAtX, y, placeAtZ) == 0 && world.getBlockId(placeAtX, y - 1, placeAtZ) == Block.FARMLAND.id)
                    world.setBlock(placeAtX, y, placeAtZ, this.baseBlock.id);
            }
        }
    }

    @Override
    public boolean onBonemealUse(World world, int x, int y, int z, BlockState state)
    {
        world.setBlockMeta(x, y, z, 7);
        return true;
    }

    @SuppressWarnings("ReassignedVariable")
    private float getGrowthChance(World world, int x, int y, int z) {
        float chance = 1.0f;
        int T = world.getBlockId(x, y, z - 1);
        int B = world.getBlockId(x, y, z + 1);
        int L = world.getBlockId(x - 1, y, z);
        int R = world.getBlockId(x + 1, y, z);
        int TL = world.getBlockId(x - 1, y, z - 1);
        int TR = world.getBlockId(x + 1, y, z - 1);
        int BR = world.getBlockId(x + 1, y, z + 1);
        int BL = world.getBlockId(x - 1, y, z + 1);
        boolean horiAdjacent = L == this.id || R == this.id;
        boolean vertAdjacent = T == this.id || B == this.id;
        boolean cornerAdjacent = TL == this.id || TR == this.id || BR == this.id || BL == this.id;
        for (int forX = x - 1; forX <= x + 1; ++forX)
        {
            for (int forZ = z - 1; forZ <= z + 1; ++forZ)
            {
                int forId = world.getBlockId(forX, y - 1, forZ);
                float calc = 0.0f;
                if (forId == Block.FARMLAND.id)
                {
                    calc = 1.0f;
                    if (world.getBlockMeta(forX, y - 1, forZ) > 0)
                        calc = 3.0f;
                }
                if (forX != x || forZ != z)
                    calc /= 4.0f;
                chance += calc;
            }
        }
        if (cornerAdjacent || horiAdjacent && vertAdjacent)
            chance /= 2.0f;
        return chance;
    }

    @Override
    public int getColor(int meta)
    {
        int r = meta * 32;
        int g = 255 - meta * 8;
        int b = meta * 4;
        return r << 16 | g << 8 | b;
    }

    @Override
    public int getColorMultiplier(BlockView blockView, int x, int y, int z)
    {
        return getColor(blockView.getBlockMeta(x, y, z));
    }

    @Override
    public int getTexture(int side, int meta)
    {
        return meta >= 0 ? YuniUtil.TEXTURE_STEM : YuniUtil.TEXTURE_STEM_CONNECTED;
    }

    @Override
    public void setupRenderBoundingBox()
    {
        setBoundingBox(0.375f, 0.0f, 0.375f, 0.625f, 0.25f, 0.625f);
    }

    @Override
    public void updateBoundingBox(BlockView blockView, int x, int y, int z)
    {
        this.maxY = (float)(blockView.getBlockMeta(x, y, z) * 2 + 2) / 16.0f;
        setBoundingBox(0.375f, 0.0f, 0.375f, 0.625f, (float)this.maxY, 0.625f);
    }

    public int getAdjacentDirection(BlockView blockView, int x, int y, int z)
    {
        int meta = blockView.getBlockMeta(x, y, z);
        if (meta < 7)
            return -1;

        if (blockView.getBlockId(x - 1, y, z) == this.baseBlock.id)
            return 0;
        if (blockView.getBlockId(x + 1, y, z) == this.baseBlock.id)
            return 1;
        if (blockView.getBlockId(x, y, z - 1) == this.baseBlock.id)
            return 2;
        if (blockView.getBlockId(x, y, z + 1) == this.baseBlock.id)
            return 3;
        return -1;
    }

    public abstract Item getSeedItem();

    @Override
    public void dropStacks(World world, int x, int y, int z, int meta, float luck)
    {
        super.dropStacks(world, x, y, z, meta, luck);
        if (world.isRemote)
            return;

        Item item = getSeedItem();
        if (item == null)
            return;

        for (int i = 0; i < 3; ++i)
        {
            if (world.random.nextInt(15) > meta)
                continue;

            float f2 = 0.7f;
            float f3 = world.random.nextFloat() * f2 + (1.0f - f2) * 0.5f;
            float f4 = world.random.nextFloat() * f2 + (1.0f - f2) * 0.5f;
            float f5 = world.random.nextFloat() * f2 + (1.0f - f2) * 0.5f;
            ItemEntity entityItem = new ItemEntity(world, (float)x + f3, (float)y + f4, (float)z + f5, new ItemStack(item));
            entityItem.pickupDelay = 10;
            world.spawnEntity(entityItem);
        }
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random)
    {
        return -1;
    }
}

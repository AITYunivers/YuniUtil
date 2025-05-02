package io.github.yunivers.yuniutil.block;

import io.github.yunivers.yuniutil.util.ESlabState;
import net.minecraft.block.Block;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateSlabBlock;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("unused")
public class YuniTemplateSlabBlock extends TemplateSlabBlock
{
    public static final EnumProperty<ESlabState> SLAB_STATE_PROPERTY = EnumProperty.of("slab_state", ESlabState.class);
    private static final Map<Block, Block> doubleSlabs = new HashMap<>();
    private static final Map<Block, Block> singleSlabs = new HashMap<>();

    private final Block baseBlock;
    private final boolean doubleSlab;

    public YuniTemplateSlabBlock(int id, Block block, boolean doubleSlab)
    {
        super(id, doubleSlab);
        baseBlock = block;
        this.doubleSlab = doubleSlab;

        setHardness(block.getHardness());
        setResistance(block.resistance / 3.0F);

        if (doubleSlab)
            doubleSlabs.put(block, this);
        else
            singleSlabs.put(block, this);

        setDefaultState(getStateManager().getDefaultState()
            .with(SLAB_STATE_PROPERTY, doubleSlab ? ESlabState.DOUBLE : ESlabState.BOTTOM));
    }

    public YuniTemplateSlabBlock(Identifier identifier, Block block, boolean doubleSlab)
    {
        super(identifier, doubleSlab);
        baseBlock = block;
        this.doubleSlab = doubleSlab;

        setHardness(block.getHardness());
        setResistance(block.resistance / 3.0F);

        if (doubleSlab)
            doubleSlabs.put(block, this);
        else
            singleSlabs.put(block, this);

        setDefaultState(getStateManager().getDefaultState()
            .with(SLAB_STATE_PROPERTY, doubleSlab ? ESlabState.DOUBLE : ESlabState.BOTTOM));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(SLAB_STATE_PROPERTY);
    }

    @Override
    public int getTexture(int side, int meta)
    {
        return baseBlock.getTexture(side, meta);
    }

    @Override
    public void onPlaced(World world, int x, int y, int z)
    {
        if (doubleSlab)
            super.onPlaced(world, x, y, z);

        int lowerBlockId = world.getBlockId(x, y - 1, z);
        if (lowerBlockId == this.id)
        {
            world.setBlock(x, y, z, 0);
            world.setBlock(x, y - 1, z, doubleSlabs.get(baseBlock).id);
        }
    }

    @Override
    public int getDroppedItemId(int blockMeta, Random random)
    {
        return singleSlabs.get(baseBlock).id;
    }

    @Override
    protected int getDroppedItemMeta(int blockMeta)
    {
        return 0;
    }

    @Override
    public boolean isSideVisible(BlockView blockView, int x, int y, int z, int side)
    {
        if (doubleSlab)
            return super.isSideVisible(blockView, x, y, z, side);

        if (side == 1)
            return true;
        else if (!super.isSideVisible(blockView, x, y, z, side))
            return false;
        else
            return side == 0 || blockView.getBlockId(x, y, z) != this.id;
    }
}

package io.github.yunivers.yuniutil.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlock;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

/* Template Blockstate: https://github.com/AITYunivers/Pocket-Reimplementation/blob/master/src/main/resources/assets/pocket/stationapi/blockstates/oak_fence_gate.json */
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class YuniTemplateFenceGateBlock extends TemplateBlock
{
    public static final BooleanProperty OPEN_PROPERTY = BooleanProperty.of("open");
    public static final EnumProperty<Direction> FACING_PROPERTY = EnumProperty.of("facing", Direction.class);
    private final Block baseBlock;

    public YuniTemplateFenceGateBlock(Identifier identifier, Block baseBlock)
    {
        super(identifier, baseBlock.material);
        this.baseBlock = baseBlock;
        setHardness(baseBlock.getHardness());
        setResistance(baseBlock.resistance / 3.0F);

        setDefaultState(getStateManager().getDefaultState()
            .with(OPEN_PROPERTY, false)
            .with(FACING_PROPERTY, Direction.NORTH));
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(OPEN_PROPERTY);
        builder.add(FACING_PROPERTY);
    }

    @Override
    public boolean canPlaceAt(World world, int x, int y, int z)
    {
        if (!Block.BLOCKS[world.getBlockId(x, y - 1, z)].isSolidFace(world, x, y - 1, z, 1))
            return false;
        return super.canPlaceAt(world, x, y, z);
    }

    @Override
    public Box getCollisionShape(World world, int x, int y, int z)
    {
        BlockState state = world.getBlockState(x, y, z);
        if (state.getBlock() == this && state.get(OPEN_PROPERTY))
            return null;
        return Box.createCached(x, y, z, x + 1, y + 1.5, z + 1);
    }

    @Override
    public boolean isOpaque()
    {
        return false;
    }

    @Override
    public boolean isFullCube()
    {
        return false;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        return getDefaultState().with(FACING_PROPERTY, context.getHorizontalPlayerFacing().rotateClockwise(Direction.Axis.Y));
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player)
    {
        BlockState state = world.getBlockState(x, y, z);
        if (state.get(OPEN_PROPERTY))
            world.setBlockStateWithNotify(x, y, z, state.with(OPEN_PROPERTY, false));
        else
        {
            Direction openDir = Direction.fromRotation(player.yaw).rotateClockwise(Direction.Axis.Y);
            if (state.get(FACING_PROPERTY) == openDir)
                state = state.with(FACING_PROPERTY, state.get(FACING_PROPERTY).getOpposite());
            world.setBlockStateWithNotify(x, y, z, state.with(OPEN_PROPERTY, true));
        }
        world.worldEvent(player, 1003, x, y, z, 0);
        return true;
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id)
    {
        if (id > 0 && Block.BLOCKS[id].canEmitRedstonePower())
        {
            BlockState state = world.getBlockState(x, y, z);
            boolean open = world.isEmittingRedstonePower(x, y, z);
            world.setBlockStateWithNotify(x, y, z, state.with(OPEN_PROPERTY, open));
            world.worldEvent(null, 1003, x, y, z, 0);
        }
    }
}

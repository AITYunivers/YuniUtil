package io.github.yunivers.yuniutil.block;

import io.github.yunivers.yuniutil.block.entity.YuniChestBlockEntity;
import io.github.yunivers.yuniutil.screen.YuniChestScreenHandler;
import io.github.yunivers.yuniutil.util.EDoubleChestSide;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.item.ItemPlacementContext;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.EnumProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

import static io.github.yunivers.yuniutil.YuniUtil.NAMESPACE;

/// Expandable Chest Template that uses Blockstates and Block Models
/// Originally made for YuniChests
@SuppressWarnings("unused")
public abstract class YuniTemplateChestBlock extends TemplateBlockWithEntity
{
    public static final EnumProperty<Direction> FACING_PROPERTY = EnumProperty.of("facing", Direction.class);
    public static final EnumProperty<EDoubleChestSide> DOUBLE_SIDE_PROPERTY = EnumProperty.of("double_side", EDoubleChestSide.class);
    public static final BooleanProperty IS_DOUBLE_PROPERTY = BooleanProperty.of("is_double");
    private final Random random = new Random();
    private final int invSize;
    private final int singleRows;
    private final int largeRows;

    @Getter
    @Setter
    private String name;

    public static int GUI_Rows;

    /// Full constructor
    public YuniTemplateChestBlock(Identifier identifier, Material material, String name, int invSize, int singleRows, int largeRows)
    {
        super(identifier, material);
        ignoreMetaUpdates();

        setDefaultState(getStateManager().getDefaultState()
                .with(FACING_PROPERTY, Direction.NORTH)
                .with(DOUBLE_SIDE_PROPERTY, EDoubleChestSide.LEFT)
                .with(IS_DOUBLE_PROPERTY, false));

        this.name = name;
        this.invSize = invSize;
        this.singleRows = singleRows;
        this.largeRows = largeRows;
    }

    // Minimal Constructor, matches the size of a vanilla chest
    public YuniTemplateChestBlock(Identifier identifier, Material material)
    {
        this(identifier, material, "Chest", 27, 3, 3);
    }

    @Override
    public void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        super.appendProperties(builder);
        builder.add(FACING_PROPERTY);
        builder.add(DOUBLE_SIDE_PROPERTY);
        builder.add(IS_DOUBLE_PROPERTY);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext context)
    {
        Direction facing = context.getHorizontalPlayerFacing().rotateClockwise(Direction.Axis.Y);
        Direction.Axis facingAxis = facing.getAxis();
        BlockState state = getDefaultState().with(FACING_PROPERTY, facing);
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockPos facingPos = pos.offset(context.getSide().getOpposite());
        Block facingBlock = Block.BLOCKS[world.getBlockId(facingPos.x, facingPos.y, facingPos.z)];
        BlockState facingState = world.getBlockState(facingPos);

        if (!canBeDouble())
            return state;

        if (player != null && player.isSneaking() && context.getSide().getAxis() != Direction.Axis.Y && facingBlock.id == id && !facingState.get(IS_DOUBLE_PROPERTY))
        {
            facing = facingState.get(FACING_PROPERTY);
            state = state.with(FACING_PROPERTY, facing);
            state = state.with(DOUBLE_SIDE_PROPERTY, facingPos.equals(pos.offset(facing)) ? EDoubleChestSide.LEFT : EDoubleChestSide.RIGHT);
            state = state.with(IS_DOUBLE_PROPERTY, true);

            BlockEntity facingEntity = world.getBlockEntity(facingPos.x, facingPos.y, facingPos.z);
            facingState = facingState.with(IS_DOUBLE_PROPERTY, true);
            facingState = facingState.with(DOUBLE_SIDE_PROPERTY, state.get(DOUBLE_SIDE_PROPERTY).getOpposite());
            world.setBlockState(facingPos, facingState);
            world.setBlockEntity(facingPos.x, facingPos.y, facingPos.z, facingEntity);
        }
        else if (player != null && !player.isSneaking())
        {
            for (Direction dir : Direction.values())
                if (dir.getAxis() == facingAxis)
                {
                    BlockPos checkPos = pos.offset(dir);
                    Block checkBlock = Block.BLOCKS[world.getBlockId(checkPos.x, checkPos.y, checkPos.z)];
                    BlockState checkState = world.getBlockState(checkPos);
                    if (checkBlock.id == id && !checkState.get(IS_DOUBLE_PROPERTY))
                    {
                        state = state.with(DOUBLE_SIDE_PROPERTY, checkPos.equals(pos.offset(facing)) ? EDoubleChestSide.LEFT : EDoubleChestSide.RIGHT);
                        state = state.with(IS_DOUBLE_PROPERTY, true);

                        BlockEntity checkEntity = world.getBlockEntity(checkPos.x, checkPos.y, checkPos.z);
                        checkState = checkState.with(IS_DOUBLE_PROPERTY, true);
                        checkState = checkState.with(DOUBLE_SIDE_PROPERTY, state.get(DOUBLE_SIDE_PROPERTY).getOpposite());
                        checkState = checkState.with(FACING_PROPERTY, state.get(FACING_PROPERTY));
                        world.setBlockState(checkPos, checkState);
                        world.setBlockEntity(checkPos.x, checkPos.y, checkPos.z, checkEntity);
                        break;
                    }
                }
        }

        return state;
    }

    @Override
    public void neighborUpdate(World world, int x, int y, int z, int id)
    {
        BlockPos pos = connectionBlockPos(world, x, y, z);
        if (pos != null && world.isAir(pos.x, pos.y, pos.z))
        {
            BlockEntity entity = world.getBlockEntity(x, y, z);
            BlockState state = world.getBlockState(x, y, z);
            state = state.with(IS_DOUBLE_PROPERTY, false);
            world.setBlockState(x, y, z, state);
            world.setBlockEntity(x, y, z, entity);
        }
    }

    @Override
    public boolean onUse(World world, int x, int y, int z, PlayerEntity player)
    {
        if (player.isSneaking() && !sneakingPlayerCanOpen())
            return false;

        BlockEntity chest = world.getBlockEntity(x, y, z);
        BlockPos connectionPos = connectionBlockPos(world, x, y, z);
        if (cantOpen(world, x, y, z) || (connectionPos != null && cantOpen(world, connectionPos.x, connectionPos.y, connectionPos.z)))
            return true;

        if (chest instanceof Inventory chestInv)
        {
            BlockState state = world.getBlockState(x, y, z);
            if (connectionPos != null && state.get(DOUBLE_SIDE_PROPERTY) == EDoubleChestSide.LEFT)
                chestInv = new DoubleInventory("Large " + name, chestInv, (Inventory)world.getBlockEntity(connectionPos.x, connectionPos.y, connectionPos.z));
            else if (connectionPos != null && state.get(DOUBLE_SIDE_PROPERTY) == EDoubleChestSide.RIGHT)
                chestInv = new DoubleInventory("Large " + name, (Inventory)world.getBlockEntity(connectionPos.x, connectionPos.y, connectionPos.z), chestInv);
            GUI_Rows = connectionPos != null ? largeRows * 2 : singleRows;

            if (!world.isRemote)
                openGUI(player, chestInv, connectionPos != null);
        }
        return true;
    }

    public boolean cantOpen(World world, int x, int y, int z)
    {
        Block topBlock = Block.BLOCKS[world.getBlockId(x, y + 1, z)];
        return !(topBlock instanceof YuniTemplateChestBlock) && topBlock.isOpaque();
    }

    public BlockPos connectionBlockPos(World world, int x, int y, int z)
    {
        BlockState state = world.getBlockState(x, y, z);
        if (!state.get(IS_DOUBLE_PROPERTY))
            return null;

        Direction facing = state.get(FACING_PROPERTY);
        EDoubleChestSide side = state.get(DOUBLE_SIDE_PROPERTY);
        BlockPos pos = new BlockPos(x, y, z);
        return pos.offset(facing, side == EDoubleChestSide.LEFT ? 1 : -1);
    }

    @Override
    public void onBreak(World world, int x, int y, int z)
    {
        BlockEntity chest = world.getBlockEntity(x, y, z);

        if (chest instanceof Inventory chestInv)
        {
            for (int i = 0; i < chestInv.size(); i++)
            {
                ItemStack stack = chestInv.getStack(i);
                if (stack != null)
                {
                    float xOffset = this.random.nextFloat() * 0.8F + 0.1F;
                    float yOffset = this.random.nextFloat() * 0.8F + 0.1F;
                    float zOffset = this.random.nextFloat() * 0.8F + 0.1F;

                    while (stack.count > 0)
                    {
                        int dropCount = this.random.nextInt(21) + 10;
                        if (dropCount > stack.count)
                            dropCount = stack.count;

                        stack.count -= dropCount;
                        ItemEntity droppedItem = new ItemEntity(
                                world,
                                (float)x + xOffset,
                                (float)y + yOffset,
                                (float)z + zOffset,
                                new ItemStack(stack.itemId, dropCount, stack.getDamage())
                        );

                        droppedItem.velocityX = (float)this.random.nextGaussian() * 0.05F;
                        droppedItem.velocityY = (float)this.random.nextGaussian() * 0.05F + 0.2F;
                        droppedItem.velocityZ = (float)this.random.nextGaussian() * 0.05F;
                        world.spawnEntity(droppedItem);
                    }
                }
            }
        }

        super.onBreak(world, x, y, z);
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean canPlaceAt(World world, int x, int y, int z, int side)
    {
        if (!canVerticallyStack())
        {
            Block topBlock = Block.BLOCKS[world.getBlockId(x, y + 1, z)];
            Block bottomBlock = Block.BLOCKS[world.getBlockId(x, y - 1, z)];

            if (topBlock instanceof ChestBlock || topBlock instanceof YuniTemplateChestBlock || bottomBlock instanceof ChestBlock || bottomBlock instanceof  YuniTemplateChestBlock)
                return false;
        }

        return true;
    }

    @Override
    protected BlockEntity createBlockEntity()
    {
        return new YuniChestBlockEntity(name, invSize);
    }

    public ScreenHandler getScreenHandler(PlayerEntity player, Inventory inventory, boolean isDouble)
    {
        return new YuniChestScreenHandler(player.inventory, inventory, GUI_Rows, isDouble);
    }

    public void openGUI(PlayerEntity player, Inventory inventory, boolean isDouble)
    {
        GuiHelper.openGUI(player, NAMESPACE.id("openYuniChest"), inventory, getScreenHandler(player, inventory, isDouble));
    }

    // Whether or not this chest can be made into a double chest
    public boolean canBeDouble()
    {
        return true;
    }

    // Whether or not the player open the chest while sneaking
    public boolean sneakingPlayerCanOpen()
    {
        return false;
    }

    // Whether or not the chests can vertically stack
    public boolean canVerticallyStack()
    {
        return true;
    }
}

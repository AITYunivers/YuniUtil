package io.github.yunivers.yuniutil.util;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Random;

@SuppressWarnings("unused")
public class MathYuniUtil
{
    public static double getBlockPosDistance(BlockPos to, BlockPos from)
    {
        int dx = from.x - to.x;
        int dy = from.y - to.y;
        int dz = from.z - to.z;
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public static Direction directionFromSide(int side)
    {
        return switch (side)
        {
            case 0 -> Direction.DOWN;
            case 1 -> Direction.UP;
            case 2 -> Direction.NORTH;
            case 3 -> Direction.SOUTH;
            case 4 -> Direction.WEST;
            case 5 -> Direction.EAST;
            default -> throw new IllegalStateException("Unexpected value: " + side);
        };
    }

    public static Direction directionFromFacing(int facing)
    {
        return switch (facing)
        {
            case 0 -> Direction.NORTH;
            case 1 -> Direction.WEST;
            case 2 -> Direction.SOUTH;
            case 3 -> Direction.EAST;
            default -> throw new IllegalStateException("Unexpected value: " + facing);
        };
    }

    public static ItemStack addStackToInventory(Inventory inventory, int slot, ItemStack stack, int maxCount)
    {
        stack = stack.copy();
        ItemStack slotStack = inventory.getStack(slot);
        if (slotStack == null || slotStack.count == 0)
            inventory.setStack(slot, stack.split(Math.min(Math.min(stack.count, inventory.getMaxCountPerStack()), maxCount)));
        else if (stack.itemId == slotStack.itemId)
        {
            maxCount = Math.min(maxCount, stack.getMaxCount() - slotStack.count);
            int count = Math.min(Math.min(stack.count, inventory.getMaxCountPerStack() - slotStack.count), maxCount);
            slotStack.count += count;
            stack.split(count);
        }
        return stack;
    }

    public static Inventory getChestInventory(ChestBlockEntity chest)
    {
        if (chest.world.getBlockId(chest.x - 1, chest.y, chest.z) == chest.getBlock().id)
            return new DoubleInventory("Large chest", (ChestBlockEntity)chest.world.getBlockEntity(chest.x - 1, chest.y, chest.z), chest);
        if (chest.world.getBlockId(chest.x + 1, chest.y, chest.z) == chest.getBlock().id)
            return new DoubleInventory("Large chest", (ChestBlockEntity)chest.world.getBlockEntity(chest.x + 1, chest.y, chest.z), chest);
        if (chest.world.getBlockId(chest.x, chest.y, chest.z - 1) == chest.getBlock().id)
            return new DoubleInventory("Large chest", (ChestBlockEntity)chest.world.getBlockEntity(chest.x, chest.y, chest.z - 1), chest);
        if (chest.world.getBlockId(chest.x, chest.y, chest.z + 1) == chest.getBlock().id)
            return new DoubleInventory("Large chest", (ChestBlockEntity)chest.world.getBlockEntity(chest.x, chest.y, chest.z + 1), chest);
        return chest;
    }

    public static double dotProduct(Vec3d vec1, Vec3d vec2)
    {
        return vec1.x * vec2.x + vec1.y * vec2.y + vec1.z * vec2.z;
    }

    /// Matches the function added in Beta 1.8
    public static int randomBounds(Random random, int min, int max)
    {
        if (min >= max)
            return min;
        return random.nextInt(max - min + 1) + min;
    }

    /// Matches the function added in Beta 1.8
    public static long floorToLong(double value)
    {
        long l = (long)value;
        return value < (double)l ? l - 1L : l;
    }

    /// Matches the function added in Beta 1.8
    public static int abs(int value)
    {
        return value >= 0 ? value : -value;
    }
}

package io.github.yunivers.yuniutil.impl.item;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.util.Util;

public interface YuniUtilDoorItem
{
    default void yuniutil$placeDoorBlock(World world, int x, int y, int z, int facing, Block block)
    {
        Util.assertImpl();
    }
}

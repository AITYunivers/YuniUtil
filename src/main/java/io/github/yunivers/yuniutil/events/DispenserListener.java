package io.github.yunivers.yuniutil.events;

import io.github.yunivers.yuniutil.entity.YuniTemplateProjectileEntity;
import io.github.yunivers.yuniutil.item.YuniTemplateThrowableItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.modificationstation.stationapi.api.dispenser.DispenseEvent;
import net.modificationstation.stationapi.api.dispenser.ItemDispenseContext;
import net.modificationstation.stationapi.api.util.math.Direction;

@SuppressWarnings("unused")
public class DispenserListener
{
    @EventListener
    public static void dispenseThrowable(DispenseEvent event) throws Exception
    {
        ItemDispenseContext context = event.context;
        World world = context.dispenser.world;

        if (context.itemStack != null)
        {
            Direction facing = context.direction;

            // Why is everything here flipped?!?!?!
            double xOffset = 0;
            double zOffset = 0;
            if (facing == Direction.NORTH)
                xOffset = -1;
            else if (facing == Direction.SOUTH)
                xOffset = 1;
            else if (facing == Direction.WEST)
                zOffset = 1;
            else if (facing == Direction.EAST)
                zOffset = -1;

            double thrownEntityX = (double)context.dispenser.x + xOffset * 0.6 + 0.5d;
            double thrownEntityY = (double)context.dispenser.y + 0.5d;
            double thrownEntityZ = (double)context.dispenser.z + zOffset * 0.6 + 0.5d;

            // Throw Stone Pebble
            if (Item.ITEMS[context.itemStack.itemId] instanceof YuniTemplateThrowableItem throwable)
            {
                YuniTemplateProjectileEntity throwableEntity = throwable.projectleEntity.getDeclaredConstructor(World.class, Double.class, Double.class, Double.class).newInstance(world, thrownEntityX, thrownEntityY, thrownEntityZ);
                throwableEntity.setVelocity(xOffset, 0.1d, zOffset, 1.1F, 6.0F);
                world.spawnEntity(throwableEntity);
                world.worldEvent(1002, context.dispenser.x, context.dispenser.y, context.dispenser.z, 0);
                event.cancel();
            }
        }
    }
}
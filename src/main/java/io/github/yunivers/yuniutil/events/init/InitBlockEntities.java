package io.github.yunivers.yuniutil.events.init;

import io.github.yunivers.yuniutil.block.entity.YuniChestBlockEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;

public class InitBlockEntities
{
    @SuppressWarnings("unused")
    @EventListener
    public void initBlockEntities(BlockEntityRegisterEvent event)
    {
        event.register(YuniChestBlockEntity.class, "yunichest");
    }
}

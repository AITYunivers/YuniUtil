package io.github.yunivers.yuniutil.events.init;

import io.github.yunivers.yuniutil.block.YuniTemplateChestBlock;
import io.github.yunivers.yuniutil.block.entity.YuniChestBlockEntity;
import io.github.yunivers.yuniutil.client.gui.YuniChestScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.modificationstation.stationapi.api.client.gui.screen.GuiHandler;
import net.modificationstation.stationapi.api.client.registry.GuiHandlerRegistry;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.registry.Registry;
import net.modificationstation.stationapi.api.util.Identifier;

import static io.github.yunivers.yuniutil.YuniUtil.NAMESPACE;

@SuppressWarnings("unused")
public class InitGUIs
{
    @Environment(EnvType.CLIENT)
    @EventListener
    public void initGUIs(GuiHandlerRegistryEvent event)
    {
        GuiHandlerRegistry registry = event.registry;
        Registry.register(registry, Identifier.of(NAMESPACE, "openYuniChest"), new GuiHandler((GuiHandler.ScreenFactoryNoMessage) this::openYuniChest, YuniChestBlockEntity::new));
    }

    @Environment(EnvType.CLIENT)
    public Screen openYuniChest(PlayerEntity player, Inventory inventoryBase)
    {
        return new YuniChestScreen(player.inventory, inventoryBase, YuniTemplateChestBlock.GUI_Rows);
    }
}

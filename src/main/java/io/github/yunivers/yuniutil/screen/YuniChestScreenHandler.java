package io.github.yunivers.yuniutil.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import static io.github.yunivers.yuniutil.client.gui.YuniChestScreen.PADDING_SIDE;
import static io.github.yunivers.yuniutil.client.gui.YuniChestScreen.PADDING_TOP;

public class YuniChestScreenHandler extends ScreenHandler
{
    @SuppressWarnings("unused")
    public YuniChestScreenHandler(Inventory playerInventory, Inventory inventory, int rows, boolean isDouble)
    {
        int columns = (int)Math.ceil((double)inventory.size() / rows);
        int width = 4 + PADDING_SIDE + (Math.max(columns, 9) * 18) + PADDING_SIDE + 4;
        int height = 4 + PADDING_TOP + (rows * 18) + 97;

        // Chest Inventory
        for (int y = 0; y < rows; y++)
        {
            int remainder = inventory.size() % columns;
            int rowSlots = y == rows - 1 ? (remainder == 0 ? columns : remainder) : columns;
            int rowWidth = rowSlots * 18;
            int rowXOffset = width / 2 - rowWidth / 2 + 1;
            for (int x = 0; x < rowSlots; x++)
                this.addSlot(new Slot(inventory, x + y * columns, rowXOffset + x * 18,  4 + PADDING_TOP + y * 18 + 1));
        }

        int invLeft = (width - 176) / 2 + 4 + PADDING_SIDE + 1;
        int invTop = height - 97 + 14 + 1;

        // Player Inventory
        for (int y = 0; y < 3; y++)
            for (int x = 0; x < 9; x++)
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, invLeft + x * 18, invTop + y * 18));

        // Player Hotbar
        for (int x = 0; x < 9; x++)
            this.addSlot(new Slot(playerInventory, x, invLeft + x * 18, height - 24));
    }

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return true;
    }
}
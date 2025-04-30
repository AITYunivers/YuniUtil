package io.github.yunivers.yuniutil.screen;

import io.github.yunivers.yuniutil.screen.slot.ActiveSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class ActiveScreenHandler extends ScreenHandler
{
    public List<ActiveSlot> activeInventorySlots = new ArrayList<>();

    @Override
    public boolean canUse(PlayerEntity player)
    {
        return false;
    }

    protected ActiveSlot addDualSlot(ActiveSlot slot)
    {
        slot.activeSlotNumber = this.activeInventorySlots.size();
        this.activeInventorySlots.add(slot);
        this.addSlot(slot);
        return slot;
    }

    @SuppressWarnings("unchecked")
    public void updateSlots(Inventory inventory)
    {
        for (ActiveSlot slot : activeInventorySlots)
            if (slot.isActive())
                this.slots.set(slot.id, slot);
            else
                this.slots.set(slot.id, new Slot(inventory, slot.id, Integer.MIN_VALUE, Integer.MIN_VALUE));
    }
}

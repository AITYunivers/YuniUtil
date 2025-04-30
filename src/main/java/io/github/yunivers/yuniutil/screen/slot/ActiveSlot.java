package io.github.yunivers.yuniutil.screen.slot;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.slot.Slot;

public class ActiveSlot extends Slot
{
    @Getter
    @Setter
    protected boolean active;
    public int activeSlotNumber;

    public ActiveSlot(Inventory inventory, int index, int x, int y, boolean active)
    {
        super(inventory, index, x, y);
        this.active = active;
    }
}

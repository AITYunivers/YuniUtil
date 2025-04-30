package io.github.yunivers.yuniutil.block.entity;

import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

public class YuniChestBlockEntity extends ChestBlockEntity
{
    private String name;

    public YuniChestBlockEntity(String name, int invSize)
    {
        this.name = name;
        this.inventory = new ItemStack[invSize];
    }

    public YuniChestBlockEntity()
    {
        this("Error", 27);
    }

    @Override
    public int size()
    {
        return this.inventory.length;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public void readNbt(NbtCompound nbt)
    {
        if (!nbt.contains("Version"))
            return;

        byte version = nbt.getByte("Version");
        if (version >= 1)
        {
            name = nbt.getString("Name");
            inventory = new ItemStack[nbt.getInt("InvSize")];
        }

        super.readNbt(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt)
    {
        nbt.putByte("Version", (byte)1);

        nbt.putString("Name", name);
        nbt.putInt("InvSize", inventory.length);

        super.writeNbt(nbt);
    }

    @Override
    public ItemStack getStack(int slot)
    {
        if (inventory[slot] == null)
            return null;
        return inventory[slot];
    }

    @Override
    public ItemStack removeStack(int slot, int amount)
    {
        if (this.inventory[slot] != null)
        {
            if (this.inventory[slot].count <= amount)
            {
                ItemStack stack = this.inventory[slot];
                this.inventory[slot] = null;
                this.markDirty();
                return stack;
            }
            else
            {
                ItemStack stack = this.inventory[slot].split(amount);
                if (this.inventory[slot].count == 0)
                    this.inventory[slot] = null;

                this.markDirty();
                return stack;
            }
        }
        return null;
    }
}
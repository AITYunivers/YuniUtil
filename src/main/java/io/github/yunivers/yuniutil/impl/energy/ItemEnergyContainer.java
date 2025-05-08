package io.github.yunivers.yuniutil.impl.energy;

import io.github.yunivers.yuniutil.api.energy.IEnergyContainerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.impl.item.StationNBTSetter;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class ItemEnergyContainer extends TemplateItem implements IEnergyContainerItem
{
    public static final String ENERGY = "Energy";

    protected int capacity;
    protected int maxReceive;
    protected int maxExtract;

    public ItemEnergyContainer(Identifier identifier)
    {
        super(identifier);
    }

    public ItemEnergyContainer(Identifier identifier, int capacity)
    {
        this(identifier, capacity, capacity, capacity);
    }

    public ItemEnergyContainer(Identifier identifier, int capacity, int maxTransfer)
    {
        this(identifier, capacity, maxTransfer, maxTransfer);
    }

    public ItemEnergyContainer(Identifier identifier, int capacity, int maxReceive, int maxExtract)
    {
        super(identifier);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public ItemEnergyContainer setCapacity(int capacity)
    {
        this.capacity = capacity;
        return this;
    }

    public ItemEnergyContainer setMaxTransfer(int maxTransfer)
    {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public ItemEnergyContainer setMaxReceive(int maxReceive)
    {
        this.maxReceive = maxReceive;
        return this;
    }

    public ItemEnergyContainer setMaxExtract(int maxExtract)
    {
        this.maxExtract = maxExtract;
        return this;
    }

    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate)
    {
        NbtCompound nbt = container.getStationNbt();
        if (nbt == null)
            nbt = new NbtCompound();
        int stored = Math.min(nbt.getInt(ENERGY), getMaxEnergyStored(container));
        int energyReceived = Math.min(capacity - stored, Math.min(this.maxReceive, maxReceive));

        if (!simulate)
        {
            stored += energyReceived;
            nbt.putInt(ENERGY, stored);
        }
        ((StationNBTSetter)(Object)container).setStationNbt(nbt);
        return energyReceived;
    }

    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

        NbtCompound nbt = container.getStationNbt();
        if (nbt == null || !nbt.contains(ENERGY))
            return 0;
        int stored = Math.min(nbt.getInt(ENERGY), getMaxEnergyStored(container));
        int energyExtracted = Math.min(stored, Math.min(this.maxExtract, maxExtract));

        if (!simulate)
        {
            stored -= energyExtracted;
            nbt.putInt(ENERGY, stored);
        }
        ((StationNBTSetter)(Object)container).setStationNbt(nbt);
        return energyExtracted;
    }

    @Override
    public int getEnergyStored(ItemStack container)
    {
        NbtCompound nbt = container.getStationNbt();
        if (nbt == null || !nbt.contains(ENERGY))
            return 0;
        return Math.min(nbt.getInt(ENERGY), getMaxEnergyStored(container));
    }

    @Override
    public int getMaxEnergyStored(ItemStack container)
    {
        return capacity;
    }
}
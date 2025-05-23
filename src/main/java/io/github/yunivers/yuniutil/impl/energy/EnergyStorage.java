package io.github.yunivers.yuniutil.impl.energy;

import io.github.yunivers.yuniutil.api.energy.IEnergyStorage;
import lombok.Getter;
import net.minecraft.nbt.NbtCompound;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class EnergyStorage implements IEnergyStorage
{
    protected int energy;
    protected int capacity;
    @Getter
    protected int maxReceive;
    @Getter
    protected int maxExtract;

    public EnergyStorage(int capacity)
    {
        this(capacity, capacity, capacity);
    }

    public EnergyStorage(int capacity, int maxTransfer)
    {
        this(capacity, maxTransfer, maxTransfer);
    }

    public EnergyStorage(int capacity, int maxReceive, int maxExtract)
    {
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public EnergyStorage readFromNBT(NbtCompound nbt)
    {
        this.energy = nbt.getInt("Energy");

        if (energy > capacity)
            energy = capacity;
        return this;
    }

    public NbtCompound writeToNBT(NbtCompound nbt)
    {
        if (energy < 0)
            energy = 0;

        nbt.putInt("Energy", energy);
        return nbt;
    }

    public EnergyStorage setCapacity(int capacity)
    {
        this.capacity = capacity;

        if (energy > capacity)
            energy = capacity;
        return this;
    }

    public EnergyStorage setMaxTransfer(int maxTransfer)
    {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public EnergyStorage setMaxReceive(int maxReceive)
    {
        this.maxReceive = maxReceive;
        return this;
    }

    public EnergyStorage setMaxExtract(int maxExtract)
    {
        this.maxExtract = maxExtract;
        return this;
    }

    public void setEnergyStored(int energy)
    {
        this.energy = energy;

        if (this.energy > capacity)
            this.energy = capacity;
        else if (this.energy < 0)
            this.energy = 0;
    }

    public void modifyEnergyStored(int energy)
    {
        this.energy += energy;

        if (this.energy > capacity)
            this.energy = capacity;
        else if (this.energy < 0)
            this.energy = 0;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate)
    {
        int energyReceived = Math.min(capacity - energy, Math.min(this.maxReceive, maxReceive));
        if (!simulate)
            energy += energyReceived;
        return energyReceived;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate)
    {
        int energyExtracted = Math.min(energy, Math.min(this.maxExtract, maxExtract));
        if (!simulate)
            energy -= energyExtracted;
        return energyExtracted;
    }

    @Override
    public int getEnergyStored()
    {
        return energy;
    }

    @Override
    public int getMaxEnergyStored()
    {
        return capacity;
    }
}
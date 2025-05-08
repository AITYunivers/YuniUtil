package io.github.yunivers.yuniutil.impl.energy;

import io.github.yunivers.yuniutil.api.energy.IEnergyProvider;
import io.github.yunivers.yuniutil.api.energy.IEnergyReceiver;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.modificationstation.stationapi.api.util.math.Direction;

@SuppressWarnings("unused")
public class BlockEnergyHandler extends BlockEntity implements IEnergyReceiver, IEnergyProvider
{
    protected EnergyStorage storage = new EnergyStorage(32000);

    @Override
    public void readNbt(NbtCompound nbt) {

        super.readNbt(nbt);
        storage.readFromNBT(nbt);
    }

    @Override
    public void writeNbt(NbtCompound nbt)
    {
        super.writeNbt(nbt);
        storage.writeToNBT(nbt);
    }

    @Override
    public boolean canConnectEnergy(Direction from)
    {
        return true;
    }

    @Override
    public int receiveEnergy(Direction from, int maxReceive, boolean simulate)
    {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(Direction from, int maxExtract, boolean simulate)
    {
        return storage.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(Direction from)
    {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(Direction from)
    {
        return storage.getMaxEnergyStored();
    }
}

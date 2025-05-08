package io.github.yunivers.yuniutil.api.energy;

public interface IEnergyStorage
{
    int receiveEnergy(int maxReceive, boolean simulate);
    int extractEnergy(int maxExtract, boolean simulate);
    int getEnergyStored();
    int getMaxEnergyStored();
}
package io.github.yunivers.yuniutil.api.energy;

import net.minecraft.item.ItemStack;

@SuppressWarnings("unused")
public interface IEnergyContainerItem
{
    int receiveEnergy(ItemStack container, int maxReceive, boolean simulate);
    int extractEnergy(ItemStack container, int maxExtract, boolean simulate);
    int getEnergyStored(ItemStack container);
    int getMaxEnergyStored(ItemStack container);
}
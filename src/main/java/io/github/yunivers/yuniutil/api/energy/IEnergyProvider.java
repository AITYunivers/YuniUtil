package io.github.yunivers.yuniutil.api.energy;

import net.modificationstation.stationapi.api.util.math.Direction;

@SuppressWarnings("unused")
public interface IEnergyProvider extends IEnergyHandler
{
    int extractEnergy(Direction from, int maxExtract, boolean simulate);
}
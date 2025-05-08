package io.github.yunivers.yuniutil.api.energy;

import net.modificationstation.stationapi.api.util.math.Direction;

@SuppressWarnings("unused")
public interface IEnergyReceiver extends IEnergyHandler
{
    int receiveEnergy(Direction from, int maxReceive, boolean simulate);
}
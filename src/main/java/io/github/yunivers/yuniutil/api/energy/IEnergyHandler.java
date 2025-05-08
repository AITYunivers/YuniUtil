package io.github.yunivers.yuniutil.api.energy;

import net.modificationstation.stationapi.api.util.math.Direction;

@SuppressWarnings("unused")
public interface IEnergyHandler extends IEnergyConnection
{
    int getEnergyStored(Direction from);
    int getMaxEnergyStored(Direction from);
}
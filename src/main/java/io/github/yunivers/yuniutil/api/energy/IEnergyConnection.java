package io.github.yunivers.yuniutil.api.energy;

import net.modificationstation.stationapi.api.util.math.Direction;

@SuppressWarnings("unused")
public interface IEnergyConnection
{
    boolean canConnectEnergy(Direction from);
}
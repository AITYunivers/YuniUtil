package io.github.yunivers.yuniutil.util;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum EDoubleChestSide implements StringIdentifiable
{
    LEFT("left"),
    RIGHT("right");

    private final String name;

    EDoubleChestSide(String str)
    {
        name = str;
    }

    @Override
    public String asString()
    {
        return name;
    }

    public EDoubleChestSide getOpposite()
    {
        return this == LEFT ? RIGHT : LEFT;
    }
}

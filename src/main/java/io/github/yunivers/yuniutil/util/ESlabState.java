package io.github.yunivers.yuniutil.util;

import net.modificationstation.stationapi.api.util.StringIdentifiable;

public enum ESlabState implements StringIdentifiable
{
    @SuppressWarnings("unused")
    TOP("top"), // Unused for now, might implement optionally someday
    BOTTOM("bottom"),
    DOUBLE("double");

    private final String value;

    ESlabState(String value)
    {
        this.value = value;
    }

    @Override
    public String asString()
    {
        return value;
    }
}

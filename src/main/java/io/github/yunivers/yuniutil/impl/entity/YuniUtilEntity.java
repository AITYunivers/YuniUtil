package io.github.yunivers.yuniutil.impl.entity;

import net.modificationstation.stationapi.api.util.Util;

@SuppressWarnings("unused")
public interface YuniUtilEntity
{
    default boolean yuniutil$isFireImmune()
    {
        return Util.assertImpl();
    }

    default void yuniutil$setOnFire(int seconds)
    {
        Util.assertImpl();
    }
}

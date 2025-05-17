package io.github.yunivers.yuniutil;

import net.modificationstation.stationapi.api.util.Namespace;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("unused")
public class YuniUtil
{
    @SuppressWarnings("UnstableApiUsage")
    public static final Namespace NAMESPACE = Namespace.resolve();

    public static final Logger LOGGER = NAMESPACE.getLogger();

    // Textures
    public static int TEXTURE_STEM;
    public static int TEXTURE_STEM_CONNECTED;
}

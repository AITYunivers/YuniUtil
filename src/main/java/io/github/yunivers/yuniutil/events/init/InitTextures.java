package io.github.yunivers.yuniutil.events.init;

import io.github.yunivers.yuniutil.YuniUtil;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.client.texture.atlas.ExpandableAtlas;

import static io.github.yunivers.yuniutil.YuniUtil.NAMESPACE;

@SuppressWarnings("unused")
public class InitTextures
{
    @EventListener
    public void initTextures(TextureRegisterEvent event)
    {
        ExpandableAtlas terrainAtlas = Atlases.getTerrain();
        YuniUtil.TEXTURE_STEM = terrainAtlas.addTexture(NAMESPACE.id("block/stem")).index;
        YuniUtil.TEXTURE_STEM_CONNECTED = terrainAtlas.addTexture(NAMESPACE.id("block/stem_connected")).index;
    }
}

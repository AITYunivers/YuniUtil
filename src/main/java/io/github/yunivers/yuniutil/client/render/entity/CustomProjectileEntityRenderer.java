package io.github.yunivers.yuniutil.client.render.entity;

import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.entity.Entity;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.util.Identifier;

@SuppressWarnings("unused")
public class CustomProjectileEntityRenderer extends ProjectileEntityRenderer
{
    private final Identifier ditto;

    public CustomProjectileEntityRenderer(Identifier ditto)
    {
        super(-1);
        this.ditto = ditto;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Override
    public void render(Entity entity, double x, double y, double z, float yaw, float pitch)
    {
        if (itemTextureId == -1)
            itemTextureId = Atlases.getGuiItems().idToTex.get(ditto).index;
        super.render(entity, x, y, z, yaw, pitch);
    }
}

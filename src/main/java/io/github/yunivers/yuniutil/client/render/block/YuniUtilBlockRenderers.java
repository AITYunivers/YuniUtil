package io.github.yunivers.yuniutil.client.render.block;

import io.github.yunivers.yuniutil.block.YuniTemplateStemCrop;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.block.BlockRenderManager;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlas;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import org.spongepowered.asm.mixin.Unique;

public class YuniUtilBlockRenderers
{
    // <editor-fold desc="Stem Crop">
    @Unique
    public static boolean renderBlockStem(BlockRenderManager renderManager, Block block, int x, int y, int z)
    {
        YuniTemplateStemCrop blockStem = (YuniTemplateStemCrop)block;
        Tessellator tessellator = Tessellator.INSTANCE;
        float lum = block.getLuminance(renderManager.blockView, x, y, z);
        float f = 1.0f;
        int n4 = blockStem.getColorMultiplier(renderManager.blockView, x, y, z);
        float f2 = (float)(n4 >> 16 & 0xFF) / 255.0f;
        float f3 = (float)(n4 >> 8 & 0xFF) / 255.0f;
        float f4 = (float)(n4 & 0xFF) / 255.0f;
        if (Minecraft.INSTANCE.options.anaglyph3d)
        {
            float f5 = (f2 * 30.0f + f3 * 59.0f + f4 * 11.0f) / 100.0f;
            float f6 = (f2 * 30.0f + f3 * 70.0f) / 100.0f;
            float f7 = (f2 * 30.0f + f4 * 70.0f) / 100.0f;
            f2 = f5;
            f3 = f6;
            f4 = f7;
        }
        tessellator.color(f * f2 * lum, f * f3 * lum, f * f4 * lum);
        blockStem.updateBoundingBox(renderManager.blockView, x, y, z);
        int n5 = blockStem.getAdjacentDirection(renderManager.blockView, x, y, z);
        if (n5 < 0)
            renderBlockStemSmall(renderManager, blockStem, renderManager.blockView.getBlockMeta(x, y, z), blockStem.maxY, x, y, z);
        else
        {
            renderBlockStemSmall(renderManager, blockStem, renderManager.blockView.getBlockMeta(x, y, z), 0.5, x, y, z);
            renderBlockStemBig(renderManager, blockStem, renderManager.blockView.getBlockMeta(x, y, z), n5, blockStem.maxY, x, y, z);
        }
        return true;
    }

    @Unique
    public static void renderBlockStemSmall(BlockRenderManager renderManager, Block block, int meta, double height, double x, double y, double z)
    {
        Tessellator tessellator = Tessellator.INSTANCE;
        int texture = block.getTexture(0, meta);
        if (renderManager.textureOverride >= 0)
            texture = renderManager.textureOverride;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(texture);
        double d5 = sprite.getStartU();
        double d6 = sprite.getEndU();
        double d7 = sprite.getStartV();
        double d8 = sprite.getEndV();
        double d9 = x + 0.5 - (double)0.45f;
        double d10 = x + 0.5 + (double)0.45f;
        double d11 = z + 0.5 - (double)0.45f;
        double d12 = z + 0.5 + (double)0.45f;
        tessellator.vertex(d9, y + height, d11, d5, d7);
        tessellator.vertex(d9, y + 0.0, d11, d5, d8);
        tessellator.vertex(d10, y + 0.0, d12, d6, d8);
        tessellator.vertex(d10, y + height, d12, d6, d7);
        tessellator.vertex(d10, y + height, d12, d5, d7);
        tessellator.vertex(d10, y + 0.0, d12, d5, d8);
        tessellator.vertex(d9, y + 0.0, d11, d6, d8);
        tessellator.vertex(d9, y + height, d11, d6, d7);
        tessellator.vertex(d9, y + height, d12, d5, d7);
        tessellator.vertex(d9, y + 0.0, d12, d5, d8);
        tessellator.vertex(d10, y + 0.0, d11, d6, d8);
        tessellator.vertex(d10, y + height, d11, d6, d7);
        tessellator.vertex(d10, y + height, d11, d5, d7);
        tessellator.vertex(d10, y + 0.0, d11, d5, d8);
        tessellator.vertex(d9, y + 0.0, d12, d6, d8);
        tessellator.vertex(d9, y + height, d12, d6, d7);
    }

    @Unique
    public static void renderBlockStemBig(BlockRenderManager renderManager, Block block, int meta, int direction, double height, double x, double y, double z)
    {
        Tessellator tessellator = Tessellator.INSTANCE;
        int n3 = block.getTexture(0, meta * -1);
        if (renderManager.textureOverride >= 0)
            n3 = renderManager.textureOverride;

        Atlas.Sprite sprite = Atlases.getTerrain().getTexture(n3);
        double d5 = sprite.getStartU();
        double d6 = sprite.getEndU();
        double d7 = sprite.getStartV();
        double d8 = sprite.getEndV();
        double d9 = x + 0.5 - 0.5;
        double d10 = x + 0.5 + 0.5;
        double d11 = z + 0.5 - 0.5;
        double d12 = z + 0.5 + 0.5;
        double d13 = x + 0.5;
        double d14 = z + 0.5;
        if ((direction + 1) / 2 % 2 == 1)
        {
            double d15 = d6;
            d6 = d5;
            d5 = d15;
        }
        if (direction < 2)
        {
            tessellator.vertex(d9, y + height, d14, d5, d7);
            tessellator.vertex(d9, y + 0.0, d14, d5, d8);
            tessellator.vertex(d10, y + 0.0, d14, d6, d8);
            tessellator.vertex(d10, y + height, d14, d6, d7);
            tessellator.vertex(d10, y + height, d14, d6, d7);
            tessellator.vertex(d10, y + 0.0, d14, d6, d8);
            tessellator.vertex(d9, y + 0.0, d14, d5, d8);
            tessellator.vertex(d9, y + height, d14, d5, d7);
        }
        else
        {
            tessellator.vertex(d13, y + height, d12, d5, d7);
            tessellator.vertex(d13, y + 0.0, d12, d5, d8);
            tessellator.vertex(d13, y + 0.0, d11, d6, d8);
            tessellator.vertex(d13, y + height, d11, d6, d7);
            tessellator.vertex(d13, y + height, d11, d6, d7);
            tessellator.vertex(d13, y + 0.0, d11, d6, d8);
            tessellator.vertex(d13, y + 0.0, d12, d5, d8);
            tessellator.vertex(d13, y + height, d12, d5, d7);
        }
    }
    // </editor-fold>
}

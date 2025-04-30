package io.github.yunivers.yuniutil.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DrawContext;

public class NineSlicesYuniUtil
{
    private final static String[] templateTextures = new String[]
    {
        "/assets/yunichests/stationapi/textures/gui/NineSlices/TopLeft.png",
        "/assets/yunichests/stationapi/textures/gui/NineSlices/Top.png",
        "/assets/yunichests/stationapi/textures/gui/NineSlices/TopRight.png",
        "/assets/yunichests/stationapi/textures/gui/NineSlices/Right.png",
        "/assets/yunichests/stationapi/textures/gui/NineSlices/BottomRight.png",
        "/assets/yunichests/stationapi/textures/gui/NineSlices/Bottom.png",
        "/assets/yunichests/stationapi/textures/gui/NineSlices/BottomLeft.png",
        "/assets/yunichests/stationapi/textures/gui/NineSlices/Left.png",
        "/assets/yunichests/stationapi/textures/gui/NineSlices/Center.png"
    };

    public String[] textures = templateTextures;

    public void DrawNineSlices(int x, int y, int width, int height, DrawContext draw, Minecraft minecraft)
    {
        for (int i = 0; i < textures.length; i++)
        {
            int textureId = minecraft.textureManager.getTextureId(textures[i]);
            minecraft.textureManager.bindTexture(textureId);
            switch (i)
            {
                case 0: // Top Left
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x, y, 4, 4, 0, 0, 4, 4);
                    break;
                case 1: // Top
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x + 4, y, 1, 4, 0, 0, width - 8, 4, 1, 4);
                    break;
                case 2: // Top Right
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x + width - 4, y, 4, 4, 0, 0, 4, 4);
                    break;
                case 3: // Right
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x + width - 4, y + 4, 4, 1, 0, 0, 4, height - 8, 4, 1);
                    break;
                case 4: // Bottom Right
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x + width - 4, y + height - 4, 4, 4, 0, 0, 4, 4);
                    break;
                case 5: // Bottom
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x + 4, y + height - 4, 1, 4, 0, 0, width - 8, 4, 1, 4);
                    break;
                case 6: // Bottom Left
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x, y + height - 4, 4, 4, 0, 0, 4, 4);
                    break;
                case 7: // Left
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x, y + 4, 4, 1, 0, 0, 4, height - 8, 4, 1);
                    break;
                case 8: // Center
                    TextureYuniUtil.DrawContextHelper.drawTexture(draw, x + 4, y + 4, 1, 1, 0, 0, width - 8, height - 8, 1, 1);
                    break;
            }
        }
    }
}
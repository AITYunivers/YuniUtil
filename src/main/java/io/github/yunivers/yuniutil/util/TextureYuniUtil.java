package io.github.yunivers.yuniutil.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.Tessellator;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SuppressWarnings("unused")
public class TextureYuniUtil
{
    /// Converts an RGBA array from a file into a BufferedImage
    /// Was originally made for use with SpriteAtlasTexture.save
    public static BufferedImage convertFile(Path path, int width, int height) throws IOException
    {
        byte[] rgba = Files.readAllBytes(path);

        BufferedImage restored = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        int index = 0;

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
            {
                int r = rgba[index++] & 0xFF;
                int g = rgba[index++] & 0xFF;
                int b = rgba[index++] & 0xFF;
                int a = rgba[index++] & 0xFF;

                int argb = (a << 24) | (r << 16) | (g << 8) | b;
                restored.setRGB(x, y, argb);
            }

        return restored;
    }

    public static class DrawContextHelper
    {
        public static void drawTexture(DrawContext draw, double x, double y, double width, double height, double u, double v, int texWidth, int texHeight)
        {
            drawTexture(draw, x, y, width, height, u, v, width, height, texWidth, texHeight);
        }

        @SuppressWarnings("UnnecessaryLocalVariable")
        public static void drawTexture(DrawContext draw, double x, double y, double width, double height, double u, double v, double scaledWidth, double scaledHeight, int texWidth, int texHeight)
        {
            double scaleU = 1d / texWidth;
            double scaleV = 1d / texHeight;

            double minX = x;
            double minY = y;
            double maxX = x + scaledWidth;
            double maxY = y + scaledHeight;

            double minU = u * scaleU;
            double minV = v * scaleV;
            double maxU = (u + width) * scaleU;
            double maxV = (v + height) * scaleV;

            Tessellator t = Tessellator.INSTANCE;
            t.startQuads();
            t.vertex(minX, maxY, draw.zOffset, minU, maxV);
            t.vertex(maxX, maxY, draw.zOffset, maxU, maxV);
            t.vertex(maxX, minY, draw.zOffset, maxU, minV);
            t.vertex(minX, minY, draw.zOffset, minU, minV);
            t.draw();
        }
    }
}

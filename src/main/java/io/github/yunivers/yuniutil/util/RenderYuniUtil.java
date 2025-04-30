package io.github.yunivers.yuniutil.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.render.Tessellator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

@SuppressWarnings("unused")
public class RenderYuniUtil
{
    public void renderLine(Vector3f origin, double dist, Vector2f dir)
    {
        double yawRad = Math.toRadians(dir.x);
        double pitchRad = Math.toRadians(dir.y);

        double dx = -Math.sin(yawRad) * Math.cos(pitchRad) * dist;
        double dy = -Math.sin(pitchRad) * dist;
        double dz = Math.cos(yawRad) * Math.cos(pitchRad) * dist;

        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start(GL11.GL_LINES);
        tessellator.vertex(origin.x, origin.y, origin.z);
        tessellator.vertex(origin.x + (float)dx, origin.y + (float)dy, origin.z + (float)dz);
        tessellator.draw();
    }

    public void renderLine(Vector3f startPos, Vector3f endPos, int color)
    {
        float r = ((color >> 16) & 0xFF) / 255.0f;
        float g = ((color >> 8) & 0xFF) / 255.0f;
        float b = (color & 0xFF) / 255.0f;
        GL11.glColor3f(r, g, b);

        Tessellator t = Tessellator.INSTANCE;
        t.start(GL11.GL_LINES);
        t.vertex(startPos.x, startPos.y, startPos.z);
        t.vertex(endPos.x, endPos.y, endPos.z);
        t.draw();
    }

    public void renderBox(PlayerEntity player, Box boundingBox, int argb, int tickDelta)
    {
        float a = ((argb >> 24) & 0xFF) / 255.0f;
        float r = ((argb >> 16) & 0xFF) / 255.0f;
        float g = ((argb >> 8) & 0xFF) / 255.0f;
        float b = (argb & 0xFF) / 255.0f;

        // Copied from WorldRenderer.renderBlockOutline
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(r, g, b, a);
        GL11.glLineWidth(2.0F);
        GL11.glDisable(3553);
        GL11.glDepthMask(false);
        float expandBy = 0.002F;

        double xDelta = player.lastTickX + (player.x - player.lastTickX) * (double) tickDelta;
        double yDelta = player.lastTickY + (player.y - player.lastTickY) * (double) tickDelta;
        double zDelta = player.lastTickZ + (player.z - player.lastTickZ) * (double) tickDelta;
        Minecraft.INSTANCE.worldRenderer.renderOutline(boundingBox.expand(expandBy, expandBy, expandBy).offset(-xDelta, -yDelta, -zDelta));

        GL11.glDepthMask(true);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
    }
}

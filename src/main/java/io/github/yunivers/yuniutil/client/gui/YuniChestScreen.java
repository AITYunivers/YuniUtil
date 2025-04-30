package io.github.yunivers.yuniutil.client.gui;

import io.github.yunivers.yuniutil.screen.YuniChestScreenHandler;
import io.github.yunivers.yuniutil.util.NineSlicesYuniUtil;
import io.github.yunivers.yuniutil.util.TextureYuniUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.inventory.DoubleInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

@Environment(EnvType.CLIENT)
public class YuniChestScreen extends HandledScreen
{
    public final static int PADDING_TOP = 13;
    public final static int PADDING_SIDE = 3;
    private final Inventory inventory;
    private final int rows;
    private final int columns;

    public YuniChestScreen(Inventory playerInventory, Inventory inventory, int rows)
    {
        super(new YuniChestScreenHandler(playerInventory, inventory, rows, inventory instanceof DoubleInventory));
        this.inventory = inventory;
        this.rows = rows;
        this.columns = (int)Math.ceil((double)inventory.size() / rows);

        int width = Math.max(columns, 9) * 18;
        backgroundWidth = 4 + PADDING_SIDE + width + PADDING_SIDE + 4;
        backgroundHeight = 4 + PADDING_TOP + (rows * 18) + 97;
    }

    protected void drawForeground()
    {
        textRenderer.draw(inventory.getName(), 8, 6, 0x404040);
        textRenderer.draw("Inventory", (backgroundWidth - 176 * 2) / 2 + 8, backgroundHeight - 96 + 2, 0x404040);
    }

    @Override
    protected void drawBackground(float tickDelta)
    {
        int left = (width - backgroundWidth) / 2;
        int top = (height - backgroundHeight) / 2;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        new NineSlicesYuniUtil().DrawNineSlices(left, top, backgroundWidth, backgroundHeight - 97 + PADDING_SIDE * 2, this, minecraft);

        int textureId = minecraft.textureManager.getTextureId("/assets/yunichests/stationapi/textures/gui/Slot.png");
        minecraft.textureManager.bindTexture(textureId);
        for (int y = 0; y < rows; y++)
        {
            int remainder = inventory.size() % columns;
            int rowSlots = y == rows - 1 ? (remainder == 0 ? columns : remainder) : columns;
            int rowWidth = rowSlots * 18;
            int rowXOffset = width / 2 - rowWidth / 2;
            for (int x = 0; x < rowSlots; x++)
                TextureYuniUtil.DrawContextHelper.drawTexture(this, rowXOffset + x * 18, top + 4 + PADDING_TOP + (y * 18), 18, 18, 0, 0, 18, 18);
        }

        textureId = minecraft.textureManager.getTextureId("/assets/yunichests/stationapi/textures/gui/Inventory.png");
        minecraft.textureManager.bindTexture(textureId);
        TextureYuniUtil.DrawContextHelper.drawTexture(this, left + (backgroundWidth - 176) / 2, top + backgroundHeight - 97, 176, 97, 0, 0, 176, 97);

        if (columns > 9)
        {
            textureId = minecraft.textureManager.getTextureId("/assets/yunichests/stationapi/textures/gui/InventoryTopLeftConnection.png");
            minecraft.textureManager.bindTexture(textureId);
            TextureYuniUtil.DrawContextHelper.drawTexture(this, left + (backgroundWidth - 176) / 2, top + backgroundHeight - 97, 4, 6, 0, 0, 4, 6);

            textureId = minecraft.textureManager.getTextureId("/assets/yunichests/stationapi/textures/gui/InventoryTopRightConnection.png");
            minecraft.textureManager.bindTexture(textureId);
            TextureYuniUtil.DrawContextHelper.drawTexture(this, left + (backgroundWidth - 176) / 2 + 176 - 3, top + backgroundHeight - 97, 4, 6, 0, 0, 4, 6);
        }
    }
}
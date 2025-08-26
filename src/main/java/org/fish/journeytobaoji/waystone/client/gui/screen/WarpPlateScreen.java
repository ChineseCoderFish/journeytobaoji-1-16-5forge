package org.fish.journeytobaoji.waystone.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.block.WarpPlateBlock;
import net.blay09.mods.waystones.container.WarpPlateContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;


public class WarpPlateScreen extends ContainerScreen<WarpPlateContainer> {

    private static final ResourceLocation WARP_PLATE_GUI_TEXTURES = new ResourceLocation(Waystones.MOD_ID, "textures/gui/container/warp_plate.png");
    private static final Style GALACTIC_STYLE = Style.EMPTY.setFontId(new ResourceLocation("minecraft", "alt"));

    public WarpPlateScreen(WarpPlateContainer container, PlayerInventory playerInventory, ITextComponent title) {
        super(container, playerInventory, title);
        ySize = 196;
        playerInventoryTitleY = 92;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1f, 1f, 1f, 1f);
        minecraft.getTextureManager().bindTexture(WARP_PLATE_GUI_TEXTURES);
        blit(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);

        blit(matrixStack, guiLeft + 86, guiTop + 34, 176, 4, 4, (int) (10 * container.getAttunementProgress()));
        blit(matrixStack, guiLeft + 107 - (int) (10 * container.getAttunementProgress()), guiTop + 51, 176, 0, (int) (10 * container.getAttunementProgress()), 4);
        blit(matrixStack, guiLeft + 86, guiTop + 72 - (int) (10 * container.getAttunementProgress()), 176, 4, 4, (int) (10 * container.getAttunementProgress()));
        blit(matrixStack, guiLeft + 69, guiTop + 51, 176, 0, (int) (10 * container.getAttunementProgress()), 4);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        super.drawGuiContainerForegroundLayer(matrixStack, x, y);

        ITextComponent galacticName = WarpPlateBlock.getGalacticName(container.getWaystone());
        int width = font.getStringPropertyWidth(galacticName);
        drawString(matrixStack, font, galacticName, xSize - width - 5, 5, 0xFFFFFFFF);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }
}

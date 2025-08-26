package org.fish.journeytobaoji.waystone.client.gui.widget;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WaystoneButton extends Button {

    private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("textures/gui/container/enchanting_table.png");

    private final int xpLevelCost;

    public WaystoneButton(int x, int y, IWaystone waystone, int xpLevelCost, IPressable pressable) {
        super(x, y, 200, 20, getWaystoneNameComponent(waystone), pressable);
        PlayerEntity player = Minecraft.getInstance().player;
        this.xpLevelCost = xpLevelCost;
        if (player == null || !PlayerWaystoneManager.mayTeleportToWaystone(player, waystone)) {
            active = false;
        } else if (player.experienceLevel < xpLevelCost && !player.abilities.isCreativeMode) {
            active = false;
        }
    }

    private static ITextComponent getWaystoneNameComponent(IWaystone waystone) {
        String effectiveName = waystone.getName();
        if (effectiveName.isEmpty()) {
            effectiveName = I18n.format("gui.waystones.waystone_selection.unnamed_waystone");
        }
        final StringTextComponent textComponent = new StringTextComponent(effectiveName);
        if (waystone.isGlobal()) {
            textComponent.mergeStyle(TextFormatting.YELLOW);
        }
        return textComponent;
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(matrixStack, mouseX, mouseY, partialTicks);
        RenderSystem.color4f(1f, 1f, 1f, 1f);

        Minecraft mc = Minecraft.getInstance();
        if (xpLevelCost > 0) {
            boolean canAfford = Objects.requireNonNull(mc.player).experienceLevel >= xpLevelCost || mc.player.abilities.isCreativeMode;
            mc.getTextureManager().bindTexture(ENCHANTMENT_TABLE_GUI_TEXTURE);
            blit(matrixStack, x + 2, y + 2, (Math.min(xpLevelCost, 3) - 1) * 16, 223 + (!canAfford ? 16 : 0), 16, 16);

            if (xpLevelCost > 3) {
                mc.fontRenderer.drawString(matrixStack, "+", x + 17, y + 6, 0xC8FF8F);
            }

            if (isHovered && mouseX <= x + 16) {
                final List<ITextComponent> tooltip = new ArrayList<>();
                final TranslationTextComponent levelRequirementText = new TranslationTextComponent("gui.waystones.waystone_selection.level_requirement", xpLevelCost);
                levelRequirementText.mergeStyle(canAfford ? TextFormatting.GREEN : TextFormatting.RED);
                tooltip.add(levelRequirementText);
                final Screen screen = Minecraft.getInstance().currentScreen;
                Objects.requireNonNull(screen).func_243308_b(matrixStack, tooltip, mouseX, mouseY + mc.fontRenderer.FONT_HEIGHT); // renderTooltip
                // TOOD used to be: GuiUtils.drawHoveringText(matrixStack, tooltip, mouseX, mouseY + mc.fontRenderer.FONT_HEIGHT, mc.getMainWindow().getWidth(), mc.getMainWindow().getHeight(), 200, mc.fontRenderer);
            }
            RenderSystem.disableLighting();
        }
    }

}

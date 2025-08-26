package org.fish.journeytobaoji.waystone.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.block.SharestoneBlock;
import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.item.ModItems;
import net.blay09.mods.waystones.tileentity.SharestoneTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;

public class SharestoneRenderer extends TileEntityRenderer<SharestoneTileEntity> {

    private static final SharestoneModel model = new SharestoneModel();
    private static final RenderMaterial MATERIAL = new RenderMaterial(Atlases.SIGN_ATLAS, new ResourceLocation(Waystones.MOD_ID, "entity/sharestone_color"));

    private static ItemStack warpStoneItem;

    public SharestoneRenderer(TileEntityRendererDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void render(SharestoneTileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        World world = tileEntity.getWorld();
        BlockState state = tileEntity.getBlockState();
        if (world == null || state.get(SharestoneBlock.HALF) != DoubleBlockHalf.LOWER) {
            return;
        }

        long gameTime = world.getGameTime();

        DyeColor color = ((SharestoneBlock) state.getBlock()).getColor();
        if (color != null) {
            matrixStack.push();
            matrixStack.translate(0.5f, 0f, 0.5f);
            matrixStack.rotate(state.get(SharestoneBlock.FACING).getRotation());
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90f));
            matrixStack.translate(0f, -2f, 0f);
            float scale = 1.01f;
            matrixStack.scale(0.5f, 0.5f, 0.5f);
            matrixStack.scale(scale, scale, scale);
            IVertexBuilder vertexBuilder = MATERIAL.getBuffer(buffer, RenderType::getEntityCutout);
            int light = WaystonesConfig.CLIENT.disableTextGlow.get() ? combinedLightIn : 15728880;
            int overlay = WaystonesConfig.CLIENT.disableTextGlow.get() ? combinedOverlayIn : OverlayTexture.NO_OVERLAY;
            float[] colors = color.getColorComponentValues();
            model.render(matrixStack, vertexBuilder, light, overlay, colors[0], colors[1], colors[2], 1f);
            matrixStack.pop();
        }

        if (warpStoneItem == null) {
            warpStoneItem = new ItemStack(ModItems.warpStone);
            warpStoneItem.addEnchantment(Enchantments.UNBREAKING, 1);
        }

        float angle = gameTime / 2f % 360;
        float offsetY = (float) Math.sin(gameTime / 8f) * 0.025f;
        matrixStack.push();
        matrixStack.translate(0.5f, 1f + offsetY, 0.5f);
        matrixStack.rotate(new Quaternion(0f, angle, 0f, true));
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        Minecraft.getInstance().getItemRenderer().renderItem(warpStoneItem, ItemCameraTransforms.TransformType.FIXED, combinedLightIn, combinedOverlayIn, matrixStack, buffer);
        matrixStack.pop();
    }
}

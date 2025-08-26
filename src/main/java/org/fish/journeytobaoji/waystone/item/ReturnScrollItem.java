package org.fish.journeytobaoji.waystone.item;

import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.api.IWaystone;
import net.blay09.mods.waystones.core.PlayerWaystoneManager;
import net.blay09.mods.waystones.core.WarpMode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class ReturnScrollItem extends BoundScrollItem {

    public static final String name = "return_scroll";
    public static final ResourceLocation registryName = new ResourceLocation(Waystones.MOD_ID, name);

    @Nullable
    @Override
    protected IWaystone getBoundTo(PlayerEntity player, ItemStack itemStack) {
        return PlayerWaystoneManager.getNearestWaystone(player);
    }

    @Override
    protected WarpMode getWarpMode() {
        return WarpMode.RETURN_SCROLL;
    }
}

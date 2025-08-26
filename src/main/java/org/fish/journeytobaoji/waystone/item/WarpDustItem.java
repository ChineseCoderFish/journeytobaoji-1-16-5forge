package org.fish.journeytobaoji.waystone.item;

import net.blay09.mods.waystones.Waystones;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class WarpDustItem extends Item {

    public static final String name = "warp_dust";
    public static final ResourceLocation registryName = new ResourceLocation(Waystones.MOD_ID, name);

    public WarpDustItem() {
        super(new Properties().group(Waystones.itemGroup));
    }

    @Override
    public boolean hasEffect(ItemStack itemStack) {
        return false;
    }

}

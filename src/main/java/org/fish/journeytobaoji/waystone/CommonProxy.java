package org.fish.journeytobaoji.waystone;

import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class CommonProxy {

    public void playSound(SoundEvent soundEvent, BlockPos pos, float pitch) {
    }

    public boolean isVivecraftInstalled() {
        return false;
    }

    public void setWaystoneCooldowns(long inventoryButtonCooldownUntil, long warpStoneCooldownUntil) {
    }
}

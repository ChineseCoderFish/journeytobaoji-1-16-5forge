package org.fish.journeytobaoji.waystone.core;

import net.blay09.mods.waystones.api.IWaystone;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public interface IPlayerWaystoneData {
    void activateWaystone(PlayerEntity player, IWaystone waystone);
    boolean isWaystoneActivated(PlayerEntity player, IWaystone waystone);
    void deactivateWaystone(PlayerEntity player, IWaystone waystone);
    long getWarpStoneCooldownUntil(PlayerEntity player);
    void setWarpStoneCooldownUntil(PlayerEntity player, long timeStamp);
    long getInventoryButtonCooldownUntil(PlayerEntity player);
    void setInventoryButtonCooldownUntil(PlayerEntity player, long timeStamp);
    List<IWaystone> getWaystones(PlayerEntity player);
    void swapWaystoneSorting(PlayerEntity player, int index, int otherIndex);
}

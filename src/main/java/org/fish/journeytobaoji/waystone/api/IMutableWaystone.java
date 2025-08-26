package org.fish.journeytobaoji.waystone.api;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.UUID;

public interface IMutableWaystone {
    void setName(String name);

    void setGlobal(boolean global);

    void setDimension(RegistryKey<World> dimension);

    void setPos(BlockPos pos);

    void setOwnerUid(UUID ownerUid);
}

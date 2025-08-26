package org.fish.journeytobaoji.waystone.handler;

import net.blay09.mods.waystones.ModStats;
import net.blay09.mods.waystones.Waystones;
import net.blay09.mods.waystones.api.WaystoneActivatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Waystones.MOD_ID)
public class WaystoneActivationStatHandler {

    @SubscribeEvent
    public static void onWaystoneActivated(WaystoneActivatedEvent event) {
        event.getPlayer().addStat(ModStats.waystoneActivated);
    }

}

package moe.kawaaii.SuicidePotion;

import net.fabricmc.api.DedicatedServerModInitializer;

public class SuicidePotionServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        SuicidePotion.registerItems();
        SuicidePotion.createDataPack();
    }
}

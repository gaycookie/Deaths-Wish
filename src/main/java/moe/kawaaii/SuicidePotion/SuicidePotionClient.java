package moe.kawaaii.SuicidePotion;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class SuicidePotionClient implements ClientModInitializer {
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        SuicidePotion.registerItems();
        SuicidePotion.createAssetPack();
        SuicidePotion.createDataPack();
    }
}

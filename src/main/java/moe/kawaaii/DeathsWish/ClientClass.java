package moe.kawaaii.DeathsWish;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public class ClientClass implements ClientModInitializer {
    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient() {
        MainClass.createAssetPack();
    }
}

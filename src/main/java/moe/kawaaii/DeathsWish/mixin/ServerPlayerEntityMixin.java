package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.Interfaces.IPlayerEntity;
import moe.kawaaii.DeathsWish.MainClass;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    /**
     * Figured out via chronosacaria's code that I injected the code at the wrong spot.
     * Thanks to them I was able to figure out why the armor did not copy over to the respawning player.
     *
     * https://www.curseforge.com/minecraft/mc-mods/save-gear-on-death
     * Source: https://github.com/chronosacaria/SaveGearOnDeath/blob/main/src/main/java/chronosacaria/sgod/mixin/ServerPlayerEntityMixin.java
     */

    @Inject(at = @At(value = "HEAD"), method = "copyFrom", cancellable = true)
    public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        if (MainClass.CONFIG.getOrDefault("keep_inventory_enabled", true)) {
            if (!alive) {
                if (!((ServerPlayerEntity) (Object) this).world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
                    if (((IPlayerEntity) oldPlayer).getKeepInventory()) {
                        ((ServerPlayerEntity) (Object) this).inventory.clone(oldPlayer.inventory);
                        ((IPlayerEntity) oldPlayer).setKeepInventory(false);
                    }
                }
            }
        }
    }
}
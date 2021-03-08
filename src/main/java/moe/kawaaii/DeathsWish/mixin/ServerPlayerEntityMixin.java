package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.Interfaces.IPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "copyFrom", cancellable = true)
    public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
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
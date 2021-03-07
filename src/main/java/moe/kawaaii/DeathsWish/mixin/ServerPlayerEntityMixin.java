package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.keepInventoryInterface;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements keepInventoryInterface {

    @Inject(at = @At(value = "TAIL"), method = "copyFrom", cancellable = true)
    public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        if (!((ServerPlayerEntity) (Object) this).world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) {
            if (((keepInventoryInterface) oldPlayer).getKeepInventory()) {
                ((ServerPlayerEntity) (Object) this).inventory.clone(oldPlayer.inventory);
                setKeepInventory(false);
            }
        }
    }

    private boolean keepInventory;

    @Override
    public void setKeepInventory(boolean value) {
        this.keepInventory = value;
    }

    @Override
    public boolean getKeepInventory() {
        return this.keepInventory;
    }
}
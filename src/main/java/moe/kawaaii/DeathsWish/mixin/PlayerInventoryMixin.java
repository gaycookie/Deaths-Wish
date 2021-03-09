package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.Interfaces.IPlayerEntity;
import moe.kawaaii.DeathsWish.MainClass;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Inject(at = @At(value = "HEAD"), method = "dropAll", cancellable = true)
    public void keepInventoryInjection(CallbackInfo ci) {
        if (MainClass.CONFIG.getOrDefault("keep_inventory_enabled", true)) {
            if (((IPlayerEntity) (((PlayerInventory) (Object) this).player)).getKeepInventory()) {
                ci.cancel();
            }
        }
    }
}

/*@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(at = @At(value = "HEAD"), method = "dropInventory", cancellable = true)
    public void keepInventoryInjection(CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);

        if (((keepInventoryInterface) player).getKeepInventory()) {
            ci.cancel();
        }
    }
}*/

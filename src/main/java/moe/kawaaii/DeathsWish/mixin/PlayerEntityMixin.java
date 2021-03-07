package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.keepInventoryInterface;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Inject(at = @At(value = "HEAD"), method = "dropInventory", cancellable = true)
    public void keepInventoryInjection(CallbackInfo ci) {
        PlayerEntity player = ((PlayerEntity) (Object) this);

        if (((keepInventoryInterface) player).getKeepInventory()) {
            ci.cancel();
        }
    }
}

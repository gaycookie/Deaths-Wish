package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.MainClass;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(at = @At(value = "HEAD"), method = "tryUseTotem", cancellable = true)
    private void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {


        if (!source.isOutOfWorld()) {
            LivingEntity livingEntity = ((LivingEntity) (Object) this);
            if (livingEntity.getType() == EntityType.PLAYER) {

                PlayerEntity playerEntity = (PlayerEntity) livingEntity;
                ItemStack blockStack = null;
                PlayerInventory inventory = playerEntity.inventory;

                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack item = inventory.getStack(i);
                    if (item.getItem() == MainClass.BLOCK_OF_UNDYING) {
                        blockStack = item.copy();
                        if (item.getDamage() <= 70) item.setDamage(item.getDamage() + 10);
                        else item.decrement(1);
                        break;
                    }
                }

                if (blockStack != null) {
                    playerEntity.setHealth(1.0F);
                    playerEntity.clearStatusEffects();
                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
                    playerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
                    playerEntity.world.sendEntityStatus(playerEntity, (byte)35);
                }

                cir.setReturnValue(blockStack != null);
            }
        }


    }
}

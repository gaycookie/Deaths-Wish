package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.MainClass;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(at = @At(value = "TAIL"), method = "onDeath", cancellable = true)
    public void soulAbsorbInjection(DamageSource source, CallbackInfo ci) {

        LivingEntity livingEntity = ((LivingEntity) (Object) this);
        Entity attackerEntity = source.getAttacker();

        if ((attackerEntity instanceof PlayerEntity) || (attackerEntity instanceof ServerPlayerEntity)) {

            /* We know its a Player or ServerPlayer, so it's safe to continue? */
            PlayerEntity playerEntity = (PlayerEntity) attackerEntity;

            /* Get main hand item and checks if that item has SOUL_ABSORB enchantment */
            ItemStack weapon = playerEntity.getMainHandStack();
            if (EnchantmentHelper.get(weapon).get(MainClass.SOUL_ABSORB) != null) {

                /* If the entity is not a mob or a player, we simply ignore it */
                if (!livingEntity.isMobOrPlayer()) return;

                /* If the entity is removed instead of killed, we ignore it */
                if (livingEntity.removed) return;

                /* If the entity is a passive entity, we ignore it */
                if (livingEntity instanceof PassiveEntity) return;

                /* Generated a random number between 1 and 100 and if its (default 50) or lower it continues */
                if (!((new Random().nextInt(100) + 1) <= MainClass.CONFIG.getOrDefault("soul_absorber_chance", 5))) return;

                /* Gets the first 'Block of Undying' that has damage, if it found one it will restore 1 charge to it */
                PlayerInventory inventory = playerEntity.inventory;
                for (int i = 0; i < inventory.size(); i++) {
                    ItemStack item = inventory.getStack(i);
                    if (item.getItem() == MainClass.BLOCK_OF_UNDYING) {
                        if (item.getDamage() > 0) {
                            item.setDamage(item.getDamage() - 10);

                            if (!playerEntity.world.isClient) {
                                ((ServerWorld) playerEntity.world).spawnParticles(ParticleTypes.HEART, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), 25, 0.5, 1, 0.5, 0.1);
                            }

                            break;
                        }
                    }
                }
            }
        }

    }

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

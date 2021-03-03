package moe.kawaaii.SuicidePotion.Items;

import moe.kawaaii.SuicidePotion.DamageSources.SuicideDamage;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

class TotemMaterial implements ToolMaterial {
    @Override
    public int getDurability() {
        return 33;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 0;
    }

    @Override
    public float getAttackDamage() {
        return 0;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 1;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}

public class TotemOfDying extends ToolItem {

    public TotemOfDying(Settings settings) {
        super(new TotemMaterial(), settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient()) ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, user.getX(), user.getY(), user.getZ(), 25, 0, 0, 0, 0.1);
        stack.damage(1, user, dmg -> dmg.sendToolBreakStatus(user.getActiveHand()));
        super.usageTick(world, user, stack, remainingUseTicks);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        stack.setDamage(0);
        super.onStoppedUsing(stack, world, user, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {

        if (!world.isClient()) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.EXPLOSION, user.getX(), user.getY(), user.getZ(), 25, 0, 0, 0, 0.1);
            if (!world.isClient) world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 1.0f, 1f);
        }

        if (!((PlayerEntity) user).isCreative()) {
            stack.decrement(1);
            user.damage(new SuicideDamage(), Float.MAX_VALUE);
        }

        return super.finishUsing(stack, world, user);
    }
}

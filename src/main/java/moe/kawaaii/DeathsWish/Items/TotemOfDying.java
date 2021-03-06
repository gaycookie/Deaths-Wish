package moe.kawaaii.DeathsWish.Items;

import moe.kawaaii.DeathsWish.MainClass;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class TotemOfDying extends DemiseItem {
    public TotemOfDying(String path, int maxCount, ItemGroup group) {
        super(path, maxCount, group);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {

        /*ItemStack blockOfUndying = null;
        PlayerInventory playerInventory = ((PlayerEntity) user).inventory;
        int items = playerInventory.getSlotWithStack(new ItemStack(MainClass.BLOCK_OF_UNDYING));
        if (items >= 0) {
            ItemStack item = playerInventory.getStack(items);
            blockOfUndying = item.copy();
            item.setDamage(item.getDamage() + 10);
        }*/

        if (!world.isClient()) ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, user.getX(), user.getY(), user.getZ(), 25, 0, 0, 0, 0.1);
        stack.damage(1, user, dmg -> dmg.sendToolBreakStatus(user.getActiveHand()));
        super.usageTick(world, user, stack, remainingUseTicks);
    }
}
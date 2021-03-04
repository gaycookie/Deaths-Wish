package moe.kawaaii.DeathsWish.Items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class TotemOfDying extends DemiseItem {
    public TotemOfDying(String path, int maxCount, ItemGroup group) {
        super(path, maxCount, group);
    }

    @Override
    public void usageTick(World world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        if (!world.isClient()) ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, user.getX(), user.getY(), user.getZ(), 25, 0, 0, 0, 0.1);
        stack.damage(1, user, dmg -> dmg.sendToolBreakStatus(user.getActiveHand()));
        super.usageTick(world, user, stack, remainingUseTicks);
    }
}
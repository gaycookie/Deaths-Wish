package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.Interfaces.IPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements IPlayerEntity {
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

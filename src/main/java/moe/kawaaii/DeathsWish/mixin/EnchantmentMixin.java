package moe.kawaaii.DeathsWish.mixin;

import moe.kawaaii.DeathsWish.MainClass;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        Enchantment enchantment = ((Enchantment) (Object) this);
        Item item = stack.getItem();

        if (enchantment == MainClass.KEEP_INVENTORY) {
            if (item == MainClass.TOTEM_OF_DYING) {
                cir.setReturnValue(true);
            }
        }

    }

}

package moe.kawaaii.DeathsWish.DamageSources;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class SuicideDamage extends DamageSource {
    public SuicideDamage() {
        super("suicide");
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        // Text.of(String.format(SuicidePotion.DEATH_MESSAGE, entity.getName().asString()))
        return new TranslatableText("death.announcement", entity.getName().asString());
    }
}

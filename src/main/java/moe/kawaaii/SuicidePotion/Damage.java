package moe.kawaaii.SuicidePotion;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class Damage extends DamageSource {
    protected Damage() {
        super("suicide");
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        // Text.of(String.format(SuicidePotion.DEATH_MESSAGE, entity.getName().asString()))
        return new TranslatableText("death.announcement", entity.getName().asString());
    }
}

package moe.kawaaii.DeathsWish.Items;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockOfUndying extends ToolItem {
    private String PATH;

    public BlockOfUndying(String path, int maxCount, ItemGroup group) {
        super(new ToolMaterial() {
            @Override
            public int getDurability() {
                return 90;
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
                return 0;
            }

            @Override
            public Ingredient getRepairIngredient() {
                return null;
            }
        }, new Item.Settings().group(group).maxCount(maxCount));

        this.PATH = path;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        tooltip.add(new TranslatableText(String.format("item.deaths_wish.%s.tooltip", this.PATH)));
        if (Screen.hasShiftDown() || Screen.hasControlDown()) {
            tooltip.add(new TranslatableText(String.format("item.deaths_wish.%s.tooltip2", this.PATH), ((stack.getMaxDamage() - stack.getDamage()) / 10) + " / 9"));
        }
    }
}

package moe.kawaaii.SuicidePotion;

import com.swordglowsblue.artifice.api.Artifice;
import moe.kawaaii.SuicidePotion.Items.TotemOfDying;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SuicidePotion implements ModInitializer {
	public static final String MODID = "suicide_potion";
	public static Item SUICIDE_POTION;
	public static ToolItem TOTEM_OF_DYING;
	//public static Enchantment KEEP_INVENTORY;
	public static SimpleConfig CONFIG;

	@Override
	public void onInitialize() {
		CONFIG = SimpleConfig.of("suicide_potion").provider(this::provider).request();
		SUICIDE_POTION = new moe.kawaaii.SuicidePotion.Items.SuicidePotion(new Item.Settings().maxCount(CONFIG.getOrDefault("stack_size", 16)).group(ItemGroup.BREWING));
		TOTEM_OF_DYING = new TotemOfDying(new Item.Settings().maxCount(1).group(ItemGroup.COMBAT));
		//KEEP_INVENTORY = new KeepInventory(Enchantment.Rarity.VERY_RARE, EnchantmentTarget., EquipmentSlot.MAINHAND);
	}

	/**
	 * Config file' default content, thats being used by SimpleConfig.
	 * @param filename config filename
	 * @return default content
	 */
	private String provider(String filename) {
		return "### Suicide Potion Config\n# To change the default config entry, remove the # in front of the entry\n\n#stack_size=16";
	}

	/**
	 * Registers the items to the Registry.
	 */
	public static void registerItems() {
		Registry.register(Registry.ITEM, id("suicide_potion"), SUICIDE_POTION);
		Registry.register(Registry.ITEM, id("totem_of_dying"), TOTEM_OF_DYING);
	}

	/**
	 * Creates and registers the Asset pack associated with this mod.
	 */
	public static void createAssetPack() {
		Artifice.registerAssetPack(id("suicide_potion_asset"), pack -> {
			pack.addItemModel(Registry.ITEM.getId(TOTEM_OF_DYING), model -> {
				model.parent(new Identifier("minecraft:item/generated"));
				model.texture("layer0", id("item/totem_of_dying"));
			});
			pack.addItemModel(Registry.ITEM.getId(SUICIDE_POTION), model -> {
				model.parent(new Identifier("minecraft:item/generated"));
				model.texture("layer0", id("item/potion"));
			});
		});
	}

	/**
	 * Creates and registers the Data pack associated with this mod.
	 */
	public static void createDataPack() {
		Artifice.registerDataPack(new Identifier(MODID, "suicide_potion_data"), pack -> {
			pack.addShapedRecipe(id("suicide_potion_recipe"), processor -> {
				processor.pattern("SSS", "SWS", "SBS");
				processor.ingredientItem('S', new Identifier("minecraft", "fermented_spider_eye"));
				processor.ingredientItem('W', new Identifier("minecraft", "water_bucket"));
				processor.ingredientItem('B', new Identifier("minecraft", "glass_bottle"));
				processor.result(Registry.ITEM.getId(SUICIDE_POTION), 1);
			});
		});
	}

	/**
	 * Creates a simple Identifier with namespace defaulted to MODID.
	 * @param path The path for the identifier.
	 * @return new Identifier
	 */
	public static Identifier id(String path) {
		return new Identifier(MODID, path);
	}
}

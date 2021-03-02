package moe.kawaaii.SuicidePotion;

import com.swordglowsblue.artifice.api.Artifice;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SuicidePotion implements ModInitializer {
	private static final String MODID = "suicide_potion";
	private static Potion SUICIDE_POTION;
	private static SimpleConfig CONFIG;
	public static String DEATH_MESSAGE;

	@Override
	public void onInitialize() {
		CONFIG = SimpleConfig.of("suicide_potion").provider(this::provider).request();
		DEATH_MESSAGE = CONFIG.getOrDefault("death_message", "%s took the easy way out!");

		createItems();
		registerItems();
		createAssetPack();
		createDataPack();
	}

	/**
	 * Config file' default content, thats being used by SimpleConfig.
	 * @param filename config filename
	 * @return default content
	 */
	private String provider(String filename) {
		return "### Suicide Potion Config\n# To change the default config entry, remove the # in front of the entry\n\n#death_message=\"%s took the easy way out!\"";
	}

	/**
	 * Create the items associated with this mod.
	 */
	public void createItems() {
		SUICIDE_POTION = new Potion(new FabricItemSettings().maxCount(16).group(ItemGroup.BREWING));
	}

	/**
	 * Registers the items to the Registry.
	 */
	public void registerItems() {
		Registry.register(Registry.ITEM, id("suicide_potion"), SUICIDE_POTION);
	}

	/**
	 * Creates and registers the Asset pack associated with this mod.
	 */
	public void createAssetPack() {
		Artifice.registerAssetPack(id("suicide_potion_asset"), pack -> {
			pack.addItemModel(Registry.ITEM.getId(SUICIDE_POTION), model -> {
				model.parent(new Identifier("minecraft:item/generated"));
				model.texture("layer0", id("item/potion"));
			});
		});
	}

	/**
	 * Creates and registers the Data pack associated with this mod.
	 */
	public void createDataPack() {
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

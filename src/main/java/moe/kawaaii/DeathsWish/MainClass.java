package moe.kawaaii.DeathsWish;

import com.swordglowsblue.artifice.api.Artifice;
import moe.kawaaii.DeathsWish.Items.PotionOfDemise;
import moe.kawaaii.DeathsWish.Items.ScrollOfDemise;
import moe.kawaaii.DeathsWish.Items.TotemOfDying;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MainClass implements ModInitializer {
	public static final String MODID = "deaths_wish";
	public static Item POTION_OF_DEMISE;
	public static ToolItem TOTEM_OF_DYING;
	public static ToolItem SCROLL_OF_DEMISE;
	public static SimpleConfig CONFIG;

	@Override
	public void onInitialize() {
		CONFIG = SimpleConfig.of("suicide_potion").provider(this::provider).request();
		POTION_OF_DEMISE = new PotionOfDemise("potion_of_demise", CONFIG.getOrDefault("stack_size", 16), ItemGroup.BREWING);
		TOTEM_OF_DYING = new TotemOfDying("totem_of_dying", 1, ItemGroup.COMBAT);
		SCROLL_OF_DEMISE = new ScrollOfDemise("scroll_of_demise", 1, ItemGroup.COMBAT);

		registerItems();
		createDataPack();
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
		Registry.register(Registry.ITEM, id("potion_of_demise"), POTION_OF_DEMISE);
		Registry.register(Registry.ITEM, id("totem_of_dying"), TOTEM_OF_DYING);
		Registry.register(Registry.ITEM, id("scroll_of_demise"), SCROLL_OF_DEMISE);
	}

	/**
	 * Creates and registers the Asset pack associated with this mod.
	 */
	public static void createAssetPack() {
		Artifice.registerAssetPack(id("deaths_wish_assetpack"), pack -> {
			pack.addItemModel(Registry.ITEM.getId(POTION_OF_DEMISE), model -> {
				model.parent(new Identifier("minecraft:item/generated"));
				model.texture("layer0", id("item/potion_of_demise"));
			});
			pack.addItemModel(Registry.ITEM.getId(TOTEM_OF_DYING), model -> {
				model.parent(new Identifier("minecraft:item/generated"));
				model.texture("layer0", id("item/totem_of_dying"));
			});
			pack.addItemModel(Registry.ITEM.getId(SCROLL_OF_DEMISE), model -> {
				model.parent(new Identifier("minecraft:item/generated"));
				model.texture("layer0", id("item/scroll_of_demise"));
			});
		});
	}

	/**
	 * Creates and registers the Data pack associated with this mod.
	 */
	public static void createDataPack() {
		Artifice.registerDataPack(new Identifier(MODID, "deaths_wish_datapack"), pack -> {
			pack.addShapedRecipe(id("potion_of_demise_recipe"), processor -> {
				processor.pattern("SSS", "SWS", "SBS");
				processor.ingredientItem('S', new Identifier("minecraft", "fermented_spider_eye"));
				processor.ingredientItem('W', new Identifier("minecraft", "water_bucket"));
				processor.ingredientItem('B', new Identifier("minecraft", "glass_bottle"));
				processor.result(Registry.ITEM.getId(POTION_OF_DEMISE), 1);
			});
			pack.addShapedRecipe(id("totem_of_dying_recipe"), processor -> {
				processor.pattern("SSS", "STS", "SSS");
				processor.ingredientItem('S', new Identifier("minecraft", "fermented_spider_eye"));
				processor.ingredientItem('T', new Identifier("minecraft", "totem_of_undying"));
				processor.result(Registry.ITEM.getId(TOTEM_OF_DYING), 1);
			});
			pack.addShapedRecipe(id("scroll_of_demise_recipe"), processor -> {
				processor.pattern("ESE", "PPP", "ESE");
				processor.ingredientItem('E', new Identifier("minecraft", "fermented_spider_eye"));
				processor.ingredientItem('P', new Identifier("minecraft", "paper"));
				processor.ingredientItem('S', new Identifier("minecraft", "stick"));
				processor.result(Registry.ITEM.getId(SCROLL_OF_DEMISE), 1);
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

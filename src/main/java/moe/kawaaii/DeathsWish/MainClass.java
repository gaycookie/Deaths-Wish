package moe.kawaaii.DeathsWish;

import com.swordglowsblue.artifice.api.Artifice;
import moe.kawaaii.DeathsWish.DamageSources.SuicideDamage;
import moe.kawaaii.DeathsWish.Enchantments.KeepInventory;
import moe.kawaaii.DeathsWish.Enchantments.SoulAbsorb;
import moe.kawaaii.DeathsWish.Items.BlockOfUndying;
import moe.kawaaii.DeathsWish.Items.PotionOfDemise;
import moe.kawaaii.DeathsWish.Items.ScrollOfDemise;
import moe.kawaaii.DeathsWish.Items.TotemOfDying;
import net.fabricmc.api.ModInitializer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MainClass implements ModInitializer {
	public static final String MODID = "deaths_wish";
	public static SimpleConfig CONFIG;

	/* Items */
	public static Item POTION_OF_DEMISE;
	public static ToolItem TOTEM_OF_DYING;
	public static ToolItem SCROLL_OF_DEMISE;
	public static ToolItem BLOCK_OF_UNDYING;

	/* Enchantments */
	public static Enchantment KEEP_INVENTORY;
	public static Enchantment SOUL_ABSORB;

	/* Damage Source */
	public static DamageSource DAMAGE_SOURCE;

	@Override
	public void onInitialize() {
		CONFIG = SimpleConfig.of("deaths_wish").provider(this::provider).request();
		POTION_OF_DEMISE = new PotionOfDemise("potion_of_demise", CONFIG.getOrDefault("potion_of_demise_stack_size", 16), ItemGroup.BREWING);
		TOTEM_OF_DYING = new TotemOfDying("totem_of_dying", 1, ItemGroup.COMBAT);
		SCROLL_OF_DEMISE = new ScrollOfDemise("scroll_of_demise", 1, ItemGroup.COMBAT);
		BLOCK_OF_UNDYING = new BlockOfUndying("block_of_undying", 1, ItemGroup.COMBAT);

		KEEP_INVENTORY = new KeepInventory(Enchantment.Rarity.RARE, EnchantmentTarget.BREAKABLE, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
		SOUL_ABSORB = new SoulAbsorb(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});

		DAMAGE_SOURCE = new SuicideDamage();

		registerItems();
		createDataPack();
	}

	/**
	 * Config file' default content, thats being used by SimpleConfig.
	 * @param filename config filename
	 * @return default content
	 */
	private String provider(String filename) {

		/**
		 * ### Death's Wish Config (1.3.3)
		 * # To change the default value, remove the '#' in front of the entry you wanna change.
		 *
		 * ## Max stack size of the Potion of Demise
		 * potion_of_demise_stack_size=16
		 *
		 * ## How often the Soul Absorber would get triggered in percentages.
		 * soul_absorber_chance=5
		 */

		return "### Death's Wish Config (1.3.3)\n" +
		"# To change the default value, remove the '#' in front of the entry you wanna change.\n\n" +
		"## Max stack size of the Potion of Demise.\n" +
		"#potion_of_demise_stack_size=16\n\n" +
		"## How often the Soul Absorber would get triggered in percentages.\n" +
		"#soul_absorber_chance=5";
	}

	/**
	 * Registers the items to the Registry.
	 */
	public static void registerItems() {
		Registry.register(Registry.ITEM, id("potion_of_demise"), POTION_OF_DEMISE);
		Registry.register(Registry.ITEM, id("totem_of_dying"), TOTEM_OF_DYING);
		Registry.register(Registry.ITEM, id("scroll_of_demise"), SCROLL_OF_DEMISE);
		Registry.register(Registry.ITEM, id("block_of_undying"), BLOCK_OF_UNDYING);

		Registry.register(Registry.ENCHANTMENT, id("keep_inventory"), KEEP_INVENTORY);
		Registry.register(Registry.ENCHANTMENT, id("soul_absorb"), SOUL_ABSORB);
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
			pack.addItemModel(Registry.ITEM.getId(BLOCK_OF_UNDYING), model -> {
				model.parent(new Identifier("minecraft:block/cube_all"));
				model.texture("all", id("item/block_of_undying"));
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
			pack.addShapedRecipe(id("block_of_undying_recipe"), processor -> {
				processor.pattern("TTT", "TTT", "TTT");
				processor.ingredientItem('T', new Identifier("minecraft", "totem_of_undying"));
				processor.result(Registry.ITEM.getId(BLOCK_OF_UNDYING), 1);
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

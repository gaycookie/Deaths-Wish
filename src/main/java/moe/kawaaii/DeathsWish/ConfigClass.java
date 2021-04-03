package moe.kawaaii.DeathsWish;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = MainClass.MODID)
public class ConfigClass implements ConfigData {
    public Enchantments enchantments = new Enchantments();
    public Items items = new Items();

    public static class Enchantments {
        public SoulAbsorb soul_absorb = new SoulAbsorb();
        public SoulBound soul_bound = new SoulBound();
        public KeepInventory keep_inventory = new KeepInventory();

        public static class SoulAbsorb {
            public boolean enabled = true;
            public int chance = 5;
        }

        public static class SoulBound {
            public boolean enabled = true;
        }

        public static class KeepInventory {
            public boolean enabled = true;
        }
    }

    static class Items {
        public PotionOfDemise potion_of_demise = new PotionOfDemise();

        public static class PotionOfDemise {
            public int stack_size = 16;
        }
    }

}
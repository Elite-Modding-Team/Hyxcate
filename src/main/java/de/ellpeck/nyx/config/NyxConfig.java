package de.ellpeck.nyx.config;

import com.google.common.collect.Sets;
import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.event.lunar.NyxEventStarShower;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Configuration;

import java.io.File;
import java.util.Set;

public final class NyxConfig {

    public static Configuration instance;
    public static Set<String> allowedDimensions;
    public static Set<String> allowedDimensionsSolar;
    public static boolean enchantments;
    public static boolean addPotionEffects;
    public static int additionalMobsChance;
    public static double maxLunarEdgeXpMult;
    public static double minLevelLunarEdgeDamage;
    public static double maxLevelLunarEdgeDamage;
    public static double baseLunarEdgeDamage;
    public static boolean fallingStars;
    public static double fallingStarRarity;
    public static double fallingStarRarityShower;
    public static double fallingStarImpactVolume;
    public static double fallingStarAmbientVolume;
    public static boolean fullMoon;
    public static boolean bloodMoonSleeping;
    public static int bloodMoonSpawnMultiplier;
    public static Set<String> mobDuplicationBlacklist;
    public static boolean isMobDuplicationWhitelist;
    public static boolean bloodMoonVanish;
    public static int bloodMoonSpawnRadius;
    public static boolean harvestMoonOnFull;
    public static boolean bloodMoonOnFull;
    public static boolean eventTint;
    public static boolean eventTintUnderground;
    public static int harvestMoonGrowAmount;
    public static int harvestMoonGrowInterval;
    public static LunarEventConfig harvestMoon;
    public static LunarEventConfig starShowers;
    public static LunarEventConfig bloodMoon;
    public static SolarEventConfig redSun;
    public static SolarEventConfig solarEclipse;
    public static double meteorChance;
    public static double meteorChanceNight;
    public static String meteorGateDimension;
    public static double meteorChanceAfterGate;
    public static double meteorChanceAfterGateNight;
    public static double meteorChanceStarShower;
    public static double meteorChanceEnd;
    public static int meteorSpawnRadius;
    public static boolean meteors;
    public static boolean meteorMessage;
    public static boolean meteorMessageVerbose;
    public static int meteorDisallowRadius;
    public static int meteorDisallowTime;
    public static boolean eventNotifications;
    public static int celestialWarhammerAbilityDamage;

    public static void init(File file) {
        instance = new Configuration(file);
        instance.load();
        load();
    }

    public static void load() {
        allowedDimensions = Sets.newHashSet(instance.get("general", "allowedDimensions", new String[]{"overworld"}, "Names of the dimensions that lunar events should occur in").getStringList());
        allowedDimensionsSolar = Sets.newHashSet(instance.get("general", "allowedDimensionsSolar", new String[]{"overworld"}, "Names of the dimensions that solar events should occur in").getStringList());
        mobDuplicationBlacklist = Sets.newHashSet(instance.get("general", "mobDuplicationBlacklist", new String[0], "The registry names of entities that should not be spawned during the full and blood moons. If isMobDuplicationWhitelist is true, this acts as a whitelist instead.").getStringList());
        isMobDuplicationWhitelist = instance.get("general", "isMobDuplicationWhitelist", false, "If the mobDuplicationBlacklist should act as a whitelist instead").getBoolean();
        eventTint = instance.get("general", "eventTint", true, "If moon and sun events should tint the sky").getBoolean();
        eventTintUnderground = instance.get("general", "eventTintUnderground", true, "If moon and sun events should also tint underground areas (can look jarring when disabled)").getBoolean();
        eventNotifications = instance.get("general", "eventNotifications", true, "If moon and sun events should be announced in chat when they start").getBoolean();

        fullMoon = instance.get("fullMoon", "fullMoon", true, "If the vanilla full moon should be considered a proper lunar event").getBoolean();
        addPotionEffects = instance.get("fullMoon", "addPotionEffects", true, "If mobs spawned during certain events should have random potion effects applied to them (similarly to spiders in the base game)").getBoolean();
        additionalMobsChance = instance.get("fullMoon", "additionalMobsChance", 5, "The chance for an additional mob to be spawned when a mob spawns during a full moon. The higher the number, the less likely. Set to 0 to disable.", 0, 1000).getInt();

        enchantments = instance.get("enchantments", "enchantments", true, "If the enchantments should be enabled").getBoolean();
        maxLunarEdgeXpMult = instance.get("enchantments", "maxLunarEdgeXpMult", 1.0D,
                "The max multiplier on the amount of xp added (which happens during a full moon)\n" +
                        "Can be set to 0 to disable lunar edge xp gains\n" +
                        "The multiplier scales up to the max according to the level and moon phase\n" +
                        "Ex: if the config option is set to 2.5, a full moon with max lunar edge level would give\n" +
                        "3.5x xp and a new moon would give 1x xp").getDouble();
        minLevelLunarEdgeDamage = instance.get("enchantments", "minLevelLunarEdgeDamage", 1.0D, "The amount of additional damage that should be applied to an item with level 1 lunar edge on a full moon.").getDouble();
        maxLevelLunarEdgeDamage = instance.get("enchantments", "maxLevelLunarEdgeDamage", 3.0D, "The amount of additional damage that should be applied to an item with max level lunar edge on a full moon.").getDouble();
        baseLunarEdgeDamage = instance.get("enchantments", "baseLunarEdgeDamage", 0, "The amount of additional damage that will always be applied regardless of moon phase.").getDouble();

        harvestMoon = new LunarEventConfig("harvestMoon", "harvestMoon", "Harvest Moon", 0.05D);
        harvestMoonOnFull = instance.get("harvestMoon", "harvestMoonOnFull", true, "If the harvest moon should only occur on full moon nights").getBoolean();
        harvestMoonGrowAmount = instance.get("harvestMoon", "harvestMoonGrowAmount", 15, "The amount of plants that should be grown per chunk during the harvest moon", 0, 100).getInt();
        harvestMoonGrowInterval = instance.get("harvestMoon", "harvestMoonGrowInterval", 10, "The amount of ticks that should pass before plants are grown again during the harvest moon", 1, 100).getInt();

        starShowers = new LunarEventConfig("fallingStars", "starShowers", "Star Showers", 0.05D);
        fallingStars = instance.get("fallingStars", "fallingStars", true, "If stars falling from the sky should be enabled").getBoolean();
        fallingStarRarity = instance.get("fallingStars", "fallingStarRarity", 0.01F, "The chance in percent (1 = 100%) for a falling star to appear at night for each player each second", 0, 1).getDouble();
        fallingStarRarityShower = instance.get("fallingStars", "fallingStarRarityShower", 0.15F, "The chance for a falling star to appear during a star shower for each player per second", 0, 1).getDouble();
        fallingStarImpactVolume = instance.get("fallingStars", "fallingStarImpactVolume", 10F, "The volume for the falling star impact sound").getDouble();
        fallingStarAmbientVolume = instance.get("fallingStars", "fallingStarAmbientVolume", 10F, "The volume for the falling star ambient sound").getDouble();

        bloodMoon = new LunarEventConfig("bloodMoon", "bloodMoon", "Blood Moon", 0.05D);
        bloodMoonSleeping = instance.get("bloodMoon", "bloodMoonSleeping", false, "If sleeping is allowed during a blood moon").getBoolean();
        bloodMoonSpawnMultiplier = instance.get("bloodMoon", "bloodMoonSpawnMultiplier", 2, "The multiplier with which mobs should spawn during the blood moon (eg 2 means 2 mobs spawn instead of 1)", 1, 1000).getInt();
        bloodMoonVanish = instance.get("bloodMoon", "bloodMoonVanish", true, "If mobs spawned by the blood moon should die at sunup").getBoolean();
        bloodMoonSpawnRadius = instance.get("bloodMoon", "bloodMoonSpawnRadius", 20, "The closest distance that mobs can spawn away from a player during the blood moon. Vanilla value is 24.").getInt();
        bloodMoonOnFull = instance.get("bloodMoon", "bloodMoonOnFull", true, "If the blood moon should only occur on full moon nights").getBoolean();

        redSun = new SolarEventConfig("redSun", "redSun", "Red Sun", 0.05D);

        solarEclipse = new SolarEventConfig("solarEclipse", "solarEclipse", "Solar Eclipse", 0.05D);

        meteors = instance.get("meteors", "meteors", true, "If meteors falling from the sky should be enabled").getBoolean();
        meteorMessage = instance.get("meteors", "meteorMessage", true, "If fallen meteors should be announced in chat on impact").getBoolean();
        meteorMessageVerbose = instance.get("meteors", "meteorMessageVerbose", false, "If chat messages for meteor impacts should include coordinates").getBoolean();
        meteorChance = instance.get("meteors", "meteorChance", 0.00007, "The chance of a meteor spawning every second, during the day").getDouble();
        meteorChanceNight = instance.get("meteors", "meteorChanceNight", 0.0012, "The chance of a meteor spawning every second, during nighttime").getDouble();
        meteorGateDimension = instance.get("meteors", "meteorGateDimension", "the_end", "The dimension that needs to be entered to increase the spawning of meteors").getString();
        meteorChanceAfterGate = instance.get("meteors", "meteorChanceAfterGate", 0.0001, "The chance of a meteor spawning every second, during the day, after the gate dimension has been entered once").getDouble();
        meteorChanceAfterGateNight = instance.get("meteors", "meteorChanceAfterGateNight", 0.0015, "The chance of a meteor spawning every second, during the day, after the gate dimension has been entered once").getDouble();
        meteorChanceStarShower = instance.get("meteors", "meteorChanceStarShower", 0.0024, "The chance of a meteor spawning every second, during a star shower").getDouble();
        meteorChanceEnd = instance.get("meteors", "meteorChanceEnd", 0.0015, "The chance of a meteor spawning every second, in the end dimension").getDouble();
        meteorSpawnRadius = instance.get("meteors", "meteorSpawnRadius", 1000, "The amount of blocks a meteor can spawn away from the nearest player").getInt();
        meteorDisallowRadius = instance.get("meteors", "meteorDisallowRadius", 14, "The radius in chunks that should be marked as invalid for meteor spawning around each player").getInt();
        meteorDisallowTime = instance.get("meteors", "meteorDisallowTime", 12000, "The amount of ticks that need to pass for each player until the chance of a meteor spawning in the area is halved (and then halved again, and so on)").getInt();
        celestialWarhammerAbilityDamage = instance.get("meteors", "celestialWarhammerAbilityDamage", 32, "The amount of damage that the celestial warhammer deals if the maximum flight time was used").getInt();

        if (instance.hasChanged())
            instance.save();
    }

    public static double getMeteorChance(World world, NyxWorld data) {
        DimensionType dim = world.provider.getDimensionType();
        if (dim == DimensionType.THE_END)
            return meteorChanceEnd;

        if (!NyxConfig.allowedDimensions.contains(dim.getName()))
            return 0;
        boolean visitedGate = data.visitedDimensions.contains(meteorGateDimension);
        if (!NyxWorld.isDaytime(world)) {
            if (data.currentLunarEvent instanceof NyxEventStarShower) {
                return meteorChanceStarShower;
            } else {
                return visitedGate ? meteorChanceAfterGateNight : meteorChanceNight;
            }
        }
        return visitedGate ? meteorChanceAfterGate : meteorChance;
    }

    public static class LunarEventConfig {
        public boolean enabled;
        public double chance;
        public int startNight;
        public int nightInterval;
        public int graceDays;

        public LunarEventConfig(String category, String name, String displayName, double defaultChance) {
            this.enabled = instance.get(category, name, true, "If the " + displayName + " should be enabled").getBoolean();
            this.chance = instance.get(category, name + "Chance", defaultChance, "The chance in percent (1 = 100%) of the " + displayName + " occurring", 0, 1).getDouble();
            this.startNight = instance.get(category, name + "StartNight", 0, "The amount of nights that should pass before the " + displayName + " occurs for the first time", 0, 1000).getInt();
            this.nightInterval = instance.get(category, name + "Interval", 0, "The interval in days at which the " + displayName + " should occur. Overrides chance setting if set to a value greater than 0.", 0, 1000).getInt();
            this.graceDays = instance.get(category, name + "GracePeriod", 0, "The amount of days that should pass until the " + displayName + " happens again", 0, 1000).getInt();
        }
    }

    public static class SolarEventConfig {
        public boolean enabled;
        public double chance;
        public int startDay;
        public int dayInterval;
        public int graceDays;

        public SolarEventConfig(String category, String name, String displayName, double defaultChance) {
            this.enabled = instance.get(category, name, true, "If the " + displayName + " should be enabled").getBoolean();
            this.chance = instance.get(category, name + "Chance", defaultChance, "The chance in percent (1 = 100%) of the " + displayName + " occurring", 0, 1).getDouble();
            this.startDay = instance.get(category, name + "StartDay", 0, "The amount of days that should pass before the " + displayName + " occurs for the first time", 0, 1000).getInt();
            this.dayInterval = instance.get(category, name + "Interval", 0, "The interval in days at which the " + displayName + " should occur. Overrides chance setting if set to a value greater than 0.", 0, 1000).getInt();
            this.graceDays = instance.get(category, name + "GracePeriod", 0, "The amount of days that should pass until the " + displayName + " happens again", 0, 1000).getInt();
        }
    }
}

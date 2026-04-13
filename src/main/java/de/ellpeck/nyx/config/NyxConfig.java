package de.ellpeck.nyx.config;

import de.ellpeck.nyx.Nyx;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Nyx.ID, name = Nyx.NAME)
public class NyxConfig {
    @Config.LangKey("config.nyx.master_switches")
    @Config.Comment("Master switches for entire modules")
    public static final MasterSwitches MASTER_SWITCHES = new MasterSwitches();

    @Config.LangKey("config.nyx.general")
    @Config.Comment("Settings for general mechanics")
    public static final General GENERAL = new General();

    @Config.LangKey("config.nyx.entities")
    @Config.Comment("Settings for entities")
    public static final Entities ENTITIES = new Entities();

    @Config.LangKey("config.nyx.events_lunar")
    @Config.Comment("Settings for lunar events")
    public static final EventsLunar EVENTS_LUNAR = new EventsLunar();

    @Config.LangKey("config.nyx.events_solar")
    @Config.Comment("Settings for solar events")
    public static final EventsSolar EVENTS_SOLAR = new EventsSolar();

    @Config.LangKey("config.nyx.falling_stars")
    @Config.Comment("Settings for falling stars")
    public static final FallingStars FALLING_STARS = new FallingStars();

    @Config.LangKey("config.nyx.meteors")
    @Config.Comment("Settings for meteors")
    public static final Meteors METEORS = new Meteors();

    @Config.LangKey("config.nyx.mod_integration")
    @Config.Comment("Settings for mod integration")
    public static final ModIntegration MOD_INTEGRATION = new ModIntegration();

    public static class MasterSwitches {
        @Config.Name("Events: Lunar")
        @Config.Comment("If lunar events should be enabled")
        public boolean lunarEventsEnabled = true;

        @Config.Name("Events: Solar")
        @Config.Comment("If solar events should be enabled")
        public boolean solarEventsEnabled = true;

        @Config.Name("Events: Meteors")
        @Config.Comment("If meteors falling from the sky should be enabled")
        public boolean meteorEventsEnabled = true;

        @Config.Name("Events: Falling Stars")
        @Config.Comment("If falling stars (including during star showers) should be enabled")
        public boolean fallingStarEventsEnabled = true;

        @Config.Name("Enchantments")
        @Config.Comment("If enchantments should be enabled")
        public boolean enchantmentsEnabled = true;

        @Config.Name("Equipment: Beam Swords")
        @Config.Comment("If beam swords should be enabled")
        public boolean beamSwordsEnabled = true;

        @Config.Name("Equipment: Celestial Warhammer")
        @Config.Comment("If the Celestial Warhammer should be enabled")
        public boolean celestialWarhammerEnabled = true;

        @Config.Name("Equipment: Meteor Detector")
        @Config.Comment("If the Meteor Detector should be enabled")
        public boolean meteorDetectorEnabled = true;

        @Config.Name("Equipment: Meteor Gear")
        @Config.Comment("If all tools and armor made out of meteor materials (Meteorite, Frezarite, Kreknorite, and Tektite) should be enabled")
        public boolean meteorGearEnabled = true;
    }

    public static class General {
        @Config.Name("Celestial Warhammer Ability Damage")
        @Config.Comment("The amount of damage that the celestial warhammer deals if the maximum flight time was used")
        public int celestialWarhammerAbilityDamage = 32;

        @Config.Name("Event Tint")
        @Config.Comment("If celestial events should tint the sky")
        public boolean eventTint = true;

        @Config.Name("Event Tint Underground")
        @Config.Comment("If celestial events should also tint underground areas (can look slightly jarring when disabled)")
        public boolean eventTintUnderground = true;

        @Config.Name("Event Tint Lightmap transition duration")
        @Config.Comment({
                "Controls the lightmap colors transitions and the sun brightness transitions",
                "Duration specified in ticks. Use -1 to disable"
        })
        @Config.RangeInt(min = -1)
        @Config.RequiresMcRestart
        public int eventTintLightmapDuration = 20 * 12;

        @Config.Name("Event Tint Sky Color transition duration")
        @Config.Comment({
                "Controls the sky, clouds and fog transitions",
                "Duration specified in ticks. Use -1 to disable"
        })
        @Config.RangeInt(min = -1)
        @Config.RequiresMcRestart
        public int eventTintSkyColorDuration = 20 * 15;

        @Config.Name("Event Notifications")
        @Config.Comment("If celestial events should be announced in chat when they start")
        public boolean eventNotifications = true;

        @Config.Name("Event Intro Sounds")
        @Config.Comment("If celestial events should play a unique sound when they start")
        public boolean eventIntroSounds = true;

        @Config.Name("Lunar Edge Damage: Base")
        @Config.Comment("The amount of additional damage that will always be applied regardless of moon phase")
        public int lunarEdgeDamageBase = 0;

        @Config.Name("Lunar Edge Damage: Min Level")
        @Config.Comment("The amount of additional damage that should be applied to an item with level 1 lunar edge on a full moon")
        public double lunarEdgeDamageMinLevel = 1.0;

        @Config.Name("Lunar Edge Damage: Max Level")
        @Config.Comment("The amount of additional damage that should be applied to an item with max level lunar edge on a full moon")
        public double lunarEdgeDamageMaxLevel = 3.0;

        @Config.Name("Lunar Edge Max XP Multiplier")
        @Config.Comment({"The max multiplier on the amount of XP added (which happens during a full moon)",
                "Can be set to 0 to disable lunar edge XP gains",
                "The multiplier scales up to the max according to the level and moon phase",
                "Example: If the config option is set to 2.5, a full moon with max lunar edge level would give 3.5x XP and a new moon would give 1x XP"})
        public double lunarEdgeMaxXPMultiplier = 1.0;
    }

    public static class Entities {
        @Config.LangKey("config.nyx.eyezor")
        @Config.Comment("Eyezor settings")
        public final Eyezor EYEZOR = new Eyezor();

        public static class Eyezor {
            @Config.Name("Laser Color")
            @Config.Comment("The hex code of the Eyezor's laser color")
            public int laserColor = 0x6231FD;
        }
    }

    public static class EventsLunar {
        @Config.LangKey("config.nyx.blood_moon")
        @Config.Comment("Blood Moon settings")
        public final BloodMoon BLOOD_MOON = new BloodMoon();

        @Config.LangKey("config.nyx.blue_moon")
        @Config.Comment("Blue Moon settings")
        public final BlueMoon BLUE_MOON = new BlueMoon();

        @Config.LangKey("config.nyx.full_moon")
        @Config.Comment("Full Moon settings")
        public final FullMoon FULL_MOON = new FullMoon();

        @Config.LangKey("config.nyx.star_shower")
        @Config.Comment("Star Shower settings")
        public final StarShower STAR_SHOWER = new StarShower();

        @Config.Name("Allowed Dimensions")
        @Config.Comment("The numeric IDs of the dimensions that lunar events should occur in")
        public Integer[] allowedDimensions = new Integer[]{0};

        public static class BloodMoon {
            @Config.Name("Chance")
            @Config.Comment("The chance in percent (1 = 100%) of the Blood Moon occurring")
            @Config.RangeDouble(min = 0.0, max = 1.0)
            public double chance = 0.05;

            @Config.Name("Start Night")
            @Config.Comment("The amount of nights that should pass before the Blood Moon occurs for the first time")
            @Config.RangeInt(min = 0)
            public int startNight = 0;

            @Config.Name("Night Interval")
            @Config.Comment({"The interval in nights at which the Blood Moon should occur", "Overrides chance setting if set to a value greater than 0"})
            @Config.RangeInt(min = 0)
            public int nightInterval = 0;

            @Config.Name("Grace Period")
            @Config.Comment("The amount of nights that should pass until the Blood Moon happens again")
            @Config.RangeInt(min = 0)
            public int gracePeriod = 8;

            @Config.Name("Cloud Color")
            @Config.Comment("The hex code of the Blood Moon's cloud color")
            public int cloudColor = 0x420d03;

            @Config.Name("Lightmap Color")
            @Config.Comment("The hex code of the Blood Moon's lightmap color")
            public int lightmapColor = 0x420d03;

            @Config.Name("Sky Color")
            @Config.Comment("The hex code of the Blood Moon's sky color")
            public int skyColor = 0x420d03;

            @Config.Name("On Full Moon")
            @Config.Comment("If the Blood Moon should only occur on full moon nights")
            public boolean onFullMoon = false;

            @Config.Name("Sleeping")
            @Config.Comment("If sleeping is allowed during a Blood Moon")
            public boolean sleeping = false;

            @Config.Name("Spawn Multiplier")
            @Config.Comment("The multiplier with which mobs should spawn during the Blood Moon (eg 2 means 2 mobs spawn instead of 1)")
            @Config.RangeInt(min = 1, max = 1000)
            public int spawnMultiplier = 2;

            @Config.Name("Spawn Radius")
            @Config.Comment({"The closest distance that mobs can spawn away from a player during the Blood Moon", "Vanilla value is 24"})
            public int spawnRadius = 20;

            @Config.Name("Mobs Vanish")
            @Config.Comment("If mobs spawned by the Blood Moon should die at sunrise")
            public boolean mobsVanish = true;

            @Config.Name("Exclusive Spawns")
            @Config.Comment({"The registry names of hostile entities that should spawn exclusively on the surface during the event (whitelist)", "Leave empty to allow all hostile entities to spawn"})
            public String[] spawnsExclusive = new String[]{};

            @Config.Name("Extra Spawns")
            @Config.Comment({"The registry names of entities that should spawn additionally alongside other entities during the event", "Syntax: originalEntity;extraEntity"})
            public String[] spawnsExtra = new String[]{
                    "minecraft:zombie;nyx:eyezor"
            };

            @Config.Name("Extra Spawns Chance")
            @Config.Comment({"The chance for an additional mob to be spawned when a mob spawns during the event", "The higher the number, the less likely", "Set to 0 to disable"})
            @Config.RangeInt(min = 0, max = 1000)
            public int spawnsExtraChance = 10;

            @Config.Name("Replacement Spawns")
            @Config.Comment({"The registry names of entities that should replace other entities during the event", "Syntax: originalEntity;replacementEntity"})
            public String[] spawnsReplacement = new String[]{};
        }

        public static class BlueMoon {
            @Config.Name("Chance")
            @Config.Comment("The chance in percent (1 = 100%) of the Blue Moon occurring")
            @Config.RangeDouble(min = 0.0, max = 1.0)
            public double chance = 0.05;

            @Config.Name("Start Night")
            @Config.Comment("The amount of nights that should pass before the Blue Moon occurs for the first time")
            @Config.RangeInt(min = 0)
            public int startNight = 0;

            @Config.Name("Night Interval")
            @Config.Comment({"The interval in nights at which the Blue Moon should occur", "Overrides chance setting if set to a value greater than 0"})
            @Config.RangeInt(min = 0)
            public int nightInterval = 0;

            @Config.Name("Grace Period")
            @Config.Comment("The amount of nights that should pass until the Blue Moon happens again")
            @Config.RangeInt(min = 0)
            public int gracePeriod = 8;

            @Config.Name("Cloud Color")
            @Config.Comment("The hex code of the Blue Moon's cloud color")
            public int cloudColor = 0x3f3fc0;

            @Config.Name("Lightmap Color")
            @Config.Comment("The hex code of the Blue Moon's lightmap color")
            public int lightmapColor = 0x3f3fc0;

            @Config.Name("Sky Color")
            @Config.Comment("The hex code of the Blue Moon's sky color")
            public int skyColor = 0x3f3fc0;

            @Config.Name("On Full Moon")
            @Config.Comment("If the Blue Moon should only occur on full moon nights")
            public boolean onFullMoon = false;

            @Config.Name("Grow Amount")
            @Config.Comment("The amount of plants that should be grown per chunk during the Blue Moon")
            @Config.RangeInt(min = 0, max = 100)
            public int growAmount = 15;

            @Config.Name("Grow Interval")
            @Config.Comment("The amount of ticks that should pass before plants are grown again during the Blue Moon")
            @Config.RangeInt(min = 1, max = 100)
            public int growInterval = 10;

            @Config.Name("Exclusive Spawns")
            @Config.Comment({"The registry names of hostile entities that should spawn exclusively on the surface during the event (whitelist)", "Leave empty to allow all hostile entities to spawn"})
            public String[] spawnsExclusive = new String[]{
                    "minecraft:slime"
            };

            @Config.Name("Extra Spawns")
            @Config.Comment({"The registry names of entities that should spawn additionally alongside other entities during the event", "Syntax: originalEntity;extraEntity"})
            public String[] spawnsExtra = new String[]{};

            @Config.Name("Extra Spawns Chance")
            @Config.Comment({"The chance for an additional mob to be spawned when a mob spawns during the event", "The higher the number, the less likely", "Set to 0 to disable"})
            @Config.RangeInt(min = 0, max = 1000)
            public int spawnsExtraChance = 10;

            @Config.Name("Replacement Spawns")
            @Config.Comment({"The registry names of entities that should replace other entities during the event", "Syntax: originalEntity;replacementEntity"})
            public String[] spawnsReplacement = new String[]{};
        }

        public static class FullMoon {
            @Config.Name("Act As Event")
            @Config.Comment("If the vanilla full moon should be considered a proper lunar event")
            public boolean actAsEvent = true;

            @Config.Name("Add Potion Effects")
            @Config.Comment("If mobs spawned during a full moon should have random potion effects applied to them (similarly to spiders in the base game)")
            public boolean addPotionEffects = true;

            @Config.Name("Exclusive Spawns")
            @Config.Comment({"The registry names of hostile entities that should spawn exclusively on the surface during the event (whitelist)", "Leave empty to allow all hostile entities to spawn"})
            public String[] spawnsExclusive = new String[]{};

            @Config.Name("Extra Spawns")
            @Config.Comment({"The registry names of entities that should spawn additionally alongside other entities during the event", "Syntax: originalEntity;extraEntity"})
            public String[] spawnsExtra = new String[]{
                    "minecraft:zombie;minecraft:zombie",
                    "minecraft:skeleton;minecraft:skeleton",
                    "minecraft:creeper;minecraft:creeper",
                    "minecraft:spider;minecraft:spider"
            };

            @Config.Name("Extra Spawns Chance")
            @Config.Comment({"The chance for an additional mob to be spawned when a mob spawns during the event", "The higher the number, the less likely", "Set to 0 to disable"})
            @Config.RangeInt(min = 0, max = 1000)
            public int spawnsExtraChance = 10;

            @Config.Name("Replacement Spawns")
            @Config.Comment({"The registry names of entities that should replace other entities during the event", "Syntax: originalEntity;replacementEntity"})
            public String[] spawnsReplacement = new String[]{};
        }

        public static class StarShower {
            @Config.Name("Chance")
            @Config.Comment("The chance in percent (1 = 100%) of Star Showers occurring")
            @Config.RangeDouble(min = 0.0, max = 1.0)
            public double chance = 0.05;

            @Config.Name("Start Night")
            @Config.Comment("The amount of nights that should pass before Star Showers occurs for the first time")
            @Config.RangeInt(min = 0)
            public int startNight = 0;

            @Config.Name("Night Interval")
            @Config.Comment({"The interval in nights at which Star Showers should occur", "Overrides chance setting if set to a value greater than 0"})
            @Config.RangeInt(min = 0)
            public int nightInterval = 0;

            @Config.Name("Grace Period")
            @Config.Comment("The amount of nights that should pass until Star Showers happen again")
            @Config.RangeInt(min = 0)
            public int gracePeriod = 15;

            @Config.Name("Cloud Color")
            @Config.Comment("The hex code of the Star Shower's cloud color")
            public int cloudColor = 0xdec25f;

            @Config.Name("Lightmap Color")
            @Config.Comment("The hex code of the Star Shower's lightmap color")
            public int lightmapColor = 0xdec25f;

            @Config.Name("Sky Color")
            @Config.Comment("The hex code of the Star Shower's sky color")
            public int skyColor = 0xdec25f;

            @Config.Name("On Full Moon")
            @Config.Comment("If the Star Shower should only occur on full moon nights")
            public boolean onFullMoon = false;

            @Config.Name("Exclusive Spawns")
            @Config.Comment({"The registry names of hostile entities that should spawn exclusively on the surface during the event (whitelist)", "Leave empty to allow all hostile entities to spawn"})
            public String[] spawnsExclusive = new String[]{};

            @Config.Name("Extra Spawns")
            @Config.Comment({"The registry names of entities that should spawn additionally alongside other entities during the event", "Syntax: originalEntity;extraEntity"})
            public String[] spawnsExtra = new String[]{
                    "minecraft:zombie;nyx:eyezor"
            };

            @Config.Name("Extra Spawns Chance")
            @Config.Comment({"The chance for an additional mob to be spawned when a mob spawns during the event", "The higher the number, the less likely", "Set to 0 to disable"})
            @Config.RangeInt(min = 0, max = 1000)
            public int spawnsExtraChance = 10;

            @Config.Name("Replacement Spawns")
            @Config.Comment({"The registry names of entities that should replace other entities during the event", "Syntax: originalEntity;replacementEntity"})
            public String[] spawnsReplacement = new String[]{};
        }
    }

    public static class EventsSolar {
        @Config.LangKey("config.nyx.grim_eclipse")
        @Config.Comment("Grim Eclipse settings")
        public final GrimEclipse GRIM_ECLIPSE = new GrimEclipse();

        @Config.LangKey("config.nyx.red_giant")
        @Config.Comment("Red Giant settings")
        public final RedGiant RED_GIANT = new RedGiant();

        @Config.Name("Allowed Dimensions")
        @Config.Comment("The numeric IDs of the dimensions that solar events should occur in")
        public Integer[] allowedDimensions = new Integer[]{0};

        public static class GrimEclipse {
            @Config.Name("Chance")
            @Config.Comment("The chance in percent (1 = 100%) of the Grim Eclipse occurring")
            @Config.RangeDouble(min = 0.0, max = 1.0)
            public double chance = 0.05;

            @Config.Name("Start Day")
            @Config.Comment("The amount of days that should pass before the Grim Eclipse occurs for the first time")
            @Config.RangeInt(min = 0)
            public int startDay = 0;

            @Config.Name("Day Interval")
            @Config.Comment({"The interval in days at which the Grim Eclipse should occur", "Overrides chance setting if set to a value greater than 0"})
            @Config.RangeInt(min = 0)
            public int dayInterval = 0;

            @Config.Name("Grace Period")
            @Config.Comment("The amount of days that should pass until the Grim Eclipse happens again")
            @Config.RangeInt(min = 0)
            public int gracePeriod = 8;

            @Config.Name("Exclusive Spawns")
            @Config.Comment({"The registry names of hostile entities that should spawn exclusively on the surface during the event (whitelist)", "Leave empty to allow all hostile entities to spawn"})
            public String[] spawnsExclusive = new String[]{};

            @Config.Name("Extra Spawns")
            @Config.Comment({"The registry names of entities that should spawn additionally alongside other entities during the event", "Syntax: originalEntity;extraEntity"})
            public String[] spawnsExtra = new String[]{
                    "minecraft:zombie;nyx:eyezor"
            };

            @Config.Name("Extra Spawns Chance")
            @Config.Comment({"The chance for an additional mob to be spawned when a mob spawns during the event", "The higher the number, the less likely", "Set to 0 to disable"})
            @Config.RangeInt(min = 0, max = 1000)
            public int spawnsExtraChance = 10;

            @Config.Name("Cloud Color")
            @Config.Comment("The hex code of the Grim Eclipse's cloud color")
            public int cloudColor = 0x131311;

            @Config.Name("Lightmap Color")
            @Config.Comment("The hex code of the Grim Eclipse's lightmap color")
            public int lightmapColor = 0x131311;

            @Config.Name("Sky Color")
            @Config.Comment("The hex code of the Grim Eclipse's sky color")
            public int skyColor = 0x070707;

            @Config.Name("Replacement Spawns")
            @Config.Comment({"The registry names of entities that should replace other entities during the event", "Syntax: originalEntity;replacementEntity"})
            public String[] spawnsReplacement = new String[]{};
        }

        public static class RedGiant {
            @Config.Name("Chance")
            @Config.Comment("The chance in percent (1 = 100%) of the Red Giant occurring")
            @Config.RangeDouble(min = 0.0, max = 1.0)
            public double chance = 0.05;

            @Config.Name("Start Day")
            @Config.Comment("The amount of days that should pass before the Red Giant occurs for the first time")
            @Config.RangeInt(min = 0)
            public int startDay = 0;

            @Config.Name("Day Interval")
            @Config.Comment({"The interval in days at which the Red Giant should occur", "Overrides chance setting if set to a value greater than 0"})
            @Config.RangeInt(min = 0)
            public int dayInterval = 0;

            @Config.Name("Grace Period")
            @Config.Comment("The amount of days that should pass until the Red Giant happens again")
            @Config.RangeInt(min = 0)
            public int gracePeriod = 15;

            @Config.Name("Exclusive Spawns")
            @Config.Comment({"The registry names of hostile entities that should spawn exclusively on the surface during the event (whitelist)", "Leave empty to allow all hostile entities to spawn"})
            public String[] spawnsExclusive = new String[]{};

            @Config.Name("Extra Spawns")
            @Config.Comment({"The registry names of entities that should spawn additionally alongside other entities during the event", "Syntax: originalEntity;extraEntity"})
            public String[] spawnsExtra = new String[]{
                    "minecraft:zombie;nyx:eyezor"
            };

            @Config.Name("Extra Spawns Chance")
            @Config.Comment({"The chance for an additional mob to be spawned when a mob spawns during the event", "The higher the number, the less likely", "Set to 0 to disable"})
            @Config.RangeInt(min = 0, max = 1000)
            public int spawnsExtraChance = 10;

            @Config.Name("Cloud Color")
            @Config.Comment("The hex code of the Red Giant's cloud color")
            public int cloudColor = 0x420d03;

            @Config.Name("Lightmap Color")
            @Config.Comment("The hex code of the Red Giant's lightmap color")
            public int lightmapColor = 0x420d03;

            @Config.Name("Sky Color")
            @Config.Comment("The hex code of the Red Giant's sky color")
            public int skyColor = 0x420d03;

            @Config.Name("Replacement Spawns")
            @Config.Comment({"The registry names of entities that should replace other entities during the event", "Syntax: originalEntity;replacementEntity"})
            public String[] spawnsReplacement = new String[]{
                    "minecraft:zombie;minecraft:husk",
                    "minecraft:skeleton;minecraft:wither_skeleton",
                    "minecraft:slime;minecraft:magma_cube"
            };
        }
    }

    public static class FallingStars {
        @Config.Name("Chance")
        @Config.Comment("The chance in percent (1 = 100%) for a falling star to appear at night for each player per minute")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        public double chanceM = 0.3;

        @Config.Name("Chance During Showers")
        @Config.Comment("The chance for a falling star to appear during a star shower for each player per minute")
        @Config.RangeDouble(min = 0.0, max = 1.0)
        public double chanceShowerM = 1.0;

        @Config.Name("Ambient Volume")
        @Config.Comment("The volume for the falling star ambient sound")
        public double volumeAmbient = 8.0;

        @Config.Name("Impact Volume")
        @Config.Comment("The volume for the falling star impact sound")
        public double volumeImpact = 8.0;
    }

    public static class Meteors {
        @Config.Name("Chance")
        @Config.Comment("The chance of a meteor spawning every minute, during the day")
        @Config.RangeDouble(min = 0.0)
        public double chanceM = 0.0042;

        @Config.Name("Chance At Night")
        @Config.Comment("The chance of a meteor spawning every minute, during nighttime")
        @Config.RangeDouble(min = 0.0)
        public double chanceNightM = 0.0072;

        @Config.Name("Gate Dimension")
        @Config.Comment("The dimension that needs to be entered to increase the spawning of meteors")
        public int gateDimension = 1;

        @Config.Name("Chance After Gate Visit")
        @Config.Comment("The chance of a meteor spawning every minute, during the day, after the gate dimension has been entered once")
        @Config.RangeDouble(min = 0.0)
        public double chanceAfterGateM = 0.006;

        @Config.Name("Chance After Gate Visit At Night")
        @Config.Comment("The chance of a meteor spawning every minute, during the night, after the gate dimension has been entered once")
        @Config.RangeDouble(min = 0.0)
        public double chanceAfterGateNightM = 0.09;

        @Config.Name("Chance During Star Showers")
        @Config.Comment("The chance of a meteor spawning every minute, during a star shower")
        @Config.RangeDouble(min = 0.0)
        public double chanceStarShowerM = 0.144;

        @Config.Name("Chance In The End")
        @Config.Comment("The chance of a meteor spawning every minute, in the end dimension")
        @Config.RangeDouble(min = 0.0)
        public double chanceEndM = 0.09;

        @Config.Name("Grace Period")
        @Config.Comment("The amount of days that should pass after world creation until meteors can fall")
        @Config.RangeInt(min = 0)
        public int gracePeriod = 0;

        @Config.Name("Spawn Radius")
        @Config.Comment("The amount of blocks a meteor can spawn away from the nearest player")
        public int spawnRadius = 1000;

        @Config.Name("Disallow Radius")
        @Config.Comment("The radius in chunks that should be marked as invalid for meteor spawning around each player")
        public int disallowRadius = 14;

        @Config.Name("Disallow Time")
        @Config.Comment("The amount of ticks that need to pass for each player until the chance of a meteor spawning in the area is halved (and then halved again, and so on)")
        public int disallowTime = 12000;

        @Config.Name("Chat Message")
        @Config.Comment("If fallen meteors should be announced in chat on impact")
        public boolean message = true;

        @Config.Name("Chat Message Verbose")
        @Config.Comment("If chat messages for meteor impacts should include coordinates")
        public boolean messageVerbose = false;

        @Config.Name("Meteor Blocks: Meteorite")
        @Config.Comment("Main blocks that are spawned on impact of regular meteors")
        public String[] meteorBlocksMeteorite = new String[]{
                "nyx:meteorite_rock_hot",
                "nyx:meteorite_rock"
        };

        @Config.Name("Meteor Blocks: Frezarite")
        @Config.Comment("Main blocks that are spawned on impact of cold meteors")
        public String[] meteorBlocksFrezarite = new String[]{
                "nyx:frezarite_rock",
                "minecraft:packed_ice"
        };

        @Config.Name("Meteor Blocks: Kreknorite")
        @Config.Comment("Main blocks that are spawned on impact of hot meteors")
        public String[] meteorBlocksKreknorite = new String[]{
                "nyx:kreknorite_rock",
                "minecraft:obsidian"
        };

        @Config.Name("Meteor Blocks: Unknown")
        @Config.Comment("Main blocks that are spawned on impact of mixed meteors")
        public String[] meteorBlocksUnknown = new String[]{
                "nyx:meteorite_rock_hot",
                "nyx:meteorite_rock",
                "nyx:frezarite_rock",
                "nyx:kreknorite_rock",
                "minecraft:packed_ice",
                "minecraft:obsidian"
        };

        @Config.Name("Filler Blocks: Meteorite")
        @Config.Comment("Subsidiary blocks that are spawned on impact of regular meteors")
        public String[] fillerBlocksMeteorite = new String[]{
                "minecraft:magma"
        };

        @Config.Name("Filler Blocks: Frezarite")
        @Config.Comment("Subsidiary blocks that are spawned on impact of cold meteors")
        public String[] fillerBlocksFrezarite = new String[]{
                "minecraft:packed_ice"
        };

        @Config.Name("Filler Blocks: Kreknorite")
        @Config.Comment("Subsidiary blocks that are spawned on impact of hot meteors")
        public String[] fillerBlocksKreknorite = new String[]{
                "minecraft:magma"
        };

        @Config.Name("Filler Blocks: Unknown")
        @Config.Comment("Subsidiary blocks that are spawned on impact of mixed meteors")
        public String[] fillerBlocksUnknown = new String[]{
                "minecraft:magma",
                "minecraft:packed_ice"
        };

        @Config.Name("Liquid Blocks: Meteorite")
        @Config.Comment("Fluid blocks that are spawned on impact of regular meteors")
        public String[] liquidBlocksMeteorite = new String[]{
                "minecraft:air"
        };

        @Config.Name("Liquid Blocks: Frezarite")
        @Config.Comment("Fluid blocks that are spawned on impact of cold meteors")
        public String[] liquidBlocksFrezarite = new String[]{
                "minecraft:water"
        };

        @Config.Name("Liquid Blocks: Kreknorite")
        @Config.Comment("Fluid blocks that are spawned on impact of hot meteors")
        public String[] liquidBlocksKreknorite = new String[]{
                "minecraft:lava"
        };

        @Config.Name("Liquid Blocks: Unknown")
        @Config.Comment("Fluid blocks that are spawned on impact of mixed meteors")
        public String[] liquidBlocksUnknown = new String[]{
                "minecraft:lava",
                "minecraft:water"
        };
    }

    public static class ModIntegration {
        @Config.LangKey("config.nyx.simple_difficulty")
        @Config.Comment("SimpleDifficulty settings")
        public final ModIntegration.SimpleDifficulty SIMPLE_DIFFICULTY = new ModIntegration.SimpleDifficulty();

        @Config.LangKey("config.nyx.tan")
        @Config.Comment("Tough As Nails settings")
        public final ModIntegration.TAN TAN = new ModIntegration.TAN();

        @Config.Name("Construct's Armory Integration")
        @Config.Comment({"Enables Construct's Armory integration", "Requires Tinkers' Construct integration to be enabled"})
        public boolean constructsArmoryIntegration = true;

        @Config.Name("Tinkers' Construct Integration")
        @Config.Comment("Enables Tinkers' Construct integration")
        public boolean tinkersConstructIntegration = true;

        @Config.Name("Peaceful Surface Integration")
        @Config.Comment({"Enables Peaceful Surface integration", "Mobs will spawn on the surface during events when enabled"})
        public boolean peacefulSurfaceIntegration = true;

        @Config.Name("Game Stages Integration")
        @Config.Comment({"Enables Game Stages integration", "See wiki for details: https://github.com/Elite-Modding-Team/Hyxcate/wiki/Game-Stages"})
        public boolean gameStagesIntegration = false;

        public static class SimpleDifficulty {
            @Config.Name("Enable SimpleDifficulty Integration")
            @Config.Comment("Enables SimpleDifficulty integration")
            public boolean enableSimpleDifficulty = true;

            @Config.Name("Red Giant Temperature")
            @Config.Comment("The temperature level during an active Red Giant event")
            public int redGiantTemperature = 10;
        }

        public static class TAN {
            @Config.Name("Enable Tough As Nails Integration")
            @Config.Comment("Enables Tough As Nails integration")
            public boolean enableTAN = true;

            @Config.Name("Red Giant Temperature")
            @Config.Comment("The temperature level during an active Red Giant event")
            public int redGiantTemperature = 22;
        }
    }

    @Mod.EventBusSubscriber(modid = Nyx.ID)
    private static class EventHandler {
        @SubscribeEvent
        public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(Nyx.ID)) {
                ConfigManager.sync(Nyx.ID, Config.Type.INSTANCE);
                NyxData.initConfigLists();
            }
        }
    }
}

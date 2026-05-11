package de.ellpeck.nyx.config;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NyxData {
    public static final List<Integer> ALLOWED_DIMENSIONS_LUNAR = new ArrayList<>();
    public static final List<Integer> ALLOWED_DIMENSIONS_SOLAR = new ArrayList<>();

    public static final List<Integer> ALLOWED_METEOR_SIZES = new ArrayList<>();
    public static final List<Integer> ALLOWED_METEOR_TYPES = new ArrayList<>();

    public static final List<Block> METEOR_BLOCKS_METEORITE = new ArrayList<>();
    public static final List<Block> METEOR_BLOCKS_FREZARITE = new ArrayList<>();
    public static final List<Block> METEOR_BLOCKS_KREKNORITE = new ArrayList<>();
    public static final List<Block> METEOR_BLOCKS_UNKNOWN = new ArrayList<>();

    public static final List<Block> FILLER_BLOCKS_METEORITE = new ArrayList<>();
    public static final List<Block> FILLER_BLOCKS_FREZARITE = new ArrayList<>();
    public static final List<Block> FILLER_BLOCKS_KREKNORITE = new ArrayList<>();
    public static final List<Block> FILLER_BLOCKS_UNKNOWN = new ArrayList<>();

    public static final List<Block> LIQUID_BLOCKS_METEORITE = new ArrayList<>();
    public static final List<Block> LIQUID_BLOCKS_FREZARITE = new ArrayList<>();
    public static final List<Block> LIQUID_BLOCKS_KREKNORITE = new ArrayList<>();
    public static final List<Block> LIQUID_BLOCKS_UNKNOWN = new ArrayList<>();

    public static final Map<ResourceLocation, List<ResourceLocation>> EXTRA_SPAWNS_BLOOD_MOON = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> EXTRA_SPAWNS_BLUE_MOON = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> EXTRA_SPAWNS_FULL_MOON = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> EXTRA_SPAWNS_STAR_SHOWER = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> EXTRA_SPAWNS_GRIM_ECLIPSE = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> EXTRA_SPAWNS_RED_GIANT = new Object2ObjectOpenHashMap<>();

    public static final Map<ResourceLocation, List<ResourceLocation>> REPLACEMENT_SPAWNS_BLOOD_MOON = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> REPLACEMENT_SPAWNS_BLUE_MOON = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> REPLACEMENT_SPAWNS_FULL_MOON = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> REPLACEMENT_SPAWNS_STAR_SHOWER = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> REPLACEMENT_SPAWNS_GRIM_ECLIPSE = new Object2ObjectOpenHashMap<>();
    public static final Map<ResourceLocation, List<ResourceLocation>> REPLACEMENT_SPAWNS_RED_GIANT = new Object2ObjectOpenHashMap<>();

    public static final List<ResourceLocation> EXCLUSIVE_SPAWNS_BLOOD_MOON = new ArrayList<>();
    public static final List<ResourceLocation> EXCLUSIVE_SPAWNS_BLUE_MOON = new ArrayList<>();
    public static final List<ResourceLocation> EXCLUSIVE_SPAWNS_FULL_MOON = new ArrayList<>();
    public static final List<ResourceLocation> EXCLUSIVE_SPAWNS_STAR_SHOWER = new ArrayList<>();
    public static final List<ResourceLocation> EXCLUSIVE_SPAWNS_GRIM_ECLIPSE = new ArrayList<>();
    public static final List<ResourceLocation> EXCLUSIVE_SPAWNS_RED_GIANT = new ArrayList<>();

    public static void initConfigLists() {
        ALLOWED_DIMENSIONS_LUNAR.clear();
        ALLOWED_DIMENSIONS_LUNAR.addAll(Arrays.asList(NyxConfig.EVENTS_LUNAR.allowedDimensions));
        ALLOWED_DIMENSIONS_SOLAR.clear();
        ALLOWED_DIMENSIONS_SOLAR.addAll(Arrays.asList(NyxConfig.EVENTS_SOLAR.allowedDimensions));

        ALLOWED_METEOR_SIZES.clear();
        ALLOWED_METEOR_SIZES.addAll(Arrays.asList(NyxConfig.METEORS.meteorSizes));
        ALLOWED_METEOR_TYPES.clear();
        ALLOWED_METEOR_TYPES.addAll(Arrays.asList(NyxConfig.METEORS.meteorTypes));

        readBlocksFromConfigAsList(NyxConfig.METEORS.meteorBlocksMeteorite, METEOR_BLOCKS_METEORITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.meteorBlocksFrezarite, METEOR_BLOCKS_FREZARITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.meteorBlocksKreknorite, METEOR_BLOCKS_KREKNORITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.meteorBlocksUnknown, METEOR_BLOCKS_UNKNOWN);

        readBlocksFromConfigAsList(NyxConfig.METEORS.fillerBlocksMeteorite, FILLER_BLOCKS_METEORITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.fillerBlocksFrezarite, FILLER_BLOCKS_FREZARITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.fillerBlocksKreknorite, FILLER_BLOCKS_KREKNORITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.fillerBlocksUnknown, FILLER_BLOCKS_UNKNOWN);

        readBlocksFromConfigAsList(NyxConfig.METEORS.liquidBlocksMeteorite, LIQUID_BLOCKS_METEORITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.liquidBlocksFrezarite, LIQUID_BLOCKS_FREZARITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.liquidBlocksKreknorite, LIQUID_BLOCKS_KREKNORITE);
        readBlocksFromConfigAsList(NyxConfig.METEORS.liquidBlocksUnknown, LIQUID_BLOCKS_UNKNOWN);

        readEntitiesFromConfigAsList(NyxConfig.EVENTS_LUNAR.BLOOD_MOON.spawnsExclusive, EXCLUSIVE_SPAWNS_BLOOD_MOON);
        readEntitiesFromConfigAsList(NyxConfig.EVENTS_LUNAR.BLUE_MOON.spawnsExclusive, EXCLUSIVE_SPAWNS_BLUE_MOON);
        readEntitiesFromConfigAsList(NyxConfig.EVENTS_LUNAR.FULL_MOON.spawnsExclusive, EXCLUSIVE_SPAWNS_FULL_MOON);
        readEntitiesFromConfigAsList(NyxConfig.EVENTS_LUNAR.STAR_SHOWER.spawnsExclusive, EXCLUSIVE_SPAWNS_STAR_SHOWER);
        readEntitiesFromConfigAsList(NyxConfig.EVENTS_SOLAR.GRIM_ECLIPSE.spawnsExclusive, EXCLUSIVE_SPAWNS_GRIM_ECLIPSE);
        readEntitiesFromConfigAsList(NyxConfig.EVENTS_SOLAR.RED_GIANT.spawnsExclusive, EXCLUSIVE_SPAWNS_RED_GIANT);

        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_LUNAR.BLOOD_MOON.spawnsExtra, EXTRA_SPAWNS_BLOOD_MOON);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_LUNAR.BLUE_MOON.spawnsExtra, EXTRA_SPAWNS_BLUE_MOON);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_LUNAR.FULL_MOON.spawnsExtra, EXTRA_SPAWNS_FULL_MOON);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_LUNAR.STAR_SHOWER.spawnsExtra, EXTRA_SPAWNS_STAR_SHOWER);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_SOLAR.GRIM_ECLIPSE.spawnsExtra, EXTRA_SPAWNS_GRIM_ECLIPSE);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_SOLAR.RED_GIANT.spawnsExtra, EXTRA_SPAWNS_RED_GIANT);

        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_LUNAR.BLOOD_MOON.spawnsReplacement, REPLACEMENT_SPAWNS_BLOOD_MOON);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_LUNAR.BLUE_MOON.spawnsReplacement, REPLACEMENT_SPAWNS_BLUE_MOON);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_LUNAR.FULL_MOON.spawnsReplacement, REPLACEMENT_SPAWNS_FULL_MOON);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_LUNAR.STAR_SHOWER.spawnsReplacement, REPLACEMENT_SPAWNS_STAR_SHOWER);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_SOLAR.GRIM_ECLIPSE.spawnsReplacement, REPLACEMENT_SPAWNS_GRIM_ECLIPSE);
        readEntitiesFromConfigAsMap(NyxConfig.EVENTS_SOLAR.RED_GIANT.spawnsReplacement, REPLACEMENT_SPAWNS_RED_GIANT);
    }

    private static void readBlocksFromConfigAsList(String[] array, List<Block> list) {
        list.clear();
        for (String string : array) {
            list.add(ForgeRegistries.BLOCKS.getValue(new ResourceLocation(string)));
        }
    }

    private static void readEntitiesFromConfigAsList(String[] array, List<ResourceLocation> list) {
        list.clear();
        for (String string : array) {
            list.add(new ResourceLocation(string));
        }
    }

    private static void readEntitiesFromConfigAsMap(String[] array, Map<ResourceLocation, List<ResourceLocation>> map) {
        map.clear();
        map.putAll(Arrays.stream(array)
                .map(string -> string.split(";"))
                .filter(subStrings -> subStrings.length == 2)
                .collect(Collectors.groupingBy(
                        subStrings -> new ResourceLocation(subStrings[0]),
                        Collectors.mapping(subStrings -> new ResourceLocation(subStrings[1]), Collectors.toList()))));
    }
}

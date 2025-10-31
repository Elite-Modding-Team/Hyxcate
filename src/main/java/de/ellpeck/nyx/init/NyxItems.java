package de.ellpeck.nyx.init;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.items.*;
import de.ellpeck.nyx.items.baubles.NyxBaubleEarthTrinket;
import de.ellpeck.nyx.items.tools.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Nyx.ID)
public class NyxItems {

    public static final List<Item> MOD_ITEMS = new ArrayList<>();

    public static Item fallenStar;
    public static Item meteoriteShard;
    public static Item tektiteGemCluster;
    public static Item frezariteCrystal;
    public static Item kreknoriteShard;
    public static Item meteoriteIngot;
    public static Item frezariteIngot;
    public static Item kreknoriteIngot;
    public static Item herosBrokenArtifact;
    public static Item celestialEmblem;
    public static Item cyberPowerCell;
    public static Item meteoritePickaxe;
    public static Item meteoriteAxe;
    public static Item meteoriteShovel;
    public static Item meteoriteHoe;
    public static Item meteoriteSword;
    public static Item meteoriteHelmet;
    public static Item meteoriteChestplate;
    public static Item meteoriteLeggings;
    public static Item meteoriteBoots;
    public static Item ancientMeteoriteHelmet;
    public static Item ancientMeteoriteChestplate;
    public static Item ancientMeteoriteLeggings;
    public static Item ancientMeteoriteBoots;
    public static Item frezaritePickaxe;
    public static Item frezariteAxe;
    public static Item frezariteShovel;
    public static Item frezariteHoe;
    public static Item frezariteSword;
    public static Item frezariteHelmet;
    public static Item frezariteChestplate;
    public static Item frezariteLeggings;
    public static Item frezariteBoots;
    public static Item ancientFrezariteHelmet;
    public static Item ancientFrezariteChestplate;
    public static Item ancientFrezariteLeggings;
    public static Item ancientFrezariteBoots;
    public static Item kreknoritePickaxe;
    public static Item kreknoriteAxe;
    public static Item kreknoriteShovel;
    public static Item kreknoriteHoe;
    public static Item kreknoriteSword;
    public static Item kreknoriteHelmet;
    public static Item kreknoriteChestplate;
    public static Item kreknoriteLeggings;
    public static Item kreknoriteBoots;
    public static Item ancientKreknoriteHelmet;
    public static Item ancientKreknoriteChestplate;
    public static Item ancientKreknoriteLeggings;
    public static Item ancientKreknoriteBoots;
    public static Item meteorFinder;
    public static Item tektitePickaxe;
    public static Item tektiteAxe;
    public static Item tektiteShovel;
    public static Item tektiteHoe;
    public static Item tektiteSword;
    public static Item tektiteGreatsword;
    public static Item tektiteHelmet;
    public static Item tektiteChestplate;
    public static Item tektiteLeggings;
    public static Item tektiteBoots;
    public static Item tektiteBow;
    public static Item celestialWarhammer;
    public static Item fallenStarBeamSword;
    public static Item meteoriteBeamSword;
    public static Item frezariteBeamSword;
    public static Item kreknoriteBeamSword;
    public static Item tektiteBeamSword;
    public static Item cyberCrystalBeamSword;

    // Mod Integration
    public static Item lunarAxe;
    public static Item solarSword;
    public static Item earthTrinket;
    public static Item.ToolMaterial meteoriteToolMaterial;
    public static ItemArmor.ArmorMaterial meteoriteArmorMaterial;
    public static ItemArmor.ArmorMaterial ancientMeteoriteArmorMaterial;
    public static Item.ToolMaterial frezariteToolMaterial;
    public static ItemArmor.ArmorMaterial frezariteArmorMaterial;
    public static ItemArmor.ArmorMaterial ancientFrezariteArmorMaterial;
    public static Item.ToolMaterial kreknoriteToolMaterial;
    public static ItemArmor.ArmorMaterial kreknoriteArmorMaterial;
    public static ItemArmor.ArmorMaterial ancientKreknoriteArmorMaterial;
    public static Item.ToolMaterial tektiteToolMaterial;
    public static ItemArmor.ArmorMaterial tektiteArmorMaterial;
    public static Item.ToolMaterial tektiteGreatswordToolMaterial;
    public static Item.ToolMaterial celestialWarhammerToolMaterial;
    public static Item.ToolMaterial lunarAxeToolMaterial;
    public static Item.ToolMaterial solarSwordToolMaterial;
    public static Item.ToolMaterial beamSwordToolMaterial;

    @SubscribeEvent
    public static void onItemRegistry(RegistryEvent.Register<Item> event) {
        fallenStar = initItem(new NyxItemFallenStar(), "fallen_star");
        tektiteGemCluster = initItem(new NyxItem(EnumRarity.EPIC), "tektite_gem_cluster");
        meteoriteShard = initItem(new NyxItem(EnumRarity.RARE), "meteorite_shard");
        meteoriteIngot = initItem(new NyxItem(EnumRarity.RARE), "meteorite_ingot");
        frezariteCrystal = initItem(new NyxItem(EnumRarity.EPIC), "frezarite_crystal");
        frezariteIngot = initItem(new NyxItem(EnumRarity.EPIC), "frezarite_ingot");
        kreknoriteShard = initItem(new NyxItem(EnumRarity.EPIC), "kreknorite_shard");
        kreknoriteIngot = initItem(new NyxItem(EnumRarity.EPIC), "kreknorite_ingot");
        herosBrokenArtifact = initItem(new NyxItem(EnumRarity.EPIC), "heros_broken_artifact");
        celestialEmblem = initItem(new NyxItem(EnumRarity.EPIC), "celestial_emblem");
        cyberPowerCell = initItem(new NyxItem(EnumRarity.EPIC), "cyber_power_cell");
        meteorFinder = initItem(new NyxToolMeteorDetector(), "meteor_detector");

        meteoritePickaxe = initItem(new NyxItemPickaxe(meteoriteToolMaterial), "meteorite_pickaxe");
        meteoriteAxe = initItem(new NyxItemAxe(meteoriteToolMaterial, 10.0F, 1.0F), "meteorite_axe");
        meteoriteShovel = initItem(new NyxItemSpade(meteoriteToolMaterial), "meteorite_shovel");
        meteoriteHoe = initItem(new NyxItemHoe(meteoriteToolMaterial), "meteorite_hoe");
        meteoriteSword = initItem(new NyxItemSword(meteoriteToolMaterial), "meteorite_sword");
        meteoriteHelmet = initItem(new NyxItemArmor(meteoriteArmorMaterial, 0, EntityEquipmentSlot.HEAD), "meteorite_helmet");
        meteoriteChestplate = initItem(new NyxItemArmor(meteoriteArmorMaterial, 1, EntityEquipmentSlot.CHEST), "meteorite_chestplate");
        meteoriteLeggings = initItem(new NyxItemArmor(meteoriteArmorMaterial, 2, EntityEquipmentSlot.LEGS), "meteorite_leggings");
        meteoriteBoots = initItem(new NyxItemArmor(meteoriteArmorMaterial, 3, EntityEquipmentSlot.FEET), "meteorite_boots");
        ancientMeteoriteHelmet = initItem(new NyxItemArmor(ancientMeteoriteArmorMaterial, 0, EntityEquipmentSlot.HEAD), "ancient_meteorite_helmet");
        ancientMeteoriteChestplate = initItem(new NyxItemArmor(ancientMeteoriteArmorMaterial, 1, EntityEquipmentSlot.CHEST), "ancient_meteorite_chestplate");
        ancientMeteoriteLeggings = initItem(new NyxItemArmor(ancientMeteoriteArmorMaterial, 2, EntityEquipmentSlot.LEGS), "ancient_meteorite_leggings");
        ancientMeteoriteBoots = initItem(new NyxItemArmor(ancientMeteoriteArmorMaterial, 3, EntityEquipmentSlot.FEET), "ancient_meteorite_boots");

        frezaritePickaxe = initItem(new NyxItemPickaxe(frezariteToolMaterial), "frezarite_pickaxe");
        frezariteAxe = initItem(new NyxItemAxe(frezariteToolMaterial, 11.0F, 1.1F), "frezarite_axe");
        frezariteShovel = initItem(new NyxItemSpade(frezariteToolMaterial), "frezarite_shovel");
        frezariteHoe = initItem(new NyxItemHoe(frezariteToolMaterial), "frezarite_hoe");
        frezariteSword = initItem(new NyxItemSword(frezariteToolMaterial), "frezarite_sword");
        frezariteHelmet = initItem(new NyxItemArmor(frezariteArmorMaterial, 0, EntityEquipmentSlot.HEAD), "frezarite_helmet");
        frezariteChestplate = initItem(new NyxItemArmor(frezariteArmorMaterial, 1, EntityEquipmentSlot.CHEST), "frezarite_chestplate");
        frezariteLeggings = initItem(new NyxItemArmor(frezariteArmorMaterial, 2, EntityEquipmentSlot.LEGS), "frezarite_leggings");
        frezariteBoots = initItem(new NyxItemArmor(frezariteArmorMaterial, 3, EntityEquipmentSlot.FEET), "frezarite_boots");
        ancientFrezariteHelmet = initItem(new NyxItemArmor(ancientFrezariteArmorMaterial, 0, EntityEquipmentSlot.HEAD), "ancient_frezarite_helmet");
        ancientFrezariteChestplate = initItem(new NyxItemArmor(ancientFrezariteArmorMaterial, 1, EntityEquipmentSlot.CHEST), "ancient_frezarite_chestplate");
        ancientFrezariteLeggings = initItem(new NyxItemArmor(ancientFrezariteArmorMaterial, 2, EntityEquipmentSlot.LEGS), "ancient_frezarite_leggings");
        ancientFrezariteBoots = initItem(new NyxItemArmor(ancientFrezariteArmorMaterial, 3, EntityEquipmentSlot.FEET), "ancient_frezarite_boots");

        kreknoritePickaxe = initItem(new NyxItemPickaxe(kreknoriteToolMaterial), "kreknorite_pickaxe");
        kreknoriteAxe = initItem(new NyxItemAxe(kreknoriteToolMaterial, 11.0F, 1.1F), "kreknorite_axe");
        kreknoriteShovel = initItem(new NyxItemSpade(kreknoriteToolMaterial), "kreknorite_shovel");
        kreknoriteHoe = initItem(new NyxItemHoe(kreknoriteToolMaterial), "kreknorite_hoe");
        kreknoriteSword = initItem(new NyxItemSword(kreknoriteToolMaterial), "kreknorite_sword");
        kreknoriteHelmet = initItem(new NyxItemArmor(kreknoriteArmorMaterial, 0, EntityEquipmentSlot.HEAD), "kreknorite_helmet");
        kreknoriteChestplate = initItem(new NyxItemArmor(kreknoriteArmorMaterial, 1, EntityEquipmentSlot.CHEST), "kreknorite_chestplate");
        kreknoriteLeggings = initItem(new NyxItemArmor(kreknoriteArmorMaterial, 2, EntityEquipmentSlot.LEGS), "kreknorite_leggings");
        kreknoriteBoots = initItem(new NyxItemArmor(kreknoriteArmorMaterial, 3, EntityEquipmentSlot.FEET), "kreknorite_boots");
        ancientKreknoriteHelmet = initItem(new NyxItemArmor(ancientKreknoriteArmorMaterial, 0, EntityEquipmentSlot.HEAD), "ancient_kreknorite_helmet");
        ancientKreknoriteChestplate = initItem(new NyxItemArmor(ancientKreknoriteArmorMaterial, 1, EntityEquipmentSlot.CHEST), "ancient_kreknorite_chestplate");
        ancientKreknoriteLeggings = initItem(new NyxItemArmor(ancientKreknoriteArmorMaterial, 2, EntityEquipmentSlot.LEGS), "ancient_kreknorite_leggings");
        ancientKreknoriteBoots = initItem(new NyxItemArmor(ancientKreknoriteArmorMaterial, 3, EntityEquipmentSlot.FEET), "ancient_kreknorite_boots");

        tektitePickaxe = initItem(new NyxItemPickaxe(tektiteToolMaterial), "tektite_pickaxe");
        tektiteAxe = initItem(new NyxItemAxe(tektiteToolMaterial, 12, 1.2F), "tektite_axe");
        tektiteShovel = initItem(new NyxItemSpade(tektiteToolMaterial), "tektite_shovel");
        tektiteHoe = initItem(new NyxItemHoe(tektiteToolMaterial), "tektite_hoe");
        tektiteSword = initItem(new NyxItemSword(tektiteToolMaterial), "tektite_Sword");
        tektiteGreatsword = initItem(new NyxToolTektiteGreatsword(tektiteGreatswordToolMaterial), "tektite_greatsword");
        tektiteHelmet = initItem(new NyxItemArmor(tektiteArmorMaterial, 0, EntityEquipmentSlot.HEAD), "tektite_helmet");
        tektiteChestplate = initItem(new NyxItemArmor(tektiteArmorMaterial, 1, EntityEquipmentSlot.CHEST), "tektite_chestplate");
        tektiteLeggings = initItem(new NyxItemArmor(tektiteArmorMaterial, 2, EntityEquipmentSlot.LEGS), "tektite_leggings");
        tektiteBoots = initItem(new NyxItemArmor(tektiteArmorMaterial, 3, EntityEquipmentSlot.FEET), "tektite_boots");
        tektiteBow = initItem(new NyxItemBow(2500, 1.35F, 1.5F, 0.3F, 0.5F, EnumRarity.EPIC, Ingredient.fromStacks(new ItemStack(tektiteGemCluster))), "tektite_bow");
        celestialWarhammer = initItem(new NyxToolCelestialWarhammer(celestialWarhammerToolMaterial), "celestial_warhammer");

        fallenStarBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial), "fallen_star_beam_sword");
        meteoriteBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial), "meteorite_beam_sword");
        frezariteBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial), "frezarite_beam_sword");
        kreknoriteBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial), "kreknorite_beam_sword");
        tektiteBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial), "tektite_beam_sword");
        cyberCrystalBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial), "cyber_crystal_beam_sword");

        // Mod Integration
        if (Loader.isModLoaded("mowziesmobs")) {
            lunarAxe = initItem(new NyxToolLunarAxe(lunarAxeToolMaterial, 14.0F, 1.6F), "lunar_axe");
            solarSword = initItem(new NyxToolSolarSword(solarSwordToolMaterial), "solar_sword");

            // Special Mowzies' Mobs bauble
            if (Loader.isModLoaded("baubles")) {
                earthTrinket = initItem(new NyxBaubleEarthTrinket(), "earth_trinket");
            }
        }

        MOD_ITEMS.forEach(event.getRegistry()::register);
    }

    public static Item initItem(Item item, String name) {
        item.setRegistryName(new ResourceLocation(Nyx.ID, name));
        item.setTranslationKey(Nyx.ID + "." + item.getRegistryName().getPath());
        item.setCreativeTab(NyxRegistry.CREATIVE_TAB);
        MOD_ITEMS.add(item);
        return item;
    }

    public static void initMaterials() {
        meteoriteToolMaterial = EnumHelper.addToolMaterial("meteorite", 4, 2032, 9.0F, 4.0F, 15).setRepairItem(new ItemStack(NyxItems.meteoriteIngot));
        meteoriteArmorMaterial = EnumHelper.addArmorMaterial("meteorite", Nyx.ID + ":meteorite", 37, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F).setRepairItem(new ItemStack(NyxItems.meteoriteIngot));
        ancientMeteoriteArmorMaterial = EnumHelper.addArmorMaterial("ancient_meteorite", Nyx.ID + ":ancient_meteorite", 37, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F).setRepairItem(new ItemStack(NyxItems.meteoriteIngot));

        frezariteToolMaterial = EnumHelper.addToolMaterial("frezarite", 5, 3000, 10.0F, 5.0F, 18).setRepairItem(new ItemStack(NyxItems.frezariteIngot));
        frezariteArmorMaterial = EnumHelper.addArmorMaterial("frezarite", Nyx.ID + ":frezarite", 48, new int[]{4, 7, 9, 4}, 18, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.5F).setRepairItem(new ItemStack(NyxItems.frezariteIngot));
        ancientFrezariteArmorMaterial = EnumHelper.addArmorMaterial("ancient_frezarite", Nyx.ID + ":ancient_frezarite", 48, new int[]{4, 7, 9, 4}, 18, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 3.5F).setRepairItem(new ItemStack(NyxItems.frezariteIngot));

        kreknoriteToolMaterial = EnumHelper.addToolMaterial("kreknorite", 5, 3000, 10.0F, 5.0F, 18).setRepairItem(new ItemStack(NyxItems.kreknoriteIngot));
        kreknoriteArmorMaterial = EnumHelper.addArmorMaterial("kreknorite", Nyx.ID + ":kreknorite", 48, new int[]{4, 7, 9, 4}, 18, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5F).setRepairItem(new ItemStack(NyxItems.kreknoriteIngot));
        ancientKreknoriteArmorMaterial = EnumHelper.addArmorMaterial("ancient_kreknorite", Nyx.ID + ":ancient_kreknorite", 48, new int[]{4, 7, 9, 4}, 18, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.5F).setRepairItem(new ItemStack(NyxItems.kreknoriteIngot));

        tektiteToolMaterial = EnumHelper.addToolMaterial("tektite", 5, 3500, 12.0F, 6.0F, 22).setRepairItem(new ItemStack(NyxItems.tektiteGemCluster));
        tektiteArmorMaterial = EnumHelper.addArmorMaterial("tektite", Nyx.ID + ":tektite", 54, new int[]{4, 8, 10, 4}, 22, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 4.0F).setRepairItem(new ItemStack(NyxItems.tektiteGemCluster));

        tektiteGreatswordToolMaterial = EnumHelper.addToolMaterial("tektite_greatsword", 5, 3500, 15.0F, 8.0F, 22).setRepairItem(new ItemStack(NyxItems.tektiteGemCluster));
        celestialWarhammerToolMaterial = EnumHelper.addToolMaterial("celestial_warhammer", 5, 5500, 15.0F, 12.0F, 30).setRepairItem(new ItemStack(NyxItems.fallenStar));
        lunarAxeToolMaterial = EnumHelper.addToolMaterial("lunar_axe", 5, 3500, 15.0F, 10.0F, 30).setRepairItem(new ItemStack(NyxItems.frezariteIngot));
        solarSwordToolMaterial = EnumHelper.addToolMaterial("solar_sword", 5, 3500, 15.0F, 10.0F, 30).setRepairItem(new ItemStack(NyxItems.kreknoriteIngot));

        beamSwordToolMaterial = EnumHelper.addToolMaterial("beam_sword", 5, 3500, 15.0F, 6.0F, 30).setRepairItem(new ItemStack(NyxBlocks.cyberCrystal));
    }
}

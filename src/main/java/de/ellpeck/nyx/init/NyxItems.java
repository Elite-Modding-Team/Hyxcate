package de.ellpeck.nyx.init;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.items.*;
import de.ellpeck.nyx.items.tools.*;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
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
    public static Item frezaritePickaxe;
    public static Item frezariteAxe;
    public static Item frezariteShovel;
    public static Item frezariteHoe;
    public static Item frezariteSword;
    public static Item frezariteHelmet;
    public static Item frezariteChestplate;
    public static Item frezariteLeggings;
    public static Item frezariteBoots;
    public static Item kreknoritePickaxe;
    public static Item kreknoriteAxe;
    public static Item kreknoriteShovel;
    public static Item kreknoriteHoe;
    public static Item kreknoriteSword;
    public static Item kreknoriteHelmet;
    public static Item kreknoriteChestplate;
    public static Item kreknoriteLeggings;
    public static Item kreknoriteBoots;
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
        celestialEmblem = initItem(new NyxItem(EnumRarity.EPIC), "celestial_emblem");
        meteorFinder = initItem(new NyxToolMeteorDetector(), "meteor_detector");

        meteoritePickaxe = initItem(new NyxItemPickaxe(meteoriteToolMaterial, 1.2D, 0.0D, EnumRarity.RARE), "meteorite_pickaxe");
        meteoriteAxe = initItem(new NyxItemAxe(meteoriteToolMaterial, 10.0F, 1.1F, 0.0D, EnumRarity.EPIC), "meteorite_axe");
        meteoriteShovel = initItem(new NyxItemSpade(meteoriteToolMaterial, 1.0D, 0.0D, EnumRarity.RARE), "meteorite_shovel");
        meteoriteHoe = initItem(new NyxItemHoe(meteoriteToolMaterial, 0.0D, EnumRarity.RARE), "meteorite_hoe");
        meteoriteSword = initItem(new NyxItemSword(meteoriteToolMaterial, 1.6D, 0.0D, EnumRarity.RARE), "meteorite_sword");
        meteoriteHelmet = initItem(new NyxItemArmor(meteoriteArmorMaterial, 0, EntityEquipmentSlot.HEAD, EnumRarity.RARE), "meteorite_helmet");
        meteoriteChestplate = initItem(new NyxItemArmor(meteoriteArmorMaterial, 1, EntityEquipmentSlot.CHEST, EnumRarity.RARE), "meteorite_chestplate");
        meteoriteLeggings = initItem(new NyxItemArmor(meteoriteArmorMaterial, 2, EntityEquipmentSlot.LEGS, EnumRarity.RARE), "meteorite_leggings");
        meteoriteBoots = initItem(new NyxItemArmor(meteoriteArmorMaterial, 3, EntityEquipmentSlot.FEET, EnumRarity.RARE), "meteorite_boots");

        frezaritePickaxe = initItem(new NyxItemPickaxe(frezariteToolMaterial, 1.2D, 0.0D, EnumRarity.EPIC), "frezarite_pickaxe");
        frezariteAxe = initItem(new NyxItemAxe(frezariteToolMaterial, 11.0F, 1.2F, 0.0D, EnumRarity.EPIC), "frezarite_axe");
        frezariteShovel = initItem(new NyxItemSpade(frezariteToolMaterial, 1.0D, 0.0D, EnumRarity.EPIC), "frezarite_shovel");
        frezariteHoe = initItem(new NyxItemHoe(frezariteToolMaterial, 0.0D, EnumRarity.EPIC), "frezarite_hoe");
        frezariteSword = initItem(new NyxItemSword(frezariteToolMaterial, 1.6D, 0.0D, EnumRarity.EPIC), "frezarite_sword");
        frezariteHelmet = initItem(new NyxItemArmor(frezariteArmorMaterial, 0, EntityEquipmentSlot.HEAD, EnumRarity.EPIC), "frezarite_helmet");
        frezariteChestplate = initItem(new NyxItemArmor(frezariteArmorMaterial, 1, EntityEquipmentSlot.CHEST, EnumRarity.EPIC), "frezarite_chestplate");
        frezariteLeggings = initItem(new NyxItemArmor(frezariteArmorMaterial, 2, EntityEquipmentSlot.LEGS, EnumRarity.EPIC), "frezarite_leggings");
        frezariteBoots = initItem(new NyxItemArmor(frezariteArmorMaterial, 3, EntityEquipmentSlot.FEET, EnumRarity.EPIC), "frezarite_boots");

        kreknoritePickaxe = initItem(new NyxItemPickaxe(kreknoriteToolMaterial, 1.2D, 0.0D, EnumRarity.EPIC), "kreknorite_pickaxe");
        kreknoriteAxe = initItem(new NyxItemAxe(kreknoriteToolMaterial, 11.0F, 1.2F, 0.0D, EnumRarity.EPIC), "kreknorite_axe");
        kreknoriteShovel = initItem(new NyxItemSpade(kreknoriteToolMaterial, 1.0D, 0.0D, EnumRarity.EPIC), "kreknorite_shovel");
        kreknoriteHoe = initItem(new NyxItemHoe(kreknoriteToolMaterial, 0.0D, EnumRarity.EPIC), "kreknorite_hoe");
        kreknoriteSword = initItem(new NyxItemSword(kreknoriteToolMaterial, 1.6D, 0.0D, EnumRarity.EPIC), "kreknorite_sword");
        kreknoriteHelmet = initItem(new NyxItemArmor(kreknoriteArmorMaterial, 0, EntityEquipmentSlot.HEAD, EnumRarity.EPIC), "kreknorite_helmet");
        kreknoriteChestplate = initItem(new NyxItemArmor(kreknoriteArmorMaterial, 1, EntityEquipmentSlot.CHEST, EnumRarity.EPIC), "kreknorite_chestplate");
        kreknoriteLeggings = initItem(new NyxItemArmor(kreknoriteArmorMaterial, 2, EntityEquipmentSlot.LEGS, EnumRarity.EPIC), "kreknorite_leggings");
        kreknoriteBoots = initItem(new NyxItemArmor(kreknoriteArmorMaterial, 3, EntityEquipmentSlot.FEET, EnumRarity.EPIC), "kreknorite_boots");

        tektitePickaxe = initItem(new NyxItemPickaxe(tektiteToolMaterial, 1.2D, 0.2D, EnumRarity.EPIC), "tektite_pickaxe");
        tektiteAxe = initItem(new NyxItemAxe(tektiteToolMaterial, 12, 1.2F, 0.2D, EnumRarity.EPIC), "tektite_axe");
        tektiteShovel = initItem(new NyxItemSpade(tektiteToolMaterial, 1.0D, 0.2D, EnumRarity.EPIC), "tektite_shovel");
        tektiteHoe = initItem(new NyxItemHoe(tektiteToolMaterial, 0.2D, EnumRarity.EPIC), "tektite_hoe");
        tektiteSword = initItem(new NyxItemSword(tektiteToolMaterial, 1.6D, 0.2D, EnumRarity.EPIC), "tektite_Sword");
        tektiteGreatsword = initItem(new NyxToolTektiteGreatsword(tektiteGreatswordToolMaterial, 1.0D, 0.2D, EnumRarity.EPIC), "tektite_greatsword");
        tektiteHelmet = initItem(new NyxItemERArmor(tektiteArmorMaterial, 0, EntityEquipmentSlot.HEAD, 0.15D, EnumRarity.EPIC), "tektite_helmet");
        tektiteChestplate = initItem(new NyxItemERArmor(tektiteArmorMaterial, 1, EntityEquipmentSlot.CHEST, 0.30D, EnumRarity.EPIC), "tektite_chestplate");
        tektiteLeggings = initItem(new NyxItemERArmor(tektiteArmorMaterial, 2, EntityEquipmentSlot.LEGS, 0.25D, EnumRarity.EPIC), "tektite_leggings");
        tektiteBoots = initItem(new NyxItemERArmor(tektiteArmorMaterial, 3, EntityEquipmentSlot.FEET, 0.10D, EnumRarity.EPIC), "tektite_boots");
        tektiteBow = initItem(new NyxItemBow(2500, 1.35F, 1.5F, 0.3F, 0.5F, EnumRarity.EPIC, Ingredient.fromStacks(new ItemStack(tektiteGemCluster))), "tektite_bow");
        celestialWarhammer = initItem(new NyxToolCelestialWarhammer(celestialWarhammerToolMaterial, 0.8D, 0.0D, EnumRarity.EPIC), "celestial_warhammer");

        fallenStarBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial, 1.4D, 0.0D, EnumRarity.EPIC), "fallen_star_beam_sword");
        meteoriteBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial, 1.4D, 0.0D, EnumRarity.EPIC), "meteorite_beam_sword");
        frezariteBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial, 1.4D, 0.0D, EnumRarity.EPIC), "frezarite_beam_sword");
        kreknoriteBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial, 1.4D, 0.0D, EnumRarity.EPIC), "kreknorite_beam_sword");
        tektiteBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial, 1.4D, 0.0D, EnumRarity.EPIC), "tektite_beam_sword");
        cyberCrystalBeamSword = initItem(new NyxToolBeamSword(beamSwordToolMaterial, 1.4D, 0.0D, EnumRarity.EPIC), "cyber_crystal_beam_sword");

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
        meteoriteArmorMaterial = EnumHelper.addArmorMaterial("meteorite", Nyx.ID + ":meteorite", 37, new int[]{3, 6, 8, 3}, 15, NyxSoundEvents.equipMetallic.getSoundEvent(), 3.0F).setRepairItem(new ItemStack(NyxItems.meteoriteIngot));

        frezariteToolMaterial = EnumHelper.addToolMaterial("frezarite", 5, 3000, 10.0F, 5.0F, 18).setRepairItem(new ItemStack(NyxItems.frezariteIngot));
        frezariteArmorMaterial = EnumHelper.addArmorMaterial("frezarite", Nyx.ID + ":frezarite", 48, new int[]{4, 7, 9, 4}, 18, NyxSoundEvents.equipCrystalline.getSoundEvent(), 3.5F).setRepairItem(new ItemStack(NyxItems.frezariteIngot));

        kreknoriteToolMaterial = EnumHelper.addToolMaterial("kreknorite", 5, 3000, 10.0F, 5.0F, 18).setRepairItem(new ItemStack(NyxItems.kreknoriteIngot));
        kreknoriteArmorMaterial = EnumHelper.addArmorMaterial("kreknorite", Nyx.ID + ":kreknorite", 48, new int[]{4, 7, 9, 4}, 18, NyxSoundEvents.equipMetallic.getSoundEvent(), 3.5F).setRepairItem(new ItemStack(NyxItems.kreknoriteIngot));

        tektiteToolMaterial = EnumHelper.addToolMaterial("tektite", 5, 3500, 12.0F, 6.0F, 22).setRepairItem(new ItemStack(NyxItems.tektiteGemCluster));
        tektiteArmorMaterial = EnumHelper.addArmorMaterial("tektite", Nyx.ID + ":tektite", 54, new int[]{4, 8, 10, 4}, 22, NyxSoundEvents.equipCrystalline.getSoundEvent(), 4.0F).setRepairItem(new ItemStack(NyxItems.tektiteGemCluster));

        tektiteGreatswordToolMaterial = EnumHelper.addToolMaterial("tektite_greatsword", 5, 3500, 15.0F, 8.0F, 22).setRepairItem(new ItemStack(NyxItems.tektiteGemCluster));
        celestialWarhammerToolMaterial = EnumHelper.addToolMaterial("celestial_warhammer", 5, 5500, 15.0F, 12.0F, 30).setRepairItem(new ItemStack(NyxItems.fallenStar));
        beamSwordToolMaterial = EnumHelper.addToolMaterial("beam_sword", 5, 3500, 15.0F, 6.0F, 30).setRepairItem(new ItemStack(NyxBlocks.cyberCrystal));
    }
}

package mod.emt.hyxcate.init;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.item.*;
import mod.emt.hyxcate.item.tool.HyxcateToolBeamSword;
import mod.emt.hyxcate.item.tool.HyxcateToolCelestialWarhammer;
import mod.emt.hyxcate.item.tool.HyxcateToolMeteorDetector;
import mod.emt.hyxcate.item.tool.HyxcateToolTektiteGreatsword;
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

@Mod.EventBusSubscriber(modid = Hyxcate.ID)
public class HyxcateItems {
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
        fallenStar = initItem(new HyxcateItemFallenStar(), "fallen_star");
        tektiteGemCluster = initItem(new HyxcateItem(EnumRarity.EPIC), "tektite_gem_cluster");
        meteoriteShard = initItem(new HyxcateItem(EnumRarity.RARE), "meteorite_shard");
        meteoriteIngot = initItem(new HyxcateItem(EnumRarity.RARE), "meteorite_ingot");
        frezariteCrystal = initItem(new HyxcateItem(EnumRarity.EPIC), "frezarite_crystal");
        frezariteIngot = initItem(new HyxcateItem(EnumRarity.EPIC), "frezarite_ingot");
        kreknoriteShard = initItem(new HyxcateItem(EnumRarity.EPIC), "kreknorite_shard");
        kreknoriteIngot = initItem(new HyxcateItem(EnumRarity.EPIC), "kreknorite_ingot");
        celestialEmblem = initItem(new HyxcateItem(EnumRarity.EPIC), "celestial_emblem");

        if (HyxcateConfig.MASTER_SWITCHES.meteorDetectorEnabled) {
            meteorFinder = initItem(new HyxcateToolMeteorDetector(), "meteor_detector");
        }

        if (HyxcateConfig.MASTER_SWITCHES.meteorGearEnabled) {
            meteoritePickaxe = initItem(new HyxcateItemPickaxe(meteoriteToolMaterial, 1.2D, 1, 0.0D, EnumRarity.RARE), "meteorite_pickaxe");
            meteoriteAxe = initItem(new HyxcateItemAxe(meteoriteToolMaterial, 10.0F, 1.1F, 1, 0.0D, EnumRarity.RARE), "meteorite_axe");
            meteoriteShovel = initItem(new HyxcateItemShovel(meteoriteToolMaterial, 1.0D, 1, 0.0D, EnumRarity.RARE), "meteorite_shovel");
            meteoriteHoe = initItem(new HyxcateItemHoe(meteoriteToolMaterial, 1, 0.0D, EnumRarity.RARE), "meteorite_hoe");
            meteoriteSword = initItem(new HyxcateItemSword(meteoriteToolMaterial, 1.6D, 1, 0.0D, EnumRarity.RARE), "meteorite_sword");
            meteoriteHelmet = initItem(new HyxcateItemArmor(meteoriteArmorMaterial, 0, EntityEquipmentSlot.HEAD, 1, 0.0D, EnumRarity.RARE), "meteorite_helmet");
            meteoriteChestplate = initItem(new HyxcateItemArmor(meteoriteArmorMaterial, 1, EntityEquipmentSlot.CHEST, 1, 0.0D, EnumRarity.RARE), "meteorite_chestplate");
            meteoriteLeggings = initItem(new HyxcateItemArmor(meteoriteArmorMaterial, 2, EntityEquipmentSlot.LEGS, 1, 0.0D, EnumRarity.RARE), "meteorite_leggings");
            meteoriteBoots = initItem(new HyxcateItemArmor(meteoriteArmorMaterial, 3, EntityEquipmentSlot.FEET, 1, 0.0D, EnumRarity.RARE), "meteorite_boots");

            frezaritePickaxe = initItem(new HyxcateItemPickaxe(frezariteToolMaterial, 1.2D, 0, 0.0D, EnumRarity.EPIC), "frezarite_pickaxe");
            frezariteAxe = initItem(new HyxcateItemAxe(frezariteToolMaterial, 11.0F, 1.2F, 0, 0.0D, EnumRarity.EPIC), "frezarite_axe");
            frezariteShovel = initItem(new HyxcateItemShovel(frezariteToolMaterial, 1.0D, 0, 0.0D, EnumRarity.EPIC), "frezarite_shovel");
            frezariteHoe = initItem(new HyxcateItemHoe(frezariteToolMaterial, 0, 0.0D, EnumRarity.EPIC), "frezarite_hoe");
            frezariteSword = initItem(new HyxcateItemSword(frezariteToolMaterial, 1.6D, 0, 0.0D, EnumRarity.EPIC), "frezarite_sword");
            frezariteHelmet = initItem(new HyxcateItemArmor(frezariteArmorMaterial, 0, EntityEquipmentSlot.HEAD, 0, 0.0D, EnumRarity.EPIC), "frezarite_helmet");
            frezariteChestplate = initItem(new HyxcateItemArmor(frezariteArmorMaterial, 1, EntityEquipmentSlot.CHEST, 0, 0.0D, EnumRarity.EPIC), "frezarite_chestplate");
            frezariteLeggings = initItem(new HyxcateItemArmor(frezariteArmorMaterial, 2, EntityEquipmentSlot.LEGS, 0, 0.0D, EnumRarity.EPIC), "frezarite_leggings");
            frezariteBoots = initItem(new HyxcateItemArmor(frezariteArmorMaterial, 3, EntityEquipmentSlot.FEET, 0, 0.0D, EnumRarity.EPIC), "frezarite_boots");

            kreknoritePickaxe = initItem(new HyxcateItemPickaxe(kreknoriteToolMaterial, 1.2D, 0, 0.0D, EnumRarity.EPIC), "kreknorite_pickaxe");
            kreknoriteAxe = initItem(new HyxcateItemAxe(kreknoriteToolMaterial, 11.0F, 1.2F, 0, 0.0D, EnumRarity.EPIC), "kreknorite_axe");
            kreknoriteShovel = initItem(new HyxcateItemShovel(kreknoriteToolMaterial, 1.0D, 0, 0.0D, EnumRarity.EPIC), "kreknorite_shovel");
            kreknoriteHoe = initItem(new HyxcateItemHoe(kreknoriteToolMaterial, 0, 0.0D, EnumRarity.EPIC), "kreknorite_hoe");
            kreknoriteSword = initItem(new HyxcateItemSword(kreknoriteToolMaterial, 1.6D, 0, 0.0D, EnumRarity.EPIC), "kreknorite_sword");
            kreknoriteHelmet = initItem(new HyxcateItemArmor(kreknoriteArmorMaterial, 0, EntityEquipmentSlot.HEAD, 0, 0.0D, EnumRarity.EPIC), "kreknorite_helmet");
            kreknoriteChestplate = initItem(new HyxcateItemArmor(kreknoriteArmorMaterial, 1, EntityEquipmentSlot.CHEST, 0, 0.0D, EnumRarity.EPIC), "kreknorite_chestplate");
            kreknoriteLeggings = initItem(new HyxcateItemArmor(kreknoriteArmorMaterial, 2, EntityEquipmentSlot.LEGS, 0, 0.0D, EnumRarity.EPIC), "kreknorite_leggings");
            kreknoriteBoots = initItem(new HyxcateItemArmor(kreknoriteArmorMaterial, 3, EntityEquipmentSlot.FEET, 0, 0.0D, EnumRarity.EPIC), "kreknorite_boots");

            tektitePickaxe = initItem(new HyxcateItemPickaxe(tektiteToolMaterial, 1.2D, 0, 0.2D, EnumRarity.EPIC), "tektite_pickaxe");
            tektiteAxe = initItem(new HyxcateItemAxe(tektiteToolMaterial, 12, 1.2F, 0, 0.2D, EnumRarity.EPIC), "tektite_axe");
            tektiteShovel = initItem(new HyxcateItemShovel(tektiteToolMaterial, 1.0D, 0, 0.2D, EnumRarity.EPIC), "tektite_shovel");
            tektiteHoe = initItem(new HyxcateItemHoe(tektiteToolMaterial, 0, 0.2D, EnumRarity.EPIC), "tektite_hoe");
            tektiteSword = initItem(new HyxcateItemSword(tektiteToolMaterial, 1.6D, 0, 0.2D, EnumRarity.EPIC), "tektite_Sword");
            tektiteGreatsword = initItem(new HyxcateToolTektiteGreatsword(tektiteGreatswordToolMaterial, 1.0D, 0, 0.2D, EnumRarity.EPIC), "tektite_greatsword");
            tektiteHelmet = initItem(new HyxcateItemArmor(tektiteArmorMaterial, 0, EntityEquipmentSlot.HEAD, 0, 0.15D, EnumRarity.EPIC), "tektite_helmet");
            tektiteChestplate = initItem(new HyxcateItemArmor(tektiteArmorMaterial, 1, EntityEquipmentSlot.CHEST, 0, 0.30D, EnumRarity.EPIC), "tektite_chestplate");
            tektiteLeggings = initItem(new HyxcateItemArmor(tektiteArmorMaterial, 2, EntityEquipmentSlot.LEGS, 0, 0.25D, EnumRarity.EPIC), "tektite_leggings");
            tektiteBoots = initItem(new HyxcateItemArmor(tektiteArmorMaterial, 3, EntityEquipmentSlot.FEET, 0, 0.10D, EnumRarity.EPIC), "tektite_boots");
            tektiteBow = initItem(new HyxcateItemBow(2500, 1.35F, 1.5F, 0.3F, 0.5F, EnumRarity.EPIC, Ingredient.fromStacks(new ItemStack(tektiteGemCluster))), "tektite_bow");
        }

        if (HyxcateConfig.MASTER_SWITCHES.celestialWarhammerEnabled) {
            celestialWarhammer = initItem(new HyxcateToolCelestialWarhammer(celestialWarhammerToolMaterial, 0.8D, 0, 0.0D, EnumRarity.EPIC), "celestial_warhammer");
        }

        if (HyxcateConfig.MASTER_SWITCHES.beamSwordsEnabled) {
            fallenStarBeamSword = initItem(new HyxcateToolBeamSword(beamSwordToolMaterial, 1.4D, 0, 0.0D, EnumRarity.EPIC), "fallen_star_beam_sword");
            meteoriteBeamSword = initItem(new HyxcateToolBeamSword(beamSwordToolMaterial, 1.4D, 0, 0.0D, EnumRarity.EPIC), "meteorite_beam_sword");
            frezariteBeamSword = initItem(new HyxcateToolBeamSword(beamSwordToolMaterial, 1.4D, 0, 0.0D, EnumRarity.EPIC), "frezarite_beam_sword");
            kreknoriteBeamSword = initItem(new HyxcateToolBeamSword(beamSwordToolMaterial, 1.4D, 0, 0.0D, EnumRarity.EPIC), "kreknorite_beam_sword");
            tektiteBeamSword = initItem(new HyxcateToolBeamSword(beamSwordToolMaterial, 1.4D, 0, 0.0D, EnumRarity.EPIC), "tektite_beam_sword");
            cyberCrystalBeamSword = initItem(new HyxcateToolBeamSword(beamSwordToolMaterial, 1.4D, 0, 0.0D, EnumRarity.EPIC), "cyber_crystal_beam_sword");
        }

        MOD_ITEMS.forEach(event.getRegistry()::register);
    }

    public static Item initItem(Item item, String name) {
        item.setRegistryName(new ResourceLocation(Hyxcate.ID, name));
        item.setTranslationKey(Hyxcate.ID + "." + item.getRegistryName().getPath());
        item.setCreativeTab(HyxcateRegistry.CREATIVE_TAB);
        MOD_ITEMS.add(item);
        return item;
    }

    public static void initMaterials() {
        meteoriteToolMaterial = EnumHelper.addToolMaterial("HYXCATE_METEORITE", 4, 2500, 9.0F, 4.0F, 18);
        meteoriteArmorMaterial = EnumHelper.addArmorMaterial("HYXCATE_METEORITE", Hyxcate.ID + ":meteorite", 42, new int[]{4, 7, 9, 4}, 18, HyxcateSoundEvents.EQUIP_METALLIC.getSoundEvent(), 3.0F);

        frezariteToolMaterial = EnumHelper.addToolMaterial("HYXCATE_FREZARITE", 5, 3000, 10.0F, 5.0F, 20);
        frezariteArmorMaterial = EnumHelper.addArmorMaterial("HYXCATE_FREZARITE", Hyxcate.ID + ":frezarite", 48, new int[]{5, 8, 10, 5}, 20, HyxcateSoundEvents.EQUIP_CRYSTALLINE.getSoundEvent(), 4.0F);

        kreknoriteToolMaterial = EnumHelper.addToolMaterial("HYXCATE_KREKNORITE", 5, 3000, 10.0F, 5.0F, 20);
        kreknoriteArmorMaterial = EnumHelper.addArmorMaterial("HYXCATE_KREKNORITE", Hyxcate.ID + ":kreknorite", 48, new int[]{5, 8, 10, 5}, 20, HyxcateSoundEvents.EQUIP_METALLIC.getSoundEvent(), 4.0F);

        tektiteToolMaterial = EnumHelper.addToolMaterial("HYXCATE_TEKTITE", 5, 3500, 12.0F, 6.0F, 22);
        tektiteArmorMaterial = EnumHelper.addArmorMaterial("HYXCATE_TEKTITE", Hyxcate.ID + ":tektite", 54, new int[]{6, 9, 11, 6}, 22, HyxcateSoundEvents.EQUIP_CRYSTALLINE.getSoundEvent(), 4.0F);

        tektiteGreatswordToolMaterial = EnumHelper.addToolMaterial("HYXCATE_TEKTITE_GREATSWORD", 5, 3500, 15.0F, 8.0F, 22);
        celestialWarhammerToolMaterial = EnumHelper.addToolMaterial("HYXCATE_CELESTIAL_WARHAMMER", 5, 5500, 15.0F, 12.0F, 30);
        beamSwordToolMaterial = EnumHelper.addToolMaterial("HYXCATE_BEAM_SWORD", 5, 3500, 15.0F, 8.0F, 30);
    }

    public static void setRepairItems() {
        meteoriteToolMaterial.setRepairItem(new ItemStack(HyxcateItems.meteoriteIngot));
        meteoriteArmorMaterial.setRepairItem(new ItemStack(HyxcateItems.meteoriteIngot));

        frezariteToolMaterial.setRepairItem(new ItemStack(HyxcateItems.frezariteIngot));
        frezariteArmorMaterial.setRepairItem(new ItemStack(HyxcateItems.frezariteIngot));

        kreknoriteToolMaterial.setRepairItem(new ItemStack(HyxcateItems.kreknoriteIngot));
        kreknoriteArmorMaterial.setRepairItem(new ItemStack(HyxcateItems.kreknoriteIngot));

        tektiteToolMaterial.setRepairItem(new ItemStack(HyxcateItems.tektiteGemCluster));
        tektiteArmorMaterial.setRepairItem(new ItemStack(HyxcateItems.tektiteGemCluster));

        tektiteGreatswordToolMaterial.setRepairItem(new ItemStack(HyxcateItems.tektiteGemCluster));
        celestialWarhammerToolMaterial.setRepairItem(new ItemStack(HyxcateItems.fallenStar));
        beamSwordToolMaterial.setRepairItem(new ItemStack(HyxcateBlocks.cyberCrystal));
    }
}

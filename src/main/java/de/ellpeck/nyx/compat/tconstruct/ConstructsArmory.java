package de.ellpeck.nyx.compat.tconstruct;

import c4.conarm.common.armor.traits.ArmorTraits;
import c4.conarm.lib.materials.*;
import c4.conarm.lib.traits.AbstractArmorTrait;
import de.ellpeck.nyx.compat.tconstruct.traits.armor.*;
import slimeknights.tconstruct.library.TinkerRegistry;

public class ConstructsArmory {
    // These traits are for armor and not tools
    public static final AbstractArmorTrait ASTRAL_PLATING = new TraitAstralPlating();
    public static final AbstractArmorTrait FREZARITES_WARD = new TraitFrezaritesWard();
    public static final AbstractArmorTrait GLEAMING_ARMOR = new TraitGleamingArmor();
    public static final AbstractArmorTrait GREATER_MAGNETIC_ARMOR = new TraitGreaterMagneticArmor(1);
    public static final AbstractArmorTrait GREATER_MAGNETIC_ARMOR2 = new TraitGreaterMagneticArmor(2);
    public static final AbstractArmorTrait HYPERCHARGE_ARMOR = new TraitHyperchargeArmor();
    public static final AbstractArmorTrait KREKNORITES_WARD = new TraitKreknoritesWard();
    public static final AbstractArmorTrait STAR_SHIELD_ARMOR = new TraitStarShieldArmor();
    public static final AbstractArmorTrait STUNLOCK_ARMOR = new TraitStunlockArmor();
    public static final AbstractArmorTrait SUPERCHARGE_ARMOR = new TraitSuperchargeArmor();

    // TODO: Rework traits for Cyber Crystal, Frezarite, Kreknorite, Meteorite, Tektite
    // Materials are already registered in the Tinkers' Construct class, this only adds support for Construct's Armory armor
    public static void registerToolMaterials() {
        TinkerRegistry.addMaterialStats(TinkersConstruct.FALLEN_STAR,
                new CoreMaterialStats(13.0F, 16.0F),
                new PlatesMaterialStats(0.9F, 5.0F, 2.0F),
                new TrimMaterialStats(4.0F));
        ArmorMaterials.addArmorTrait(TinkersConstruct.FALLEN_STAR, STAR_SHIELD_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FALLEN_STAR, ArmorTraits.alien, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FALLEN_STAR, ArmorTraits.alien, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FALLEN_STAR, ArmorTraits.alien, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstruct.CYBER_CRYSTAL,
                new CoreMaterialStats(23.0F, 22.5F),
                new PlatesMaterialStats(1.6F, 16.0F, 6.0F),
                new TrimMaterialStats(18.0F));
        ArmorMaterials.addArmorTrait(TinkersConstruct.CYBER_CRYSTAL, GLEAMING_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.CYBER_CRYSTAL, HYPERCHARGE_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.CYBER_CRYSTAL, ArmorTraits.alien, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.CYBER_CRYSTAL, HYPERCHARGE_ARMOR, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.CYBER_CRYSTAL, ArmorTraits.alien, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.CYBER_CRYSTAL, HYPERCHARGE_ARMOR, ArmorMaterialType.TRIM);
        ArmorMaterials.addArmorTrait(TinkersConstruct.CYBER_CRYSTAL, ArmorTraits.alien, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstruct.TEKTITE,
                new CoreMaterialStats(23.0F, 22.5F),
                new PlatesMaterialStats(1.55F, 15.0F, 5.5F),
                new TrimMaterialStats(18.0F));
        ArmorMaterials.addArmorTrait(TinkersConstruct.TEKTITE, STUNLOCK_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.TEKTITE, SUPERCHARGE_ARMOR, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.TEKTITE, ArmorTraits.alien, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.TEKTITE, SUPERCHARGE_ARMOR, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.TEKTITE, ArmorTraits.alien, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.TEKTITE, SUPERCHARGE_ARMOR, ArmorMaterialType.TRIM);
        ArmorMaterials.addArmorTrait(TinkersConstruct.TEKTITE, ArmorTraits.alien, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstruct.METEORITE,
                new CoreMaterialStats(19.5F, 19.0F),
                new PlatesMaterialStats(1.25F, 12.0F, 4.0F),
                new TrimMaterialStats(16.0F));
        ArmorMaterials.addArmorTrait(TinkersConstruct.METEORITE, GREATER_MAGNETIC_ARMOR2, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.METEORITE, ArmorTraits.alien, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.METEORITE, GREATER_MAGNETIC_ARMOR, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.METEORITE, ArmorTraits.alien, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.METEORITE, GREATER_MAGNETIC_ARMOR, ArmorMaterialType.TRIM);
        ArmorMaterials.addArmorTrait(TinkersConstruct.METEORITE, ArmorTraits.alien, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstruct.FREZARITE,
                new CoreMaterialStats(25.0F, 24.5F),
                new PlatesMaterialStats(1.5F, 18.0F, 5.0F),
                new TrimMaterialStats(20.0F));
        ArmorMaterials.addArmorTrait(TinkersConstruct.FREZARITE, ASTRAL_PLATING, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FREZARITE, FREZARITES_WARD, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FREZARITE, ArmorTraits.alien, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FREZARITE, FREZARITES_WARD, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FREZARITE, ArmorTraits.alien, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FREZARITE, FREZARITES_WARD, ArmorMaterialType.TRIM);
        ArmorMaterials.addArmorTrait(TinkersConstruct.FREZARITE, ArmorTraits.alien, ArmorMaterialType.TRIM);

        TinkerRegistry.addMaterialStats(TinkersConstruct.KREKNORITE,
                new CoreMaterialStats(25.0F, 24.5F),
                new PlatesMaterialStats(1.5F, 18.0F, 5.0F),
                new TrimMaterialStats(20.0F));
        ArmorMaterials.addArmorTrait(TinkersConstruct.KREKNORITE, ASTRAL_PLATING, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.KREKNORITE, KREKNORITES_WARD, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.KREKNORITE, ArmorTraits.alien, ArmorMaterialType.CORE);
        ArmorMaterials.addArmorTrait(TinkersConstruct.KREKNORITE, KREKNORITES_WARD, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.KREKNORITE, ArmorTraits.alien, ArmorMaterialType.PLATES);
        ArmorMaterials.addArmorTrait(TinkersConstruct.KREKNORITE, KREKNORITES_WARD, ArmorMaterialType.TRIM);
        ArmorMaterials.addArmorTrait(TinkersConstruct.KREKNORITE, ArmorTraits.alien, ArmorMaterialType.TRIM);
    }
}

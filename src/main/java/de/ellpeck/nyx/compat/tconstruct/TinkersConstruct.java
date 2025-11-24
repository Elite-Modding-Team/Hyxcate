package de.ellpeck.nyx.compat.tconstruct;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.compat.tconstruct.traits.*;
import de.ellpeck.nyx.init.NyxBlocks;
import de.ellpeck.nyx.init.NyxItems;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import slimeknights.tconstruct.library.MaterialIntegration;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.fluid.FluidMolten;
import slimeknights.tconstruct.library.materials.*;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.smeltery.block.BlockMolten;
import slimeknights.tconstruct.tools.TinkerTraits;

public class TinkersConstruct {
    // These materials are used universally between tools and armor
    public static final Material FALLEN_STAR = new Material(Nyx.ID + "." + "fallen_star", 0xDFCE62);
    public static final Material FREZARITE = new Material(Nyx.ID + "." + "frezarite", 0x1AC5E1);
    public static final Material CYBER_CRYSTAL = new Material(Nyx.ID + "." + "cyber_crystal", 0x4F7694);
    public static final Material KREKNORITE = new Material(Nyx.ID + "." + "kreknorite", 0x8F0E0E);
    public static final Material METEORITE = new Material(Nyx.ID + "." + "meteorite", 0x554F75);
    public static final Material TEKTITE = new Material(Nyx.ID + "." + "tektite", 0xC82323);

    public static final FluidMolten FREZARITE_FLUID = new FluidMolten("frezarite", 0x1AC5E1);
    public static final FluidMolten KREKNORITE_FLUID = new FluidMolten("kreknorite", 0x8F0E0E);
    public static final FluidMolten METEORITE_FLUID = new FluidMolten("meteorite", 0x554F75);

    // These traits are for tools and not armor
    public static final AbstractTrait FEINT = new TraitFeint();
    public static final AbstractTrait FREZARITES_BANE = new TraitFrezaritesBane();
    public static final AbstractTrait GLACIER = new TraitGlacier();
    public static final AbstractTrait GLEAMING = new TraitGleaming();
    public static final AbstractTrait GREATER_MAGNETIC = new TraitGreaterMagnetic(1);
    public static final AbstractTrait GREATER_MAGNETIC2 = new TraitGreaterMagnetic(2);
    public static final AbstractTrait HYPERCHARGE = new TraitHypercharge();
    public static final AbstractTrait KREKNORITES_BANE = new TraitKreknoritesBane();
    public static final AbstractTrait STUNLOCK = new TraitStunlock();
    public static final AbstractTrait SUPERCHARGE = new TraitSupercharge();
    public static final AbstractTrait VOLCANO = new TraitVolcano();

    // TODO: Return bucket when picked from JEI
    public static void registerFluid(Fluid fluid) {
        FluidRegistry.addBucketForFluid(fluid);
        BlockMolten blockMolten = (BlockMolten) new BlockMolten(fluid).setRegistryName(Nyx.ID, "molten_" + fluid.getName());
        ItemBlock itemBlockMolten = (ItemBlock) new ItemBlock(blockMolten).setRegistryName(blockMolten.getRegistryName());
        ForgeRegistries.BLOCKS.register(blockMolten);
        ForgeRegistries.ITEMS.register(itemBlockMolten);
    }

    // TODO: Rework traits for Cyber Crystal, Frezarite, Kreknorite, Meteorite, Tektite
    public static void registerToolMaterials() {
        TinkerRegistry.addMaterialStats(FALLEN_STAR,
                new HeadMaterialStats(250, 8.00F, 4.50F, HarvestLevels.OBSIDIAN),
                new HandleMaterialStats(1.10F, 70),
                new ExtraMaterialStats(80),
                new BowMaterialStats(0.75F, 2.0F, 0.0F),
                new ArrowShaftMaterialStats(0.8F, 5));
        FALLEN_STAR.addTrait(FEINT, MaterialTypes.HEAD);
        FALLEN_STAR.addTrait(TinkerTraits.alien, MaterialTypes.HEAD);
        FALLEN_STAR.addTrait(TinkerTraits.alien);
        FALLEN_STAR.addTrait(TinkerTraits.endspeed, MaterialTypes.SHAFT);
        TinkerRegistry.integrate(new MaterialIntegration(FALLEN_STAR, null, "Star")).preInit();

        TinkerRegistry.addMaterialStats(CYBER_CRYSTAL,
                new HeadMaterialStats(1200, 14.00F, 8.00F, HarvestLevels.COBALT),
                new HandleMaterialStats(1.60F, 250),
                new ExtraMaterialStats(375),
                new BowMaterialStats(0.65F, 1.7F, 10.0F));
        CYBER_CRYSTAL.addTrait(GLEAMING, MaterialTypes.HEAD);
        CYBER_CRYSTAL.addTrait(HYPERCHARGE, MaterialTypes.HEAD);
        CYBER_CRYSTAL.addTrait(TinkerTraits.alien, MaterialTypes.HEAD);
        CYBER_CRYSTAL.addTrait(HYPERCHARGE);
        CYBER_CRYSTAL.addTrait(TinkerTraits.alien);
        TinkerRegistry.integrate(new MaterialIntegration(CYBER_CRYSTAL)).preInit();

        TinkerRegistry.addMaterialStats(TEKTITE,
                new HeadMaterialStats(1300, 12.00F, 8.00F, HarvestLevels.COBALT),
                new HandleMaterialStats(1.50F, 250),
                new ExtraMaterialStats(375),
                new BowMaterialStats(0.65F, 1.65F, 9.5F));
        TEKTITE.addTrait(STUNLOCK, MaterialTypes.HEAD);
        TEKTITE.addTrait(SUPERCHARGE, MaterialTypes.HEAD);
        TEKTITE.addTrait(TinkerTraits.alien, MaterialTypes.HEAD);
        TEKTITE.addTrait(SUPERCHARGE);
        TEKTITE.addTrait(TinkerTraits.alien);
        TinkerRegistry.integrate(new MaterialIntegration(TEKTITE, null, "Tektite")).preInit();

        TinkerRegistry.addMaterialStats(METEORITE,
                new HeadMaterialStats(1200, 9.00F, 7.00F, HarvestLevels.COBALT),
                new HandleMaterialStats(1.20F, 200),
                new ExtraMaterialStats(350),
                new BowMaterialStats(0.5F, 1.5F, 8.0F));
        METEORITE.addTrait(GREATER_MAGNETIC2, MaterialTypes.HEAD);
        METEORITE.addTrait(TinkerTraits.alien, MaterialTypes.HEAD);
        METEORITE.addTrait(GREATER_MAGNETIC);
        METEORITE.addTrait(TinkerTraits.alien);
        registerFluid(METEORITE_FLUID);
        METEORITE_FLUID.setTemperature(900);
        TinkerRegistry.integrate(new MaterialIntegration(METEORITE, METEORITE_FLUID, "Meteorite")).preInit();

        TinkerRegistry.addMaterialStats(FREZARITE,
                new HeadMaterialStats(1400, 10.00F, 9.00F, HarvestLevels.COBALT),
                new HandleMaterialStats(1.40F, 300),
                new ExtraMaterialStats(400),
                new BowMaterialStats(0.65F, 1.6F, 9.0F));
        FREZARITE.addTrait(GLACIER, MaterialTypes.HEAD);
        FREZARITE.addTrait(FREZARITES_BANE, MaterialTypes.HEAD);
        FREZARITE.addTrait(TinkerTraits.alien, MaterialTypes.HEAD);
        FREZARITE.addTrait(FREZARITES_BANE);
        FREZARITE.addTrait(TinkerTraits.alien);
        registerFluid(FREZARITE_FLUID);
        FREZARITE_FLUID.setTemperature(900);
        TinkerRegistry.integrate(new MaterialIntegration(FREZARITE, FREZARITE_FLUID, "Frezarite")).preInit();

        TinkerRegistry.addMaterialStats(KREKNORITE,
                new HeadMaterialStats(1400, 10.00F, 9.00F, HarvestLevels.COBALT),
                new HandleMaterialStats(1.40F, 300),
                new ExtraMaterialStats(400),
                new BowMaterialStats(0.65F, 1.6F, 9.0F));
        KREKNORITE.addTrait(VOLCANO, MaterialTypes.HEAD);
        KREKNORITE.addTrait(KREKNORITES_BANE, MaterialTypes.HEAD);
        KREKNORITE.addTrait(TinkerTraits.alien, MaterialTypes.HEAD);
        KREKNORITE.addTrait(KREKNORITES_BANE);
        KREKNORITE.addTrait(TinkerTraits.alien);
        registerFluid(KREKNORITE_FLUID);
        KREKNORITE_FLUID.setTemperature(900);
        TinkerRegistry.integrate(new MaterialIntegration(KREKNORITE, KREKNORITE_FLUID, "Kreknorite")).preInit();
    }

    public static void registerToolRecipes() {
        FALLEN_STAR.addItem(new ItemStack(NyxItems.fallenStar), 1, Material.VALUE_Ingot);
        FALLEN_STAR.setRepresentativeItem(NyxItems.fallenStar);
        FALLEN_STAR.setCraftable(true).setCastable(false);

        CYBER_CRYSTAL.addItem(new ItemStack(NyxBlocks.cyberCrystal), 1, Material.VALUE_Ingot);
        CYBER_CRYSTAL.setRepresentativeItem(NyxBlocks.cyberCrystal);
        CYBER_CRYSTAL.setCraftable(true).setCastable(false);

        TEKTITE.addItem(new ItemStack(NyxItems.tektiteGemCluster), 1, Material.VALUE_Ingot);
        TEKTITE.addCommonItems("Tektite");
        TEKTITE.setRepresentativeItem(NyxItems.tektiteGemCluster);
        TEKTITE.setCraftable(true).setCastable(false);

        METEORITE.addCommonItems("Meteorite");
        METEORITE.setRepresentativeItem(NyxItems.meteoriteIngot);
        METEORITE.setFluid(METEORITE_FLUID);
        METEORITE.setShard(NyxItems.meteoriteShard);
        METEORITE.setCraftable(false).setCastable(true);

        FREZARITE.addCommonItems("Frezarite");
        FREZARITE.setRepresentativeItem(NyxItems.frezariteIngot);
        FREZARITE.setFluid(FREZARITE_FLUID);
        FREZARITE.setShard(NyxItems.frezariteCrystal);
        FREZARITE.setCraftable(false).setCastable(true);

        KREKNORITE.addCommonItems("Kreknorite");
        KREKNORITE.setRepresentativeItem(NyxItems.kreknoriteIngot);
        KREKNORITE.setFluid(KREKNORITE_FLUID);
        KREKNORITE.setShard(NyxItems.kreknoriteShard);
        KREKNORITE.setCraftable(false).setCastable(true);
    }

    public static void registerSmelteryRecipes() {
        // Smeltery stuff goes here
        TinkerRegistry.registerMelting(NyxItems.meteoriteShard, METEORITE_FLUID, Material.VALUE_Ingot * 2);
        TinkerSmeltery.registerToolpartMeltingCasting(METEORITE);

        TinkerSmeltery.registerToolpartMeltingCasting(FREZARITE);

        TinkerSmeltery.registerToolpartMeltingCasting(KREKNORITE);
    }
}

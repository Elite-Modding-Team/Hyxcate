package de.ellpeck.nyx.init;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.capability.NyxWorld;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Nyx.ID)
public final class NyxRegistry {

    public static final SoundType DENSE_CRYSTAL = new SoundType(1.0F, 1.0F, NyxSoundEvents.BLOCK_DENSE_CRYSTAL_BREAK.getSoundEvent(), NyxSoundEvents.BLOCK_LIGHT_CRYSTAL_STEP.getSoundEvent(), NyxSoundEvents.BLOCK_DENSE_CRYSTAL_PLACE.getSoundEvent(), NyxSoundEvents.BLOCK_LIGHT_CRYSTAL_HIT.getSoundEvent(), NyxSoundEvents.BLOCK_LIGHT_CRYSTAL_HIT.getSoundEvent());
    public static final SoundType LIGHT_CRYSTAL = new SoundType(1.0F, 1.0F, NyxSoundEvents.BLOCK_LIGHT_CRYSTAL_BREAK.getSoundEvent(), NyxSoundEvents.BLOCK_LIGHT_CRYSTAL_STEP.getSoundEvent(), NyxSoundEvents.BLOCK_LIGHT_CRYSTAL_PLACE.getSoundEvent(), NyxSoundEvents.BLOCK_LIGHT_CRYSTAL_HIT.getSoundEvent(), NyxSoundEvents.BLOCK_LIGHT_CRYSTAL_HIT.getSoundEvent());
    public static final SoundType METEORIC_ROCK = new SoundType(1.0F, 1.0F, NyxSoundEvents.BLOCK_METEORIC_ROCK_BREAK.getSoundEvent(), NyxSoundEvents.BLOCK_METEORIC_ROCK_STEP.getSoundEvent(), NyxSoundEvents.BLOCK_METEORIC_ROCK_PLACE.getSoundEvent(), NyxSoundEvents.BLOCK_METEORIC_ROCK_STEP.getSoundEvent(), NyxSoundEvents.BLOCK_METEORIC_ROCK_STEP.getSoundEvent());
    
    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(Nyx.ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(NyxItems.fallenStar);
        }
    };

    @CapabilityInject(NyxWorld.class)
    public static Capability<NyxWorld> worldCapability;

    public static void preInit() {
        NyxItems.initMaterials();

        CapabilityManager.INSTANCE.register(NyxWorld.class, new Capability.IStorage<NyxWorld>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<NyxWorld> capability, NyxWorld instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<NyxWorld> capability, NyxWorld instance, EnumFacing side, NBTBase nbt) {

            }
        }, () -> null);
    }

    public static void init() {
        GameRegistry.addSmelting(new ItemStack(NyxBlocks.starBlock), new ItemStack(NyxBlocks.crackedStarBlock), 0.1F);
        GameRegistry.addSmelting(new ItemStack(NyxBlocks.frezariteRock), new ItemStack(NyxItems.frezariteCrystal), 1.5F);
        GameRegistry.addSmelting(new ItemStack(NyxBlocks.kreknoriteRock), new ItemStack(NyxItems.kreknoriteShard), 1.5F);
        GameRegistry.addSmelting(new ItemStack(NyxItems.meteoriteShard), new ItemStack(NyxItems.meteoriteIngot), 1.0F);
        GameRegistry.addSmelting(new ItemStack(NyxBlocks.meteoriteRock), new ItemStack(NyxItems.meteoriteShard), 1.0F);
        
        OreDictionary.registerOre("blockFrezarite", new ItemStack(NyxBlocks.frezariteBlock));
        OreDictionary.registerOre("blockKreknorite", new ItemStack(NyxBlocks.kreknoriteBlock));
        OreDictionary.registerOre("blockMeteorite", new ItemStack(NyxBlocks.meteoriteBlock));
        OreDictionary.registerOre("blockStar", new ItemStack(NyxBlocks.chiseledStarBlock));
        OreDictionary.registerOre("blockStar", new ItemStack(NyxBlocks.crackedStarBlock));
        OreDictionary.registerOre("blockStar", new ItemStack(NyxBlocks.starBlock));
        OreDictionary.registerOre("blockTektite", new ItemStack(NyxBlocks.tektiteBlock));

        OreDictionary.registerOre("gemTektite", new ItemStack(NyxItems.tektiteGemCluster));

        OreDictionary.registerOre("ingotFrezarite", new ItemStack(NyxItems.frezariteIngot));
        OreDictionary.registerOre("ingotKreknorite", new ItemStack(NyxItems.kreknoriteIngot));
        OreDictionary.registerOre("ingotMeteorite", new ItemStack(NyxItems.meteoriteIngot));
    }
}

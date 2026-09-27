package mod.emt.hyxcate.init;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.HyxcateWorld;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = Hyxcate.ID)
public final class HyxcateRegistry {

    public static final SoundType DENSE_CRYSTAL = new SoundType(1.0F, 1.0F, HyxcateSoundEvents.BLOCK_DENSE_CRYSTAL_BREAK.getSoundEvent(), HyxcateSoundEvents.BLOCK_LIGHT_CRYSTAL_STEP.getSoundEvent(), HyxcateSoundEvents.BLOCK_DENSE_CRYSTAL_PLACE.getSoundEvent(), HyxcateSoundEvents.BLOCK_LIGHT_CRYSTAL_HIT.getSoundEvent(), HyxcateSoundEvents.BLOCK_LIGHT_CRYSTAL_HIT.getSoundEvent());
    public static final SoundType LIGHT_CRYSTAL = new SoundType(1.0F, 1.0F, HyxcateSoundEvents.BLOCK_LIGHT_CRYSTAL_BREAK.getSoundEvent(), HyxcateSoundEvents.BLOCK_LIGHT_CRYSTAL_STEP.getSoundEvent(), HyxcateSoundEvents.BLOCK_LIGHT_CRYSTAL_PLACE.getSoundEvent(), HyxcateSoundEvents.BLOCK_LIGHT_CRYSTAL_HIT.getSoundEvent(), HyxcateSoundEvents.BLOCK_LIGHT_CRYSTAL_HIT.getSoundEvent());
    public static final SoundType METEORIC_ROCK = new SoundType(1.0F, 1.0F, HyxcateSoundEvents.BLOCK_METEORIC_ROCK_BREAK.getSoundEvent(), HyxcateSoundEvents.BLOCK_METEORIC_ROCK_STEP.getSoundEvent(), HyxcateSoundEvents.BLOCK_METEORIC_ROCK_PLACE.getSoundEvent(), HyxcateSoundEvents.BLOCK_METEORIC_ROCK_STEP.getSoundEvent(), HyxcateSoundEvents.BLOCK_METEORIC_ROCK_STEP.getSoundEvent());

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(Hyxcate.ID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(HyxcateItems.fallenStar);
        }
    };

    @CapabilityInject(HyxcateWorld.class)
    public static Capability<HyxcateWorld> worldCapability;

    public static void preInit() {
        HyxcateItems.initMaterials();

        CapabilityManager.INSTANCE.register(HyxcateWorld.class, new Capability.IStorage<HyxcateWorld>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<HyxcateWorld> capability, HyxcateWorld instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<HyxcateWorld> capability, HyxcateWorld instance, EnumFacing side, NBTBase nbt) {

            }
        }, () -> null);
    }

    public static void init() {
        HyxcateItems.setRepairItems();

        GameRegistry.addSmelting(new ItemStack(HyxcateBlocks.starBlock), new ItemStack(HyxcateBlocks.crackedStarBlock), 0.1F);
        GameRegistry.addSmelting(new ItemStack(HyxcateItems.frezariteCrystal), new ItemStack(HyxcateItems.frezariteIngot), 1.5F);
        GameRegistry.addSmelting(new ItemStack(HyxcateBlocks.frezariteRock), new ItemStack(HyxcateItems.frezariteCrystal), 1.5F);
        GameRegistry.addSmelting(new ItemStack(HyxcateItems.kreknoriteShard), new ItemStack(HyxcateItems.kreknoriteIngot), 1.5F);
        GameRegistry.addSmelting(new ItemStack(HyxcateBlocks.kreknoriteRock), new ItemStack(HyxcateItems.kreknoriteShard), 1.5F);
        GameRegistry.addSmelting(new ItemStack(HyxcateItems.meteoriteShard), new ItemStack(HyxcateItems.meteoriteIngot), 1.0F);
        GameRegistry.addSmelting(new ItemStack(HyxcateBlocks.meteoriteRock), new ItemStack(HyxcateItems.meteoriteShard), 1.0F);

        OreDictionary.registerOre("blockFrezarite", new ItemStack(HyxcateBlocks.frezariteBlock));
        OreDictionary.registerOre("blockKreknorite", new ItemStack(HyxcateBlocks.kreknoriteBlock));
        OreDictionary.registerOre("blockMeteorite", new ItemStack(HyxcateBlocks.meteoriteBlock));
        OreDictionary.registerOre("blockStar", new ItemStack(HyxcateBlocks.chiseledStarBlock));
        OreDictionary.registerOre("blockStar", new ItemStack(HyxcateBlocks.crackedStarBlock));
        OreDictionary.registerOre("blockStar", new ItemStack(HyxcateBlocks.starBlock));
        OreDictionary.registerOre("blockTektite", new ItemStack(HyxcateBlocks.tektiteBlock));

        OreDictionary.registerOre("gemTektite", new ItemStack(HyxcateItems.tektiteGemCluster));

        OreDictionary.registerOre("ingotFrezarite", new ItemStack(HyxcateItems.frezariteIngot));
        OreDictionary.registerOre("ingotKreknorite", new ItemStack(HyxcateItems.kreknoriteIngot));
        OreDictionary.registerOre("ingotMeteorite", new ItemStack(HyxcateItems.meteoriteIngot));
    }

    @SubscribeEvent
    public static void onSoundEventRegistry(RegistryEvent.Register<SoundEvent> event) {
        for (HyxcateSoundEvents soundEvents : HyxcateSoundEvents.values()) {
            event.getRegistry().register(soundEvents.getSoundEvent());
        }
    }
}

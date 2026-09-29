package mod.emt.hyxcate.init;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.client.renderer.*;
import mod.emt.hyxcate.entity.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

    @CapabilityInject(CapabilityCelestialEvent.class)
    public static Capability<CapabilityCelestialEvent> worldCapability;

    public static void preInit() {
        HyxcateItems.initMaterials();

        CapabilityManager.INSTANCE.register(CapabilityCelestialEvent.class, new Capability.IStorage<CapabilityCelestialEvent>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<CapabilityCelestialEvent> capability, CapabilityCelestialEvent instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<CapabilityCelestialEvent> capability, CapabilityCelestialEvent instance, EnumFacing side, NBTBase nbt) {

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

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingStar.class, RenderEmpty::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingMeteor.class, RenderMeteor::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityAlienCreeper.class, RenderAlienCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCometKitty.class, RenderCometKitty::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityAlienKitty.class, RenderAlienKitty::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityEyezor.class, RenderEyezor::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, RenderLaser::new);
        //RenderingRegistry.registerEntityRenderingHandler(EntityStellarProtector.class, RenderStelarProtector::new);

        for (Item item : HyxcateItems.MOD_ITEMS)
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerFluidRenderer(Fluid fluid) {
        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        FluidStateMapper mapper = new FluidStateMapper(fluid);
        ModelBakery.registerItemVariants(item);
        ModelLoader.setCustomMeshDefinition(item, mapper);
        ModelLoader.setCustomStateMapper(block, mapper);
    }

    private static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {
        private final ModelResourceLocation location;

        public FluidStateMapper(Fluid fluid) {
            this.location = new ModelResourceLocation(new ResourceLocation(Hyxcate.ID, "fluids"), fluid.getName());
        }

        @Override
        protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
            return this.location;
        }

        @Override
        public ModelResourceLocation getModelLocation(ItemStack stack) {
            return this.location;
        }
    }
}

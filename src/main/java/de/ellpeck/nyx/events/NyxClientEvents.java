package de.ellpeck.nyx.events;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.entities.*;
import de.ellpeck.nyx.entities.renderer.*;
import de.ellpeck.nyx.init.NyxItems;
import de.ellpeck.nyx.init.NyxPotions;
import de.ellpeck.nyx.items.NyxItemBow;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Nyx.ID, value = Side.CLIENT)
public final class NyxClientEvents {
    private static long lastCraftSoundTime = 0L;
    private static String lastMoonTextures;
    private static String lastSunTextures;

    @SubscribeEvent
    public static void onDebug(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.gameSettings.showDebugInfo) return;
        event.getLeft().add("");
        NyxWorld world = NyxWorld.get(mc.world);
        String pre = TextFormatting.GREEN + "[" + Nyx.NAME + "]" + TextFormatting.RESET;
        String name = "None";
        if (world.currentLunarEvent != null) {
            name = world.currentLunarEvent.name;
        } else if (world.currentSolarEvent != null) {
            name = world.currentSolarEvent.name;
        }
        event.getLeft().add(pre + " Current Event: " + name);
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        World world = Minecraft.getMinecraft().world;
        if (world == null) return;
        NyxWorld nyx = NyxWorld.get(world);
        if (nyx == null) return;
        nyx.update();

        long time = world.getWorldTime() % 24000;

        // https://minecraft.wiki/w/Daylight_cycle#24-hour_Minecraft_day
        // 167: The moon disappears on the horizon
        // 11834: The moon appears on the horizon
        String moonTex = nyx.currentLunarEvent != null ? nyx.currentLunarEvent.getMoonTexture() : null;
        if (!Objects.equals(moonTex, lastMoonTextures) && (moonTex != null || (time > 167 && time < 11834))) {
            lastMoonTextures = moonTex;
            ResourceLocation res = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, null, "field_110927_h");
            ObfuscationReflectionHelper.setPrivateValue(ResourceLocation.class, res, moonTex == null ? "minecraft" : Nyx.ID, "field_110626_a");
            ObfuscationReflectionHelper.setPrivateValue(ResourceLocation.class, res, moonTex == null ? "textures/environment/moon_phases.png" : "textures/moon/" + moonTex + ".png", "field_110625_b");
        }

        // https://minecraft.wiki/w/Daylight_cycle#24-hour_Minecraft_day
        // 13702: The sun disappears on the horizon
        // 22300: The sun appears on the horizon
        String sunTex = nyx.currentSolarEvent != null ? nyx.currentSolarEvent.getSunTexture() : null;
        if (!Objects.equals(sunTex, lastSunTextures) && (sunTex != null || (time > 13702 && time < 22300))) {
            lastSunTextures = sunTex;
            ResourceLocation res = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, null, "field_110928_i");
            ObfuscationReflectionHelper.setPrivateValue(ResourceLocation.class, res, sunTex == null ? "minecraft" : Nyx.ID, "field_110626_a");
            ObfuscationReflectionHelper.setPrivateValue(ResourceLocation.class, res, sunTex == null ? "textures/environment/sun.png" : "textures/sun/" + sunTex + ".png", "field_110625_b");
        }
    }

    @SubscribeEvent
    public static void onFogRender(EntityViewRenderEvent.FogColors event) {
        if (!NyxConfig.eventTint) return;
        NyxWorld world = NyxWorld.get(Minecraft.getMinecraft().world);
        if (world == null || world.currentSkyColor == 0) return;
        event.setRed(lerp(event.getRed(), (world.currentSkyColor >> 16 & 255) / 255F, world.eventSkyModifier));
        event.setGreen(lerp(event.getGreen(), (world.currentSkyColor >> 8 & 255) / 255F, world.eventSkyModifier));
        event.setBlue(lerp(event.getBlue(), (world.currentSkyColor & 255) / 255F, world.eventSkyModifier));
    }

    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(NyxEntityFallingStar.class, NyxRendererEmpty::new);
        RenderingRegistry.registerEntityRenderingHandler(NyxEntityFallingMeteor.class, NyxRendererMeteor::new);
        RenderingRegistry.registerEntityRenderingHandler(NyxEntityAlienCreeper.class, NyxRendererAlienCreeper::new);
        RenderingRegistry.registerEntityRenderingHandler(NyxEntityCometKitty.class, NyxRendererCometKitty::new);
        RenderingRegistry.registerEntityRenderingHandler(NyxEntityAlienKitty.class, NyxRendererAlienKitty::new);
        //RenderingRegistry.registerEntityRenderingHandler(NyxEntityStellarProtector.class, NyxRendererStellarProtector::new);

        for (Item item : NyxItems.MOD_ITEMS)
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }

    @SubscribeEvent
    public static void onColorRegistry(ColorHandlerEvent.Item event) {
        /*if (Config.meteors)
            event.getItemColors().registerItemColorHandler((stack, tintIndex) -> 0xd44a13, Registry.meteorFinder);*/
    }

    // Just stole this fluid stuff from Actually Additions lol
    public static void registerFluidRenderer(Fluid fluid) {
        Block block = fluid.getBlock();
        Item item = Item.getItemFromBlock(block);
        FluidStateMapper mapper = new FluidStateMapper(fluid);
        ModelBakery.registerItemVariants(item);
        ModelLoader.setCustomMeshDefinition(item, mapper);
        ModelLoader.setCustomStateMapper(block, mapper);
    }

    private static float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    // Courtesy of NeRdTheNed
    @SubscribeEvent
    public static void bowFOV(FOVUpdateEvent event) {
        final EntityPlayer eventPlayer = event.getEntity();
        final Item eventItem = eventPlayer.getActiveItemStack().getItem();

        if (eventItem instanceof NyxItemBow) {
            float finalFov = event.getFov();
            final float itemUseCount = eventItem.getMaxItemUseDuration(eventPlayer.getActiveItemStack()) - eventPlayer.getItemInUseCount();

            /*
             * First, we have to reverse the standard bow zoom.
             * Minecraft helpfully applies the standard bow zoom
             * to any item that is an instance of a ItemBow.
             * However, our custom bows draw back at different speeds,
             * so the standard zoom is not at the right speed.
             * To compensate for this, we just calculate the standard bow zoom,
             * and apply it in reverse.
             */

            float realBow = itemUseCount / 20.0F;

            if (realBow > 1.0F) {
                realBow = 1.0F;
            } else {
                realBow *= realBow;
            }

            /*
             * Minecraft uses finalFov *= 1.0F - (realBow * 0.15F)
             * to calculate the standard bow zoom.
             * To reverse this, we just divide it instead.
             */

            finalFov /= 1.0F - (realBow * 0.15F);

            /*
             * We now calculate and apply our custom bow zoom.
             * The only difference between standard bow zoom and custom bow zoom
             * is that we change the hardcoded value of 20.0F to
             * whatever drawTime is.
             */

            float drawTime = 20.0F * ((NyxItemBow) eventItem).drawTimeMult;
            float customBow = itemUseCount / drawTime;

            if (customBow > 1.0F) {
                customBow = 1.0F;
            } else {
                customBow *= customBow;
            }

            finalFov *= 1.0F - (customBow * 0.15F);
            event.setNewfov(finalFov);
        }
    }

    // Courtesy of NeRdTheNed
    @SubscribeEvent
    public static void renderBow(RenderSpecificHandEvent event) {
        final Minecraft mc = Minecraft.getMinecraft();
        final Item eventItem = event.getItemStack().getItem();

        // Only handle rendering if we're in first person and drawing back a CustomBow.
        if ((mc.gameSettings.thirdPersonView == 0) && mc.player.isHandActive() && (mc.player.getActiveHand() == event.getHand()) && (mc.player.getItemInUseCount() > 0) && (event.getItemStack().getItem() instanceof NyxItemBow)) {
            // Cancel rendering so we can render instead
            event.setCanceled(true);
            GlStateManager.pushMatrix();

            final boolean rightHanded = (event.getHand() == EnumHand.MAIN_HAND ? mc.player.getPrimaryHand() : mc.player.getPrimaryHand().opposite()) == EnumHandSide.RIGHT;
            final int handedSide = rightHanded ? 1 : -1;

            GlStateManager.translate(handedSide * 0.2814318F, -0.3365561F + (event.getEquipProgress() * -0.6F), -0.5626847F);

            // Rotate angles
            GlStateManager.rotate(-13.935F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(handedSide * 35.3F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(handedSide * -9.785F, 0.0F, 0.0F, 1.0F);

            final float ticks = eventItem.getMaxItemUseDuration(event.getItemStack()) - ((mc.player.getItemInUseCount() - event.getPartialTicks()) + 1.0F);
            float drawTime = 20.0F * ((NyxItemBow) eventItem).drawTimeMult;
            float divTicks = ticks / drawTime;
            divTicks = ((divTicks * divTicks) + (divTicks * 2.0F)) / 3.0F;

            if (divTicks > 1.0F) {
                divTicks = 1.0F;
            }

            // Bow animations and transformations
            if (divTicks > 0.1F) {
                // Bow shake
                GlStateManager.translate(0.0F, MathHelper.sin((ticks - 0.1F) * 1.3F) * (divTicks - 0.1F) * 0.004F, 0.0F);
            }

            // Backwards motion ("draw back" animation)
            GlStateManager.translate(0.0F, 0.0F, divTicks * 0.04F);

            // Relative scaling for FOV reasons
            GlStateManager.scale(1.0F, 1.0F, 1.0F + (divTicks * 0.2F));

            // Rotate bow based on handedness
            GlStateManager.rotate(handedSide * 45.0F, 0.0F, -1.0F, 0.0F);

            // Let Minecraft do the rest of the item rendering
            mc.getItemRenderer().renderItemSide(mc.player, event.getItemStack(), rightHanded ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !rightHanded);
            GlStateManager.popMatrix();
        }
    }

    // TODO: Seems to not work with certain mods or modpacks (Hexxit II for example). There might be a better way?
    @SubscribeEvent
    public static void craftingSound(PlayerEvent.ItemCraftedEvent event) {
        if (lastCraftSoundTime != event.player.world.getWorldTime()) {
            final ItemStack result = event.crafting;

            if (!result.isEmpty() && result.getItem() == NyxItems.celestialEmblem) {
                event.player.playSound(NyxSoundEvents.ITEM_CELESTIAL_EMBLEM_CREATE.getSoundEvent(), 0.5F, 1.0F);
                lastCraftSoundTime = event.player.world.getWorldTime();
                return;
            }

            final IInventory inv = event.craftMatrix;

            for (int slots = inv.getSizeInventory(), i = 0; i < slots; ++i) {
                if (inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getItem() == NyxItems.celestialEmblem) {
                    event.player.playSound(NyxSoundEvents.ITEM_CELESTIAL_EMBLEM_CREATE.getSoundEvent(), 0.5F, 1.0F);
                    lastCraftSoundTime = event.player.world.getWorldTime();
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMouseEvent(MouseEvent event) {
        // When a player is paralyzed, it will be impossible for them to use their mouse
        if (Minecraft.getMinecraft().player.isPotionActive(NyxPotions.PARALYSIS) && Minecraft.getMinecraft().inGameHasFocus) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onInputUpdateEvent(InputUpdateEvent event) {
        // When a player is paralyzed, it will be impossible for them to move
        if (event.getEntityPlayer().isPotionActive(NyxPotions.PARALYSIS)) {
            event.getMovementInput().jump = false;
            event.getMovementInput().moveForward = 0;
            event.getMovementInput().moveStrafe = 0;
            event.getMovementInput().sneak = false;
        }
    }

    private static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition {

        private final ModelResourceLocation location;

        public FluidStateMapper(Fluid fluid) {
            this.location = new ModelResourceLocation(new ResourceLocation(Nyx.ID, "fluids"), fluid.getName());
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

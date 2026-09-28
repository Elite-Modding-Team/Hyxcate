package mod.emt.hyxcate.event.client;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.ColorTransitionUtil;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Objects;

@SideOnly(Side.CLIENT)
public class CelestialWorldEvent {
    private static String lastMoonTextures;
    private static String lastSunTextures;

    /**
     * Used mainly in {@link ColorTransitionUtil} to
     * instantly transition on world join
     */
    public static long joinTime = -1;

    @SubscribeEvent
    public void onWorldUnload(WorldEvent.Unload event) {
        // Allow joinTime to be set again during first client tick
        if (event.getWorld().isRemote) {
            joinTime = -1;
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        World world = Minecraft.getMinecraft().world;
        if (world == null) return;

        if (joinTime == -1) {
            joinTime = world.getWorldTime();
        }

        HyxcateWorld hyxcate = HyxcateWorld.get(world);
        if (hyxcate == null) return;
        hyxcate.update();

        String moonTex = hyxcate.currentLunarEvent != null ? hyxcate.currentLunarEvent.getMoonTexture() : null;
        if (!Objects.equals(moonTex, lastMoonTextures)) {
            lastMoonTextures = moonTex;
            ResourceLocation res = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, null, "field_110927_h");
            ObfuscationReflectionHelper.setPrivateValue(ResourceLocation.class, res, moonTex == null ? "minecraft" : Hyxcate.ID, "field_110626_a");
            ObfuscationReflectionHelper.setPrivateValue(ResourceLocation.class, res, moonTex == null ? "textures/environment/moon_phases.png" : "textures/moon/" + moonTex + ".png", "field_110625_b");
        }

        String sunTex = hyxcate.currentSolarEvent != null ? hyxcate.currentSolarEvent.getSunTexture() : null;
        if (!Objects.equals(sunTex, lastSunTextures)) {
            lastSunTextures = sunTex;
            ResourceLocation res = ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, null, "field_110928_i");
            ObfuscationReflectionHelper.setPrivateValue(ResourceLocation.class, res, sunTex == null ? "minecraft" : Hyxcate.ID, "field_110626_a");
            ObfuscationReflectionHelper.setPrivateValue(ResourceLocation.class, res, sunTex == null ? "textures/environment/sun.png" : "textures/sun/" + sunTex + ".png", "field_110625_b");
        }
    }

    private static final ColorTransitionUtil fogColorTransition = new ColorTransitionUtil(HyxcateConfig.GENERAL.eventTintSkyColorDuration);

    @SubscribeEvent
    public void onFogRender(EntityViewRenderEvent.FogColors event) {
        if (!HyxcateConfig.GENERAL.eventTint) {
            return;
        }

        World world = event.getEntity().world;
        if (world == null) {
            return;
        }

        HyxcateWorld hyxcateWorld = HyxcateWorld.get(world);
        if (hyxcateWorld == null) {
            return;
        }

        long worldTime = world.getWorldTime();
        float[] initialColors = new float[]{event.getRed(), event.getGreen(), event.getBlue()};

        if (hyxcateWorld.currentSolarEvent != null && hyxcateWorld.currentSolarEvent.getSkyColor() != 0) {
            fogColorTransition.transition(
                    initialColors,
                    ColorUtil.getRgbIntAsFloatArray(hyxcateWorld.currentSolarEvent.getSkyColor()),
                    worldTime,
                    ColorTransitionUtil.TargetType.CUSTOM_COLOR
            );
        } else if (hyxcateWorld.currentLunarEvent != null && hyxcateWorld.currentLunarEvent.getSkyColor() != 0) {
            fogColorTransition.transition(
                    initialColors,
                    ColorUtil.getRgbIntAsFloatArray(hyxcateWorld.currentLunarEvent.getSkyColor()),
                    worldTime,
                    ColorTransitionUtil.TargetType.CUSTOM_COLOR
            );
        } else {
            fogColorTransition.transition(
                    initialColors,
                    worldTime,
                    ColorTransitionUtil.TargetType.DEFAULT_COLOR
            );
        }

        if (fogColorTransition.isOverriding()) {
            float[] customFogColors = fogColorTransition.getCurrentColor(worldTime, (float) event.getRenderPartialTicks());
            event.setRed(customFogColors[0]);
            event.setGreen(customFogColors[1]);
            event.setBlue(customFogColors[2]);
        }
    }
}

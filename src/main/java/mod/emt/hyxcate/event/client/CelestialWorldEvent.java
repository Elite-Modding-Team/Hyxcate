package mod.emt.hyxcate.event.client;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.EntityViewRenderEvent;
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

    private static long fogLastTransitionStartTime = Long.MIN_VALUE;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        World world = Minecraft.getMinecraft().world;
        if (world == null) return;

        CapabilityCelestialEvent hyxcate = CapabilityCelestialEvent.get(world);
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

    @SubscribeEvent
    public void onFogRender(EntityViewRenderEvent.FogColors event) {
        if (!HyxcateConfig.GENERAL.eventTint) {
            return;
        }

        World world = event.getEntity().world;
        if (world == null) {
            return;
        }

        CapabilityCelestialEvent cap = CapabilityCelestialEvent.get(world);
        if (cap == null) {
            return;
        }

        float[] vanillaColor = new float[]{
                event.getRed(),
                event.getGreen(),
                event.getBlue()
        };

        boolean active = false;
        float[] eventColor = null;
        boolean stopping = false;
        float[] previousEventColor = null;
        long transitionStartTime = -1;

        if (cap.currentSolarEvent != null && cap.currentSolarEvent.getSkyColor() != 0) {
            active = true;
            eventColor = ColorUtil.getRgbIntAsFloatArray(cap.currentSolarEvent.getSkyColor());
            transitionStartTime = cap.solarTransitionStartTime;
        } else if (cap.currentLunarEvent != null && cap.currentLunarEvent.getSkyColor() != 0) {
            active = true;
            eventColor = ColorUtil.getRgbIntAsFloatArray(cap.currentLunarEvent.getSkyColor());
            transitionStartTime = cap.lunarTransitionStartTime;
        } else if (cap.currentSolarEvent == null && cap.currentLunarEvent == null) {
            boolean solarStopping = cap.lastSolarEvent != null && cap.lastSolarEvent.getSkyColor() != 0 && cap.solarTransitionStopping && cap.solarTransitionStartTime >= 0;
            boolean lunarStopping = cap.lastLunarEvent != null && cap.lastLunarEvent.getSkyColor() != 0 && cap.lunarTransitionStopping && cap.lunarTransitionStartTime >= 0;

            if (solarStopping && (!lunarStopping || cap.solarTransitionStartTime >= cap.lunarTransitionStartTime)) {
                stopping = true;
                previousEventColor = ColorUtil.getRgbIntAsFloatArray(cap.lastSolarEvent.getSkyColor());
                transitionStartTime = cap.solarTransitionStartTime;
            } else if (lunarStopping) {
                stopping = true;
                previousEventColor = ColorUtil.getRgbIntAsFloatArray(cap.lastLunarEvent.getSkyColor());
                transitionStartTime = cap.lunarTransitionStartTime;
            }
        }

        float progress = 1.0F;

        if (transitionStartTime >= 0) {
            int duration = HyxcateConfig.GENERAL.eventTintSkyColorDuration;
            progress = duration == -1.0F ? 1.0F : Math.max(0.0F, Math.min(1.0F, (world.getTotalWorldTime() + (float) event.getRenderPartialTicks() - transitionStartTime) / (float) duration));
        }

        if (active && eventColor != null) {
            if (transitionStartTime >= 0 && transitionStartTime != fogLastTransitionStartTime) {
                fogLastTransitionStartTime = transitionStartTime;
            }

            float[] result = new float[]{
                    vanillaColor[0] + ((eventColor[0] - vanillaColor[0]) * progress),
                    vanillaColor[1] + ((eventColor[1] - vanillaColor[1]) * progress),
                    vanillaColor[2] + ((eventColor[2] - vanillaColor[2]) * progress)
            };

            event.setRed(result[0]);
            event.setGreen(result[1]);
            event.setBlue(result[2]);
            return;
        }

        if (stopping && previousEventColor != null) {
            if (transitionStartTime >= 0 && transitionStartTime != fogLastTransitionStartTime) {
                fogLastTransitionStartTime = transitionStartTime;
            }

            float eventInfluence = 1F - progress;
            float[] result = new float[]{
                    vanillaColor[0] + ((previousEventColor[0] - vanillaColor[0]) * eventInfluence),
                    vanillaColor[1] + ((previousEventColor[1] - vanillaColor[1]) * eventInfluence),
                    vanillaColor[2] + ((previousEventColor[2] - vanillaColor[2]) * eventInfluence)
            };

            event.setRed(result[0]);
            event.setGreen(result[1]);
            event.setBlue(result[2]);
        }
    }
}

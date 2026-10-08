package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RenderGlobal.class)
public abstract class HyxcateRenderSkyMixin {
    @Shadow
    private WorldClient world;

    @Unique
    private static final float NORMAL_SUN_SIZE = 30.0F;

    @Unique
    private static final int SUN_ANIMATION_TICKS = 40;

    @ModifyConstant(method = "renderSky(FI)V", constant = @Constant(floatValue = 30.0F, ordinal = 6))
    private float hyxcateRenderSky(float constant) {
        if (this.world == null) {
            return NORMAL_SUN_SIZE;
        }

        CapabilityCelestialEvent celestial = CapabilityCelestialEvent.get(this.world);

        if (celestial == null) {
            return NORMAL_SUN_SIZE;
        }

        if (!HyxcateConfig.GENERAL.eventSunGrowthAnimation) {
            return celestial.currentSolarEvent != null ? celestial.currentSolarEvent.getSunSize() : NORMAL_SUN_SIZE;
        }

        if (celestial.solarTransitionStartTime < 0) {
            return celestial.currentSolarEvent != null ? celestial.currentSolarEvent.getSunSize() : NORMAL_SUN_SIZE;
        }

        float progress = hyxcate$getProgress(celestial);

        if (!celestial.solarTransitionStopping) {
            float targetSize = celestial.currentSolarEvent != null ? celestial.currentSolarEvent.getSunSize() : NORMAL_SUN_SIZE;
            return NORMAL_SUN_SIZE + (targetSize - NORMAL_SUN_SIZE) * progress;
        }

        float startSize = celestial.lastSolarEvent != null ? celestial.lastSolarEvent.getSunSize() : NORMAL_SUN_SIZE;
        return startSize + (NORMAL_SUN_SIZE - startSize) * progress;
    }

    @Unique
    private float hyxcate$getProgress(CapabilityCelestialEvent celestial) {
        float elapsed = (this.world.getTotalWorldTime() - celestial.solarTransitionStartTime) + Minecraft.getMinecraft().getRenderPartialTicks();
        float progress = elapsed / (float) SUN_ANIMATION_TICKS;
        progress = Math.max(0.0F, Math.min(1.0F, progress));
        progress = progress * progress * (3.0F - 2.0F * progress);
        return progress;
    }
}
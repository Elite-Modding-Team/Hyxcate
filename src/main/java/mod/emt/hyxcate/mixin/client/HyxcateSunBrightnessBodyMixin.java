package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.celestialevent.solar.SolarEventGrimEclipse;
import mod.emt.hyxcate.util.ColorTransitionUtil;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class HyxcateSunBrightnessBodyMixin {
    @Shadow
    public abstract long getTotalWorldTime();

    @Unique
    private final ColorTransitionUtil hyxcate$brightnessTransition = new ColorTransitionUtil();

    @Unique
    private long hyxcate$lastTransitionStartTime = Long.MIN_VALUE;

    @Inject(method = "getSunBrightnessBody", at = @At("TAIL"), cancellable = true, remap = false)
    private void HyxcateSetSunBrightnessBody(float partialTicks, CallbackInfoReturnable<Float> cir) {
        CapabilityCelestialEvent cap = CapabilityCelestialEvent.get((World) (Object) this);

        if (cap == null) {
            return;
        }

        boolean active = cap.currentSolarEvent instanceof SolarEventGrimEclipse;
        boolean stopping = !active && cap.currentLunarEvent == null && cap.lastSolarEvent instanceof SolarEventGrimEclipse && cap.solarTransitionStopping && cap.solarTransitionStartTime >= 0;
        long transitionStartTime = -1;

        if (active) {
            transitionStartTime = cap.solarTransitionStartTime;
        } else if (stopping) {
            transitionStartTime = cap.solarTransitionStartTime;
        }

        if (transitionStartTime >= 0 && transitionStartTime != hyxcate$lastTransitionStartTime) {
            if (active) {
                hyxcate$brightnessTransition.forceTransition(new float[]{1.0F, 0.0F, 0.0F}, new float[]{0.0F, 0.0F, 0.0F}, ColorTransitionUtil.TargetType.CUSTOM_COLOR);
            } else if (stopping) {
                hyxcate$brightnessTransition.forceTransition(new float[]{0.0F, 0.0F, 0.0F}, new float[]{1.0F, 0.0F, 0.0F}, ColorTransitionUtil.TargetType.DEFAULT_COLOR);
            }
            hyxcate$lastTransitionStartTime = transitionStartTime;
        }

        if (!hyxcate$brightnessTransition.isOverriding()) {
            return;
        }

        if (transitionStartTime < 0) {
            return;
        }

        int duration = HyxcateConfig.GENERAL.eventTintLightmapDuration;
        float progress = duration == -1.0F ? 1.0F : Math.max(0.0F, Math.min(1.0F, (getTotalWorldTime() + partialTicks - transitionStartTime) / (float) duration));
        float customBrightness = hyxcate$brightnessTransition.getCurrentColor(progress)[0];
        cir.setReturnValue(cir.getReturnValue() * customBrightness);
    }
}
package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.ColorTransitionUtil;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldProvider.class)
public abstract class HyxcateLightmapColorsMixin {
    @Unique
    private final ColorTransitionUtil hyxcate$colorTransition = new ColorTransitionUtil();

    @Unique
    private long hyxcate$lastTransitionStartTime = Long.MIN_VALUE;

    @Shadow
    protected World world;

    @Inject(method = "getLightmapColors", at = @At("HEAD"), remap = false)
    private void HyxcateSetLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors, CallbackInfo ci) {
        if (!HyxcateConfig.GENERAL.eventTint || (!HyxcateConfig.GENERAL.eventTintUnderground && skyLight == 0)) {
            return;
        }

        CapabilityCelestialEvent cap = CapabilityCelestialEvent.get(this.world);

        if (cap == null) {
            return;
        }

        float[] defaultColor = new float[]{1F, 1F, 1F};
        boolean active = false;
        float[] targetColor = null;
        boolean stopping = false;
        float[] previousEventColor = null;
        long transitionStartTime = -1;

        if (cap.currentSolarEvent != null && cap.currentSolarEvent.getLightmapColor() != 0) {
            active = true;
            targetColor = ColorUtil.getRgbIntAsFloatArray(ColorUtil.adjustBrightness(cap.currentSolarEvent.getLightmapColor(), 2.0F));
            transitionStartTime =
                    cap.solarTransitionStartTime;
        } else if (cap.currentLunarEvent != null && cap.currentLunarEvent.getLightmapColor() != 0) {
            active = true;
            targetColor = ColorUtil.getRgbIntAsFloatArray(cap.currentLunarEvent.getLightmapColor());
            transitionStartTime = cap.lunarTransitionStartTime;
        } else if (cap.currentSolarEvent == null && cap.currentLunarEvent == null && cap.lastSolarEvent != null && cap.solarTransitionStartTime >= 0 && cap.solarTransitionStartTime == Math.max(cap.lunarTransitionStartTime, cap.solarTransitionStartTime) && cap.lastSolarEvent.getLightmapColor() != 0) {
            stopping = true;
            previousEventColor = ColorUtil.getRgbIntAsFloatArray(ColorUtil.adjustBrightness(cap.lastSolarEvent.getLightmapColor(), 2.0F));
            transitionStartTime = cap.solarTransitionStartTime;
        } else if (cap.currentSolarEvent == null && cap.currentLunarEvent == null && cap.lastLunarEvent != null && cap.lunarTransitionStartTime >= 0 && cap.lunarTransitionStartTime == Math.max(cap.lunarTransitionStartTime, cap.solarTransitionStartTime) && cap.lastLunarEvent.getLightmapColor() != 0) {
            stopping = true;
            previousEventColor = ColorUtil.getRgbIntAsFloatArray(cap.lastLunarEvent.getLightmapColor());
            transitionStartTime = cap.lunarTransitionStartTime;
        }

        if (transitionStartTime >= 0 && transitionStartTime != hyxcate$lastTransitionStartTime) {
            if (active) {
                hyxcate$colorTransition.forceTransition(defaultColor, targetColor, ColorTransitionUtil.TargetType.CUSTOM_COLOR);
            } else if (stopping) {
                hyxcate$colorTransition.forceTransition(previousEventColor, defaultColor, ColorTransitionUtil.TargetType.DEFAULT_COLOR);
            }

            hyxcate$lastTransitionStartTime = transitionStartTime;
        }

        if (!hyxcate$colorTransition.isOverriding()) {
            return;
        }

        if (transitionStartTime < 0) {
            return;
        }

        int duration = HyxcateConfig.GENERAL.eventTintLightmapDuration;
        float progress = duration == -1.0F ? 1.0F : Math.max(0.0F, Math.min(1.0F, (this.world.getTotalWorldTime() + partialTicks - transitionStartTime) / (float) duration));
        float[] customLightmapColors = hyxcate$colorTransition.getCurrentColor(progress);
        colors[0] = Math.min(1.0F, colors[0] * customLightmapColors[0]);
        colors[1] = Math.min(1.0F, colors[1] * customLightmapColors[1]);
        colors[2] = Math.min(1.0F, colors[2] * customLightmapColors[2]);
    }
}
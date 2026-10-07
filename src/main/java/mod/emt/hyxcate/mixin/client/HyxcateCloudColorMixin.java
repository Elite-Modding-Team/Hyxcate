package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.ColorTransitionUtil;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class HyxcateCloudColorMixin {
    @Unique
    private final ColorTransitionUtil hyxcate$colorTransition = new ColorTransitionUtil();

    @Unique
    private long hyxcate$lastTransitionStartTime = Long.MIN_VALUE;

    @Shadow
    public abstract long getTotalWorldTime();

    @Inject(method = "getCloudColorBody", at = @At("TAIL"), cancellable = true, remap = false)
    private void HyxcateSetCloudColor(float partialTicks, CallbackInfoReturnable<Vec3d> cir) {
        if (!HyxcateConfig.GENERAL.eventTint) {
            return;
        }

        CapabilityCelestialEvent cap = CapabilityCelestialEvent.get((World) (Object) this);

        if (cap == null) {
            return;
        }

        float[] defaultColor = ColorUtil.getVec3dAsFloatArray(cir.getReturnValue());
        boolean active = false;
        float[] targetColor = null;
        boolean stopping = false;
        float[] previousEventColor = null;
        long transitionStartTime = -1;

        if (cap.currentSolarEvent != null && cap.currentSolarEvent.getCloudColor() != 0) {
            active = true;
            targetColor = ColorUtil.getRgbIntAsFloatArray(cap.currentSolarEvent.getCloudColor());
            transitionStartTime = cap.solarTransitionStartTime;
        } else if (cap.currentLunarEvent != null && cap.currentLunarEvent.getCloudColor() != 0) {
            active = true;
            targetColor = ColorUtil.getRgbIntAsFloatArray(cap.currentLunarEvent.getCloudColor());
            transitionStartTime = cap.lunarTransitionStartTime;
        } else if (cap.currentSolarEvent == null && cap.currentLunarEvent == null && cap.lastSolarEvent != null && cap.solarTransitionStopping && cap.solarTransitionStartTime >= 0 && cap.lastSolarEvent.getCloudColor() != 0) {
            stopping = true;
            previousEventColor = ColorUtil.getRgbIntAsFloatArray(cap.lastSolarEvent.getCloudColor());
            transitionStartTime = cap.solarTransitionStartTime;
        } else if (cap.currentSolarEvent == null && cap.currentLunarEvent == null && cap.lastLunarEvent != null && cap.lunarTransitionStopping && cap.lunarTransitionStartTime >= 0 && cap.lastLunarEvent.getCloudColor() != 0) {
            stopping = true;
            previousEventColor = ColorUtil.getRgbIntAsFloatArray(cap.lastLunarEvent.getCloudColor());
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

        int duration = HyxcateConfig.GENERAL.eventTintSkyColorDuration;
        float progress = duration == -1.0F ? 1.0F : Math.max(0.0F, Math.min(1.0F, (getTotalWorldTime() + partialTicks - transitionStartTime) / (float) duration));
        float[] result = hyxcate$colorTransition.getCurrentColor(progress);
        cir.setReturnValue(ColorUtil.getFloatArrayAsVec3d(result));
    }
}
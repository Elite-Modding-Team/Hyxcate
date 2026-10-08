package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class HyxcateCloudColorMixin {
    @Inject(method = "getCloudColour", at = @At("RETURN"), cancellable = true, remap = false)
    private void HyxcateSetCloudColor(float partialTicks, CallbackInfoReturnable<Vec3d> cir) {
        if (!HyxcateConfig.GENERAL.eventTint) {
            return;
        }

        World world = (World) (Object) this;
        CapabilityCelestialEvent cap = CapabilityCelestialEvent.get(world);

        if (cap == null) {
            return;
        }

        float[] vanillaColor = ColorUtil.getVec3dAsFloatArray(cir.getReturnValue());
        boolean active = false;
        float[] eventColor = null;
        boolean stopping = false;
        float[] previousEventColor = null;
        long transitionStartTime = -1;

        if (cap.currentSolarEvent != null && cap.currentSolarEvent.getCloudColor() != 0) {
            active = true;
            eventColor = ColorUtil.getRgbIntAsFloatArray(cap.currentSolarEvent.getCloudColor());
            transitionStartTime = cap.solarTransitionStartTime;
        } else if (cap.currentLunarEvent != null && cap.currentLunarEvent.getCloudColor() != 0) {
            active = true;
            eventColor = ColorUtil.getRgbIntAsFloatArray(cap.currentLunarEvent.getCloudColor());
            transitionStartTime = cap.lunarTransitionStartTime;
        } else if (cap.currentSolarEvent == null && cap.currentLunarEvent == null) {
            boolean solarStopping = cap.lastSolarEvent != null && cap.lastSolarEvent.getCloudColor() != 0 && cap.solarTransitionStopping && cap.solarTransitionStartTime >= 0;
            boolean lunarStopping = cap.lastLunarEvent != null && cap.lastLunarEvent.getCloudColor() != 0 && cap.lunarTransitionStopping && cap.lunarTransitionStartTime >= 0;
            if (solarStopping && (!lunarStopping || cap.solarTransitionStartTime >= cap.lunarTransitionStartTime)) {
                stopping = true;
                previousEventColor = ColorUtil.getRgbIntAsFloatArray(cap.lastSolarEvent.getCloudColor());
                transitionStartTime = cap.solarTransitionStartTime;
            } else if (lunarStopping) {
                stopping = true;
                previousEventColor = ColorUtil.getRgbIntAsFloatArray(cap.lastLunarEvent.getCloudColor());
                transitionStartTime = cap.lunarTransitionStartTime;
            }
        }

        if (!active && !stopping) {
            return;
        }

        float progress = 1.0F;

        if (transitionStartTime >= 0) {
            int duration = HyxcateConfig.GENERAL.eventTintSkyColorDuration;
            progress = duration == -1 ? 1.0F : Math.max(0.0F, Math.min(1.0F, (world.getTotalWorldTime() + partialTicks - transitionStartTime) / (float) duration));
        }

        if (active && eventColor != null) {
            float[] result = new float[]{
                    vanillaColor[0] + ((eventColor[0] - vanillaColor[0]) * progress),
                    vanillaColor[1] + ((eventColor[1] - vanillaColor[1]) * progress),
                    vanillaColor[2] + ((eventColor[2] - vanillaColor[2]) * progress)
            };
            cir.setReturnValue(ColorUtil.getFloatArrayAsVec3d(result));
            return;
        }

        if (stopping && previousEventColor != null) {
            float eventInfluence = 1.0F - progress;
            float[] result = new float[]{
                    vanillaColor[0] + ((previousEventColor[0] - vanillaColor[0]) * eventInfluence),
                    vanillaColor[1] + ((previousEventColor[1] - vanillaColor[1]) * eventInfluence),
                    vanillaColor[2] + ((previousEventColor[2] - vanillaColor[2]) * eventInfluence)
            };
            cir.setReturnValue(ColorUtil.getFloatArrayAsVec3d(result));
        }
    }
}
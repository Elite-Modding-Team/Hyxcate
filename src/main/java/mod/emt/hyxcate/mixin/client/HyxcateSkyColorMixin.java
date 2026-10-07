package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.ColorTransitionUtil;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooksClient.class, remap = false)
public abstract class HyxcateSkyColorMixin {
    @Unique
    private static final ColorTransitionUtil hyxcate$colorTransition = new ColorTransitionUtil();

    @Unique
    private static long hyxcate$lastTransitionStartTime = Long.MIN_VALUE;

    @Unique
    private static World hyxcate$lastWorld;

    @Shadow
    private static boolean skyInit;

    @Inject(method = "getSkyBlendColour", at = @At("RETURN"), cancellable = true)
    private static void HyxcateSetSkyColor(World world, BlockPos center, CallbackInfoReturnable<Integer> cir) {
        if (!HyxcateConfig.GENERAL.eventTint) {
            return;
        }

        CapabilityCelestialEvent cap = CapabilityCelestialEvent.get(world);

        if (cap == null || !skyInit) {
            return;
        }

        if (hyxcate$lastWorld != world) {
            hyxcate$lastWorld = world;
            hyxcate$lastTransitionStartTime = Long.MIN_VALUE;
        }

        float[] defaultColor = ColorUtil.getRgbIntAsFloatArray(cir.getReturnValue());
        boolean customColor = false;
        float[] eventColor = null;
        float[] previousEventColor = null;
        long transitionStartTime = -1;

        if (cap.currentSolarEvent != null && cap.currentSolarEvent.getSkyColor() != 0) {
            customColor = true;
            eventColor = ColorUtil.getRgbIntAsFloatArray(ColorUtil.adjustBrightness(cap.currentSolarEvent.getSkyColor(), 1.5F));
            transitionStartTime = cap.solarTransitionStartTime;
        } else if (cap.currentLunarEvent != null && cap.currentLunarEvent.getSkyColor() != 0) {
            customColor = true;
            eventColor = ColorUtil.getRgbIntAsFloatArray(ColorUtil.adjustBrightness(cap.currentLunarEvent.getSkyColor(), 1.5F));
            transitionStartTime = cap.lunarTransitionStartTime;
        } else if (cap.currentSolarEvent == null && cap.currentLunarEvent == null && cap.lastSolarEvent != null && cap.solarTransitionStopping && cap.solarTransitionStartTime >= 0) {
            if (cap.lastSolarEvent.getSkyColor() != 0) {
                previousEventColor = ColorUtil.getRgbIntAsFloatArray(ColorUtil.adjustBrightness(cap.lastSolarEvent.getSkyColor(), 1.5F));
                transitionStartTime = cap.solarTransitionStartTime;
            }
        } else if (cap.currentSolarEvent == null && cap.currentLunarEvent == null && cap.lastLunarEvent != null && cap.lunarTransitionStopping && cap.lunarTransitionStartTime >= 0) {
            if (cap.lastLunarEvent.getSkyColor() != 0) {
                previousEventColor = ColorUtil.getRgbIntAsFloatArray(ColorUtil.adjustBrightness(cap.lastLunarEvent.getSkyColor(), 1.5F));
                transitionStartTime = cap.lunarTransitionStartTime;
            }
        }

        if (customColor && transitionStartTime >= 0 && transitionStartTime != hyxcate$lastTransitionStartTime) {
            hyxcate$colorTransition.forceTransition(defaultColor, eventColor, ColorTransitionUtil.TargetType.CUSTOM_COLOR);
            hyxcate$lastTransitionStartTime = transitionStartTime;
        } else if (!customColor && previousEventColor != null && transitionStartTime >= 0 && transitionStartTime != hyxcate$lastTransitionStartTime) {
            hyxcate$colorTransition.forceTransition(previousEventColor, defaultColor, ColorTransitionUtil.TargetType.DEFAULT_COLOR);
            hyxcate$lastTransitionStartTime = transitionStartTime;
        }

        if (!hyxcate$colorTransition.isOverriding()) {
            return;
        }

        if (transitionStartTime < 0) {
            return;
        }

        int duration = HyxcateConfig.GENERAL.eventTintSkyColorDuration;
        float progress = duration == -1.0F ? 1.0F : Math.max(0.0F, Math.min(1.0F, ((world.getTotalWorldTime() + 0.0F) - transitionStartTime) / (float) duration));
        float[] result = hyxcate$colorTransition.getCurrentColor(progress);
        cir.setReturnValue(ColorUtil.getFloatArrayAsRgbInt(result));
    }
}
package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.event.solar.SolarEventGrimEclipse;
import mod.emt.hyxcate.util.HyxcateColorTransition;
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
    public abstract long getWorldTime();

    @Unique
    private final HyxcateColorTransition hyxcate$brightnessTransition = new HyxcateColorTransition(HyxcateConfig.GENERAL.eventTintLightmapDuration);

    @Inject(method = "getSunBrightnessBody", at = @At("TAIL"), cancellable = true, remap = false)
    private void HyxcateSetSunBrightnessBody(float partialTicks, CallbackInfoReturnable<Float> cir) {

        HyxcateWorld hyxcateWorld = HyxcateWorld.get((World) (Object) this);

        if(hyxcateWorld == null) {
            return;
        }

        long worldTime = getWorldTime();

        // Re-using HyxcateColorTransition even tho this isn’t technically a color.
        // Since I'm using the brightness purely as a multiplier factor
        // (similar to the Lightmap mixin, where it’s split into RGB channels though),
        // only the first channel is used, the others are kept to 0.

        if(hyxcateWorld.currentSolarEvent instanceof SolarEventGrimEclipse) {
            hyxcate$brightnessTransition.transition(
                    new float[]{1, 0, 0},
                    new float[]{0, 0, 0},
                    worldTime,
                    HyxcateColorTransition.TargetType.CUSTOM_COLOR
            );
        } else {
            hyxcate$brightnessTransition.transition(
                    new float[]{1, 0, 0},
                    worldTime,
                    HyxcateColorTransition.TargetType.DEFAULT_COLOR
            );
        }

        if(hyxcate$brightnessTransition.isOverriding()) {
            float customBrightness = hyxcate$brightnessTransition.getCurrentColor(worldTime, partialTicks)[0];
            cir.setReturnValue(cir.getReturnValue() * customBrightness);
        }

    }

}

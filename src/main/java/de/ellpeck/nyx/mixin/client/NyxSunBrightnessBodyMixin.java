package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.event.solar.NyxEventGrimEclipse;
import de.ellpeck.nyx.util.NyxColorTransition;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = World.class, remap = false)
public abstract class NyxSunBrightnessBodyMixin {

    @Shadow
    public abstract long getWorldTime();

    @Unique
    private final NyxColorTransition hyxcate$brightnessTransition = new NyxColorTransition(NyxConfig.GENERAL.eventTintLightmapDuration);

    @Inject(method = "getSunBrightnessBody", at = @At("TAIL"), cancellable = true)
    private void nyxSetSunBrightnessBody(float partialTicks, CallbackInfoReturnable<Float> cir) {

        NyxWorld nyxWorld = NyxWorld.get((World) (Object) this);

        if(nyxWorld == null) {
            return;
        }

        long worldTime = getWorldTime();

        // Re-using NyxColorTransition even tho this isn’t technically a color.
        // Since I'm using the brightness purely as a multiplier factor
        // (similar to the Lightmap mixin, where it’s split into RGB channels though),
        // only the first channel is used, the others are kept to 0.

        if(nyxWorld.currentSolarEvent instanceof NyxEventGrimEclipse) {
            hyxcate$brightnessTransition.transition(
                    new float[]{1, 0, 0},
                    new float[]{0, 0, 0},
                    worldTime,
                    NyxColorTransition.TargetType.CUSTOM_COLOR
            );
        } else {
            hyxcate$brightnessTransition.transition(
                    new float[]{1, 0, 0},
                    worldTime,
                    NyxColorTransition.TargetType.DEFAULT_COLOR
            );
        }

        if(hyxcate$brightnessTransition.isOverriding()) {
            float customBrightness = hyxcate$brightnessTransition.getCurrentColor(worldTime, partialTicks)[0];
            cir.setReturnValue(cir.getReturnValue() * customBrightness);
        }

    }

}

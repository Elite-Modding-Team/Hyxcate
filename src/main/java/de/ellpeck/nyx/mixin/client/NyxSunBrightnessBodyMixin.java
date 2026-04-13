package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.event.solar.NyxEventGrimEclipse;
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
    private long hyxcate$startTicks = -1;

    @Inject(method = "getSunBrightnessBody", at = @At("TAIL"), cancellable = true)
    private void nyxSetSunBrightnessBody(float partialTicks, CallbackInfoReturnable<Float> cir) {

        NyxWorld nyxWorld = NyxWorld.get((World) (Object) this);
        float brightness = cir.getReturnValue();

        if(nyxWorld == null) {
            return;
        }

        if(nyxWorld.currentSolarEvent instanceof NyxEventGrimEclipse) {

            if(hyxcate$startTicks == -1) {
                hyxcate$startTicks = this.getWorldTime();
            }

            long elapsedTicks = this.getWorldTime() - hyxcate$startTicks;
            // Reach 0% brightness in the config-specified amount of ticks
            brightness *= Math.max(0, 1 - ((float) elapsedTicks / NyxConfig.GENERAL.eventTintLightmapDuration));

            cir.setReturnValue(brightness);

        } else if(hyxcate$startTicks != -1) {
            hyxcate$startTicks = -1;
        }

    }

}

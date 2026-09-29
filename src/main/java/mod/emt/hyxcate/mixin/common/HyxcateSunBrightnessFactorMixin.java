package mod.emt.hyxcate.mixin.common;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.celestialevent.solar.SolarEventGrimEclipse;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = World.class, remap = false)
public abstract class HyxcateSunBrightnessFactorMixin {

    @Inject(method = "getSunBrightnessFactor", at = @At("HEAD"), cancellable = true)
    private void HyxcateSetSunBrightnessFactor(float partialTicks, CallbackInfoReturnable<Float> cir) {
        CapabilityCelestialEvent hyxcateWorld = CapabilityCelestialEvent.get((World) (Object) this);
        if (hyxcateWorld != null && hyxcateWorld.currentSolarEvent instanceof SolarEventGrimEclipse) {
            cir.setReturnValue(0.0F);
        }
    }
}

package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.celestialevent.solar.SolarEventGrimEclipse;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class HyxcateRenderStarMixin {
    @Inject(method = "getStarBrightness", at = @At("RETURN"), cancellable = true)
    private void hyxcateEclipseStarBrightness(float partialTicks, CallbackInfoReturnable<Float> cir) {
        World world = (World) (Object) this;
        CapabilityCelestialEvent cap = CapabilityCelestialEvent.get(world);
        if (cap != null && cap.currentSolarEvent instanceof SolarEventGrimEclipse) {
            cir.setReturnValue(0.3F);
        }
    }
}
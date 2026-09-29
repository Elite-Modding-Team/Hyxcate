package mod.emt.hyxcate.compat.peacefulsurface.mixin.common;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import lain.mods.peacefulsurface.PeacefulSurface;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PeacefulSurface.class, remap = false)
public abstract class PSEntitySpawnFilterMixin {
    @Inject(method = "CheckSpawn", at = @At("HEAD"), cancellable = true)
    private void psCheckSpawn(LivingSpawnEvent.CheckSpawn event, CallbackInfo ci) {
        if (HyxcateConfig.MOD_INTEGRATION.peacefulSurfaceIntegration) {
            CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(event.getWorld());
            if (Hyxcate != null && (Hyxcate.currentSolarEvent != null || Hyxcate.currentLunarEvent != null)) {
                ci.cancel();
            }
        }
    }
}

package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.util.NyxColorUtils;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = World.class, remap = false)
public abstract class NyxCloudColorMixin {

    @Inject(method = "getCloudColorBody", at = @At("HEAD"), cancellable = true)
    private void nyxSetCloudColor(float partialTicks, CallbackInfoReturnable<Vec3d> cir) {
        if (!NyxConfig.eventTint) return;
        NyxWorld nyxWorld = NyxWorld.get((World) (Object) this);
        if (nyxWorld == null || nyxWorld.currentSkyColor == 0) return;
        if (nyxWorld.currentSolarEvent != null)
            cir.setReturnValue(NyxColorUtils.getRgbIntAsVec3d(nyxWorld.currentSolarEvent.getSkyColor()));
        if (nyxWorld.currentLunarEvent != null)
            cir.setReturnValue(NyxColorUtils.getRgbIntAsVec3d(nyxWorld.currentLunarEvent.getSkyColor()));
    }
}

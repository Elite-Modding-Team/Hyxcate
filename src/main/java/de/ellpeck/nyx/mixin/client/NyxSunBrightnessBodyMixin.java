package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.events.solar.NyxEventSolarEclipse;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = World.class, remap = false)
public abstract class NyxSunBrightnessBodyMixin {

    @Inject(method = "getSunBrightnessBody", at = @At("HEAD"), cancellable = true)
    private void nyxSetSunBrightnessBody(float partialTicks, CallbackInfoReturnable<Float> cir) {
        NyxWorld nyxWorld = NyxWorld.get((World) (Object) this);
        if (nyxWorld != null && nyxWorld.currentSolarEvent instanceof NyxEventSolarEclipse) {
            cir.setReturnValue(0.0F);
        }
    }
}

package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.util.NyxColorTransition;
import de.ellpeck.nyx.util.NyxColorUtils;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = World.class, remap = false)
public abstract class NyxCloudColorMixin {

    @Shadow
    public abstract long getWorldTime();

    @Unique
    private final NyxColorTransition hyxcate$colorTransition = new NyxColorTransition(NyxConfig.GENERAL.eventTintSkyColorDuration);

    @Inject(method = "getCloudColorBody", at = @At("TAIL"), cancellable = true)
    private void nyxSetCloudColor(float partialTicks, CallbackInfoReturnable<Vec3d> cir) {

        if(!NyxConfig.GENERAL.eventTint) {
            return;
        }

        NyxWorld nyxWorld = NyxWorld.get((World) (Object) this);

        if(nyxWorld == null) {
            return;
        }

        float[] initialColors = NyxColorUtils.getVec3dAsFloatArray(cir.getReturnValue());
        long worldTime = getWorldTime();

        if(nyxWorld.currentSolarEvent != null && nyxWorld.currentSolarEvent.getCloudColor() != 0) {
            hyxcate$colorTransition.transition(
                    initialColors,
                    NyxColorUtils.getRgbIntAsFloatArray(nyxWorld.currentSolarEvent.getCloudColor()),
                    worldTime,
                    NyxColorTransition.TargetType.CUSTOM_COLOR
            );
        } else if(nyxWorld.currentLunarEvent != null && nyxWorld.currentLunarEvent.getCloudColor() != 0) {
            hyxcate$colorTransition.transition(
                    initialColors,
                    NyxColorUtils.getRgbIntAsFloatArray(nyxWorld.currentLunarEvent.getCloudColor()),
                    worldTime,
                    NyxColorTransition.TargetType.CUSTOM_COLOR
            );
        } else {
            hyxcate$colorTransition.transition(
                    initialColors,
                    worldTime,
                    NyxColorTransition.TargetType.DEFAULT_COLOR
            );
        }

        if(hyxcate$colorTransition.isOverriding()) {
            float[] customCloudColors = hyxcate$colorTransition.getCurrentColor(worldTime, partialTicks);
            cir.setReturnValue(NyxColorUtils.getFloatArrayAsVec3d(customCloudColors));
        }

    }

}

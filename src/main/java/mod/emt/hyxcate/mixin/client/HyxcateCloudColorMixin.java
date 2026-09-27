package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.ColorTransitionUtil;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class HyxcateCloudColorMixin {

    @Shadow
    public abstract long getWorldTime();

    @Unique
    private final ColorTransitionUtil hyxcate$colorTransition = new ColorTransitionUtil(HyxcateConfig.GENERAL.eventTintSkyColorDuration);

    @Inject(method = "getCloudColorBody", at = @At("TAIL"), cancellable = true, remap = false)
    private void HyxcateSetCloudColor(float partialTicks, CallbackInfoReturnable<Vec3d> cir) {

        if(!HyxcateConfig.GENERAL.eventTint) {
            return;
        }

        HyxcateWorld hyxcateWorld = HyxcateWorld.get((World) (Object) this);

        if(hyxcateWorld == null) {
            return;
        }

        float[] initialColors = ColorUtil.getVec3dAsFloatArray(cir.getReturnValue());
        long worldTime = getWorldTime();

        if(hyxcateWorld.currentSolarEvent != null && hyxcateWorld.currentSolarEvent.getCloudColor() != 0) {
            hyxcate$colorTransition.transition(
                    initialColors,
                    ColorUtil.getRgbIntAsFloatArray(hyxcateWorld.currentSolarEvent.getCloudColor()),
                    worldTime,
                    ColorTransitionUtil.TargetType.CUSTOM_COLOR
            );
        } else if(hyxcateWorld.currentLunarEvent != null && hyxcateWorld.currentLunarEvent.getCloudColor() != 0) {
            hyxcate$colorTransition.transition(
                    initialColors,
                    ColorUtil.getRgbIntAsFloatArray(hyxcateWorld.currentLunarEvent.getCloudColor()),
                    worldTime,
                    ColorTransitionUtil.TargetType.CUSTOM_COLOR
            );
        } else {
            hyxcate$colorTransition.transition(
                    initialColors,
                    worldTime,
                    ColorTransitionUtil.TargetType.DEFAULT_COLOR
            );
        }

        if(hyxcate$colorTransition.isOverriding()) {
            float[] customCloudColors = hyxcate$colorTransition.getCurrentColor(worldTime, partialTicks);
            cir.setReturnValue(ColorUtil.getFloatArrayAsVec3d(customCloudColors));
        }

    }

}

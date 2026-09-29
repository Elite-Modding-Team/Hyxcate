package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.ColorTransitionUtil;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldProvider.class)
public abstract class HyxcateLightmapColorsMixin {

    @Unique
    private static final float[] hyxcate$START_MULTIPLIER = new float[] {1, 1, 1};

    @Unique
    private final ColorTransitionUtil hyxcate$colorTransition = new ColorTransitionUtil(HyxcateConfig.GENERAL.eventTintLightmapDuration);

    @Shadow
    protected World world;

    @Inject(method = "getLightmapColors", at = @At("HEAD"), remap = false)
    private void HyxcateSetLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors, CallbackInfo ci) {

        if(!HyxcateConfig.GENERAL.eventTint || (!HyxcateConfig.GENERAL.eventTintUnderground && skyLight == 0)) {
            return;
        }

        CapabilityCelestialEvent hyxcateWorld = CapabilityCelestialEvent.get(this.world);

        if(hyxcateWorld == null) {
            return;
        }

        long worldTime = world.getWorldTime();

        if(hyxcateWorld.currentSolarEvent != null && hyxcateWorld.currentSolarEvent.getLightmapColor() != 0) {
            hyxcate$colorTransition.transition(
                    hyxcate$START_MULTIPLIER,
                    ColorUtil.getRgbIntAsFloatArray(ColorUtil.adjustBrightness(hyxcateWorld.currentSolarEvent.getLightmapColor(), 2.0F)),
                    worldTime,
                    ColorTransitionUtil.TargetType.CUSTOM_COLOR
            );
        } else if(hyxcateWorld.currentLunarEvent != null && hyxcateWorld.currentLunarEvent.getLightmapColor() != 0) {
            hyxcate$colorTransition.transition(
                    hyxcate$START_MULTIPLIER,
                    ColorUtil.getRgbIntAsFloatArray(hyxcateWorld.currentLunarEvent.getLightmapColor()),
                    worldTime,
                    ColorTransitionUtil.TargetType.CUSTOM_COLOR
            );
        } else {
            hyxcate$colorTransition.transition(
                    hyxcate$START_MULTIPLIER,
                    worldTime,
                    ColorTransitionUtil.TargetType.DEFAULT_COLOR
            );
        }

        if(hyxcate$colorTransition.isOverriding()) {
            float[] customLightmapColors = hyxcate$colorTransition.getCurrentColor(worldTime, partialTicks);
            colors[0] *= customLightmapColors[0];
            colors[1] *= customLightmapColors[1];
            colors[2] *= customLightmapColors[2];
        }

    }

}

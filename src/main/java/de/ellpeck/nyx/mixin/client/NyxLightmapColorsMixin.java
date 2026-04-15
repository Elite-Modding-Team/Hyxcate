package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.util.NyxColorTransition;
import de.ellpeck.nyx.util.NyxColorUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldProvider.class)
public abstract class NyxLightmapColorsMixin {

    @Unique
    private static final float[] hyxcate$START_MULTIPLIER = new float[] {1, 1, 1};

    @Unique
    private final NyxColorTransition hyxcate$colorTransition = new NyxColorTransition(NyxConfig.GENERAL.eventTintLightmapDuration);

    @Shadow
    protected World world;

    @Inject(method = "getLightmapColors", at = @At("HEAD"), remap = false)
    private void nyxSetLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors, CallbackInfo ci) {

        if(!NyxConfig.GENERAL.eventTint || (!NyxConfig.GENERAL.eventTintUnderground && skyLight == 0)) {
            return;
        }

        NyxWorld nyxWorld = NyxWorld.get(this.world);

        if(nyxWorld == null) {
            return;
        }

        long worldTime = world.getWorldTime();

        if(nyxWorld.currentSolarEvent != null && nyxWorld.currentSolarEvent.getLightmapColor() != 0) {
            hyxcate$colorTransition.transition(
                    hyxcate$START_MULTIPLIER,
                    NyxColorUtils.getRgbIntAsFloatArray(NyxColorUtils.adjustBrightness(nyxWorld.currentSolarEvent.getLightmapColor(), 2.0F)),
                    worldTime,
                    NyxColorTransition.TargetType.CUSTOM_COLOR
            );
        } else if(nyxWorld.currentLunarEvent != null && nyxWorld.currentLunarEvent.getLightmapColor() != 0) {
            hyxcate$colorTransition.transition(
                    hyxcate$START_MULTIPLIER,
                    NyxColorUtils.getRgbIntAsFloatArray(nyxWorld.currentLunarEvent.getLightmapColor()),
                    worldTime,
                    NyxColorTransition.TargetType.CUSTOM_COLOR
            );
        } else {
            hyxcate$colorTransition.transition(
                    hyxcate$START_MULTIPLIER,
                    worldTime,
                    NyxColorTransition.TargetType.DEFAULT_COLOR
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

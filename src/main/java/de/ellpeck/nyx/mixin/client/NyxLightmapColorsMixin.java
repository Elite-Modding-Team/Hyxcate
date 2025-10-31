package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.util.NyxColorUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WorldProvider.class, remap = false)
public abstract class NyxLightmapColorsMixin {

    @Shadow
    protected World world;

    // TODO: Fix brightness
    @Inject(method = "getLightmapColors", at = @At("HEAD"))
    private void nyxSetLightmapColors(float partialTicks, float sunBrightness, float skyLight, float blockLight, float[] colors, CallbackInfo ci) {
        if (!NyxConfig.eventTint) return;
        NyxWorld nyxWorld = NyxWorld.get(this.world);
        if (nyxWorld == null || nyxWorld.currentSkyColor == 0) return;
        if (nyxWorld.currentSolarEvent != null) {
            float[] customLightmapColors = NyxColorUtils.getRgbIntAsFloatArray(NyxColorUtils.adjustBrightness(nyxWorld.currentSolarEvent.getSkyColor(), 2.0F));
            colors[0] = customLightmapColors[0];
            colors[1] = customLightmapColors[1];
            colors[2] = customLightmapColors[2];
        }
        if (nyxWorld.currentLunarEvent != null) {
            float[] customLightmapColors = NyxColorUtils.getRgbIntAsFloatArray(NyxColorUtils.adjustBrightness(nyxWorld.currentLunarEvent.getSkyColor(), 0.5F));
            colors[0] = customLightmapColors[0];
            colors[1] = customLightmapColors[1];
            colors[2] = customLightmapColors[2];
        }
    }
}

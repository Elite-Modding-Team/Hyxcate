package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.util.NyxColorTransition;
import de.ellpeck.nyx.util.NyxColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = WorldProvider.class, remap = false)
public abstract class NyxSkyColorMixin {

    @Unique
    private static final Minecraft hyxcate$mc = Minecraft.getMinecraft();

    @Unique
    private static final NyxColorTransition hyxcate$colorTransition = new NyxColorTransition(NyxConfig.GENERAL.eventTintSkyColorDuration);

    @Inject(method = "getSkyColor", at = @At("TAIL"), cancellable = true)
    private static void nyxGetSkyColor(Entity cameraEntity, float partialTicks, CallbackInfoReturnable<Vec3d> cir) {

        if(!NyxConfig.GENERAL.eventTint) {
            return;
        }

        World world = cameraEntity.world;

        if(world == null) {
            return;
        }

        NyxWorld nyxWorld = NyxWorld.get(world);

        if(nyxWorld == null) {
            return;
        }

        float[] initialColors = NyxColorUtils.getVec3dAsFloatArray(cir.getReturnValue());
        long worldTime = world.getWorldTime();

        if(nyxWorld.currentSolarEvent != null && nyxWorld.currentSolarEvent.getSkyColor() != 0) {
            hyxcate$colorTransition.transition(
                    initialColors,
                    NyxColorUtils.getRgbIntAsFloatArray(NyxColorUtils.adjustBrightness(nyxWorld.currentSolarEvent.getSkyColor(), 1.5F)),
                    worldTime,
                    NyxColorTransition.TargetType.CUSTOM_COLOR
            );
        } else if(nyxWorld.currentLunarEvent != null && nyxWorld.currentLunarEvent.getSkyColor() != 0) {
            hyxcate$colorTransition.transition(
                    initialColors,
                    NyxColorUtils.getRgbIntAsFloatArray(NyxColorUtils.adjustBrightness(nyxWorld.currentLunarEvent.getSkyColor(), 1.5F)),
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
            float[] customSkyColors = hyxcate$colorTransition.getCurrentColor(worldTime, hyxcate$mc.getRenderPartialTicks());
            cir.setReturnValue(NyxColorUtils.getFloatArrayAsVec3d(customSkyColors));
        }

    }

}

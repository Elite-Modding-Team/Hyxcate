package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.util.NyxColorTransition;
import de.ellpeck.nyx.util.NyxColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooksClient.class, remap = false)
public abstract class NyxSkyColorMixin {

    @Unique
    private static final Minecraft hyxcate$mc = Minecraft.getMinecraft();

    @Unique
    private static final NyxColorTransition hyxcate$colorTransition = new NyxColorTransition(NyxConfig.GENERAL.eventTintSkyColorDuration);

    @Shadow
    private static boolean skyInit;

    @Inject(method = "getSkyBlendColour", at = @At("RETURN"), cancellable = true)
    private static void nyxSetSkyColor(World world, BlockPos center, CallbackInfoReturnable<Integer> cir) {

        if(!NyxConfig.GENERAL.eventTint) {
            return;
        }

        NyxWorld nyxWorld = NyxWorld.get(world);

        if(nyxWorld == null || !skyInit) {
            return;
        }

        float[] initialColors = NyxColorUtils.getRgbIntAsFloatArray(cir.getReturnValue());
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
            cir.setReturnValue(NyxColorUtils.getFloatArrayAsRgbInt(customSkyColors));
        }

    }

}

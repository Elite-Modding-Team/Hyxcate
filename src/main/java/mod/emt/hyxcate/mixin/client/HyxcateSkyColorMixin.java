package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.util.HyxcateColorTransition;
import mod.emt.hyxcate.util.HyxcateColorUtils;
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
public abstract class HyxcateSkyColorMixin {

    @Unique
    private static final Minecraft hyxcate$mc = Minecraft.getMinecraft();

    @Unique
    private static final HyxcateColorTransition hyxcate$colorTransition = new HyxcateColorTransition(HyxcateConfig.GENERAL.eventTintSkyColorDuration);

    @Shadow
    private static boolean skyInit;

    @Inject(method = "getSkyBlendColour", at = @At("RETURN"), cancellable = true)
    private static void HyxcateSetSkyColor(World world, BlockPos center, CallbackInfoReturnable<Integer> cir) {

        if(!HyxcateConfig.GENERAL.eventTint) {
            return;
        }

        HyxcateWorld hyxcateWorld = HyxcateWorld.get(world);

        if(hyxcateWorld == null || !skyInit) {
            return;
        }

        float[] initialColors = HyxcateColorUtils.getRgbIntAsFloatArray(cir.getReturnValue());
        long worldTime = world.getWorldTime();

        if(hyxcateWorld.currentSolarEvent != null && hyxcateWorld.currentSolarEvent.getSkyColor() != 0) {
            hyxcate$colorTransition.transition(
                    initialColors,
                    HyxcateColorUtils.getRgbIntAsFloatArray(HyxcateColorUtils.adjustBrightness(hyxcateWorld.currentSolarEvent.getSkyColor(), 1.5F)),
                    worldTime,
                    HyxcateColorTransition.TargetType.CUSTOM_COLOR
            );
        } else if(hyxcateWorld.currentLunarEvent != null && hyxcateWorld.currentLunarEvent.getSkyColor() != 0) {
            hyxcate$colorTransition.transition(
                    initialColors,
                    HyxcateColorUtils.getRgbIntAsFloatArray(HyxcateColorUtils.adjustBrightness(hyxcateWorld.currentLunarEvent.getSkyColor(), 1.5F)),
                    worldTime,
                    HyxcateColorTransition.TargetType.CUSTOM_COLOR
            );
        } else {
            hyxcate$colorTransition.transition(
                    initialColors,
                    worldTime,
                    HyxcateColorTransition.TargetType.DEFAULT_COLOR
            );
        }

        if(hyxcate$colorTransition.isOverriding()) {
            float[] customSkyColors = hyxcate$colorTransition.getCurrentColor(worldTime, hyxcate$mc.getRenderPartialTicks());
            cir.setReturnValue(HyxcateColorUtils.getFloatArrayAsRgbInt(customSkyColors));
        }

    }

}

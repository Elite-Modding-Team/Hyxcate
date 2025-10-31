package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.util.NyxColorUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ForgeHooksClient.class, remap = false)
public abstract class NyxSkyColorMixin {

    @Shadow
    private static int skyRGBMultiplier;

    @Inject(method = "getSkyBlendColour", at = @At("HEAD"))
    private static void nyxSetSkyColor(World world, BlockPos center, CallbackInfoReturnable<Integer> cir) {
        if (!NyxConfig.eventTint) return;
        NyxWorld nyxWorld = NyxWorld.get(world);
        if (nyxWorld == null || nyxWorld.currentSkyColor == 0) return;
        if (nyxWorld.currentLunarEvent != null)
            skyRGBMultiplier = NyxColorUtils.adjustBrightness(nyxWorld.currentLunarEvent.getSkyColor(), 1.5F);
        else if (nyxWorld.currentSolarEvent != null)
            skyRGBMultiplier = NyxColorUtils.adjustBrightness(nyxWorld.currentSolarEvent.getSkyColor(), 1.5F);
    }
}

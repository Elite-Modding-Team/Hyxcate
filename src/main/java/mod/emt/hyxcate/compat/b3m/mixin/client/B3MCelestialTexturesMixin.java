package mod.emt.hyxcate.compat.b3m.mixin.client;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sedridor.B3M.B3M_Core;

@Mixin(value = B3M_Core.class, remap = false)
public abstract class B3MCelestialTexturesMixin {
    @Inject(method = "getMoonTexture", at = @At("HEAD"), cancellable = true)
    private static void b3mGetMoonTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, null, "field_110927_h"));
    }

    @Inject(method = "getSunTexture", at = @At("HEAD"), cancellable = true)
    private static void b3mGetSunTexture(CallbackInfoReturnable<ResourceLocation> cir) {
        cir.setReturnValue(ObfuscationReflectionHelper.getPrivateValue(RenderGlobal.class, null, "field_110928_i"));
    }
}

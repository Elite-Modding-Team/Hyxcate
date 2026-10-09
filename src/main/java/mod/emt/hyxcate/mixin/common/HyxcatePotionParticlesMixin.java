package mod.emt.hyxcate.mixin.common;

import mod.emt.hyxcate.potion.HyxcatePotion;
import net.minecraft.potion.PotionEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// TODO: This also disables the potion icon on the screen :/
@Mixin(PotionEffect.class)
public class HyxcatePotionParticlesMixin {
    @Inject(method = "doesShowParticles()Z", at = @At("HEAD"), cancellable = true)
    private void hyxcate$hidePotionSwirls(CallbackInfoReturnable<Boolean> cir) {
        PotionEffect effect = (PotionEffect) (Object) this;
        if (effect.getPotion() instanceof HyxcatePotion) {
            cir.setReturnValue(false);
        }
    }
}
package mod.emt.hyxcate.mixin.common;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import mod.emt.hyxcate.init.HyxcatePotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityLivingBase.class)
public class HyxcateEntityGlowingMixin {
    @WrapOperation(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z"))
    private boolean hyxcate$astralErosionGlowing(EntityLivingBase entity, Potion potion, Operation<Boolean> original) {
        return original.call(entity, potion) || entity.isPotionActive(HyxcatePotions.ASTRAL_EROSION);
    }
}

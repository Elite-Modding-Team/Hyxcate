package mod.emt.hyxcate.mixin.common;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.celestialevent.solar.SolarEventRedGiant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class HyxcateEntityBrightnessMixin {

    @Shadow
    public World world;

    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    private void HyxcateSetBrightness(CallbackInfoReturnable<Float> cir) {
        HyxcateWorld hyxcateWorld = HyxcateWorld.get(this.world);
        if (this instanceof IMob && hyxcateWorld != null && hyxcateWorld.currentSolarEvent instanceof SolarEventRedGiant) {
            cir.setReturnValue(0.0F);
        }
    }
}

package de.ellpeck.nyx.mixin.common;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.event.solar.NyxEventRedGiant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.IMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class NyxEntityBrightnessMixin {

    @Shadow
    public World world;

    @Inject(method = "getBrightness", at = @At("HEAD"), cancellable = true)
    private void nyxSetBrightness(CallbackInfoReturnable<Float> cir) {
        NyxWorld nyxWorld = NyxWorld.get(this.world);
        if (this instanceof IMob && nyxWorld != null && nyxWorld.currentSolarEvent instanceof NyxEventRedGiant) {
            cir.setReturnValue(0.0F);
        }
    }
}

package de.ellpeck.nyx.mixin.client;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.event.solar.NyxEventRedGiant;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RenderGlobal.class)
public abstract class NyxRenderSkyMixin {

    @Shadow
    private WorldClient world;

    @ModifyConstant(method = "renderSky(FI)V", constant = @Constant(floatValue = 30.0F, ordinal = 6))
    private float nyxRenderSky(float constant) {
        NyxWorld nyxWorld = NyxWorld.get(this.world);
        if (nyxWorld != null && nyxWorld.currentSolarEvent instanceof NyxEventRedGiant) {
            return 90.0F;
        }
        return constant;
    }
}

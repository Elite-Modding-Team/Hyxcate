package mod.emt.hyxcate.mixin.client;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.celestialevent.solar.SolarEventRedGiant;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderGlobal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(RenderGlobal.class)
public abstract class HyxcateRenderSkyMixin {

    @Shadow
    private WorldClient world;

    @ModifyConstant(method = "renderSky(FI)V", constant = @Constant(floatValue = 30.0F, ordinal = 6))
    private float HyxcateRenderSky(float constant) {
        HyxcateWorld hyxcateWorld = HyxcateWorld.get(this.world);
        if (hyxcateWorld != null && hyxcateWorld.currentSolarEvent instanceof SolarEventRedGiant) {
            return 90.0F;
        }
        return constant;
    }
}

package mod.emt.hyxcate.mixin.common;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.event.solar.SolarEventGrimEclipse;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityMob.class)
public abstract class HyxcateMobValidLightLevelMixin {

    @Redirect(method = "isValidLightLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isThundering()Z"))
    private boolean HyxcateSetValidLightLevel(World world) {
        HyxcateWorld hyxcateWorld = HyxcateWorld.get(world);
        if (hyxcateWorld != null && hyxcateWorld.currentSolarEvent instanceof SolarEventGrimEclipse) {
            return true;
        }
        return world.isThundering();
    }
}

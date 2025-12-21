package de.ellpeck.nyx.mixin.common;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.event.solar.NyxEventGrimEclipse;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = EntityMob.class)
public abstract class NyxMobValidLightLevelMixin {

    @Redirect(method = "isValidLightLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isThundering()Z"))
    private boolean nyxSetValidLightLevel(World world) {
        NyxWorld nyxWorld = NyxWorld.get(world);
        if (nyxWorld != null && nyxWorld.currentSolarEvent instanceof NyxEventGrimEclipse) {
            return true;
        }
        return world.isThundering();
    }
}

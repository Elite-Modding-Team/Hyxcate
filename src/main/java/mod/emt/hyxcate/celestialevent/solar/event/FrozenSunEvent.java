package mod.emt.hyxcate.celestialevent.solar.event;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.celestialevent.solar.SolarEventRedGiant;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.mixin.common.HyxcateEntityAccessor;
import mod.emt.hyxcate.util.helpers.ConfigHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// Unused until the actual event is implemented
public class FrozenSunEvent {
    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent.SpecialSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(entity.world);
        if (Hyxcate == null) return;

        if (Hyxcate.currentSolarEvent instanceof SolarEventRedGiant) {
            if (HyxcateConfig.EVENTS_SOLAR.RED_GIANT.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_SOLAR.RED_GIANT.spawnsExtraChance) == 0) {
                ConfigHelper.handleExtraSpawn(entity, "red_giant_spawn", HyxcateData.EXTRA_SPAWNS_RED_GIANT);
            }
            event.setCanceled(ConfigHelper.handleReplacementSpawn(entity, "red_giant_spawn", HyxcateData.REPLACEMENT_SPAWNS_RED_GIANT));

            // TODO: Seems silly and rushed for the event, needs a redo
            // Make immune to fire
            if (!entity.isImmuneToFire()) ((HyxcateEntityAccessor) entity).setIsImmuneToFire(true);
        }
    }
}

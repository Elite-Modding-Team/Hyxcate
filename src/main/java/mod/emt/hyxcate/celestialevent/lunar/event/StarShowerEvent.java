package mod.emt.hyxcate.celestialevent.lunar.event;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventStarShower;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.util.helpers.ConfigHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class StarShowerEvent {
    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(entity.world);
        if (Hyxcate == null || !(entity instanceof IMob || entity instanceof EntityMob)) return;
        if (event.getSpawner() == null && entity.world.canSeeSky(entity.getPosition())) {
            ResourceLocation name = EntityList.getKey(entity);
            if (Hyxcate.currentLunarEvent instanceof LunarEventStarShower) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_STAR_SHOWER.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_STAR_SHOWER.contains(name)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent.SpecialSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(entity.world);
        if (Hyxcate == null) return;
        if (Hyxcate.currentLunarEvent instanceof LunarEventStarShower) {
            if (HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.spawnsExtraChance) == 0) {
                ConfigHelper.handleExtraSpawn(entity, "star_shower_spawn", HyxcateData.EXTRA_SPAWNS_STAR_SHOWER);
            }
            event.setCanceled(ConfigHelper.handleReplacementSpawn(entity, "star_shower_spawn", HyxcateData.REPLACEMENT_SPAWNS_STAR_SHOWER));
        }
    }
}

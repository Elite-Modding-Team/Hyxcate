package mod.emt.hyxcate.celestialevent.solar.event;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.celestialevent.solar.SolarEventGrimEclipse;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.util.helpers.ConfigHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GrimEclipseEvent {
    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        HyxcateWorld Hyxcate = HyxcateWorld.get(entity.world);
        if (Hyxcate == null || !(entity instanceof IMob || entity instanceof EntityMob)) return;
        if (event.getSpawner() == null && entity.world.canSeeSky(entity.getPosition())) {
            ResourceLocation name = EntityList.getKey(entity);
            if (Hyxcate.currentSolarEvent instanceof SolarEventGrimEclipse) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_GRIM_ECLIPSE.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_GRIM_ECLIPSE.contains(name)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public void onSpawn(LivingSpawnEvent.SpecialSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        HyxcateWorld Hyxcate = HyxcateWorld.get(entity.world);
        if (Hyxcate == null) return;
        if (Hyxcate.currentSolarEvent instanceof SolarEventGrimEclipse) {
            if (HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.spawnsExtraChance) == 0) {
                ConfigHelper.handleExtraSpawn(entity, "grim_eclipse_spawn", HyxcateData.EXTRA_SPAWNS_GRIM_ECLIPSE);
            }
            event.setCanceled(ConfigHelper.handleReplacementSpawn(entity, "grim_eclipse_spawn", HyxcateData.REPLACEMENT_SPAWNS_GRIM_ECLIPSE));
        }
    }

    @SubscribeEvent
    public void onSleep(PlayerSleepInBedEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        HyxcateWorld Hyxcate = HyxcateWorld.get(player.world);
        if (Hyxcate != null) {
            if (Hyxcate.currentSolarEvent instanceof SolarEventGrimEclipse) {
                event.setResult(EntityPlayer.SleepResult.NOT_POSSIBLE_NOW); // TODO: Make conditional?
            }
        }
    }
}

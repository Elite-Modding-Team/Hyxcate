package mod.emt.hyxcate.celestialevent.solar.event;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.celestialevent.solar.SolarEventGrimEclipse;
import mod.emt.hyxcate.celestialevent.solar.SolarEventRedGiant;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.mixin.common.HyxcateEntityAccessor;
import mod.emt.hyxcate.util.helpers.ConfigHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RedGiantEvent {
    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(entity.world);
        if (Hyxcate == null || !(entity instanceof IMob || entity instanceof EntityMob)) return;
        if (event.getSpawner() == null && entity.world.canSeeSky(entity.getPosition())) {
            ResourceLocation name = EntityList.getKey(entity);
            if (Hyxcate.currentSolarEvent instanceof SolarEventRedGiant) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_RED_GIANT.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_RED_GIANT.contains(name)) {
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

        if (Hyxcate.currentSolarEvent instanceof SolarEventRedGiant) {
            if (HyxcateConfig.EVENTS_SOLAR.RED_GIANT.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_SOLAR.RED_GIANT.spawnsExtraChance) == 0) {
                ConfigHelper.handleExtraSpawn(entity, "red_giant_spawn", HyxcateData.EXTRA_SPAWNS_RED_GIANT);
            }
            event.setCanceled(ConfigHelper.handleReplacementSpawn(entity, "red_giant_spawn", HyxcateData.REPLACEMENT_SPAWNS_RED_GIANT));

            // TODO: Seems silly and rushed for the event, needs a redo
            // Increase health by 50%, make immune to fire
            IAttributeInstance maxHealthAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            double newMaxHealth = maxHealthAttribute.getBaseValue() * 1.5;
            maxHealthAttribute.setBaseValue(newMaxHealth);
            entity.setHealth((float) newMaxHealth);
            if (!entity.isImmuneToFire()) ((HyxcateEntityAccessor) entity).setIsImmuneToFire(true);
        }
    }

    @SubscribeEvent
    public void onSleep(PlayerSleepInBedEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(player.world);
        if (Hyxcate != null) {
            if (Hyxcate.currentSolarEvent instanceof SolarEventGrimEclipse) {
                event.setResult(EntityPlayer.SleepResult.NOT_POSSIBLE_NOW); // TODO: Make conditional?
            }
        }
    }
}

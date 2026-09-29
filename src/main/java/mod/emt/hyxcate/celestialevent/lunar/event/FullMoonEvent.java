package mod.emt.hyxcate.celestialevent.lunar.event;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventFullMoon;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.util.helpers.ConfigHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FullMoonEvent {
    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(entity.world);
        if (Hyxcate == null || !(entity instanceof IMob || entity instanceof EntityMob)) return;
        if (event.getSpawner() == null && entity.world.canSeeSky(entity.getPosition())) {
            ResourceLocation name = EntityList.getKey(entity);
            if (Hyxcate.currentLunarEvent instanceof LunarEventFullMoon) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_FULL_MOON.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_FULL_MOON.contains(name)) {
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
        if (Hyxcate.currentLunarEvent instanceof LunarEventFullMoon) {
            if (HyxcateConfig.EVENTS_LUNAR.FULL_MOON.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_LUNAR.FULL_MOON.spawnsExtraChance) == 0) {
                ConfigHelper.handleExtraSpawn(entity, "full_moon_spawn", HyxcateData.EXTRA_SPAWNS_FULL_MOON);
            }
            event.setCanceled(ConfigHelper.handleReplacementSpawn(entity, "full_moon_spawn", HyxcateData.REPLACEMENT_SPAWNS_FULL_MOON));

            // Set random effect
            if (HyxcateConfig.EVENTS_LUNAR.FULL_MOON.addPotionEffects) {
                Potion effect = null;
                int i = entity.world.rand.nextInt(20);

                if (i <= 2) {
                    effect = MobEffects.SPEED;
                } else if (i <= 4) {
                    effect = MobEffects.STRENGTH;
                } else if (i <= 6) {
                    effect = MobEffects.REGENERATION;
                } else if (i <= 7) {
                    effect = MobEffects.INVISIBILITY;
                }

                // TODO: Add a configure list of mobs that can and cannot get effects. Maybe we could do this for all events as well
                if (effect != null && !(entity instanceof EntityCreeper))
                    entity.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE));
            }
        }
    }
}

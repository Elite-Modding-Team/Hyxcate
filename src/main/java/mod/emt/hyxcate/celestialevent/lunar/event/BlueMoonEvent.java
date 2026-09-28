package mod.emt.hyxcate.celestialevent.lunar.event;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventBlueMoon;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.util.helpers.ConfigHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Objects;

public class BlueMoonEvent {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;
        if (Objects.requireNonNull(HyxcateWorld.get(player.world)).currentLunarEvent instanceof LunarEventBlueMoon) {
            // Adds Luck II to all players while the blue moon is active
            player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 2, 1, false, false));
        }
    }

    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        HyxcateWorld Hyxcate = HyxcateWorld.get(entity.world);
        if (Hyxcate == null || !(entity instanceof IMob || entity instanceof EntityMob)) return;
        if (event.getSpawner() == null && entity.world.canSeeSky(entity.getPosition())) {
            ResourceLocation name = EntityList.getKey(entity);
            if (Hyxcate.currentLunarEvent instanceof LunarEventBlueMoon) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_BLUE_MOON.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_BLUE_MOON.contains(name)) {
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
        if (Hyxcate.currentLunarEvent instanceof LunarEventBlueMoon) {
            if (HyxcateConfig.EVENTS_LUNAR.BLUE_MOON.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_LUNAR.BLUE_MOON.spawnsExtraChance) == 0) {
                ConfigHelper.handleExtraSpawn(entity, "blue_moon_spawn", HyxcateData.EXTRA_SPAWNS_BLUE_MOON);
            }
            event.setCanceled(ConfigHelper.handleReplacementSpawn(entity, "blue_moon_spawn", HyxcateData.REPLACEMENT_SPAWNS_BLUE_MOON));
        }
    }
}

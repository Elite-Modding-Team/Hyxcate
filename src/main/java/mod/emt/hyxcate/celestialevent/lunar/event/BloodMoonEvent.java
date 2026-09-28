package mod.emt.hyxcate.celestialevent.lunar.event;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventBloodMoon;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.util.helpers.ConfigHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

// TODO: Blood moon exclusive spawns don't work properly yet
public class BloodMoonEvent {
    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        // Deletes monsters spawned by the blood moon after the event is over
        if (HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.mobsVanish && !entity.world.isRemote && HyxcateWorld.isDaytime(entity.world) && entity.getEntityData().getBoolean(Hyxcate.ID + ":blood_moon_spawn")) {
            ((WorldServer) entity.world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX, entity.posY, entity.posZ, 10, 0.5, 1, 0.5, 0);
            entity.setDead();
        }
    }

    @SubscribeEvent
    public void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        HyxcateWorld Hyxcate = HyxcateWorld.get(entity.world);
        if (Hyxcate == null || !(entity instanceof IMob || entity instanceof EntityMob)) return;
        if (event.getSpawner() == null && entity.world.canSeeSky(entity.getPosition())) {
            ResourceLocation name = EntityList.getKey(entity);
            if (Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_BLOOD_MOON.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_BLOOD_MOON.contains(name)) {
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
        if (Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon) {
            if (HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.spawnsExtraChance) == 0) {
                ConfigHelper.handleExtraSpawn(entity, "blood_moon_spawn", HyxcateData.EXTRA_SPAWNS_BLOOD_MOON);
            }
            event.setCanceled(ConfigHelper.handleReplacementSpawn(entity, "blood_moon_spawn", HyxcateData.REPLACEMENT_SPAWNS_BLOOD_MOON));
        }
    }

    @SubscribeEvent
    public void onInteract(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        HyxcateWorld hyxcate = HyxcateWorld.get(world);

        // Prevents sleeping during a blood moon
        if (hyxcate != null && hyxcate.currentLunarEvent instanceof LunarEventBloodMoon && !HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.sleeping && block instanceof BlockBed) {
            player.sendStatusMessage(new TextComponentTranslation("info." + Hyxcate.ID + ".blood_moon_sleeping"), true);
        }
    }

    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        HyxcateWorld Hyxcate = HyxcateWorld.get(player.world);
        if (Hyxcate != null) {
            if (Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon && !HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.sleeping) {
                event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
            }
        }
    }
}

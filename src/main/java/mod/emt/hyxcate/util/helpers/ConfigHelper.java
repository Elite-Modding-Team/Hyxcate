package mod.emt.hyxcate.util.helpers;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventStarShower;
import mod.emt.hyxcate.util.WorldUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ConfigHelper {
    public static final Random RANDOM = new Random();

    public static void handleExtraSpawn(Entity entity, String key, Map<ResourceLocation, List<ResourceLocation>> map) {
        ResourceLocation name = EntityList.getKey(entity);
        if (name != null && map.containsKey(name)) {
            List<ResourceLocation> extras = map.get(name);
            if (!extras.isEmpty()) {
                Entity extra = EntityList.createEntityByIDFromName(extras.get(RANDOM.nextInt(extras.size())), entity.world);
                if (extra instanceof EntityLiving) {
                    doExtraSpawn(entity, key, extra);
                }
            }
        }
    }

    public static void doExtraSpawn(Entity original, String key, Entity extra) {
        String addedSpawnKey = Hyxcate.ID + ":" + key;
        if (!original.getEntityData().getBoolean(addedSpawnKey)) {
            ResourceLocation name = EntityList.getKey(extra);
            if (name != null) {
                for (int x = -2; x <= 2; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            if (x == 0 && y == 0 && z == 0) continue;
                            BlockPos offset = original.getPosition().add(x, y, z);
                            if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, original.world, offset))
                                continue;
                            Entity entity = EntityList.createEntityByIDFromName(name, original.world);
                            if (!(entity instanceof EntityLiving)) continue;
                            EntityLiving living = (EntityLiving) entity;
                            entity.setLocationAndAngles(original.posX + x, original.posY + y, original.posZ + z, MathHelper.wrapDegrees(original.world.rand.nextFloat() * 360), 0);
                            living.rotationYawHead = living.rotationYaw;
                            living.renderYawOffset = living.rotationYaw;
                            original.getEntityData().setBoolean(addedSpawnKey, true);
                            if (!ForgeEventFactory.doSpecialSpawn(living, original.world, (float) original.posX + x, (float) original.posY + y, (float) original.posZ + z, null))
                                living.onInitialSpawn(original.world.getDifficultyForLocation(new BlockPos(living)), null);
                            original.world.spawnEntity(entity);
                            return;
                        }
                    }
                }
            }
        }
    }

    public static boolean handleReplacementSpawn(Entity entity, String key, Map<ResourceLocation, List<ResourceLocation>> map) {
        ResourceLocation name = EntityList.getKey(entity);
        if (name != null && map.containsKey(name)) {
            List<ResourceLocation> replacements = map.get(name);
            if (!replacements.isEmpty()) {
                Entity replacement = EntityList.createEntityByIDFromName(replacements.get(RANDOM.nextInt(replacements.size())), entity.world);
                if (replacement instanceof EntityLiving) {
                    doReplacementSpawn(entity, key, (EntityLiving) replacement);
                    return true;
                }
            }
        }
        return false;
    }

    public static void doReplacementSpawn(Entity original, String key, EntityLiving replacement) {
        String addedSpawnKey = Hyxcate.ID + ":" + key;
        if (!original.getEntityData().getBoolean(addedSpawnKey)) {
            ResourceLocation name = EntityList.getKey(original);
            if (name != null) {
                if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, original.world, original.getPosition()))
                    return;
                replacement.setLocationAndAngles(original.posX, original.posY, original.posZ, MathHelper.wrapDegrees(original.world.rand.nextFloat() * 360), 0);
                replacement.rotationYawHead = replacement.rotationYaw;
                replacement.renderYawOffset = replacement.rotationYaw;
                original.getEntityData().setBoolean(addedSpawnKey, true);
                if (!ForgeEventFactory.doSpecialSpawn(replacement, original.world, (float) original.posX, (float) original.posY, (float) original.posZ, null))
                    replacement.onInitialSpawn(original.world.getDifficultyForLocation(new BlockPos(replacement)), null);
                original.world.spawnEntity(replacement);
            }
        }
    }

    public static double getMeteorChance(World world, CapabilityCelestialEvent data) {
        DimensionType dim = world.provider.getDimensionType();
        if (dim == DimensionType.THE_END) return HyxcateConfig.METEORS.chanceEndM;
        if (!HyxcateData.ALLOWED_DIMENSIONS_LUNAR.contains(dim.getId())) return 0;
        boolean visitedGate = data.visitedDimensions.contains(DimensionType.getById(HyxcateConfig.METEORS.gateDimension).getName());
        if (!WorldUtil.isDaytime(world)) {
            if (data.currentLunarEvent instanceof LunarEventStarShower) {
                return HyxcateConfig.METEORS.chanceStarShowerM;
            } else {
                return visitedGate ? HyxcateConfig.METEORS.chanceAfterGateNightM : HyxcateConfig.METEORS.chanceNightM;
            }
        }
        return visitedGate ? HyxcateConfig.METEORS.chanceAfterGateM : HyxcateConfig.METEORS.chanceM;
    }
}

package de.ellpeck.nyx.util;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.client.sound.NyxSoundBeamSword;
import de.ellpeck.nyx.client.sound.NyxSoundCelestialWarhammer;
import de.ellpeck.nyx.client.sound.NyxSoundFallenEntity;
import de.ellpeck.nyx.client.sound.NyxSoundFallingEntity;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.config.NyxData;
import de.ellpeck.nyx.event.lunar.NyxEventStarShower;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Map;
import java.util.Random;

// Courtesy of UeberallGebannt for the chance methods
public class NyxUtils {
    public static final Random RANDOM = new Random();

    public static ItemStack checkNBT(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }

        return stack;
    }

    /**
     * Returns true with a certain chance
     *
     * @param chance The chance to return true
     * @param random The random instance to be used
     * @return true with a certain chance or false
     */
    public static boolean setChance(double chance, Random random) {
        double value = random.nextDouble();
        return value <= chance;
    }

    /**
     * Returns true with a certain chance
     *
     * @param chance The chance to return true
     * @return true with a certain chance or false
     */
    public static boolean setChance(double chance) {
        return setChance(chance, RANDOM);
    }

    public static ItemStack setUnbreakable(ItemStack stack) {
        checkNBT(stack);

        if (!stack.getTagCompound().hasKey("Unbreakable")) {
            stack.getTagCompound().setBoolean("Unbreakable", true);
        }

        return stack;
    }

    public static boolean handleExtraSpawn(Entity entity, String key, Map<ResourceLocation, List<ResourceLocation>> map) {
        ResourceLocation name = EntityList.getKey(entity);
        if (name != null && map.containsKey(name)) {
            List<ResourceLocation> extras = map.get(name);
            if (!extras.isEmpty()) {
                Entity extra = EntityList.createEntityByIDFromName(extras.get(RANDOM.nextInt(extras.size())), entity.world);
                if (extra instanceof EntityLiving) {
                    doExtraSpawn(entity, key, extra);
                    return true;
                }
            }
        }
        return false;
    }

    public static void doExtraSpawn(Entity original, String key, Entity extra) {
        String addedSpawnKey = Nyx.ID + ":" + key;
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
                            if (!(entity instanceof EntityLiving)) return;
                            EntityLiving living = (EntityLiving) entity;
                            entity.setLocationAndAngles(original.posX + x, original.posY + y, original.posZ + z, MathHelper.wrapDegrees(original.world.rand.nextFloat() * 360), 0);
                            living.rotationYawHead = living.rotationYaw;
                            living.renderYawOffset = living.rotationYaw;
                            living.getEntityData().setBoolean(addedSpawnKey, true);
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
        String addedSpawnKey = Nyx.ID + ":" + key;
        if (!original.getEntityData().getBoolean(addedSpawnKey)) {
            ResourceLocation name = EntityList.getKey(original);
            if (name != null) {
                if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, original.world, original.getPosition()))
                    return;
                replacement.setLocationAndAngles(original.posX, original.posY, original.posZ, MathHelper.wrapDegrees(original.world.rand.nextFloat() * 360), 0);
                replacement.rotationYawHead = replacement.rotationYaw;
                replacement.renderYawOffset = replacement.rotationYaw;
                replacement.getEntityData().setBoolean(addedSpawnKey, true);
                if (!ForgeEventFactory.doSpecialSpawn(replacement, original.world, (float) original.posX, (float) original.posY, (float) original.posZ, null))
                    replacement.onInitialSpawn(original.world.getDifficultyForLocation(new BlockPos(replacement)), null);
                original.world.spawnEntity(replacement);
            }
        }
    }

    public static double getMeteorChance(World world, NyxWorld data) {
        DimensionType dim = world.provider.getDimensionType();
        if (dim == DimensionType.THE_END) return NyxConfig.METEORS.chanceEndM;
        if (!NyxData.ALLOWED_DIMENSIONS_LUNAR.contains(dim.getId())) return 0;
        boolean visitedGate = data.visitedDimensions.contains(DimensionType.getById(NyxConfig.METEORS.gateDimension).getName());
        if (!NyxWorld.isDaytime(world)) {
            if (data.currentLunarEvent instanceof NyxEventStarShower) {
                return NyxConfig.METEORS.chanceStarShowerM;
            } else {
                return visitedGate ? NyxConfig.METEORS.chanceAfterGateNightM : NyxConfig.METEORS.chanceNightM;
            }
        }
        return visitedGate ? NyxConfig.METEORS.chanceAfterGateM : NyxConfig.METEORS.chanceM;
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundBeamSword(ItemStack stack) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundBeamSword(stack));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundFallenStar(EntityItem entityItem) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundFallenEntity(entityItem, NyxSoundEvents.ENTITY_STAR_IDLE.getSoundEvent(), 1F));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundFallingMeteor(Entity entity) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundFallingEntity(entity, NyxSoundEvents.ENTITY_METEOR_FALLING.getSoundEvent(), 5F));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundFallingStar(Entity entity) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundFallingEntity(entity, NyxSoundEvents.ENTITY_STAR_FALLING.getSoundEvent(), (float) NyxConfig.FALLING_STARS.volumeAmbient));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundWarhammer(World world) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundCelestialWarhammer(1.35F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F)));
    }
}

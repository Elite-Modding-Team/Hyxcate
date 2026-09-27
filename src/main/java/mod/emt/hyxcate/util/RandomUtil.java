package mod.emt.hyxcate.util;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.client.sound.HyxcateSoundBeamSword;
import mod.emt.hyxcate.client.sound.HyxcateSoundCelestialWarhammer;
import mod.emt.hyxcate.client.sound.HyxcateSoundFallenEntity;
import mod.emt.hyxcate.client.sound.HyxcateSoundFallingEntity;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.event.lunar.LunarEventStarShower;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
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
public class RandomUtil {
    public static final Random RANDOM = new Random();

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
}

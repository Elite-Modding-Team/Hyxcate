package de.ellpeck.nyx.util;

import de.ellpeck.nyx.client.sound.NyxSoundBeamSword;
import de.ellpeck.nyx.client.sound.NyxSoundCelestialWarhammer;
import de.ellpeck.nyx.client.sound.NyxSoundFallenEntity;
import de.ellpeck.nyx.client.sound.NyxSoundFallingEntity;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundFallingEntity(entity, NyxSoundEvents.ENTITY_STAR_FALLING.getSoundEvent(), (float) NyxConfig.fallingStarAmbientVolume));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSoundWarhammer(World world) {
        Minecraft.getMinecraft().getSoundHandler().playSound(new NyxSoundCelestialWarhammer(1.35F, 1.0F / (world.rand.nextFloat() * 0.4F + 0.8F)));
    }
}

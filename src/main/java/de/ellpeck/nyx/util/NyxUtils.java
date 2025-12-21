package de.ellpeck.nyx.util;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
}

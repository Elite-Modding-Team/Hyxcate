package mod.emt.hyxcate.util.helpers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class NBTHelper {
    public static final Random RANDOM = new Random();

    public static void checkNBT(ItemStack stack) {
        if (stack.getTagCompound() == null) {
            stack.setTagCompound(new NBTTagCompound());
        }
    }

    public static void setUnbreakable(ItemStack stack) {
        checkNBT(stack);
        if (!stack.getTagCompound().hasKey("Unbreakable")) {
            stack.getTagCompound().setBoolean("Unbreakable", true);
        }
    }
}

package mod.emt.hyxcate.compat.urkazmoontools;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventBloodMoon;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventBlueMoon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class HyxcateMoonPhaseGetter implements IItemPropertyGetter {
    @SideOnly(Side.CLIENT)
    public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
        boolean flag = entityIn != null;
        Entity entity = flag ? entityIn : stack.getItemFrame();

        if (worldIn == null && entity != null) {
            worldIn = entity.world;
        }

        if (worldIn == null) {
            return 0;
        } else {
            int moonFactor;
            if (worldIn.provider.isSurfaceWorld()) {
                int extras = 0;
                CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(worldIn);
                if (Hyxcate != null && Hyxcate.currentLunarEvent instanceof LunarEventBlueMoon) {
                    extras += 100;
                }
                if (Hyxcate != null && Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon) {
                    extras += 10;
                }

                moonFactor = worldIn.provider.getMoonPhase(worldIn.getWorldTime()) + extras;
            } else {
                double randomDouble = Math.random();
                randomDouble = randomDouble * 8;
                moonFactor = (int) randomDouble;
            }
            return moonFactor;
        }
    }
}

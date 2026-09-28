package mod.emt.hyxcate.compat.urkazmoontools.mixin.common;

import com.urkaz.moontools.ModSettings;
import com.urkaz.moontools.block.MoonSensorBlock;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventBloodMoon;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventBlueMoon;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(value = MoonSensorBlock.class, remap = false)
public abstract class UMTMoonSensorBlockMixin {

    /**
     * @author ACGaming
     * @reason Urkaz Moon Tools compat
     */
    @Overwrite
    protected int signal(World worldIn, BlockPos pos) {
        if (worldIn == null || !worldIn.provider.isSurfaceWorld()) {
            return 0;
        } else {
            //Check events and mod compatibility
            boolean isBloodMoon = false;
            boolean isHarvestMoon = false;
            if (ModSettings.EmmitExtraRedstoneOnLunarEvent) {
                HyxcateWorld Hyxcate = HyxcateWorld.get(worldIn);
                if (Hyxcate != null && Hyxcate.currentLunarEvent instanceof LunarEventBlueMoon) {
                    isHarvestMoon = true;
                }
                if (Hyxcate != null && Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon) {
                    isBloodMoon = true;
                }
            }

            //Check if is night
            long wt = worldIn.provider.getWorldTime();
            boolean isNight = true;
            if (ModSettings.SensorOnlyNight) {
                isNight = wt % 24000L >= 12000L;
            }

            //Get Redstone value
            if (ModSettings.SensorPhasesShifted) {
                int moonPhase = worldIn.provider.getMoonPhase(wt - 24000);
                if (wt - 24000 < 0) {
                    moonPhase = 7;
                }
                if (worldIn.canBlockSeeSky(pos) && isNight) {
                    if (isBloodMoon) {
                        return 9;
                    } else if (isHarvestMoon) {
                        return 10;
                    } else return 1 + moonPhase;
                } else return 0;
            } else {
                if (worldIn.canBlockSeeSky(pos) && isNight) {
                    if (isBloodMoon) {
                        return 9;
                    } else if (isHarvestMoon) {
                        return 10;
                    } else return 1 + worldIn.provider.getMoonPhase(wt);
                } else return 0;
            }
        }
    }
}

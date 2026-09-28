package mod.emt.hyxcate.compat.simpledifficulty.modifier;

import com.charles445.simpledifficulty.temperature.ModifierBase;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.celestialevent.solar.SolarEventRedGiant;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SDTemperatureModifier extends ModifierBase {
    public SDTemperatureModifier(String id) {
        super(id);
    }

    @Override
    public String getName() {
        return "Hyxcate Event";
    }

    @Override
    public float getWorldInfluence(World world, BlockPos pos) {
        HyxcateWorld data = HyxcateWorld.get(world);
        if (data != null && data.currentSolarEvent instanceof SolarEventRedGiant) {
            return applyUndergroundEffect(HyxcateConfig.MOD_INTEGRATION.SIMPLE_DIFFICULTY.redGiantTemperature, world, pos); // Default Nether: 10
        }
        return super.getWorldInfluence(world, pos);
    }
}

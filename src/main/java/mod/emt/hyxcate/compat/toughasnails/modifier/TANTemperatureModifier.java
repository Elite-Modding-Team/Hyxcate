package mod.emt.hyxcate.compat.toughasnails.modifier;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.event.solar.SolarEventRedGiant;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import toughasnails.api.temperature.IModifierMonitor;
import toughasnails.api.temperature.Temperature;
import toughasnails.temperature.modifier.TemperatureModifier;

public class TANTemperatureModifier extends TemperatureModifier {
    public TANTemperatureModifier(String id) {
        super(id);
    }

    @Override
    public Temperature applyEnvironmentModifiers(World world, BlockPos pos, Temperature initialTemperature, IModifierMonitor monitor) {
        HyxcateWorld data = HyxcateWorld.get(world);
        if (data != null && data.currentSolarEvent instanceof SolarEventRedGiant) {
            int newTemperatureLevel = HyxcateConfig.MOD_INTEGRATION.TAN.redGiantTemperature; // Default Nether: 22
            monitor.addEntry(new IModifierMonitor.Context(this.getId(), "Hyxcate Event", initialTemperature, new Temperature(newTemperatureLevel)));
            return new Temperature(newTemperatureLevel);
        }
        return initialTemperature;
    }

    @Override
    public boolean isPlayerSpecific() {
        return false;
    }
}

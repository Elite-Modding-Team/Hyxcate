package de.ellpeck.nyx.compat.toughasnails.modifier;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.event.solar.NyxEventRedGiant;
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
        NyxWorld data = NyxWorld.get(world);
        if (data != null && data.currentSolarEvent instanceof NyxEventRedGiant) {
            int newTemperatureLevel = 22; // Default Nether: 22
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

package de.ellpeck.nyx.compat.simpledifficulty.modifier;

import com.charles445.simpledifficulty.temperature.ModifierBase;
import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.event.solar.NyxEventRedGiant;
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
        NyxWorld data = NyxWorld.get(world);
        if (data != null && data.currentSolarEvent instanceof NyxEventRedGiant) {
            return applyUndergroundEffect(10, world, pos); // Default Nether: 10
        }
        return super.getWorldInfluence(world, pos);
    }
}

package mod.emt.hyxcate.compat.astralsorcery;

import hellfirepvp.astralsorcery.common.constellation.distribution.ConstellationSkyHandler;
import hellfirepvp.astralsorcery.common.constellation.distribution.WorldSkyHandler;
import net.minecraft.world.World;

public class AstralSorcery {

    public static boolean isDayOfLunarEclipse(World world) {
        WorldSkyHandler handler = ConstellationSkyHandler.getInstance().getWorldHandler(world);
        return handler != null && handler.dayOfLunarEclipse;
    }

    public static boolean isDayOfSolarEclipse(World world) {
        WorldSkyHandler handler = ConstellationSkyHandler.getInstance().getWorldHandler(world);
        return handler != null && handler.dayOfSolarEclipse;
    }
}

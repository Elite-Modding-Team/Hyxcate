package mod.emt.hyxcate.proxy;

import mod.emt.hyxcate.init.HyxcateCelestialEvents;
import mod.emt.hyxcate.init.HyxcateEvents;
import net.minecraft.util.math.BlockPos;

public class CommonProxy {
    public void preInit() {
    }

    public void init() {
        HyxcateCelestialEvents.registerCelestialEvents();
        HyxcateEvents.registerEvents();
    }

    public void postInit() {
    }

    public void sendBreakPacket(BlockPos pos) {
    }
}

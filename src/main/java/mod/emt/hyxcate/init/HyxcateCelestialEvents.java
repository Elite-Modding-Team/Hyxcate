package mod.emt.hyxcate.init;

import mod.emt.hyxcate.celestialevent.lunar.event.BloodMoonEvent;
import mod.emt.hyxcate.celestialevent.lunar.event.BlueMoonEvent;
import mod.emt.hyxcate.celestialevent.lunar.event.FullMoonEvent;
import mod.emt.hyxcate.celestialevent.lunar.event.StarShowerEvent;
import mod.emt.hyxcate.celestialevent.solar.event.GrimEclipseEvent;
import mod.emt.hyxcate.celestialevent.solar.event.RedGiantEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import net.minecraftforge.common.MinecraftForge;

public class HyxcateCelestialEvents {
    public static void registerCelestialEvents() {
        if (HyxcateConfig.MASTER_SWITCHES.lunarEventsEnabled) {
            MinecraftForge.EVENT_BUS.register(new BloodMoonEvent());
            MinecraftForge.EVENT_BUS.register(new BlueMoonEvent());
            MinecraftForge.EVENT_BUS.register(new FullMoonEvent());
            MinecraftForge.EVENT_BUS.register(new StarShowerEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.solarEventsEnabled) {
            MinecraftForge.EVENT_BUS.register(new GrimEclipseEvent());
            MinecraftForge.EVENT_BUS.register(new RedGiantEvent());
        }
    }
}

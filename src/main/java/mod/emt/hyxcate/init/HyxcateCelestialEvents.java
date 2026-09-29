package mod.emt.hyxcate.init;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.api.celestialevent.HyxcateCelestialEventRegistry;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventBloodMoon;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventBlueMoon;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventFullMoon;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventStarShower;
import mod.emt.hyxcate.celestialevent.lunar.event.BloodMoonEvent;
import mod.emt.hyxcate.celestialevent.lunar.event.BlueMoonEvent;
import mod.emt.hyxcate.celestialevent.lunar.event.FullMoonEvent;
import mod.emt.hyxcate.celestialevent.lunar.event.StarShowerEvent;
import mod.emt.hyxcate.celestialevent.solar.SolarEventGrimEclipse;
import mod.emt.hyxcate.celestialevent.solar.SolarEventRedGiant;
import mod.emt.hyxcate.celestialevent.solar.event.GrimEclipseEvent;
import mod.emt.hyxcate.celestialevent.solar.event.RedGiantEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import net.minecraftforge.common.MinecraftForge;

public class HyxcateCelestialEvents {
    public static void registerCelestialEvents() {
        if (HyxcateConfig.MASTER_SWITCHES.lunarEventsEnabled) {
            HyxcateCelestialEventRegistry.registerLunar(Hyxcate.ID + ":blue_moon", LunarEventBlueMoon::new);
            HyxcateCelestialEventRegistry.registerLunar(Hyxcate.ID + ":star_shower", LunarEventStarShower::new);
            HyxcateCelestialEventRegistry.registerLunar(Hyxcate.ID + ":blood_moon", LunarEventBloodMoon::new);
            // This needs to stay at the end to prioritize random events
            HyxcateCelestialEventRegistry.registerLunar(Hyxcate.ID + ":full_moon", 0, LunarEventFullMoon::new);

            MinecraftForge.EVENT_BUS.register(new BloodMoonEvent());
            MinecraftForge.EVENT_BUS.register(new BlueMoonEvent());
            MinecraftForge.EVENT_BUS.register(new FullMoonEvent());
            MinecraftForge.EVENT_BUS.register(new StarShowerEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.solarEventsEnabled) {
            HyxcateCelestialEventRegistry.registerSolar(Hyxcate.ID + ":red_giant", SolarEventRedGiant::new);
            HyxcateCelestialEventRegistry.registerSolar(Hyxcate.ID + ":grim_eclipse", SolarEventGrimEclipse::new);

            MinecraftForge.EVENT_BUS.register(new GrimEclipseEvent());
            MinecraftForge.EVENT_BUS.register(new RedGiantEvent());
        }
    }
}

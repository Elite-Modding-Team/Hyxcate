package mod.emt.hyxcate.init;

import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.event.*;
import mod.emt.hyxcate.event.attribute.*;
import mod.emt.hyxcate.event.client.BowFOVEvent;
import mod.emt.hyxcate.event.client.CelestialWorldEvent;
import mod.emt.hyxcate.event.client.F3InfoEvent;
import mod.emt.hyxcate.event.enchantment.*;
import mod.emt.hyxcate.event.entity.EyezorEvent;
import mod.emt.hyxcate.event.entity.WolfEvent;
import mod.emt.hyxcate.event.potion.DeepFreezeEvent;
import mod.emt.hyxcate.event.potion.ParalysisEvent;
import mod.emt.hyxcate.event.tool.BeamSwordEvent;
import mod.emt.hyxcate.event.tool.CelestialWarhammerEvent;
import mod.emt.hyxcate.event.tool.FrezariteToolEvent;
import mod.emt.hyxcate.event.tool.KreknoriteToolEvent;
import mod.emt.hyxcate.event.world.FallingMeteorEvent;
import mod.emt.hyxcate.event.world.FallingStarEvent;
import net.minecraftforge.common.MinecraftForge;

public class HyxcateEvents {
    public static void registerEvents() {
        if (HyxcateConfig.GENERAL.f3Info) {
            MinecraftForge.EVENT_BUS.register(new F3InfoEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.beamSwordsEnabled) {
            MinecraftForge.EVENT_BUS.register(new BeamSwordEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.celestialWarhammerEnabled) {
            MinecraftForge.EVENT_BUS.register(new CelestialWarhammerEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.enchantmentsEnabled) {
            MinecraftForge.EVENT_BUS.register(new LunarEdgeEvent());
            MinecraftForge.EVENT_BUS.register(new LunarShieldEvent());
            MinecraftForge.EVENT_BUS.register(new MagnetiferousEvent());
            MinecraftForge.EVENT_BUS.register(new SolarEdgeEvent());
            MinecraftForge.EVENT_BUS.register(new SolarShieldEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.fallingStarEventsEnabled) {
            MinecraftForge.EVENT_BUS.register(new FallingStarEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.lunarEventsEnabled) {
            MinecraftForge.EVENT_BUS.register(new WolfEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.meteorEventsEnabled) {
            MinecraftForge.EVENT_BUS.register(new FallingMeteorEvent());
        }

        if (HyxcateConfig.MASTER_SWITCHES.meteorGearEnabled) {
            MinecraftForge.EVENT_BUS.register(new ArmorEvent());
            MinecraftForge.EVENT_BUS.register(new FrezariteToolEvent());
            MinecraftForge.EVENT_BUS.register(new KreknoriteToolEvent());
        }

        MinecraftForge.EVENT_BUS.register(new AnvilRepairEvent());
        MinecraftForge.EVENT_BUS.register(new CraftingEvent());
        MinecraftForge.EVENT_BUS.register(new DamageSourceEvent());

        // Attributes
        MinecraftForge.EVENT_BUS.register(new ExplosionResistanceEvent());
        MinecraftForge.EVENT_BUS.register(new LunarDamageEvent());
        MinecraftForge.EVENT_BUS.register(new LunarWardEvent());
        MinecraftForge.EVENT_BUS.register(new MagnetizationEvent());
        MinecraftForge.EVENT_BUS.register(new ParalysisChanceEvent());
        MinecraftForge.EVENT_BUS.register(new SolarDamageEvent());
        MinecraftForge.EVENT_BUS.register(new SolarWardEvent());

        // Client
        MinecraftForge.EVENT_BUS.register(new BowFOVEvent());

        // Entities
        MinecraftForge.EVENT_BUS.register(new EyezorEvent());

        // Potions
        MinecraftForge.EVENT_BUS.register(new DeepFreezeEvent());
        MinecraftForge.EVENT_BUS.register(new ParalysisEvent());

        // World
        MinecraftForge.EVENT_BUS.register(new CelestialWorldEvent());
        MinecraftForge.EVENT_BUS.register(new WorldEvent());
    }
}

package mod.emt.hyxcate.compat.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IWorld;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.event.lunar.LunarEventBloodMoon;
import mod.emt.hyxcate.event.lunar.LunarEventBlueMoon;
import mod.emt.hyxcate.event.lunar.LunarEventFullMoon;
import mod.emt.hyxcate.event.lunar.LunarEventStarShower;
import mod.emt.hyxcate.event.solar.SolarEventGrimEclipse;
import mod.emt.hyxcate.event.solar.SolarEventRedGiant;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethod;

@SuppressWarnings("unused")
@ZenRegister
@ZenExpansion("crafttweaker.world.IWorld")
public class CraftTweaker {
    @ZenMethod
    public static boolean isHyxcateBloodMoon(IWorld world) {
        World mcWorld = CraftTweakerMC.getWorld(world);
        HyxcateWorld data = HyxcateWorld.get(mcWorld);
        return data != null && data.currentLunarEvent instanceof LunarEventBloodMoon;
    }

    @ZenMethod
    public static boolean isHyxcateBlueMoon(IWorld world) {
        World mcWorld = CraftTweakerMC.getWorld(world);
        HyxcateWorld data = HyxcateWorld.get(mcWorld);
        return data != null && data.currentLunarEvent instanceof LunarEventBlueMoon;
    }

    @ZenMethod
    public static boolean isHyxcateFullMoon(IWorld world) {
        World mcWorld = CraftTweakerMC.getWorld(world);
        HyxcateWorld data = HyxcateWorld.get(mcWorld);
        return data != null && data.currentLunarEvent instanceof LunarEventFullMoon;
    }

    @ZenMethod
    public static boolean isHyxcateStarShower(IWorld world) {
        World mcWorld = CraftTweakerMC.getWorld(world);
        HyxcateWorld data = HyxcateWorld.get(mcWorld);
        return data != null && data.currentLunarEvent instanceof LunarEventStarShower;
    }

    @ZenMethod
    public static boolean isHyxcateGrimEclipse(IWorld world) {
        World mcWorld = CraftTweakerMC.getWorld(world);
        HyxcateWorld data = HyxcateWorld.get(mcWorld);
        return data != null && data.currentSolarEvent instanceof SolarEventGrimEclipse;
    }

    @ZenMethod
    public static boolean isHyxcateRedGiant(IWorld world) {
        World mcWorld = CraftTweakerMC.getWorld(world);
        HyxcateWorld data = HyxcateWorld.get(mcWorld);
        return data != null && data.currentSolarEvent instanceof SolarEventRedGiant;
    }
}

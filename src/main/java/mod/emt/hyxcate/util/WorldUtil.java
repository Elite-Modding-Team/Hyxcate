package mod.emt.hyxcate.util;

import net.minecraft.world.World;

public class WorldUtil {
    public static boolean isDaytime(World world) {
        return !isNighttime(world);
    }

    public static boolean isNighttime(World world) {
        // https://minecraft.wiki/w/Daylight_cycle#24-hour_Minecraft_day
        // 12786: Solar zenith angle is 0 (beginning of night)
        // 23216: Solar zenith angle is 0 (end of night)
        long time = world.getWorldTime() % 24000;
        return time >= 12786 && time < 23216;
    }

    public static boolean shouldStartLunar(World world, boolean wasDaytime) {
        return wasDaytime && isNighttime(world);
    }

    public static boolean shouldStartSolar(World world, boolean wasNighttime) {
        return wasNighttime && isDaytime(world);
    }
}

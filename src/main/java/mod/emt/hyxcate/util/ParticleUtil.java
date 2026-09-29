package mod.emt.hyxcate.util;

import mod.emt.hyxcate.Hyxcate;
import net.minecraft.world.World;

public class ParticleUtil {
    public static void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime) {
        Hyxcate.proxy.spawnParticleGlow(world, x, y, z, vx, vy, vz, r, g, b, a, scale, lifetime);
    }

    public static void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float scale, int lifetime) {
        Hyxcate.proxy.spawnParticleGlow(world, x, y, z, vx, vy, vz, r, g, b, scale, lifetime);
    }

    public static void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
        Hyxcate.proxy.spawnParticleGlow(world, x, y, z, vx, vy, vz, a, scale, lifetime, transitionColors);
    }

    public static void spawnStarSpark(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
        Hyxcate.proxy.spawnStarSpark(world, x, y, z, vx, vy, vz, a, scale, lifetime, transitionColors);
    }

    public static void spawnParticleGlowBurst(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime, boolean growth) {
        Hyxcate.proxy.spawnParticleGlowBurst(world, x, y, z, vx, vy, vz, r, g, b, a, scale, lifetime, growth);
    }
}

package mod.emt.hyxcate.proxy;

import mod.emt.hyxcate.init.HyxcateCelestialEvents;
import mod.emt.hyxcate.init.HyxcateEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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

    /* Particles */
    public void spawnCrystalEnergy(World world, float x, float y, float z, double angle, double radius, double rotationSpeed, float a, float scale, int lifetime, int[][] transitionColors) {
    }

    public void spawnParticleFire(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
    }

    public void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime) {
    }

    public void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float scale, int lifetime) {
    }

    public void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
    }

    public void spawnParticleGlowBurst(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime, boolean growth) {
    }

    public void spawnStarShooting(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
    }

    public void spawnStarSpark(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
    }
}

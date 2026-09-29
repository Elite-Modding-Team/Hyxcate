package mod.emt.hyxcate.proxy;

import mod.emt.hyxcate.client.particle.*;
import mod.emt.hyxcate.init.HyxcateEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class ClientProxy extends CommonProxy {
    public static Random random = new Random();
    static int particleCounter;

    @Override
    public void preInit() {
        super.preInit();
        HyxcateEvents.registerParticleEvent();
    }

    @Override
    public void init() {
        super.init();
        HyxcateEvents.registerClientEvents();
    }

    @Override
    public void postInit() {
        super.postInit();
    }

    @Override
    public void sendBreakPacket(BlockPos pos) {
        NetHandlerPlayClient netHandlerPlayClient = Minecraft.getMinecraft().getConnection();
        assert netHandlerPlayClient != null;
        netHandlerPlayClient.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, Minecraft.getMinecraft().objectMouseOver.sideHit));
    }

    /* Particles */
    @Override
    public void spawnCrystalEnergy(World world, float x, float y, float z, double angle, double radius, double rotationSpeed, float a, float scale, int lifetime, int[][] transitionColors) {
        particleCounter += random.nextInt(3);
        if (particleCounter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
            ParticleRenderer.INSTANCE.addParticle(new ParticleCrystalEnergy(world, x, y, z, angle, radius, rotationSpeed, a, scale, lifetime, transitionColors));
        }
    }

    @Override
    public void spawnParticleFire(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
        particleCounter += random.nextInt(3);
        if (particleCounter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
            ParticleRenderer.INSTANCE.addParticle(new ParticleFire(world, x, y, z, vx, vy, vz, a, scale, lifetime, transitionColors));
        }
    }

    @Override
    public void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime) {
        particleCounter += random.nextInt(3);
        if (particleCounter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
            ParticleRenderer.INSTANCE.addParticle(new ParticleGlow(world, x, y, z, vx, vy, vz, r, g, b, a, scale, lifetime));
        }
    }

    @Override
    public void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
        particleCounter += random.nextInt(3);
        if (particleCounter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
            ParticleRenderer.INSTANCE.addParticle(new ParticleGlow(world, x, y, z, vx, vy, vz, a, scale, lifetime, transitionColors));
        }
    }

    @Override
    public void spawnParticleGlowBurst(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale, int lifetime, boolean growth) {
        particleCounter += random.nextInt(3);
        if (particleCounter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
            ParticleRenderer.INSTANCE.addParticle(new ParticleGlow(world, x, y, z, vx, vy, vz, r, g, b, a, scale, lifetime, growth));
        }
    }

    @Override
    public void spawnStarSpark(World world, float x, float y, float z, float vx, float vy, float vz, float a, float scale, int lifetime, int[][] transitionColors) {
        particleCounter += random.nextInt(3);
        if (particleCounter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
            ParticleRenderer.INSTANCE.addParticle(new ParticleStarSpark(world, x, y, z, vx, vy, vz, a, scale, lifetime, transitionColors));
        }
    }
}

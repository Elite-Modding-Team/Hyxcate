package mod.emt.hyxcate.client.particle;

import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.world.World;

public class ParticleCrystalEnergy extends ParticleGlow {
    private final double centerX;
    private final double centerY;
    private final double centerZ;
    private double angle;
    private final double maxRadius;
    private final double rotationSpeed;
    private double currentRadius;
    private final double verticalOffset;
    private final double verticalSpeed;
    private final double verticalAmplitude;

    public ParticleCrystalEnergy(World world, double x, double y, double z, double angle, double radius, double rotationSpeed, float alpha, float scale, int lifetime, int[][] transitionColors) {
        super(world, x, y, z, 0, 0, 0, alpha, scale, lifetime, transitionColors);
        this.centerX = x;
        this.centerY = y;
        this.centerZ = z;
        this.angle = angle;
        this.maxRadius = radius;
        this.currentRadius = 0.05D;
        this.rotationSpeed = rotationSpeed;
        this.verticalOffset = this.rand.nextDouble() * Math.PI * 2.0D;
        this.verticalSpeed = 0.12D + this.rand.nextDouble() * 0.06D;
        this.verticalAmplitude = 0.20D + this.rand.nextDouble() * 0.15D;
        this.motionX = 0;
        this.motionY = 0;
        this.motionZ = 0;
        this.particleAngle = 0.0F;
        this.prevParticleAngle = 0.0F;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.angle += this.rotationSpeed;
        this.currentRadius = Math.min(this.maxRadius, this.currentRadius + 0.015D);
        this.posX = this.centerX + Math.cos(this.angle) * this.currentRadius;
        this.posZ = this.centerZ + Math.sin(this.angle) * this.currentRadius;
        double verticalPhase = this.particleAge * this.verticalSpeed + this.verticalOffset;
        this.posY = this.centerY + Math.sin(verticalPhase) * this.verticalAmplitude;
        this.particleAge++;

        if (this.particleAge >= this.particleMaxAge) {
            this.setExpired();
            return;
        }

        float life = (float) this.particleAge / (float) this.particleMaxAge;
        this.particleScale = this.initScale * (1.0F - life);
        this.particleAlpha = this.initAlpha * (1.0F - life);

        if (this.transitionColors != null) {
            int[] color = ColorUtil.colorTransition(this.transitionColors, Math.min(1.0F, life));
            this.setRBGColorF(color[0] / 255.0F, color[1] / 255.0F, color[2] / 255.0F);
        }

        this.particleAngle = 0.0F;
        this.prevParticleAngle = 0.0F;
    }
}
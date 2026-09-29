package mod.emt.hyxcate.client.particle;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleStarSpark extends ParticleGlow {
    public static final ResourceLocation texture = new ResourceLocation(Hyxcate.ID, "particle/star");

    public ParticleStarSpark(World world, double x, double y, double z, double vx, double vy, double vz, float a, float scale, int lifetime, int[][] transitionColors) {
        super(world, x, y, z, vx, vy, vz, a, scale, lifetime, transitionColors);
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;
        this.particleAngle = 0.0F;
        this.prevParticleAngle = 0.0F;
    }

    @Override
    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.move(this.motionX, this.motionY, this.motionZ);
        this.motionY -= 0.004D;
        this.motionX *= 0.91D;
        this.motionY *= 0.91D;
        this.motionZ *= 0.91D;
        this.particleAge++;

        if (this.particleAge >= this.particleMaxAge) {
            this.setExpired();
            return;
        }

        float scale = getScale();
        this.particleScale = this.initScale * scale;
        this.particleAlpha = this.initAlpha * Math.min(scale, 1.0F);

        if (this.transitionColors != null) {
            float progress = Math.min(1.0F, ((float) this.particleAge / (float) this.particleMaxAge) / 0.60F);
            int[] color = ColorUtil.colorTransition(this.transitionColors, progress);
            this.setRBGColorF(color[0] / 255.0F, color[1] / 255.0F, color[2] / 255.0F);
        }

        this.particleAngle = 0.0F;
        this.prevParticleAngle = 0.0F;
    }

    private float getScale() {
        float life = (float) this.particleAge / (float) this.particleMaxAge;
        float scale;
        if (life < 0.18F) {
            float progress = life / 0.18F;
            float eased = 1.0F - (1.0F - progress) * (1.0F - progress);
            float burst = (float) Math.sin(progress * Math.PI);
            scale = eased * (1.0F + burst * 0.18F);
        } else {
            float progress = (life - 0.18F) / 0.82F;
            float eased = progress * progress * (3.0F - 2.0F * progress);
            scale = 1.0F - eased;
        }
        return scale;
    }

    @Override
    protected ResourceLocation getParticleTexture() {
        return texture;
    }
}

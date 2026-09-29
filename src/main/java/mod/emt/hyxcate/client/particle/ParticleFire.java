package mod.emt.hyxcate.client.particle;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleFire extends Particle implements IHyxcateParticle {
    public float colorR;
    public float colorG;
    public float colorB;
    public float initScale;
    public float initAlpha;
    public boolean growth;
    public int[][] transitionColors;

    public ResourceLocation texture = new ResourceLocation(Hyxcate.ID, "particle/fire");

    public ParticleFire(World world, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float a, float scale, int lifetime) {
        super(world, x, y, z, 0, 0, 0);
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        if (this.colorR > 1.0F) {
            this.colorR /= 255.0F;
        }
        if (this.colorG > 1.0F) {
            this.colorG /= 255.0F;
        }
        if (this.colorB > 1.0F) {
            this.colorB /= 255.0F;
        }
        this.setRBGColorF(colorR, colorG, colorB);
        this.setAlphaF(a);
        this.initAlpha = a;
        this.particleMaxAge = lifetime;
        this.particleScale = scale;
        this.initScale = scale;
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;
        this.particleAngle = 0.0F;
        this.prevParticleAngle = 0.0F;
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(getParticleTexture().toString());
        this.setParticleTexture(sprite);
    }

    public ParticleFire(World world, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float a, float scale, int lifetime, boolean growth) {
        this(world, x, y, z, vx, vy, vz, r, g, b, a, scale, lifetime);
        this.growth = growth;
        if (growth) {
            this.particleScale = 0.0F;
        }
    }

    public ParticleFire(World world, double x, double y, double z, double vx, double vy, double vz, float a, float scale, int lifetime, int[][] transitionColors) {
        this(world, x, y, z, vx, vy, vz, transitionColors[0][0], transitionColors[0][1], transitionColors[0][2], a, scale, lifetime);
        this.transitionColors = transitionColors;
    }

    @Override
    public int getBrightnessForRender(float partialTicks) {
        return 0xF000F0;
    }

    @Override
    public boolean shouldDisableDepth() {
        return true;
    }

    @Override
    public int getFXLayer() {
        return 1;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        float lifeCoeff = Math.min(1.0F, (float) this.particleAge / (float) this.particleMaxAge);
        float scale = 1.0F;
        if (this.growth) {
            scale = Math.min(1.0F, this.particleAge / 5.0F);
        }
        if (this.transitionColors != null) {
            float progress = Math.min(1.0F, ((float) this.particleAge / (float) this.particleMaxAge) / 0.80F);
            int[] color = ColorUtil.colorTransition(this.transitionColors, progress);
            this.setRBGColorF(color[0] / 255.0F, color[1] / 255.0F, color[2] / 255.0F);
        }
        this.particleScale = this.initScale * scale * (1.0F - lifeCoeff);
        this.particleAlpha = this.initAlpha * (1.0F - lifeCoeff);
        this.prevParticleAngle = 0.0F;
        this.particleAngle = 0.0F;
    }

    @Override
    public boolean alive() {
        return this.particleAge < this.particleMaxAge;
    }

    @Override
    public boolean isAdditive() {
        return true;
    }

    @Override
    public boolean renderThroughBlocks() {
        return false;
    }

    protected ResourceLocation getParticleTexture() {
        return texture;
    }
}
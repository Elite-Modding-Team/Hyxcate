package mod.emt.hyxcate.client.particle;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleGlow extends Particle implements IHyxcateParticle {
    public float colorR;
    public float colorG;
    public float colorB;
    public float initScale;
    public float initAlpha;
    public boolean growth;
    public int[][] transitionColors;
    public ResourceLocation texture = new ResourceLocation(Hyxcate.ID, "particle/glow");

    public ParticleGlow(World world, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float a, float scale, int lifetime) {
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
        this.particleMaxAge = (int) ((float) lifetime * 0.5F);
        this.particleScale = scale;
        this.initScale = scale;
        this.motionX = vx * 2.0F;
        this.motionY = vy * 2.0F;
        this.motionZ = vz * 2.0F;
        this.initAlpha = a;
        this.particleAngle = 2.0F * (float) Math.PI;
        TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(getParticleTexture().toString());
        this.setParticleTexture(sprite);
        this.canCollide = true;
    }

    public ParticleGlow(World world, double x, double y, double z, double vx, double vy, double vz, float r, float g, float b, float a, float scale, int lifetime, boolean growth) {
        this(world, x, y, z, vx, vy, vz, r, g, b, a, scale, lifetime);
        this.growth = growth;
        if (growth) {
            this.particleScale = 0.0F;
        }
    }

    public ParticleGlow(World world, double x, double y, double z, double vx, double vy, double vz, float a, float scale, int lifetime, int[][] transitionColors) {
        this(world, x, y, z, vx, vy, vz, transitionColors[0][0], transitionColors[0][1], transitionColors[0][2], a, scale, lifetime);
        this.transitionColors = transitionColors;
    }

    @Override
    public int getBrightnessForRender(float pTicks) {
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
        float lifeCoeff = Math.min(1.0F, (float) particleAge / (float) particleMaxAge);
        float scale = 1.0F;
        if (growth) {
            scale = Math.min(1.0F, particleAge / 5.0F);
        }
        if (transitionColors != null) {
            float progress = Math.min(1.0F, (float) particleAge / (float) particleMaxAge);
            int[] color = ColorUtil.colorTransition(transitionColors, progress);
            this.setRBGColorF(color[0] / 255.0F, color[1] / 255.0F, color[2] / 255.0F);
        }
        this.particleScale = initScale * scale * (1.0F - lifeCoeff);
        this.particleAlpha = initAlpha * (1.0F - lifeCoeff);
        this.prevParticleAngle = particleAngle;
        particleAngle += 1.0F;
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

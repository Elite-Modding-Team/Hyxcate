package de.ellpeck.nyx.client.renderer;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.client.model.NyxModelAlienCreeper;
import de.ellpeck.nyx.entity.NyxEntityAlienCreeper;
import de.ellpeck.nyx.client.model.layer.NyxLayerAlienCreeperCharge;
import de.ellpeck.nyx.client.model.layer.NyxLayerAlienCreeperGlow;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class NyxRendererAlienCreeper extends RenderLiving<NyxEntityAlienCreeper> {
    private static final ResourceLocation ALIEN = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/meteorite.png");
    private static final ResourceLocation FREZARITE = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/frezarite.png");
    private static final ResourceLocation KREKNORITE = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/kreknorite.png");

    public NyxRendererAlienCreeper(RenderManager renderManager) {
        super(renderManager, new NyxModelAlienCreeper(), 0.5F);
        this.addLayer(new NyxLayerAlienCreeperGlow(this));
        this.addLayer(new NyxLayerAlienCreeperCharge(this));
    }

    private void updateCreeperScale(NyxEntityAlienCreeper entity, float partialTickTime) {
        float f1 = entity.getCreeperFlashIntensity(partialTickTime);
        float f2 = 1.0F + MathHelper.sin(f1 * 100.0F) * f1 * 0.01F;

        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f1 > 1.0F) {
            f1 = 1.0F;
        }

        f1 *= f1;
        f1 *= f1;
        float f3 = (1.0F + f1 * 0.4F) * f2;
        float f4 = (1.0F + f1 * 0.1F) / f2;
        GlStateManager.scale(f3, f4, f3);
    }

    private int updateCreeperColorMultiplier(NyxEntityAlienCreeper entity, float lightBrightness, float partialTickTime) {
        float var5 = entity.getCreeperFlashIntensity(partialTickTime);

        if ((int) (var5 * 10.0F) % 2 == 0) {
            return 0;
        } else {
            int var6 = (int) (var5 * 0.2F * 255.0F);

            if (var6 < 0) {
                var6 = 0;
            }

            if (var6 > 255) {
                var6 = 255;
            }

            short var7 = 255;
            short var8 = 255;
            short var9 = 255;
            return var6 << 24 | var7 << 16 | var8 << 8 | var9;
        }
    }

    @Override
    protected void preRenderCallback(NyxEntityAlienCreeper entity, float partialTickTime) {
        updateCreeperScale(entity, partialTickTime);
    }

    @Override
    protected int getColorMultiplier(NyxEntityAlienCreeper entity, float lightBrightness, float partialTickTime) {
        return updateCreeperColorMultiplier(entity, lightBrightness, partialTickTime);
    }

    @Override
    protected ResourceLocation getEntityTexture(NyxEntityAlienCreeper entity) {
        switch (entity.getDataManager().get(NyxEntityAlienCreeper.TYPE)) {
            case 2: // Frezarite
                return FREZARITE;
            case 3: // Kreknorite
                return KREKNORITE;
            default: // Alien
                return ALIEN;
        }
    }
}

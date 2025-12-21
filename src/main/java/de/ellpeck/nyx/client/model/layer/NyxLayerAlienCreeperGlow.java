package de.ellpeck.nyx.client.model.layer;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entity.NyxEntityAlienCreeper;
import de.ellpeck.nyx.client.renderer.NyxRendererAlienCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;

public class NyxLayerAlienCreeperGlow implements LayerRenderer<NyxEntityAlienCreeper> {
    private static final ResourceLocation ALIEN_GLOW = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/meteorite_glow.png");
    private static final ResourceLocation FREZARITE_GLOW = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/frezarite_glow.png");
    private static final ResourceLocation KREKNORITE_GLOW = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/kreknorite_glow.png");
    private final NyxRendererAlienCreeper renderer;

    public NyxLayerAlienCreeperGlow(NyxRendererAlienCreeper renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(NyxEntityAlienCreeper entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderer.bindTexture(getEntityLayer(entity));
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
        GlStateManager.disableLighting();
        int i = 61680;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        GlStateManager.enableLighting();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        i = entity.getBrightnessForRender();
        j = i % 65536;
        k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        this.renderer.setLightmap(entity);
        GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }

    protected ResourceLocation getEntityLayer(NyxEntityAlienCreeper entity) {
        switch (entity.getDataManager().get(NyxEntityAlienCreeper.TYPE)) {
            case 2: // Frezarite
                return FREZARITE_GLOW;
            case 3: // Kreknorite
                return KREKNORITE_GLOW;
            default: // Alien
                return ALIEN_GLOW;
        }
    }
}

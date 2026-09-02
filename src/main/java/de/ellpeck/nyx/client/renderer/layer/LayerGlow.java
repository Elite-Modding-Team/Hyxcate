package de.ellpeck.nyx.client.renderer.layer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerGlow<T extends EntityLiving> implements LayerRenderer<T> {
    private final RenderLiving<T> renderer;
    private final ResourceLocation texture;
    private final ResourceLocation[] textures;

    public LayerGlow(RenderLiving<T> renderer, ResourceLocation texture) {
        this.renderer = renderer;
        this.texture = texture;
        this.textures = null;
    }

    public LayerGlow(RenderLiving<T> renderer, ResourceLocation[] textures) {
        this.renderer = renderer;
        this.texture = null;
        this.textures = textures;
    }

    private ResourceLocation getTexture(T entity) {
        if (textures != null) {
            return textures[getTextureIndex(entity)];
        }

        return texture;
    }

    @Override
    public void doRenderLayer(T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        this.renderer.bindTexture(getTexture(entity));
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, entity.isInvisible() ? GlStateManager.DestFactor.ONE : GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        GlStateManager.disableLighting();
        GlStateManager.enablePolygonOffset();
        GlStateManager.doPolygonOffset(-0.1F, -1.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.renderer.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        this.renderer.setLightmap(entity);
        GlStateManager.doPolygonOffset(0.0F, 0.0F);
        GlStateManager.disablePolygonOffset();
        GlStateManager.depthMask(true);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
    }

    protected int getTextureIndex(T entity) {
        return 0;
    }

    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
}
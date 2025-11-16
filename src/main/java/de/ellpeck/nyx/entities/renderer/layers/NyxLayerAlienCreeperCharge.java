package de.ellpeck.nyx.entities.renderer.layers;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entities.NyxEntityAlienCreeper;
import de.ellpeck.nyx.entities.renderer.NyxModelAlienCreeper;
import de.ellpeck.nyx.entities.renderer.NyxRendererAlienCreeper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class NyxLayerAlienCreeperCharge implements LayerRenderer<NyxEntityAlienCreeper> {
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper_armor.png");
    private final NyxRendererAlienCreeper alienCreeperRendered;
    private final NyxModelAlienCreeper alienCreeperModel = new NyxModelAlienCreeper(2.0F);

    public NyxLayerAlienCreeperCharge(NyxRendererAlienCreeper alienCreeperRenderedIn) {
        this.alienCreeperRendered = alienCreeperRenderedIn;
    }

    @Override
    public void doRenderLayer(NyxEntityAlienCreeper entityAlienCreeper, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entityAlienCreeper.getPowered()) {
            this.alienCreeperRendered.bindTexture(LIGHTNING_TEXTURE);
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.loadIdentity();
            float f = (float) entityAlienCreeper.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.enableBlend();
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.alienCreeperModel.setModelAttributes(this.alienCreeperRendered.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.alienCreeperModel.render(entityAlienCreeper, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

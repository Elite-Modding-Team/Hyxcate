package de.ellpeck.nyx.client.model.layer;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entity.NyxEntityAlienCreeper;
import de.ellpeck.nyx.client.model.NyxModelAlienCreeper;
import de.ellpeck.nyx.client.renderer.NyxRendererAlienCreeper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class NyxLayerAlienCreeperCharge implements LayerRenderer<NyxEntityAlienCreeper> {
    private static final ResourceLocation ALIEN_CHARGE = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/meteorite_armor.png");
    private static final ResourceLocation FREZARITE_CHARGE = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/frezarite_armor.png");
    private static final ResourceLocation KREKNORITE_CHARGE = new ResourceLocation(Nyx.ID, "textures/entities/alien_creeper/kreknorite_armor.png");
    private final NyxRendererAlienCreeper renderer;
    private final NyxModelAlienCreeper model = new NyxModelAlienCreeper(2.0F);

    public NyxLayerAlienCreeperCharge(NyxRendererAlienCreeper renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(NyxEntityAlienCreeper entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        if (entity.getPowered()) {
            this.renderer.bindTexture(getEntityLayer(entity));
            GlStateManager.matrixMode(GL11.GL_TEXTURE);
            GlStateManager.loadIdentity();
            float f = (float) entity.ticksExisted + partialTicks;
            GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.enableBlend();
            GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
            GlStateManager.disableLighting();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            this.model.setModelAttributes(this.renderer.getMainModel());
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.model.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
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

    protected ResourceLocation getEntityLayer(NyxEntityAlienCreeper entity) {
        switch (entity.getDataManager().get(NyxEntityAlienCreeper.TYPE)) {
            case 2: // Frezarite
                return FREZARITE_CHARGE;
            case 3: // Kreknorite
                return KREKNORITE_CHARGE;
            default: // Alien
                return ALIEN_CHARGE;
        }
    }
}

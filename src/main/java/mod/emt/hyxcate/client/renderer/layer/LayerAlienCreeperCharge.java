package mod.emt.hyxcate.client.renderer.layer;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.client.model.ModelAlienCreeper;
import mod.emt.hyxcate.client.renderer.RenderAlienCreeper;
import mod.emt.hyxcate.entity.EntityAlienCreeper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LayerAlienCreeperCharge implements LayerRenderer<EntityAlienCreeper> {
    private static final ResourceLocation ALIEN_CHARGE = new ResourceLocation(Hyxcate.ID, "textures/entities/alien_creeper/meteorite_armor.png");
    private static final ResourceLocation FREZARITE_CHARGE = new ResourceLocation(Hyxcate.ID, "textures/entities/alien_creeper/frezarite_armor.png");
    private static final ResourceLocation KREKNORITE_CHARGE = new ResourceLocation(Hyxcate.ID, "textures/entities/alien_creeper/kreknorite_armor.png");
    private final RenderAlienCreeper renderer;
    private final ModelAlienCreeper model = new ModelAlienCreeper(2.0F);

    public LayerAlienCreeperCharge(RenderAlienCreeper renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityAlienCreeper entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
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

    protected ResourceLocation getEntityLayer(EntityAlienCreeper entity) {
        switch (entity.getDataManager().get(EntityAlienCreeper.TYPE)) {
            case 2: // Frezarite
                return FREZARITE_CHARGE;
            case 3: // Kreknorite
                return KREKNORITE_CHARGE;
            default: // Alien
                return ALIEN_CHARGE;
        }
    }
}

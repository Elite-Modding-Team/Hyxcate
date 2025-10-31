package de.ellpeck.nyx.entities.renderer;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entities.NyxEntityFallingMeteor;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class NyxRendererMeteor extends Render<NyxEntityFallingMeteor> {

    private static final ResourceLocation METEORITE = new ResourceLocation(Nyx.ID, "textures/models/meteor.png");
    private static final ResourceLocation FREZARITE = new ResourceLocation(Nyx.ID, "textures/models/frezarite_meteor.png");
    private static final ResourceLocation KREKNORITE = new ResourceLocation(Nyx.ID, "textures/models/kreknorite_meteor.png");
    private static final ResourceLocation UNKNOWN = new ResourceLocation(Nyx.ID, "textures/models/unknown_meteor.png");
    private final ModelOverlay model = new ModelOverlay();

    public NyxRendererMeteor(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(NyxEntityFallingMeteor entity) {
        switch (entity.getDataManager().get(NyxEntityFallingMeteor.TYPE)) {
            case 2: // Frezarite
                return FREZARITE;
            case 3: // Kreknorite
                return KREKNORITE;
            case 4: // Unknown
                return UNKNOWN;
            default: // Meteorite
                return METEORITE;
        }
    }

    @Override
    public void doRender(NyxEntityFallingMeteor entity, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!entity.isLoaded()) return;
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);
        GlStateManager.disableLighting();
        float size = entity.getDataManager().get(NyxEntityFallingMeteor.SIZE);
        GlStateManager.scale(size, size, size);
        GlStateManager.translate(-1, -1, -1);
        this.bindTexture(this.getEntityTexture(entity));
        this.model.render();
        GlStateManager.enableLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public boolean shouldRender(NyxEntityFallingMeteor livingEntity, ICamera camera, double camX, double camY, double camZ) {
        return true;
    }

    private static class ModelOverlay extends ModelBase {

        private final ModelRenderer box;

        public ModelOverlay() {
            this.box = new ModelRenderer(this, 0, 0);
            this.box.setTextureSize(128, 128);
            this.box.addBox(0, 0, 0, 32, 32, 32);
        }

        public void render() {
            this.box.render(1 / 16F);
        }
    }
}

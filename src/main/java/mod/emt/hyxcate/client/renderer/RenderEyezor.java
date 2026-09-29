package mod.emt.hyxcate.client.renderer;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.client.renderer.layer.LayerGlow;
import mod.emt.hyxcate.entity.EntityEyezor;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.util.ResourceLocation;

public class RenderEyezor extends RenderBiped<EntityEyezor> {
    private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Hyxcate.ID, "textures/entities/eyezor/bloody.png"),
            new ResourceLocation(Hyxcate.ID, "textures/entities/eyezor/stargazer.png")
    };
    private static final ResourceLocation[] TEXTURES_GLOW = new ResourceLocation[]{
            new ResourceLocation(Hyxcate.ID, "textures/entities/eyezor/bloody_layer.png"),
            new ResourceLocation(Hyxcate.ID, "textures/entities/eyezor/stargazer_layer.png")
    };

    public RenderEyezor(RenderManager renderManager) {
        super(renderManager, new ModelZombie(), 0.5F);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
        this.addLayer(new LayerGlow<EntityEyezor>(this, TEXTURES_GLOW) {
            @Override
            protected int getTextureIndex(EntityEyezor entity) {
                return entity.getType();
            }
        });
    }

    protected void preRenderCallback(EntityEyezor entity, float partialTickTime) {
        GlStateManager.scale(1.0625F, 1.0625F, 1.0625F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityEyezor entity) {
        return TEXTURES[entity.getType()];
    }
}

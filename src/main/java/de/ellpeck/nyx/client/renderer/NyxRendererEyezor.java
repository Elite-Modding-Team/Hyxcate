package de.ellpeck.nyx.client.renderer;

import de.ellpeck.nyx.entity.NyxEntityEyezor;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;

// TODO: Update model
public class NyxRendererEyezor extends RenderBiped<NyxEntityEyezor> {
    /*private static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
            new ResourceLocation(Nyx.ID, "textures/entities/eyezor/basic.png"),
            new ResourceLocation(Nyx.ID, "textures/entities/eyezor/basic2.png")
    };*/

    public NyxRendererEyezor(RenderManager renderManager) {
        super(renderManager, new ModelZombie(), 0.5F);
        LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this) {
            protected void initArmor() {
                this.modelLeggings = new ModelZombie(0.5F, true);
                this.modelArmor = new ModelZombie(1.0F, true);
            }
        };
        this.addLayer(layerbipedarmor);
    }

    protected void preRenderCallback(NyxEntityEyezor entity, float partialTickTime) {
        GlStateManager.scale(1.0625F, 1.0625F, 1.0625F);
        if (entity.isChild()) GlStateManager.scale(0.505F, 0.505F, 0.505F);
    }

    /*@Override
    protected ResourceLocation getEntityTexture(NyxEntityEyezor entity) {
        return TEXTURES[entity.getType()];
    }*/
}

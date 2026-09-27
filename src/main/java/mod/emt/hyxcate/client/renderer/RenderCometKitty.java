package mod.emt.hyxcate.client.renderer;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.client.model.ModelCometKitty;
import mod.emt.hyxcate.entity.EntityCometKitty;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderCometKitty extends RenderLiving<EntityCometKitty> {
    private static final ResourceLocation SKIN = new ResourceLocation(Hyxcate.ID, "textures/entities/comet_kitty/meteorite.png");

    public RenderCometKitty(RenderManager renderManager) {
        super(renderManager, new ModelCometKitty(), 0.4F);
        mainModel = new ModelCometKitty();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCometKitty entity) {
        return SKIN;
    }
}

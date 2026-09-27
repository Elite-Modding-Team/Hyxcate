package mod.emt.hyxcate.client.renderer;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.entity.EntityStellarProtector;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderStellarProtector extends RenderLiving<EntityStellarProtector> {
    private static final ResourceLocation SKIN = new ResourceLocation(Hyxcate.ID, "textures/entities/stellar_protector.png");

    public RenderStellarProtector(RenderManager renderManager) {
        super(renderManager, new ModelBlaze(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityStellarProtector entity) {
        return SKIN;
    }
}

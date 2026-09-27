package mod.emt.hyxcate.client.renderer;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEmpty extends Render<Entity> {
    public RenderEmpty(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float entityYaw, float partialTicks) {

    }
}

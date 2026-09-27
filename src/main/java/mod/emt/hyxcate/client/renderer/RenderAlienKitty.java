package mod.emt.hyxcate.client.renderer;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.client.model.ModelAlienKitty;
import mod.emt.hyxcate.entity.EntityAlienKitty;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderAlienKitty extends RenderLiving<EntityAlienKitty> {
    private static final ResourceLocation ALIEN = new ResourceLocation(Hyxcate.ID, "textures/entities/alien_kitty/alien_green.png");
    private static final ResourceLocation FREZARITE = new ResourceLocation(Hyxcate.ID, "textures/entities/alien_kitty/frezarite.png");
    private static final ResourceLocation KREKNORITE = new ResourceLocation(Hyxcate.ID, "textures/entities/alien_kitty/kreknorite.png");

    public RenderAlienKitty(RenderManager renderManager) {
        super(renderManager, new ModelAlienKitty(), 0.4F);
        mainModel = new ModelAlienKitty();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityAlienKitty entity) {
        switch (entity.getDataManager().get(EntityAlienKitty.TYPE)) {
            case 2: // Frezarite
                return FREZARITE;
            case 3: // Kreknorite
                return KREKNORITE;
            default: // Alien
                return ALIEN;
        }
    }
}

package de.ellpeck.nyx.client.renderer;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.client.model.NyxModelAlienKitty;
import de.ellpeck.nyx.entity.NyxEntityAlienKitty;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class NyxRendererAlienKitty extends RenderLiving<NyxEntityAlienKitty> {
    private static final ResourceLocation ALIEN = new ResourceLocation(Nyx.ID, "textures/entities/alien_kitty/alien_green.png");
    private static final ResourceLocation FREZARITE = new ResourceLocation(Nyx.ID, "textures/entities/alien_kitty/frezarite.png");
    private static final ResourceLocation KREKNORITE = new ResourceLocation(Nyx.ID, "textures/entities/alien_kitty/kreknorite.png");

    public NyxRendererAlienKitty(RenderManager renderManager) {
        super(renderManager, new NyxModelAlienKitty(), 0.4F);
        mainModel = new NyxModelAlienKitty();
    }

    @Override
    protected ResourceLocation getEntityTexture(NyxEntityAlienKitty entity) {
        switch (entity.getDataManager().get(NyxEntityAlienKitty.TYPE)) {
            case 2: // Frezarite
                return FREZARITE;
            case 3: // Kreknorite
                return KREKNORITE;
            default: // Alien
                return ALIEN;
        }
    }
}

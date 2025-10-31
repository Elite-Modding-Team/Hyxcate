package de.ellpeck.nyx.entities.renderer;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entities.NyxEntityCometKitty;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class NyxRendererCometKitty extends RenderLiving<NyxEntityCometKitty> {

    private static final ResourceLocation SKIN = new ResourceLocation(Nyx.ID, "textures/entities/comet_kitty.png");

    public NyxRendererCometKitty(RenderManager renderManager) {
        super(renderManager, new NyxModelCometKitty(), 0.4F);
        mainModel = new NyxModelCometKitty();
    }

    @Override
    protected ResourceLocation getEntityTexture(NyxEntityCometKitty entity) {
        return SKIN;
    }
}

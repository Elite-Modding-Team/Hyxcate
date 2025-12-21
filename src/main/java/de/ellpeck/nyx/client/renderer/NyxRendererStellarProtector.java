package de.ellpeck.nyx.client.renderer;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entity.NyxEntityStellarProtector;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class NyxRendererStellarProtector extends RenderLiving<NyxEntityStellarProtector> {
    private static final ResourceLocation SKIN = new ResourceLocation(Nyx.ID, "textures/entities/stellar_protector.png");

    public NyxRendererStellarProtector(RenderManager renderManager) {
        super(renderManager, new ModelBlaze(), 0.5F);
    }

    @Override
    protected ResourceLocation getEntityTexture(NyxEntityStellarProtector entity) {
        return SKIN;
    }
}

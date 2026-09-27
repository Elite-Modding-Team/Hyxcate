package mod.emt.hyxcate.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelLaser extends ModelBase {
    private final ModelRenderer beam;

    public ModelLaser() {
        this.textureWidth = 32;
        this.textureHeight = 32;
        this.beam = new ModelRenderer(this, 0, 0);
        this.beam.addBox(-0.5F, -0.5F, -2.0F, 1, 1, 5);
        this.beam.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.setRotation(this.beam, 0.0F, 0.0F, 0.0F);
    }

    public void render() {
        this.beam.render(0.0625F);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}

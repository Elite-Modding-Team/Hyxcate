package de.ellpeck.nyx.entities.renderer;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NyxModelAlienCreeper extends ModelCreeper {

    private final ModelRenderer antennaBase;
    private final ModelRenderer antennaTip;

    public NyxModelAlienCreeper() {
        this(0.0F);
    }

    public NyxModelAlienCreeper(float scale) {
        super(scale);

        this.antennaBase = new ModelRenderer(this, 33, 6);
        this.antennaBase.addBox(-0.5F, -14F, -0.5F, 1, 6, 1);
        this.head.addChild(this.antennaBase);

        this.antennaTip = new ModelRenderer(this, 33, 1);
        this.antennaTip.addBox(-1F, -16F, -1F, 2, 2, 2);
        this.head.addChild(this.antennaTip);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);

        if (limbSwingAmount > 0) {
            this.antennaBase.rotateAngleX = 0.1F * MathHelper.sin(limbSwing);
            this.antennaBase.rotateAngleZ = 0.1F * MathHelper.cos(limbSwing);
            this.antennaTip.rotateAngleX = 0.1F * MathHelper.sin(limbSwing);
            this.antennaTip.rotateAngleZ = 0.1F * MathHelper.cos(limbSwing);
        } else {
            this.antennaBase.rotateAngleX = 0.0F;
            this.antennaBase.rotateAngleZ = 0.0F;
            this.antennaTip.rotateAngleX = 0.0F;
            this.antennaTip.rotateAngleZ = 0.0F;
        }
    }
}

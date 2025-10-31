package de.ellpeck.nyx.entities.renderer;

import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NyxModelCometKitty extends ModelOcelot {

    ModelRenderer helmet;

    public NyxModelCometKitty() {
        super();
        this.setTextureOffset("helmet.main", 34, 17);
        this.setTextureOffset("helmet.top", 48, 12);
        this.setTextureOffset("helmet.left", 14, 25);
        this.setTextureOffset("helmet.right", 24, 25);
        this.setTextureOffset("helmet.right2", 6, 29);
        this.setTextureOffset("helmet.left2", 0, 29);
        this.helmet = new ModelRenderer(this, "helmet");
        this.helmet.addBox("main", -3.5F, -4.0F, -5.0F, 7, 7, 8);
        this.helmet.addBox("top", -1F, -6F, -3F, 2, 1, 4);
        this.helmet.addBox("left", 4F, -2F, -3F, 1, 3, 4);
        this.helmet.addBox("right", -5F, -2F, -3F, 1, 3, 4);
        this.helmet.addBox("right2", -6F, -1F, -2F, 1, 1, 2);
        this.helmet.addBox("left2", 5F, -1F, -2F, 1, 1, 2);
        this.helmet.setRotationPoint(0.0F, 15.0F, -9.0F);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entity) {
        super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entity);
        this.helmet.rotateAngleX = headPitch / (180F / (float) Math.PI);
        this.helmet.rotateAngleY = headPitch / (180F / (float) Math.PI);
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        super.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 10.0F * scale, 4.0F * scale);
            this.helmet.render(scale);
            GlStateManager.popMatrix();
        } else {
            this.helmet.render(scale);
        }
    }

    @Override
    public void setLivingAnimations(EntityLivingBase entityLivingBase, float limbSwing, float limbSwingAmount, float partialTickTime) {
        super.setLivingAnimations(entityLivingBase, limbSwing, limbSwingAmount, partialTickTime);
        EntityOcelot var5 = (EntityOcelot) entityLivingBase;
        this.helmet.rotationPointY = 15.0F;
        this.helmet.rotationPointZ = -9.0F;
        if (var5.isSneaking()) {
            this.helmet.rotationPointY += 2.0F;
        } else if (var5.isSitting()) {
            this.helmet.rotationPointY += -3.3F;
            ++this.helmet.rotationPointZ;
        }
    }
}

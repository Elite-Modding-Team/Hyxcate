package de.ellpeck.nyx.entities.renderer;

import de.ellpeck.nyx.entities.NyxEntityAlienKitty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NyxModelAlienKitty extends ModelBase {
    private final ModelRenderer antenna;
    private final ModelRenderer antenna2;
    private final ModelRenderer tail;
    private final ModelRenderer tailBase;
    private final ModelRenderer head;
    private final ModelRenderer main;
    private final ModelRenderer body;
    private final ModelRenderer backLegRight;
    private final ModelRenderer backLegLeft;
    private final ModelRenderer frontLegRight;
    private final ModelRenderer frontLegLeft;

    public NyxModelAlienKitty() {
        textureWidth = 64;
        textureHeight = 64;

        antenna = new ModelRenderer(this);
        antenna.setRotationPoint(-1.3F, 13.1624F, -9.8698F);
        setRotationAngle(antenna, 0.0F, -1.5708F, -0.1745F);
        antenna.cubeList.add(new ModelBox(antenna, 28, 14, 0.0F, -4.1624F, -0.6302F, 0, 3, 2, 0.0F, false));
        antenna.cubeList.add(new ModelBox(antenna, 22, 27, 0.0F, -2.1624F, -0.1302F, 0, 3, 1, 0.0F, false));

        antenna2 = new ModelRenderer(this);
        antenna2.setRotationPoint(1.3F, 13.1624F, -9.8698F);
        setRotationAngle(antenna2, 0.0F, 1.5708F, 0.1745F);
        antenna2.cubeList.add(new ModelBox(antenna2, 28, 14, 0.0F, -4.1624F, -0.6302F, 0, 3, 2, 0.0F, true));
        antenna2.cubeList.add(new ModelBox(antenna2, 22, 27, 0.0F, -2.1624F, -0.1302F, 0, 3, 1, 0.0F, true));

        tail = new ModelRenderer(this);
        tail.setRotationPoint(0.0F, 24.0F, 0.0F);
        setRotationAngle(tail, 0.0F, -1.5708F, 0.0F);
        tail.cubeList.add(new ModelBox(tail, 20, 10, 13.0F, -4.5F, -0.5F, 7, 1, 1, 0.0F, false));
        tail.cubeList.add(new ModelBox(tail, 24, 26, 19.0F, -5.0F, -1.0F, 2, 2, 2, 0.0F, false));

        tailBase = new ModelRenderer(this);
        tailBase.setRotationPoint(8.0F, -7.0F, 0.0F);
        tail.addChild(tailBase);
        setRotationAngle(tailBase, 0.0F, 0.0F, 0.6109F);
        tailBase.cubeList.add(new ModelBox(tailBase, 16, 19, -2.0F, -1.0F, -0.5F, 8, 1, 1, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 23.0F, 0.0F);
        setRotationAngle(head, 0.0F, -1.5708F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 10, -12.0F, -10.0F, -2.5F, 5, 4, 5, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 16, 27, -9.0F, -11.0F, 1.0F, 2, 1, 1, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 28, 12, -9.0F, -11.0F, -2.0F, 2, 1, 1, 0.0F, false));
        head.cubeList.add(new ModelBox(head, 24, 21, -13.0F, -8.0F, -1.5F, 1, 2, 3, 0.0F, false));

        main = new ModelRenderer(this);
        main.setRotationPoint(0.0F, 24.0F, 0.0F);

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, -6.0F, 0.0F);
        main.addChild(body);
        setRotationAngle(body, 0.0F, -1.5708F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 0, -7.0F, -4.0F, -2.0F, 14, 6, 4, 0.0F, false));

        backLegRight = new ModelRenderer(this);
        backLegRight.setRotationPoint(-1.1F, -4.0F, 5.0F);
        main.addChild(backLegRight);
        backLegRight.cubeList.add(new ModelBox(backLegRight, 16, 21, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

        backLegLeft = new ModelRenderer(this);
        backLegLeft.setRotationPoint(1.1F, -4.0F, 5.0F);
        main.addChild(backLegLeft);
        backLegLeft.cubeList.add(new ModelBox(backLegLeft, 20, 12, -1.0F, 0.0F, -1.0F, 2, 4, 2, 0.0F, false));

        frontLegRight = new ModelRenderer(this);
        frontLegRight.setRotationPoint(-1.2F, -9.0F, -4.0F);
        main.addChild(frontLegRight);
        frontLegRight.cubeList.add(new ModelBox(frontLegRight, 8, 19, -1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F, false));

        frontLegLeft = new ModelRenderer(this);
        frontLegLeft.setRotationPoint(1.2F, -9.0F, -4.0F);
        main.addChild(frontLegLeft);
        frontLegLeft.cubeList.add(new ModelBox(frontLegLeft, 0, 19, -1.0F, 0.0F, -1.0F, 2, 9, 2, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        if (this.isChild) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.75F, 0.75F, 0.75F);
            GlStateManager.translate(0.0F, 10.0F * f5, 4.0F * f5);
            head.render(f5);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.scale(0.5F, 0.5F, 0.5F);
            GlStateManager.translate(0.0F, 24.0F * f5, 0.0F);
            main.render(f5);
            tail.render(f5);
            GlStateManager.translate(0.0F, -2.0F * f5, 0.0F);
            antenna.render(f5);
            antenna2.render(f5);
            GlStateManager.popMatrix();
        } else {
            head.render(f5);
            main.render(f5);
            tail.render(f5);
            antenna.render(f5);
            antenna2.render(f5);
        }
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
        NyxEntityAlienKitty alienKitty = (NyxEntityAlienKitty) entityIn;
        if (alienKitty.isSitting()) {
            // Lying pose
            this.main.rotationPointY = 26.0F;

            this.frontLegRight.rotateAngleX = -1.5708F;
            this.frontLegRight.rotationPointY = -3.0F;
            this.frontLegRight.rotationPointZ = -1.0F;
            this.frontLegLeft.rotateAngleX = -1.5708F;
            this.frontLegLeft.rotationPointY = -3.0F;
            this.frontLegLeft.rotationPointZ = -1.0F;

            this.backLegRight.rotateAngleX = -1.5708F;
            this.backLegRight.rotationPointY = -3.0F;
            this.backLegRight.rotationPointZ = 7.0F;
            this.backLegLeft.rotateAngleX = -1.5708F;
            this.backLegLeft.rotationPointY = -3.0F;
            this.backLegLeft.rotationPointZ = 7.0F;

            this.head.rotationPointY = 25.0F;

            this.tail.rotationPointY = 26.0F;

            this.antenna.rotateAngleZ = -0.1745F + (float) Math.sin(ageInTicks * 0.1F) * 0.05F;
            this.antenna2.rotateAngleZ = 0.2182F + (float) Math.sin(ageInTicks * 0.1F + 1.0F) * 0.05F;
            this.antenna.rotationPointY = 15.0F;
            this.antenna2.rotationPointY = 15.0F;
        } else {
            // Standing pose
            this.main.rotationPointY = 24.0F;

            this.frontLegRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.frontLegRight.rotationPointY = -9.0F;
            this.frontLegRight.rotationPointZ = -4.0F;
            this.frontLegLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.frontLegLeft.rotationPointY = -9.0F;
            this.frontLegLeft.rotationPointZ = -4.0F;

            this.backLegRight.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
            this.backLegRight.rotationPointY = -4.0F;
            this.backLegRight.rotationPointZ = 5.0F;
            this.backLegLeft.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
            this.backLegLeft.rotationPointY = -4.0F;
            this.backLegLeft.rotationPointZ = 5.0F;

            this.head.rotationPointY = 23.0F;

            this.tail.rotateAngleY = (float) Math.cos(limbSwing * 0.5F) * 0.3F * limbSwingAmount - 1.5708F;
            this.tail.rotationPointY = 24.0F;

            this.antenna.rotateAngleZ = -0.1745F + (float) Math.sin(ageInTicks * 0.2F) * 0.1F;
            this.antenna2.rotateAngleZ = 0.2182F + (float) Math.sin(ageInTicks * 0.2F + 1.0F) * 0.1F;
            this.antenna.rotationPointY = 13.0F;
            this.antenna2.rotationPointY = 13.0F;
        }
    }

    private void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

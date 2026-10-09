package mod.emt.hyxcate.util.helpers;

import mod.emt.hyxcate.Hyxcate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public final class VignetteHelper {
    private static final ResourceLocation VIGNETTE = new ResourceLocation(Hyxcate.ID, "textures/misc/vignette.png");

    public static void render(ScaledResolution resolution, float red, float green, float blue, float alpha) {
        if (alpha <= 0.0F) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();
        GlStateManager.pushMatrix();
        GlStateManager.pushAttrib();
        mc.getTextureManager().bindTexture(VIGNETTE);
        GlStateManager.enableBlend();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(red, green, blue, alpha);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0, resolution.getScaledHeight(), 0).tex(0.0D, 1.0D).endVertex();
        buffer.pos(resolution.getScaledWidth(), resolution.getScaledHeight(), 0).tex(1.0D, 1.0D).endVertex();
        buffer.pos(resolution.getScaledWidth(), 0, 0).tex(1.0D, 0.0D).endVertex();
        buffer.pos(0, 0, 0).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.disableBlend();
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
    }
}
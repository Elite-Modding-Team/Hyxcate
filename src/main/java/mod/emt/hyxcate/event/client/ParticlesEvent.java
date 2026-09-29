package mod.emt.hyxcate.event.client;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.client.particle.ParticleRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticlesEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.side == Side.CLIENT && event.phase == TickEvent.Phase.START && !Minecraft.getMinecraft().isGamePaused()) {
            ParticleRenderer.INSTANCE.updateParticles();
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        GlStateManager.pushMatrix();
        ParticleRenderer.INSTANCE.renderParticles(event.getPartialTicks());
        GlStateManager.popMatrix();
    }

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre event) {
        event.getMap().registerSprite(new ResourceLocation(Hyxcate.ID, "particle/fire"));
        event.getMap().registerSprite(new ResourceLocation(Hyxcate.ID, "particle/glow"));
        event.getMap().registerSprite(new ResourceLocation(Hyxcate.ID, "particle/star"));
    }
}

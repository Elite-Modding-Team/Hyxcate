package mod.emt.hyxcate.event.client;

import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.util.VignetteUtil;
import mod.emt.hyxcate.util.helpers.VignetteHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class VignetteEvent {
    private static final VignetteUtil ASTRAL_EROSION = new VignetteUtil(0.5F, 0.5F, 0.5F);
    private static final VignetteUtil DEEP_FREEZE = new VignetteUtil(2.0F, 2.0F, 0.5F);
    private static final VignetteUtil INFERNO = new VignetteUtil(0.3F, 0.5F, 0.45F);
    private static final VignetteUtil PARALYSIS = new VignetteUtil(0.08F, 0.30F, 0.40F);

    private boolean hadAstralErosion = false;
    private boolean hadDeepFreeze = false;
    private boolean hadInferno = false;
    private boolean hadParalysis = false;

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft mc = Minecraft.getMinecraft();

        if (mc.world == null || mc.player == null || mc.isGamePaused()) {
            return;
        }

        updateAstralErosion(mc);
        updateDeepFreeze(mc);
        updateInferno(mc);
        updateParalysis(mc);

        ASTRAL_EROSION.update();
        DEEP_FREEZE.update();
        INFERNO.update();
        PARALYSIS.update();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HELMET) {
            return;
        }

        renderAstralErosion(event);
        renderDeepFreeze(event);
        renderInferno(event);
        renderParalysis(event);
    }

    private void renderAstralErosion(RenderGameOverlayEvent.Pre event) {
        float alpha = ASTRAL_EROSION.getAlpha();
        if (alpha <= 0.0F) {
            return;
        }
        VignetteHelper.render(event.getResolution(), 0.40F, 0.25F, 0.90F, alpha);
    }

    private void renderDeepFreeze(RenderGameOverlayEvent.Pre event) {
        float alpha = DEEP_FREEZE.getAlpha();
        if (alpha <= 0.0F) {
            return;
        }
        VignetteHelper.render(event.getResolution(), 0.75F, 0.90F, 1.0F, alpha);
    }

    private void renderInferno(RenderGameOverlayEvent.Pre event) {
        float alpha = INFERNO.getAlpha();
        if (alpha <= 0.0F) {
            return;
        }
        VignetteHelper.render(event.getResolution(), 1.0F, 0.25F, 0.02F, alpha);
    }

    private void renderParalysis(RenderGameOverlayEvent.Pre event) {
        float alpha = PARALYSIS.getAlpha();
        if (alpha <= 0.0F) {
            return;
        }
        VignetteHelper.render(event.getResolution(), 1.0F, 0.85F, 0.35F, alpha);
    }

    private void updateAstralErosion(Minecraft mc) {
        boolean active = mc.player.isPotionActive(HyxcatePotions.ASTRAL_EROSION);
        if (active != hadAstralErosion) {
            hadAstralErosion = active;
            ASTRAL_EROSION.setPulsing(active);
        }
    }

    private void updateDeepFreeze(Minecraft mc) {
        boolean active = mc.player.isPotionActive(HyxcatePotions.DEEP_FREEZE);
        if (active != hadDeepFreeze) {
            hadDeepFreeze = active;
            DEEP_FREEZE.setActive(active);
        }
    }

    private void updateInferno(Minecraft mc) {
        boolean active = mc.player.isPotionActive(HyxcatePotions.INFERNO);
        if (active != hadInferno) {
            hadInferno = active;
            INFERNO.setPulsing(active);
        }
    }

    private void updateParalysis(Minecraft mc) {
        boolean active = mc.player.isPotionActive(HyxcatePotions.PARALYSIS);
        if (active != hadParalysis) {
            hadParalysis = active;
            PARALYSIS.setFlickering(active);
        }
    }
}
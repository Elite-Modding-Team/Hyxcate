package mod.emt.hyxcate.event.potion;

import com.expandedevents.api.event.LivingSprintStartEvent;
import mod.emt.hyxcate.init.HyxcatePotions;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ParalysisEvent {
    @SubscribeEvent
    public void onSprintStart(LivingSprintStartEvent event) {
        // Prevents affected players from sprinting
        if (event.getEntityLiving().isPotionActive(HyxcatePotions.PARALYSIS)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        // Stop affected players that are currently sprinting
        if (entity.isSprinting()) {
            if (entity.isPotionActive(HyxcatePotions.PARALYSIS)) {
                entity.setSprinting(false);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onMouseEvent(MouseEvent event) {
        // When a player is paralyzed, it will be impossible for them to use their mouse
        if (Minecraft.getMinecraft().player.isPotionActive(HyxcatePotions.PARALYSIS) && Minecraft.getMinecraft().inGameHasFocus) {
            event.setCanceled(true);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onInputUpdateEvent(InputUpdateEvent event) {
        // When a player is paralyzed, it will be impossible for them to move
        if (event.getEntityPlayer().isPotionActive(HyxcatePotions.PARALYSIS)) {
            event.getMovementInput().jump = false;
            event.getMovementInput().moveForward = 0;
            event.getMovementInput().moveStrafe = 0;
            event.getMovementInput().sneak = false;
        }
    }
}

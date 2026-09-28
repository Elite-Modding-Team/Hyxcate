package mod.emt.hyxcate.event.potion;

import com.expandedevents.api.event.LivingSprintStartEvent;
import mod.emt.hyxcate.init.HyxcatePotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DeepFreezeEvent {
    @SubscribeEvent
    public void onSprintStart(LivingSprintStartEvent event) {
        // Prevents affected players from sprinting
        if (event.getEntityLiving().isPotionActive(HyxcatePotions.DEEP_FREEZE)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        // Stop affected players that are currently sprinting
        if (entity.isSprinting()) {
            if (entity.isPotionActive(HyxcatePotions.DEEP_FREEZE)) {
                entity.setSprinting(false);
            }
        }
    }
}

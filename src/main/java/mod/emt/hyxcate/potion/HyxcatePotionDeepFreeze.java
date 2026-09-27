package mod.emt.hyxcate.potion;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.util.HyxcateDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Hyxcate.ID)
public class HyxcatePotionDeepFreeze extends HyxcatePotion {
    public HyxcatePotionDeepFreeze(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    // Slows down breaking blocks for players
    @SubscribeEvent
    public static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer().isPotionActive(HyxcatePotions.DEEP_FREEZE)) {
            event.setNewSpeed(event.getOriginalSpeed() * (1.0F - 0.2F * (1 + event.getEntityLiving().getActivePotionEffect(HyxcatePotions.DEEP_FREEZE).getAmplifier())));
        }
    }

    // Slows down jumping
    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving().isPotionActive(HyxcatePotions.DEEP_FREEZE)) {
            if (event.getEntityLiving().getActivePotionEffect(HyxcatePotions.DEEP_FREEZE).getAmplifier() > 0) {
                event.getEntity().motionY *= 0.5D;
            } else {
                event.getEntity().motionY *= 0.75D;
            }
        }
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        // EVERYBODY FREEZE!
        entity.attackEntityFrom(HyxcateDamageSource.DEEP_FREEZE, 1.0F + (1.0F * amplifier)); // 1.0F = 1 half heart
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 40 >> amplifier;

        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}

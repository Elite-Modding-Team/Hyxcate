package de.ellpeck.nyx.potion;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxPotions;
import de.ellpeck.nyx.util.NyxDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Nyx.ID)
public class NyxPotionDeepFreeze extends NyxPotion {
    public NyxPotionDeepFreeze(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        // EVERYBODY FREEZE!
        entity.attackEntityFrom(NyxDamageSource.DEEP_FREEZE, 1.0F + (1.0F * amplifier)); // 1.0F = 1 half heart
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

    // Slows down breaking blocks for players
    @SubscribeEvent
    public static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer().isPotionActive(NyxPotions.DEEP_FREEZE)) {
            event.setNewSpeed(event.getOriginalSpeed() * (1.0F - 0.2F * (1 + event.getEntityLiving().getActivePotionEffect(NyxPotions.DEEP_FREEZE).getAmplifier())));
        }
    }

    // Slows down jumping
    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving().isPotionActive(NyxPotions.DEEP_FREEZE)) {
            if (event.getEntityLiving().getActivePotionEffect(NyxPotions.DEEP_FREEZE).getAmplifier() > 0) {
                event.getEntity().motionY *= 0.5D;
            } else {
                event.getEntity().motionY *= 0.75D;
            }
        }
    }
}

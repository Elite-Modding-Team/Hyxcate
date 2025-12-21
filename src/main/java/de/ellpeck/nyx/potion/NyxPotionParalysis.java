package de.ellpeck.nyx.potion;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxPotions;
import de.ellpeck.nyx.util.NyxDamageSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Nyx.ID)
public class NyxPotionParalysis extends NyxPotion {
    public NyxPotionParalysis(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.attackEntityFrom(NyxDamageSource.PARALYSIS, 1.0F + (1.0F * amplifier)); // 1.0F = 1 half heart

        // Disables mob ai when paralyzed, players are affected differently
        if (entity instanceof EntityPlayer) {
            return;
        } else if (entity instanceof EntityLiving) {
            ((EntityLiving) entity).setNoAI(true);
        } else {
            entity.updateBlocked = true;
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 25 >> amplifier;

        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }

    // Prevents breaking blocks for players
    @SubscribeEvent
    public static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer().isPotionActive(NyxPotions.PARALYSIS)) {
            event.setNewSpeed(0);
        }
    }

    // Prevents jumping
    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving().isPotionActive(NyxPotions.PARALYSIS)) {
            event.getEntity().motionY = 0;
        }
    }

    // Re-enables mob ai when Paralysis effect ends
    @SubscribeEvent
    public static void onPotionExpired(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(NyxPotions.PARALYSIS) && !(entity instanceof EntityPlayer)) {
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).setNoAI(false);
            } else {
                entity.updateBlocked = false;
            }
        }
    }

    @SubscribeEvent
    public static void onPotionRemoved(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(NyxPotions.PARALYSIS) && !(entity instanceof EntityPlayer)) {
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).setNoAI(false);
            } else {
                entity.updateBlocked = false;
            }
        }
    }
}

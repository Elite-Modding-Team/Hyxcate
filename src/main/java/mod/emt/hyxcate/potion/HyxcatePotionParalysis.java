package mod.emt.hyxcate.potion;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.util.HyxcateDamageSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Hyxcate.ID)
public class HyxcatePotionParalysis extends HyxcatePotion {
    public HyxcatePotionParalysis(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    // Prevents breaking blocks for players
    @SubscribeEvent
    public static void onBreakSpeedEvent(PlayerEvent.BreakSpeed event) {
        if (event.getEntityPlayer().isPotionActive(HyxcatePotions.PARALYSIS)) {
            event.setNewSpeed(0);
        }
    }

    // Prevents jumping
    @SubscribeEvent
    public static void onLivingJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (event.getEntityLiving().isPotionActive(HyxcatePotions.PARALYSIS)) {
            event.getEntity().motionY = 0;
        }
    }

    // Re-enables mob ai when Paralysis effect ends
    @SubscribeEvent
    public static void onPotionExpired(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(HyxcatePotions.PARALYSIS) && !(entity instanceof EntityPlayer)) {
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

        if (event.getPotionEffect().getPotion().equals(HyxcatePotions.PARALYSIS) && !(entity instanceof EntityPlayer)) {
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).setNoAI(false);
            } else {
                entity.updateBlocked = false;
            }
        }
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.attackEntityFrom(HyxcateDamageSource.PARALYSIS, 1.0F + (1.0F * amplifier)); // 1.0F = 1 half heart

        // Disables mob ai when paralyzed, players are affected differently
        if (entity instanceof EntityPlayer) {
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
}

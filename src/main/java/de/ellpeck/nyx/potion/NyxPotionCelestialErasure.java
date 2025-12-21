package de.ellpeck.nyx.potion;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxPotions;
import de.ellpeck.nyx.util.NyxDamageSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Nyx.ID)
public class NyxPotionCelestialErasure extends NyxPotion {
    public NyxPotionCelestialErasure(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.attackEntityFrom(NyxDamageSource.CELESTIAL, 2.0F + (2.0F * amplifier));
        entity.hurtResistantTime = 0;
        entity.hurtTime = 0;

        if (entity instanceof EntityLiving) {
            entity.setGlowing(true);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 10 >> amplifier;

        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }

    // Removes glowing when potion ends
    @SubscribeEvent
    public static void onPotionExpired(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(NyxPotions.ASTRAL_EROSION)) {
            if (entity instanceof EntityLiving) {
                entity.setGlowing(false);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionRemoved(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(NyxPotions.ASTRAL_EROSION)) {
            if (entity instanceof EntityLiving) {
                entity.setGlowing(false);
            }
        }
    }
}

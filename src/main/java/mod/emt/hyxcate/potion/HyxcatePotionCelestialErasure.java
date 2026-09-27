package mod.emt.hyxcate.potion;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.util.HyxcateDamageSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber(modid = Hyxcate.ID)
public class HyxcatePotionCelestialErasure extends HyxcatePotion {
    public HyxcatePotionCelestialErasure(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    // Removes glowing when potion ends
    @SubscribeEvent
    public static void onPotionExpired(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(HyxcatePotions.ASTRAL_EROSION)) {
            if (entity instanceof EntityLiving) {
                entity.setGlowing(false);
            }
        }
    }

    @SubscribeEvent
    public static void onPotionRemoved(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(HyxcatePotions.ASTRAL_EROSION)) {
            if (entity instanceof EntityLiving) {
                entity.setGlowing(false);
            }
        }
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.attackEntityFrom(HyxcateDamageSource.CELESTIAL, 2.0F + (2.0F * amplifier));
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
}

package mod.emt.hyxcate.potion;

import mod.emt.hyxcate.util.HyxcateDamageSource;
import net.minecraft.entity.EntityLivingBase;

public class HyxcatePotionCelestialErasure extends HyxcatePotion {
    public HyxcatePotionCelestialErasure(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.attackEntityFrom(HyxcateDamageSource.CELESTIAL, 2.0F + (2.0F * amplifier));
        entity.hurtResistantTime = 0;
        entity.hurtTime = 0;
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

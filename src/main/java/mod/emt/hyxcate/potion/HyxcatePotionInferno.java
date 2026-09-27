package mod.emt.hyxcate.potion;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.util.HyxcateDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = Hyxcate.ID)
public class HyxcatePotionInferno extends HyxcatePotion {
    public HyxcatePotionInferno(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        // BURN BURN BURN TO THE GROUND
        if (entity.isImmuneToFire() || entity.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
            entity.attackEntityFrom(HyxcateDamageSource.INFERNO, 2.0F + (2.0F * amplifier)); // 1.0F = 1 half heart
        } else {
            entity.attackEntityFrom(HyxcateDamageSource.INFERNO, 1.0F + (1.0F * amplifier));
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        int i = 20 >> amplifier;

        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}

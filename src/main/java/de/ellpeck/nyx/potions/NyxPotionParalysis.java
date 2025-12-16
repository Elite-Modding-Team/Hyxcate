package de.ellpeck.nyx.potions;

import de.ellpeck.nyx.util.NyxDamageSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class NyxPotionParalysis extends NyxPotionBase {
    public NyxPotionParalysis(String name, boolean isBadEffect, int liquidColor) {
        super(name, isBadEffect, liquidColor);
    }

    // TODO: Scale damage
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier) {
        entity.attackEntityFrom(NyxDamageSource.PARALYSIS, 1.0F);

        // Disable mob ai when paralyzed
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
        int i = 20 >> amplifier;

        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}

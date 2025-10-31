package de.ellpeck.nyx.compat.tinkers.traits;

import de.ellpeck.nyx.Nyx;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitKreknoritesBane extends AbstractTrait {
    public TraitKreknoritesBane() {
        super(Nyx.ID + "." + "kreknorites_bane", 0x8F0E0E);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        int level = 0;
        PotionEffect potionEffect = target.getActivePotionEffect(MobEffects.WITHER);

        if (potionEffect != null) {
            level = potionEffect.getAmplifier();
        }

        level = Math.min(4, level + 1);

        target.addPotionEffect(new PotionEffect(MobEffects.WITHER, 5 * 20, level));
        target.setFire(level + 1);
    }
}

package de.ellpeck.nyx.compat.tconstruct.traits;

import de.ellpeck.nyx.Nyx;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitFrezaritesBane extends AbstractTrait {
    public TraitFrezaritesBane() {
        super(Nyx.ID + "." + "frezarites_bane", 0x1AC5E1);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        int level = 0;
        PotionEffect potionEffect = target.getActivePotionEffect(MobEffects.SLOWNESS);

        if (potionEffect != null) {
            level = potionEffect.getAmplifier();
        }

        level = Math.min(4, level + 1);

        target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5 * 20, level));
    }
}

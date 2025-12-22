package de.ellpeck.nyx.compat.tconstruct.traits;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitKreknoritesBane extends AbstractTrait {
    public TraitKreknoritesBane() {
        super(Nyx.ID + "." + "kreknorites_bane", 0x8F0E0E);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        int level = -1;
        PotionEffect potionEffect = target.getActivePotionEffect(NyxPotions.INFERNO);

        if (potionEffect != null) {
            level = potionEffect.getAmplifier();
        }

        level = Math.min(4, level + 1);

        target.addPotionEffect(new PotionEffect(NyxPotions.INFERNO, 3 * 20, level));
    }
}

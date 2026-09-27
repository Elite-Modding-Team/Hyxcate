package mod.emt.hyxcate.compat.tconstruct.traits;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.init.HyxcatePotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitFrezaritesBane extends AbstractTrait {
    public TraitFrezaritesBane() {
        super(Hyxcate.ID + "." + "frezarites_bane", 0x1AC5E1);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        int level = -1;
        PotionEffect potionEffect = target.getActivePotionEffect(HyxcatePotions.DEEP_FREEZE);

        if (potionEffect != null) {
            level = potionEffect.getAmplifier();
        }

        level = Math.min(4, level + 1);

        target.addPotionEffect(new PotionEffect(HyxcatePotions.DEEP_FREEZE, 3 * 20, level));
    }
}

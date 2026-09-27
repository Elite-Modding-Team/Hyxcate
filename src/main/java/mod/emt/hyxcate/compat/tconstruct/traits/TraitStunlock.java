package mod.emt.hyxcate.compat.tconstruct.traits;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.util.HyxcateUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitStunlock extends AbstractTrait {
    public TraitStunlock() {
        super(Hyxcate.ID + "." + "stunlock", 0xC82323);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (HyxcateUtils.setChance(0.075F) && wasHit) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, HyxcateSoundEvents.EFFECT_PARALYSIS_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (target.world.rand.nextFloat() * 0.4F + 1.2F));
            target.addPotionEffect(new PotionEffect(HyxcatePotions.PARALYSIS, 5 * 20, 0));
        }
    }
}

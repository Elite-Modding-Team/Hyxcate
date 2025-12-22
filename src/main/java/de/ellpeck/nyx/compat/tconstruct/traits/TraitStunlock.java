package de.ellpeck.nyx.compat.tconstruct.traits;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxPotions;
import de.ellpeck.nyx.init.NyxSoundEvents;
import de.ellpeck.nyx.util.NyxUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitStunlock extends AbstractTrait {
    public TraitStunlock() {
        super(Nyx.ID + "." + "stunlock", 0xC82323);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (NyxUtils.setChance(0.15F) && wasHit) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, NyxSoundEvents.EFFECT_PARALYSIS_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (target.world.rand.nextFloat() * 0.4F + 1.2F));
            target.addPotionEffect(new PotionEffect(NyxPotions.PARALYSIS, 8 * 20, 0));
        }
    }
}

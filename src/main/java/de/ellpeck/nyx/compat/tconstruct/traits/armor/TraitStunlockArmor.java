package de.ellpeck.nyx.compat.tconstruct.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxPotions;
import de.ellpeck.nyx.init.NyxSoundEvents;
import de.ellpeck.nyx.util.NyxUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class TraitStunlockArmor extends AbstractArmorTrait {
    public TraitStunlockArmor() {
        super(Nyx.ID + "." + "stunlock", 0xC82323);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
        if (NyxUtils.setChance(0.025F)) {
            Entity trueSource = source.getTrueSource();

            if (!player.world.isRemote) {
                if (trueSource instanceof EntityLivingBase) {
                    trueSource.world.playSound(null, trueSource.posX, trueSource.posY, trueSource.posZ, NyxSoundEvents.EFFECT_PARALYSIS_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (trueSource.world.rand.nextFloat() * 0.4F + 1.2F));
                    ((EntityLivingBase) trueSource).addPotionEffect(new PotionEffect(NyxPotions.PARALYSIS, 5 * 20, 0));
                }
            }
        }

        return newDamage;
    }
}

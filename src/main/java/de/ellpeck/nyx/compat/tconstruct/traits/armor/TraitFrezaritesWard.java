package de.ellpeck.nyx.compat.tconstruct.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import de.ellpeck.nyx.Nyx;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class TraitFrezaritesWard extends AbstractArmorTrait {
    public TraitFrezaritesWard() {
        super(Nyx.ID + "." + "frezarites_ward", 0x1AC5E1);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
        Entity trueSource = source.getTrueSource();

        if (!player.world.isRemote) {
            if (trueSource instanceof EntityLivingBase) {
                int level = 0;
                PotionEffect potionEffect = ((EntityLivingBase) trueSource).getActivePotionEffect(MobEffects.SLOWNESS);

                if (potionEffect != null) {
                    level = potionEffect.getAmplifier();
                }

                level = Math.min(4, level + 1);

                ((EntityLivingBase) trueSource).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 5 * 20, level));
            }
        }

        return newDamage;
    }
}

package de.ellpeck.nyx.compat.tinkers.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitStunlockArmor extends AbstractArmorTrait {
    public TraitStunlockArmor() {
        super(Nyx.ID + "." + "stunlock", 0xC82323);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
        if (random.nextInt(10) == 0) {
            Entity trueSource = source.getTrueSource();

            if (!player.world.isRemote) {
                if (trueSource instanceof EntityLivingBase) {
                    trueSource.world.playSound(null, trueSource.posX, trueSource.posY, trueSource.posZ, NyxSoundEvents.stun.getSoundEvent(), SoundCategory.PLAYERS, 0.8F, 1.5F / (trueSource.world.rand.nextFloat() * 0.4F + 1.2F));
                    trueSource.world.playSound(null, trueSource.posX, trueSource.posY, trueSource.posZ, Sounds.shocking_discharge, SoundCategory.PLAYERS, 1.0F, 1.5F / (trueSource.world.rand.nextFloat() * 0.4F + 1.2F));
                    ((EntityLivingBase) trueSource).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 9));
                    TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ELECTRO, trueSource, 5);
                }
            }
        }

        return newDamage;
    }
}

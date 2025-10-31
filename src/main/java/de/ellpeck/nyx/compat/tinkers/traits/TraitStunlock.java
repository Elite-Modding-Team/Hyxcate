package de.ellpeck.nyx.compat.tinkers.traits;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitStunlock extends AbstractTrait {
    public TraitStunlock() {
        super(Nyx.ID + "." + "stunlock", 0xC82323);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (random.nextInt(5) == 0 && wasHit) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, NyxSoundEvents.stun.getSoundEvent(), SoundCategory.PLAYERS, 0.8F, 1.5F / (target.world.rand.nextFloat() * 0.4F + 1.2F));
            target.world.playSound(null, target.posX, target.posY, target.posZ, Sounds.shocking_discharge, SoundCategory.PLAYERS, 1.0F, 1.5F / (target.world.rand.nextFloat() * 0.4F + 1.2F));
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 9));
            TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ELECTRO, target, 5);
        }
    }
}

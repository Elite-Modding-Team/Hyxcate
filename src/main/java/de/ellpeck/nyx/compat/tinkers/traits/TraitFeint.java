package de.ellpeck.nyx.compat.tinkers.traits;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitFeint extends AbstractTrait {
    public TraitFeint() {
        super(Nyx.ID + "." + "feint", 0xDFCE62);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (random.nextInt(4) == 0 && wasHit) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, NyxSoundEvents.fallingStarImpact.getSoundEvent(), SoundCategory.PLAYERS, 0.5F, 2.0F / (target.world.rand.nextFloat() * 0.4F + 1.2F));
            TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ARMOR, target, 5);
            target.attackEntityFrom(DamageSource.causeMobDamage(player).setDamageBypassesArmor(), damageDealt * 1.25F); // +25% damage + armor piercing
        }
    }
}

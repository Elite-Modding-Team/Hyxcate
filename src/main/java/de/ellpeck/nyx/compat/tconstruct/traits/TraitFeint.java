package de.ellpeck.nyx.compat.tconstruct.traits;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import de.ellpeck.nyx.util.Utils;
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

    // TODO: Some additional starry particles would be nice
    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (Utils.setChance(0.2F) && wasHit) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, NyxSoundEvents.fallingStarImpact.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 2.0F / (target.world.rand.nextFloat() * 0.4F + 1.2F));
            TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ARMOR, target, 5);
            target.attackEntityFrom(DamageSource.causeMobDamage(player).setDamageBypassesArmor(), damageDealt * 1.25F); // +25% damage + armor piercing
        }
    }
}

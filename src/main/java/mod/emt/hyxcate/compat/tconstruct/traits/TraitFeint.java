package mod.emt.hyxcate.compat.tconstruct.traits;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.util.HyxcateUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitFeint extends AbstractTrait {
    public TraitFeint() {
        super(Hyxcate.ID + "." + "feint", 0xDFCE62);
    }

    // TODO: Some additional starry particles would be nice
    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (HyxcateUtils.setChance(0.2F) && wasHit) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, HyxcateSoundEvents.ENTITY_STAR_IMPACT.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 2.0F / (target.world.rand.nextFloat() * 0.4F + 1.2F));
            TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ARMOR, target, 5);
            target.attackEntityFrom(DamageSource.causeMobDamage(player).setDamageBypassesArmor(), damageDealt * 1.25F); // +25% damage + armor piercing
        }
    }
}

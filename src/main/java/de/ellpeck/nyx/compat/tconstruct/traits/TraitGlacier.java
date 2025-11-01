package de.ellpeck.nyx.compat.tconstruct.traits;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

// Same as Volcano except it slows!
public class TraitGlacier extends AbstractTrait {
    public TraitGlacier() {
        super(Nyx.ID + "." + "glacier", 0x1AC5E1);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (target.getHealth() > 0) {
            target.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, target.posX, target.posY + 1.0D, target.posZ, 0.0D, 0.0D, 0.0D);

            for (Entity nearbyLivingEntity : target.world.getEntitiesWithinAABBExcludingEntity(player, target.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D))) {
                if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(player) && !nearbyLivingEntity.isEntityEqual(player)) {
                    for (int i = 0; i < 20; ++i) {
                        double d0 = target.world.rand.nextGaussian() * 0.02D;
                        double d1 = target.world.rand.nextGaussian() * 0.02D;
                        double d2 = target.world.rand.nextGaussian() * 0.02D;
                        nearbyLivingEntity.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, nearbyLivingEntity.posX + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.width * 2.0F) - (double) nearbyLivingEntity.width - d0 * 10.0D, nearbyLivingEntity.posY + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.height) - d1 * 10.0D, nearbyLivingEntity.posZ + (double) (nearbyLivingEntity.world.rand.nextFloat() * nearbyLivingEntity.width * 2.0F) - (double) nearbyLivingEntity.width - d2 * 10.0D, d0, d1, d2);
                    }
                }
            }
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (player instanceof EntityPlayer) {
            target.world.playSound(null, target.posX, target.posY, target.posZ, NyxSoundEvents.glacier.getSoundEvent(), SoundCategory.PLAYERS, 0.75F, 2.0F / (target.world.rand.nextFloat() * 0.4F + 1.2F));
            // Explosion deals AoE damage
            for (Entity nearbyLivingEntity : target.world.getEntitiesWithinAABBExcludingEntity(player, target.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D))) {
                if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(player) && !nearbyLivingEntity.isEntityEqual(player)) {
                    if (nearbyLivingEntity instanceof EntityLiving) {
                        EntityLiving entity = (EntityLiving) nearbyLivingEntity;

                        entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10 * 20, 2));
                    }

                    TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_FIRE, nearbyLivingEntity, 5);
                    nearbyLivingEntity.attackEntityFrom(DamageSource.causeMobDamage(player), damageDealt);
                }
            }
        }
    }
}

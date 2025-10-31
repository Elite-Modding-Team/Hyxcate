package de.ellpeck.nyx.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class NyxEntityLaser extends EntityThrowable {
    private float damage;

    public NyxEntityLaser(World world, EntityLivingBase entity, float damageAmount) {
        super(world, entity);
        this.rotationPitch = entity.rotationPitch;
        this.rotationYaw = entity.renderYawOffset;
        this.damage = damageAmount;
    }

    public NyxEntityLaser(final World world, final double x, final double y, final double z) {
        super(world, x, y, z);
    }

    public NyxEntityLaser(final World world, EntityPlayer player, float damageAmount) {
        super(world, player);
        this.rotationPitch = -player.rotationPitch;
        this.rotationYaw = -player.rotationYaw;
        this.damage = damageAmount;
    }

    protected void onImpact(RayTraceResult result) {
        Entity shooter = this.thrower;
        Entity target = result.entityHit;

        if (!this.world.isRemote && result != null) {
            if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
                if (target != null && shooter != null && target instanceof EntityLivingBase && target != shooter && !target.isOnSameTeam(shooter)) {
                    if (target.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, shooter).setProjectile(), this.damage)) {
                        float local_difficulty = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty();
                        // 5 seconds * regional difficulty
                        int difficulty_calculation = (int) (100 * local_difficulty);

                        target.setFire(10 + difficulty_calculation);

                        if (shooter instanceof EntityLivingBase) {
                            this.applyEnchantments((EntityLivingBase) shooter, target);
                        }
                    }
                }
            } else if (result.typeOfHit == RayTraceResult.Type.BLOCK) {
                if (!world.isRemote) {
                    world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0.0, 0.0, 0.0);
                }
            }
        }
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.ticksExisted > 70) {
            this.setDead();
        }
    }

    protected float getGravityVelocity() {
        return 0.0F;
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public boolean attackEntityFrom(DamageSource source, int amount) {
        return false;
    }
}



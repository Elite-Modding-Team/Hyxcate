package de.ellpeck.nyx.entity;

import de.ellpeck.nyx.init.NyxEntities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class NyxEntityStellarProtector extends EntityMob implements IMob {
    /**
     * Random offset used in floating behaviour
     */
    private float heightOffset = 0.5F;
    /**
     * ticks until heightOffset is randomized
     */
    private int heightOffsetUpdateTime;

    public NyxEntityStellarProtector(World world) {
        super(world);
        this.tasks.addTask(4, new NyxEntityStellarProtector.AIFireballAttack(this));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
        isImmuneToFire = true;
        this.experienceValue = 20;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
    }

    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }

        if (this.world.isRemote) {
            /*if (this.rand.nextInt(24) == 0 && !this.isSilent()) {
                this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, this.getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
            }*/

            for (int i = 0; i < 2; ++i) {
                this.world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
            }
        }

        super.onLivingUpdate();
    }

    protected void updateAITasks() {
        --this.heightOffsetUpdateTime;

        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = 100;
            this.heightOffset = 0.5F + (float) this.rand.nextGaussian() * 3.0F;
        }

        EntityLivingBase entitylivingbase = this.getAttackTarget();

        if (entitylivingbase != null && entitylivingbase.posY + (double) entitylivingbase.getEyeHeight() > this.posY + (double) this.getEyeHeight() + (double) this.heightOffset) {
            this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
            this.isAirBorne = true;
        }

        super.updateAITasks();
    }

    public void fall(float distance, float damageMultiplier) {
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() {
        return null;
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel() {
        return true;
    }

    static class AIFireballAttack extends EntityAIBase {
        private final NyxEntityStellarProtector phase;
        private int attackStep;
        private int attackTime;

        public AIFireballAttack(NyxEntityStellarProtector phaseIn) {
            this.phase = phaseIn;
            this.setMutexBits(3);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.phase.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.attackStep = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            //this.phase.setOnFire(false);
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask() {
            --this.attackTime;
            EntityLivingBase entitylivingbase = this.phase.getAttackTarget();
            double d0 = this.phase.getDistanceSq(entitylivingbase);

            if (d0 < 4.0D) {
                if (this.attackTime <= 0) {
                    this.attackTime = 20;
                    this.phase.attackEntityAsMob(entitylivingbase);
                }

                this.phase.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            } else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
                double d1 = entitylivingbase.posX - this.phase.posX;
                double d2 = entitylivingbase.getEntityBoundingBox().minY + (double) (entitylivingbase.height / 2.0F) - (this.phase.posY + (double) (this.phase.height / 2.0F));
                double d3 = entitylivingbase.posZ - this.phase.posZ;

                if (this.attackTime <= 0) {
                    ++this.attackStep;

                    if (this.attackStep == 1) {
                        this.attackTime = 60;
                        //this.phase.setOnFire(true);
                    } else if (this.attackStep <= 4) {
                        this.attackTime = 6;
                    } else {
                        this.attackTime = 100;
                        this.attackStep = 0;
                        //this.phase.setOnFire(false);
                    }

                    if (this.attackStep > 1) {
                        float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
                        this.phase.world.playEvent(null, 1018, new BlockPos((int) this.phase.posX, (int) this.phase.posY, (int) this.phase.posZ), 0);

                        for (int i = 0; i < 1; ++i) {
                            EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.phase.world, this.phase, d1 + this.phase.getRNG().nextGaussian() * (double) f, d2, d3 + this.phase.getRNG().nextGaussian() * (double) f);
                            entitysmallfireball.posY = this.phase.posY + (double) (this.phase.height / 2.0F) + 0.5D;
                            this.phase.world.spawnEntity(entitysmallfireball);
                        }
                    }
                }

                this.phase.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            } else {
                this.phase.getNavigator().clearPath();
                this.phase.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }

            super.updateTask();
        }

        private double getFollowDistance() {
            IAttributeInstance iattributeinstance = this.phase.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
            return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
        }
    }
}

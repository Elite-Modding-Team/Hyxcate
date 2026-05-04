package de.ellpeck.nyx.entity;

import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.init.NyxLootTables;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NyxEntityEyezor extends EntityZombie implements IRangedAttackMob {
    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(NyxEntityEyezor.class, DataSerializers.VARINT);

    public NyxEntityEyezor(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TYPE, 0);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        // TODO: Turn into unique hybrid AI
        this.tasks.addTask(2, new EntityAIAttackRanged(this, 1.0D, 30, 16.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(7.0D);
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() {
        return NyxLootTables.EYEZOR;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("type", this.dataManager.get(TYPE));
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(TYPE, compound.getInteger("type"));
    }

    @Nullable
    @Override
    public IEntityLivingData onInitialSpawn(@Nonnull DifficultyInstance difficulty, @Nullable IEntityLivingData entityLivingData) {
        this.setType(this.rand.nextInt(2));

        return super.onInitialSpawn(difficulty, entityLivingData);
    }

    public int getType() {
        return this.dataManager.get(TYPE);
    }

    public void setType(int skinType) {
        this.dataManager.set(TYPE, skinType);
    }

    public IAttribute getReinforcementsAttribute() {
        return EntityZombie.SPAWN_REINFORCEMENTS_CHANCE;
    }

    @Override
    protected boolean shouldBurnInDay() {
        return false;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        /*double d0 = target.posY + (double) target.getEyeHeight() - 2.0D;
        double d1 = target.posX + target.motionX - this.posX;
        double d2 = d0 - this.posY;
        double d3 = target.posZ + target.motionZ - this.posZ;
        NyxEntityLaser laser = new NyxEntityLaser(this.world, this, 8.0F, NyxConfig.ENTITIES.EYEZOR.laserColor);
        laser.shoot(d1, d2, d3, 1.0F, 1.0F);
        this.world.playSound(null, this.posX, this.posY, this.posZ, NyxSoundEvents.RANDOM_LASER.getSoundEvent(), SoundCategory.HOSTILE, 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
        this.world.spawnEntity(laser);*/
    }

    @Override
    public boolean isOnSameTeam(@Nonnull Entity entity) {
        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isOnSameTeam(entity)) {
            return true;
        } else if (entity instanceof NyxEntityEyezor) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return NyxSoundEvents.ENTITY_EYEZOR_HURT.getSoundEvent();
    }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound() {
        return NyxSoundEvents.ENTITY_EYEZOR_DEATH.getSoundEvent();
    }
}

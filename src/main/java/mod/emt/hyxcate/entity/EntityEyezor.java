package mod.emt.hyxcate.entity;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.celestialevent.lunar.LunarEventStarShower;
import mod.emt.hyxcate.init.HyxcateLootTables;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.util.ParticleUtil;
import net.minecraft.entity.*;
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

public class EntityEyezor extends EntityZombie {
    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityEyezor.class, DataSerializers.VARINT);

    public EntityEyezor(World world) {
        super(world);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TYPE, 0);
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
        return HyxcateLootTables.EYEZOR;
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
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(world);
        if (Hyxcate != null) {
            if (Hyxcate.currentLunarEvent instanceof LunarEventStarShower) {
                this.setType(1);
            }
        }
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
    public void onUpdate() {
        super.onUpdate();
        this.rotationYaw = this.rotationYawHead;
        if (!this.isDead && this.getHealth() > 0 && !this.isAIDisabled() && this.getAttackTarget() != null && !this.world.isRemote) {
            float health = this.getHealth();
            float maxHealth = this.getMaxHealth();
            float healthPercent = health / maxHealth;
            int attackDelay = (int) (10.0F + 90.0F * healthPercent);
            if (this.ticksExisted % attackDelay == 0) {
                EntityLaser laser = getLaser();
                this.world.playSound(null, this.posX, this.posY, this.posZ, HyxcateSoundEvents.RANDOM_LASER.getSoundEvent(), SoundCategory.HOSTILE, 1.0F, 0.4F + this.rand.nextFloat() * 0.4F);
                this.world.spawnEntity(laser);
            }
        }
    }

    @Nonnull
    private EntityLaser getLaser() {
        EntityLivingBase target = this.getAttackTarget();
        double d0 = target.posY + (double) target.getEyeHeight() - 2.0D;
        double d1 = target.posX + target.motionX - this.posX;
        double d2 = d0 - this.posY;
        double d3 = target.posZ + target.motionZ - this.posZ;
        int laserColor = HyxcateConfig.ENTITIES.EYEZOR.laserColor;
        EntityLaser laser = new EntityLaser(this.world, this, 6.0F, laserColor);
        for (int i = 0; i < 10; ++i) {
            float r = (float) (laserColor >> 16 & 255) / 255.0F;
            float g = (float) (laserColor >> 8 & 255) / 255.0F;
            float b = (float) (laserColor & 255) / 255.0F;
            double vx = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
            double vy = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
            double vz = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
            ParticleUtil.spawnParticleGlow(this.world, (float) this.posX, (float) this.posY + this.getEyeHeight(), (float) this.posZ, (float) vx, (float) vy, (float) vz, r, g, b, 0.8F, 1.0F, 30);
        }
        laser.shoot(d1, d2, d3, 1.0F, 1.0F);
        return laser;
    }

    @Override
    public boolean isOnSameTeam(@Nonnull Entity entity) {
        if (entity == null) {
            return false;
        } else if (entity == this) {
            return true;
        } else if (super.isOnSameTeam(entity)) {
            return true;
        } else if (entity instanceof EntityEyezor) {
            return this.getTeam() == null && entity.getTeam() == null;
        } else {
            return false;
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Nonnull
    @Override
    protected SoundEvent getHurtSound(@Nonnull DamageSource damageSource) {
        return HyxcateSoundEvents.ENTITY_EYEZOR_HURT.getSoundEvent();
    }

    @Nonnull
    @Override
    protected SoundEvent getDeathSound() {
        return HyxcateSoundEvents.ENTITY_EYEZOR_DEATH.getSoundEvent();
    }
}

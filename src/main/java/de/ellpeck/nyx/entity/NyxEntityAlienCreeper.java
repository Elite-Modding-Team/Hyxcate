package de.ellpeck.nyx.entity;

import de.ellpeck.nyx.init.NyxEntities;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NyxEntityAlienCreeper extends EntityCreeper {
    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(NyxEntityAlienCreeper.class, DataSerializers.VARINT);

    public NyxEntityAlienCreeper(World world) {
        super(world);
        this.isImmuneToFire = true;
        this.dataManager.set(TYPE, world.rand.nextInt(4) + 1);
    }

    public NyxEntityAlienCreeper(World world, int type) {
        super(world);
        this.isImmuneToFire = true;
        this.dataManager.set(TYPE, type);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(TYPE, 1);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAICreeperSwell(this));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, NyxEntityAlienKitty.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, NyxEntityCometKitty.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() {
        return NyxEntities.ALIEN_CREEPER;
    }

    @Override
    public boolean getPowered() {
        return true;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public int getTalkInterval() {
        return super.getTalkInterval() * 2;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return NyxSoundEvents.ENTITY_ALIEN_CREEPER_IDLE.getSoundEvent();
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("type", this.dataManager.get(TYPE));
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(TYPE, compound.getInteger("type"));
    }
}

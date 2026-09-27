package mod.emt.hyxcate.entity;

import mod.emt.hyxcate.init.HyxcateLootTables;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
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

public class EntityAlienCreeper extends EntityCreeper {
    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityAlienCreeper.class, DataSerializers.VARINT);

    public EntityAlienCreeper(World world) {
        super(world);
        this.isImmuneToFire = true;
        this.dataManager.set(TYPE, world.rand.nextInt(4) + 1);
    }

    public EntityAlienCreeper(World world, int type) {
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
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityAlienKitty.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, EntityCometKitty.class, 6.0F, 1.0D, 1.2D));
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
        switch (this.getDataManager().get(TYPE)) {
            case 2: // Frezarite
                return HyxcateLootTables.ALIEN_CREEPER_FREZARITE;
            case 3: // Kreknorite
                return HyxcateLootTables.ALIEN_CREEPER_KREKNORITE;
            default: // Meteorite
                return HyxcateLootTables.ALIEN_CREEPER_METEORITE;
        }
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
        return HyxcateSoundEvents.ENTITY_ALIEN_CREEPER_IDLE.getSoundEvent();
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

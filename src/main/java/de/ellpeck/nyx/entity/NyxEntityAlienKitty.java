package de.ellpeck.nyx.entity;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxLootTables;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NyxEntityAlienKitty extends EntityOcelot {
    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(NyxEntityAlienKitty.class, DataSerializers.VARINT);

    public NyxEntityAlienKitty(World world) {
        super(world);
        this.isImmuneToFire = true;
        this.dataManager.set(TYPE, world.rand.nextInt(4) + 1);
    }

    public NyxEntityAlienKitty(World world, int type) {
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
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() {
        switch (this.getDataManager().get(TYPE)) {
            case 2: // Frezarite
                return NyxLootTables.ALIEN_KITTY_FREZARITE;
            case 3: // Kreknorite
                return NyxLootTables.ALIEN_KITTY_KREKNORITE;
            default: // Alien Green
                return NyxLootTables.ALIEN_KITTY_GREEN;
        }
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingData) {
        if (this.world.rand.nextInt(7) == 0) {
            for (int i = 0; i < 2; ++i) {
                NyxEntityAlienKitty alienKitty = new NyxEntityAlienKitty(this.world);
                alienKitty.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                alienKitty.setGrowingAge(-24000);
                this.world.spawnEntity(alienKitty);
            }
        }
        return livingData;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public NyxEntityAlienKitty createChild(EntityAgeable ageable) {
        NyxEntityAlienKitty alienKitty = new NyxEntityAlienKitty(this.world);
        if (this.isTamed()) {
            alienKitty.setOwnerId(this.getOwnerId());
            alienKitty.setTamed(true);
            alienKitty.setTameSkin(this.getTameSkin());
        }
        return alienKitty;
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(otherAnimal instanceof NyxEntityAlienKitty)) {
            return false;
        } else {
            NyxEntityAlienKitty alienKitty = (NyxEntityAlienKitty) otherAnimal;
            if (!alienKitty.isTamed()) {
                return false;
            } else {
                return this.isInLove() && alienKitty.isInLove();
            }
        }
    }

    // This needs to be done or else it will be named 'Cat' when tamed
    @Override
    public String getName() {
        if (this.hasCustomName()) {
            return this.getCustomNameTag();
        } else {
            return this.isTamed() ? I18n.format("entity." + Nyx.ID + ".comet_kitty.name") : super.getName();
        }
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return NyxSoundEvents.ENTITY_ALIEN_KITTY_IDLE.getSoundEvent();
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

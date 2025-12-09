package de.ellpeck.nyx.entities;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxEntities;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NyxEntityCometKitty extends EntityOcelot {
    public NyxEntityCometKitty(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
    }

    @Nonnull
    @Override
    protected ResourceLocation getLootTable() {
        return NyxEntities.COMET_KITTY;
    }

    @Override
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingData) {
        if (this.world.rand.nextInt(7) == 0) {
            for (int i = 0; i < 2; ++i) {
                NyxEntityCometKitty cometKitty = new NyxEntityCometKitty(this.world);
                cometKitty.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
                cometKitty.setGrowingAge(-24000);
                this.world.spawnEntity(cometKitty);
            }
        }
        return livingData;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public NyxEntityCometKitty createChild(EntityAgeable ageable) {
        NyxEntityCometKitty cometKitty = new NyxEntityCometKitty(this.world);
        if (this.isTamed()) {
            cometKitty.setOwnerId(this.getOwnerId());
            cometKitty.setTamed(true);
            cometKitty.setTameSkin(this.getTameSkin());
        }
        return cometKitty;
    }

    @Override
    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!this.isTamed()) {
            return false;
        } else if (!(otherAnimal instanceof NyxEntityCometKitty)) {
            return false;
        } else {
            NyxEntityCometKitty cometKitty = (NyxEntityCometKitty) otherAnimal;
            if (!cometKitty.isTamed()) {
                return false;
            } else {
                return this.isInLove() && cometKitty.isInLove();
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
}

package de.ellpeck.nyx.entities;

import de.ellpeck.nyx.init.NyxEntities;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class NyxEntityAlienCreeper extends EntityCreeper {
    public NyxEntityAlienCreeper(World world) {
        super(world);
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, NyxEntityAlienKitty.class, 6.0F, 0.25F, 0.3F));
        this.tasks.addTask(3, new EntityAIAvoidEntity<>(this, NyxEntityCometKitty.class, 6.0F, 0.25F, 0.3F));
        isImmuneToFire = true;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
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
}

package de.ellpeck.nyx.entities;

import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.events.lunar.NyxEventBloodMoon;
import de.ellpeck.nyx.events.lunar.NyxEventFullMoon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;

public class NyxAIWolfSpecialMoon extends EntityAITargetNonTamed<EntityLivingBase> {
    public NyxAIWolfSpecialMoon(EntityTameable entityIn) {
        super(entityIn, EntityLivingBase.class, false, e -> {
            if (e instanceof EntityWolf) return false;
            return e instanceof EntityPlayer || e instanceof EntityAnimal || e instanceof EntitySkeleton;
        });
    }

    @Override
    public boolean shouldExecute() {
        return super.shouldExecute() && this.shouldHappen();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return super.shouldContinueExecuting() && this.shouldHappen();
    }

    private boolean shouldHappen() {
        NyxWorld nyx = NyxWorld.get(this.taskOwner.world);
        if (nyx == null) return false;
        return nyx.currentLunarEvent instanceof NyxEventFullMoon || nyx.currentLunarEvent instanceof NyxEventBloodMoon;
    }
}

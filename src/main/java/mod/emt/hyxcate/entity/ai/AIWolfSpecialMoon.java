package mod.emt.hyxcate.entity.ai;

import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.event.lunar.LunarEventBloodMoon;
import mod.emt.hyxcate.event.lunar.LunarEventFullMoon;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;

public class AIWolfSpecialMoon extends EntityAITargetNonTamed<EntityLivingBase> {
    public AIWolfSpecialMoon(EntityTameable entityIn) {
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
        HyxcateWorld Hyxcate = HyxcateWorld.get(this.taskOwner.world);
        if (Hyxcate == null) return false;
        return Hyxcate.currentLunarEvent instanceof LunarEventFullMoon || Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon;
    }
}

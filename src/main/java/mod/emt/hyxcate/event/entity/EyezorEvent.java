package mod.emt.hyxcate.event.entity;

import mod.emt.hyxcate.entity.EntityEyezor;
import mod.emt.hyxcate.init.HyxcateItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.ZombieEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EyezorEvent {
    @SubscribeEvent
    public void onAttackEvent(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        // Don't harm other mobs of the same team
        if (trueSource instanceof EntityEyezor && trueSource != null) {
            if (entity.isOnSameTeam(trueSource)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onZombieSummonAid(ZombieEvent.SummonAidEvent event) {
        if (event.getEntity() instanceof EntityEyezor) {
            event.setCustomSummonedAid(new EntityEyezor(event.getWorld()));
            if (((EntityLivingBase) event.getEntity()).getRNG().nextFloat() < ((EntityEyezor) event.getEntity()).getEntityAttribute(((EntityEyezor) event.getEntity()).getReinforcementsAttribute()).getAttributeValue()) {
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }
}

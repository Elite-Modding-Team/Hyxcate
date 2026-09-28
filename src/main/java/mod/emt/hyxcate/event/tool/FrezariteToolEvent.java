package mod.emt.hyxcate.event.tool;

import mod.emt.hyxcate.init.HyxcateItems;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.item.tool.IHyxcateTool;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FrezariteToolEvent {
    @SubscribeEvent
    public void onDamageEvent(LivingDamageEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        if (entity instanceof EntityLivingBase && trueSource instanceof EntityLivingBase) {
            Item heldItem = ((EntityLivingBase) trueSource).getHeldItemMainhand().getItem();

            if (heldItem instanceof IHyxcateTool && !damageSource.damageType.equals("mob")) {
                Item.ToolMaterial material = ((IHyxcateTool) heldItem).getToolMaterial();

                if (material == HyxcateItems.frezariteToolMaterial) {
                    entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_DEEP_FREEZE_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));

                    // Explosion deals AoE damage
                    for (Entity nearbyLivingEntity : entity.world.getEntitiesWithinAABBExcludingEntity(trueSource, entity.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D))) {
                        if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(trueSource) && !nearbyLivingEntity.isEntityEqual(trueSource)) {
                            if (nearbyLivingEntity instanceof EntityLiving) {
                                EntityLiving entity2 = (EntityLiving) nearbyLivingEntity;
                                entity2.addPotionEffect(new PotionEffect(HyxcatePotions.DEEP_FREEZE, 8 * 20, 0));
                            }
                            nearbyLivingEntity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) trueSource), event.getAmount() + 4.0F);
                        }
                    }
                }
            }
        }
    }
}

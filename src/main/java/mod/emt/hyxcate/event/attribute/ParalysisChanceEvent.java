package mod.emt.hyxcate.event.attribute;

import mod.emt.hyxcate.init.HyxcateAttributes;
import mod.emt.hyxcate.init.HyxcatePotions;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.util.RandomUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ParalysisChanceEvent {
    @SubscribeEvent
    public void onDamageEvent(LivingDamageEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        if (entity instanceof EntityLivingBase && trueSource instanceof EntityLivingBase) {
            IAttributeInstance paralysis = ((EntityLivingBase) trueSource).getEntityAttribute(HyxcateAttributes.PARALYSIS);

            if (paralysis != null && !paralysis.getModifiers().isEmpty()) {
                float paralysisValue = 0.0F;

                for (AttributeModifier attributemodifier : paralysis.getModifiers()) {
                    paralysisValue += (float) attributemodifier.getAmount();
                }

                // Inflicts mob with Paralysis when the attribute is successful
                if (paralysisValue > 0 && RandomUtil.setChance(paralysisValue)) {
                    entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_PARALYSIS_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
                    entity.addPotionEffect(new PotionEffect(HyxcatePotions.PARALYSIS, 8 * 20, 0));
                }
            }
        }
    }
}

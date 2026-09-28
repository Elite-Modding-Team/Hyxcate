package mod.emt.hyxcate.event;

import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.util.HyxcateDamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DamageSourceEvent {
    // Plays different sounds when these specific damage sources are applied
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();

        if (damageSource == HyxcateDamageSource.CELESTIAL) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.RANDOM_STAR_AURA.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        if (damageSource == HyxcateDamageSource.DEEP_FREEZE) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_DEEP_FREEZE_START.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        if (damageSource == HyxcateDamageSource.INFERNO) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_INFERNO_START.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        if (damageSource == HyxcateDamageSource.PARALYSIS) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_PARALYSIS_ZAP.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }
    }
}

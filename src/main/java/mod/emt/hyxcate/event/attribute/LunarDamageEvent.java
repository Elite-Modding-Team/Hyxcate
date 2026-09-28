package mod.emt.hyxcate.event.attribute;

import mod.emt.hyxcate.init.HyxcateAttributes;
import mod.emt.hyxcate.util.helpers.AttributeHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LunarDamageEvent {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        if (entity instanceof EntityLivingBase && trueSource instanceof EntityLivingBase) {
            long time = entity.world.getWorldTime() % 24000;
            boolean isNight = (time >= 13000 && time < 23000);

            float extraDamage = 0.0F;

            if (isNight) {
                extraDamage += AttributeHelper.getAttributeValue((EntityLivingBase) trueSource, HyxcateAttributes.LUNAR_DAMAGE);
            }

            if (extraDamage <= 0) return;
            event.setAmount(event.getAmount() + extraDamage);
        }
    }
}

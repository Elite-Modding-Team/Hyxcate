package mod.emt.hyxcate.event.attribute;

import mod.emt.hyxcate.init.HyxcateAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SolarWardEvent {
    @SubscribeEvent
    public void onDamage(LivingDamageEvent event) {
        long time = event.getEntity().world.getWorldTime() % 24000;
        boolean isDay = (time > 0 && time < 12000);

        // Solar Ward Attribute
        if (isDay) {
            IAttributeInstance solarWard = event.getEntityLiving().getEntityAttribute(HyxcateAttributes.SOLAR_WARD);

            if (solarWard != null && !solarWard.getModifiers().isEmpty()) {
                float solarWardValue = 0.0F;

                for (AttributeModifier attributemodifier : solarWard.getModifiers()) {
                    solarWardValue += (float) attributemodifier.getAmount();
                }

                if (solarWardValue <= 0) return;

                // If exceeding 100%, set to 100% to prevent healing
                if (solarWardValue > 1.0F) {
                    solarWardValue = 1.0F;
                }

                // Reduce damage by attribute amount
                event.setAmount(event.getAmount() * (1.0F - solarWardValue));
            }
        }
    }
}

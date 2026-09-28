package mod.emt.hyxcate.event.attribute;

import mod.emt.hyxcate.init.HyxcateAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LunarWardEvent {
    @SubscribeEvent
    public void onDamage(LivingDamageEvent event) {
        long time = event.getEntity().world.getWorldTime() % 24000;
        boolean isNight = (time >= 13000 && time < 23000);

        // Lunar Ward Attribute
        if (isNight) {
            IAttributeInstance lunarWard = event.getEntityLiving().getEntityAttribute(HyxcateAttributes.LUNAR_WARD);

            if (lunarWard != null && !lunarWard.getModifiers().isEmpty()) {
                float lunarWardValue = 0.0F;

                for (AttributeModifier attributemodifier : lunarWard.getModifiers()) {
                    lunarWardValue += (float) attributemodifier.getAmount();
                }

                if (lunarWardValue <= 0) return;

                // If exceeding 100%, set to 100% to prevent healing
                if (lunarWardValue > 1.0F) {
                    lunarWardValue = 1.0F;
                }

                // Reduce damage by attribute amount
                event.setAmount(event.getAmount() * (1.0F - lunarWardValue));
            }
        }
    }
}

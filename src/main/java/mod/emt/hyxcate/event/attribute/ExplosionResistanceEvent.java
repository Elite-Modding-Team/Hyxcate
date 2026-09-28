package mod.emt.hyxcate.event.attribute;

import mod.emt.hyxcate.init.HyxcateAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ExplosionResistanceEvent {
    @SubscribeEvent
    public void onDamage(LivingDamageEvent event) {
        if (event.getSource().isExplosion()) {
            IAttributeInstance explosionResistance = event.getEntityLiving().getEntityAttribute(HyxcateAttributes.EXPLOSION_RESISTANCE);

            if (explosionResistance != null && !explosionResistance.getModifiers().isEmpty()) {
                float explosionResistanceValue = 0.0F;

                for (AttributeModifier attributemodifier : explosionResistance.getModifiers()) {
                    explosionResistanceValue += (float) attributemodifier.getAmount();
                }
                if (explosionResistanceValue <= 0) return;

                // If exceeding 100%, set to 100% to prevent healing
                if (explosionResistanceValue > 1.0F) {
                    explosionResistanceValue = 1.0F;
                }

                // Reduce explosion damage by attribute amount
                event.setAmount(event.getAmount() * (1.0F - explosionResistanceValue));
            }
        }
    }
}

package mod.emt.hyxcate.event.enchantment;

import com.expandedevents.api.event.ItemAttributeModifierEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcateAttributes;
import mod.emt.hyxcate.init.HyxcateEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;

public class SolarEdgeEvent {
    public static int solarEdgeLevel;

    @SubscribeEvent
    public void onItemAttribute(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        solarEdgeLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.solarEdge, stack);
        double solarEdgeBonus = HyxcateConfig.GENERAL.solarEdgeDamageBase + (HyxcateConfig.GENERAL.solarEdgeDamageSubsequent * solarEdgeLevel);

        if (solarEdgeLevel > 0 && event.getSlotType() == EntityEquipmentSlot.MAINHAND) {
            Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.SOLAR_DAMAGE.getName());
            AttributeModifier toModify = null;

            for (AttributeModifier modifier : modifiers) {
                if (modifier.getID().equals(HyxcateAttributes.SOLAR_DAMAGE_TOOL_ID)) {
                    toModify = modifier;
                    break;
                }
            }

            if (toModify != null) {
                event.removeModifier(HyxcateAttributes.SOLAR_DAMAGE, toModify);
                event.addModifier(HyxcateAttributes.SOLAR_DAMAGE, new AttributeModifier(
                        toModify.getID(),
                        toModify.getName(),
                        toModify.getAmount() + solarEdgeBonus,
                        toModify.getOperation())
                );
            } else {
                event.addModifier(HyxcateAttributes.SOLAR_DAMAGE, new AttributeModifier(
                        HyxcateAttributes.SOLAR_DAMAGE_TOOL_ID,
                        "Solar Damage modifier",
                        solarEdgeBonus,
                        Constants.AttributeModifierOperation.ADD)
                );
            }
        }
    }
}

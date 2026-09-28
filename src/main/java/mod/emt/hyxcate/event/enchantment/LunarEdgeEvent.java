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

public class LunarEdgeEvent {
    public static int lunarEdgeLevel;

    @SubscribeEvent
    public void onItemAttribute(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        lunarEdgeLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.lunarEdge, stack);
        double lunarEdgeBonus = HyxcateConfig.GENERAL.lunarEdgeDamageBase + (HyxcateConfig.GENERAL.lunarEdgeDamageSubsequent * lunarEdgeLevel);

        if (lunarEdgeLevel > 0 && event.getSlotType() == EntityEquipmentSlot.MAINHAND) {
            Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.LUNAR_DAMAGE.getName());
            AttributeModifier toModify = null;

            for (AttributeModifier modifier : modifiers) {
                if (modifier.getID().equals(HyxcateAttributes.LUNAR_DAMAGE_TOOL_ID)) {
                    toModify = modifier;
                    break;
                }
            }

            if (toModify != null) {
                event.removeModifier(HyxcateAttributes.LUNAR_DAMAGE, toModify);
                event.addModifier(HyxcateAttributes.LUNAR_DAMAGE, new AttributeModifier(
                        toModify.getID(),
                        toModify.getName(),
                        toModify.getAmount() + lunarEdgeBonus,
                        toModify.getOperation())
                );
            } else {
                event.addModifier(HyxcateAttributes.LUNAR_DAMAGE, new AttributeModifier(
                        HyxcateAttributes.LUNAR_DAMAGE_TOOL_ID,
                        "Lunar Damage modifier",
                        lunarEdgeBonus,
                        Constants.AttributeModifierOperation.ADD)
                );
            }
        }
    }
}

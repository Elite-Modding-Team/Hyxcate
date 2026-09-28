package mod.emt.hyxcate.event.enchantment;

import com.expandedevents.api.event.ItemAttributeModifierEvent;
import mod.emt.hyxcate.init.HyxcateAttributes;
import mod.emt.hyxcate.init.HyxcateEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.UUID;

public class MagnetiferousEvent {
    public static int magnetizationLevel;

    @SubscribeEvent
    public void onItemAttribute(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        magnetizationLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.magnetization, stack);
        double magnetizationBonus = 0.5D * magnetizationLevel;

        // Checks are a bit hacky but we don't want the slot types to overlap
        if (magnetizationLevel > 0 && stack.getItem() instanceof ItemArmor && event.getSlotType() == ((ItemArmor) stack.getItem()).armorType) {
            UUID magnetizationArmorSlotId = HyxcateAttributes.MAGNETIZATION_ARMOR_ID.get(event.getSlotType());

            Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.MAGNETIZATION.getName());
            AttributeModifier toModify = null;

            for (AttributeModifier modifier : modifiers) {
                if (modifier.getID().equals(magnetizationArmorSlotId)) {
                    toModify = modifier;
                    break;
                }
            }

            if (toModify != null) {
                event.removeModifier(HyxcateAttributes.MAGNETIZATION, toModify);
                event.addModifier(HyxcateAttributes.MAGNETIZATION, new AttributeModifier(
                        toModify.getID(),
                        toModify.getName(),
                        toModify.getAmount() + magnetizationBonus,
                        toModify.getOperation())
                );
            } else {
                event.addModifier(HyxcateAttributes.MAGNETIZATION, new AttributeModifier(
                        magnetizationArmorSlotId,
                        "Magnetization modifier",
                        magnetizationBonus,
                        Constants.AttributeModifierOperation.ADD)
                );
            }
        } else if (magnetizationLevel > 0 && !(stack.getItem() instanceof ItemArmor) && event.getSlotType() == EntityEquipmentSlot.MAINHAND) {
            Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.MAGNETIZATION.getName());
            AttributeModifier toModify = null;

            for (AttributeModifier modifier : modifiers) {
                if (modifier.getID().equals(HyxcateAttributes.MAGNETIZATION_TOOL_ID)) {
                    toModify = modifier;
                    break;
                }
            }

            if (toModify != null) {
                event.removeModifier(HyxcateAttributes.MAGNETIZATION, toModify);
                event.addModifier(HyxcateAttributes.MAGNETIZATION, new AttributeModifier(
                        toModify.getID(),
                        toModify.getName(),
                        toModify.getAmount() + magnetizationBonus,
                        toModify.getOperation())
                );
            } else {
                event.addModifier(HyxcateAttributes.MAGNETIZATION, new AttributeModifier(
                        HyxcateAttributes.MAGNETIZATION_TOOL_ID,
                        "Magnetization modifier",
                        magnetizationBonus,
                        Constants.AttributeModifierOperation.ADD)
                );

            }
        }
    }
}

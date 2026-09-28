package mod.emt.hyxcate.event.enchantment;

import com.expandedevents.api.event.ItemAttributeModifierEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.init.HyxcateAttributes;
import mod.emt.hyxcate.init.HyxcateEnchantments;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Collection;
import java.util.UUID;

public class SolarShieldEvent {
    public static int solarShieldLevel;

    @SubscribeEvent
    public void onItemAttribute(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        solarShieldLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.solarShield, stack);
        double solarShieldBonus = HyxcateConfig.GENERAL.solarShieldWardBase + (HyxcateConfig.GENERAL.solarShieldWardSubsequent * solarShieldLevel);

        if (solarShieldLevel > 0 && stack.getItem() instanceof ItemArmor && event.getSlotType() == ((ItemArmor) stack.getItem()).armorType) {
            UUID solarWardArmorSlotID = HyxcateAttributes.SOLAR_WARD_ARMOR_ID.get(event.getSlotType());

            Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.SOLAR_WARD.getName());
            AttributeModifier toModify = null;

            for (AttributeModifier modifier : modifiers) {
                if (modifier.getID().equals(solarWardArmorSlotID)) {
                    toModify = modifier;
                    break;
                }
            }

            if (toModify != null) {
                event.removeModifier(HyxcateAttributes.SOLAR_WARD, toModify);
                event.addModifier(HyxcateAttributes.SOLAR_WARD, new AttributeModifier(
                        toModify.getID(),
                        toModify.getName(),
                        toModify.getAmount() + solarShieldBonus,
                        toModify.getOperation())
                );
            } else {
                event.addModifier(HyxcateAttributes.SOLAR_WARD, new AttributeModifier(
                        solarWardArmorSlotID,
                        "Solar modifier",
                        solarShieldBonus,
                        Constants.AttributeModifierOperation.MULTIPLY)
                );
            }

        }
    }
}

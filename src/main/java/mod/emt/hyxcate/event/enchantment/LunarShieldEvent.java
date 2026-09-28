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

public class LunarShieldEvent {
    public static int lunarShieldLevel;

    @SubscribeEvent
    public void onItemAttribute(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        lunarShieldLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.lunarShield, stack);
        double lunarShieldBonus = HyxcateConfig.GENERAL.lunarShieldWardBase + (HyxcateConfig.GENERAL.lunarShieldWardSubsequent * lunarShieldLevel);

        if (lunarShieldLevel > 0 && stack.getItem() instanceof ItemArmor && event.getSlotType() == ((ItemArmor) stack.getItem()).armorType) {
            UUID lunarWardArmorSlotID = HyxcateAttributes.LUNAR_WARD_ARMOR_ID.get(event.getSlotType());

            Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.LUNAR_WARD.getName());
            AttributeModifier toModify = null;

            for (AttributeModifier modifier : modifiers) {
                if (modifier.getID().equals(lunarWardArmorSlotID)) {
                    toModify = modifier;
                    break;
                }
            }

            if (toModify != null) {
                event.removeModifier(HyxcateAttributes.LUNAR_WARD, toModify);
                event.addModifier(HyxcateAttributes.LUNAR_WARD, new AttributeModifier(
                        toModify.getID(),
                        toModify.getName(),
                        toModify.getAmount() + lunarShieldBonus,
                        toModify.getOperation())
                );
            } else {
                event.addModifier(HyxcateAttributes.LUNAR_WARD, new AttributeModifier(
                        lunarWardArmorSlotID,
                        "Lunar Ward modifier",
                        lunarShieldBonus,
                        Constants.AttributeModifierOperation.ADD_MULTIPLE)
                );
            }
        }
    }
}

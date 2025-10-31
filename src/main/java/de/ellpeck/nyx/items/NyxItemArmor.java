package de.ellpeck.nyx.items;

import de.ellpeck.nyx.events.NyxEvents;
import de.ellpeck.nyx.init.NyxItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

import javax.annotation.Nullable;
import java.util.List;

public class NyxItemArmor extends ItemArmor {
    public NyxItemArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot) {
        super(material, renderIndex, equipmentSlot);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack chestplate = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack leggings = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);

        // Meteorite (Any Piece) - Built-in Magnetization effect
        if (boots.getItem() == NyxItems.meteoriteBoots || chestplate.getItem() == NyxItems.meteoriteChestplate || helmet.getItem() == NyxItems.meteoriteHelmet || leggings.getItem() == NyxItems.meteoriteLeggings) {
            // If an item with Magnetization exists, cancel out the armor's effect in favor of the leveled enchants
            if (NyxEvents.magnetizationLevel == 0) {
                NyxEvents.pullItems(player, 4.0D, 0.0125F);
            }
        }

        // Frezarite (Boots) - Built-in Frost Walker effect, Frost Walker enchantment will improve the effect
        if (boots.getItem() == NyxItems.frezariteBoots) {
            if (!player.world.isRemote) {
                boolean isLastOnGround = player.onGround;

                player.onGround = true;
                EnchantmentFrostWalker.freezeNearby(player, player.world, new BlockPos(player), 2 + EnchantmentHelper.getEnchantmentLevel(Enchantments.FROST_WALKER, boots));
                player.onGround = isLastOnGround;
            }
        }

        // Full set effects
        // Frezarite - Water Breathing
        if ((boots.getItem() == NyxItems.frezariteBoots && chestplate.getItem() == NyxItems.frezariteChestplate && helmet.getItem() == NyxItems.frezariteHelmet && leggings.getItem() == NyxItems.frezariteLeggings)) {

            player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 2, 0, true, false));
        }

        // Kreknorite - Fire Resistance
        if ((boots.getItem() == NyxItems.kreknoriteBoots && chestplate.getItem() == NyxItems.kreknoriteChestplate && helmet.getItem() == NyxItems.kreknoriteHelmet && leggings.getItem() == NyxItems.kreknoriteLeggings)) {
            player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 2, 0, true, false));
        }
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        if (this == NyxItems.frezariteBoots || this == NyxItems.frezariteChestplate || this == NyxItems.frezariteHelmet || this == NyxItems.frezariteLeggings) {
            return EnumRarity.EPIC;
        } else if (this == NyxItems.kreknoriteBoots || this == NyxItems.kreknoriteChestplate || this == NyxItems.kreknoriteHelmet || this == NyxItems.kreknoriteLeggings) {
            return EnumRarity.EPIC;
        } else if (this == NyxItems.meteoriteBoots || this == NyxItems.meteoriteChestplate || this == NyxItems.meteoriteHelmet || this == NyxItems.meteoriteLeggings) {
            return EnumRarity.RARE;
        } else if (this == NyxItems.tektiteBoots || this == NyxItems.tektiteChestplate || this == NyxItems.tektiteHelmet || this == NyxItems.tektiteLeggings) {
            return EnumRarity.EPIC;
        }

        return EnumRarity.COMMON;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (this == NyxItems.frezariteBoots || this == NyxItems.frezariteChestplate || this == NyxItems.frezariteHelmet || this == NyxItems.frezariteLeggings) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.frezarite_armor"));
        } else if (this == NyxItems.kreknoriteBoots || this == NyxItems.kreknoriteChestplate || this == NyxItems.kreknoriteHelmet || this == NyxItems.kreknoriteLeggings) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.kreknorite_armor"));
        } else if (this == NyxItems.meteoriteBoots || this == NyxItems.meteoriteChestplate || this == NyxItems.meteoriteHelmet || this == NyxItems.meteoriteLeggings) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.meteorite_armor"));
        } else if (this == NyxItems.tektiteBoots || this == NyxItems.tektiteChestplate || this == NyxItems.tektiteHelmet || this == NyxItems.tektiteLeggings) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.tektite_armor"));
        }

        if (this == NyxItems.frezariteBoots) {
            tooltip.add(I18n.format(""));
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.frezarite_boots"));
        }
    }
}

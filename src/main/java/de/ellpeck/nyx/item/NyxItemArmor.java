package de.ellpeck.nyx.item;

import de.ellpeck.nyx.init.NyxAttributes;
import de.ellpeck.nyx.init.NyxItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentFrostWalker;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
import net.minecraftforge.fml.common.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.Multimap;
import com.invadermonky.futurefireproof.api.IFireproofItem;

import java.util.List;

// If Future Fireproof is installed, make it fireproof like Netherite!
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofItem", striprefs = true)
public class NyxItemArmor extends ItemArmor implements IFireproofItem {
    public AttributeModifier explosiveResistance;
    public AttributeModifier magnetizationAmount;
    public EnumRarity rarity;

    public NyxItemArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, int magnetizationAmount, double explosiveResistance, EnumRarity rarity) {
        super(material, renderIndex, equipmentSlot);
        this.magnetizationAmount = new AttributeModifier(NyxAttributes.MAGNETIZATION_ID.toString(), magnetizationAmount, 0);
        this.explosiveResistance = new AttributeModifier(NyxAttributes.EXPLOSION_RESISTANCE_ID.toString(), explosiveResistance, 1);
        this.rarity = rarity;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        ItemStack boots = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        ItemStack chestplate = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        ItemStack leggings = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS);

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
        return rarity;
    }

    // TODO: Remove these tooltips in favor of a proper set bonus tooltip
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (this == NyxItems.frezariteBoots || this == NyxItems.frezariteChestplate || this == NyxItems.frezariteHelmet || this == NyxItems.frezariteLeggings) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.frezarite_armor"));
        } else if (this == NyxItems.kreknoriteBoots || this == NyxItems.kreknoriteChestplate || this == NyxItems.kreknoriteHelmet || this == NyxItems.kreknoriteLeggings) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.kreknorite_armor"));
        }
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType) {
            multimap.put(NyxAttributes.MAGNETIZATION.getName(), this.magnetizationAmount);
            multimap.put(NyxAttributes.EXPLOSION_RESISTANCE.getName(), this.explosiveResistance);
        }

        return multimap;
    }
}

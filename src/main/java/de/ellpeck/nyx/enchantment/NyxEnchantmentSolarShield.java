package de.ellpeck.nyx.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

import static net.minecraft.inventory.EntityEquipmentSlot.*;

public class NyxEnchantmentSolarShield extends NyxEnchantment {
    public NyxEnchantmentSolarShield() {
        super("solar_shield", Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[]{HEAD, CHEST, LEGS, FEET});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 10 + (enchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 8;
    }

    @Override
    public int getMaxLevel() {
        return 4;
    }

    @Override
    protected boolean canApplyTogether(@Nonnull Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof NyxEnchantmentLunarShield);
    }
}

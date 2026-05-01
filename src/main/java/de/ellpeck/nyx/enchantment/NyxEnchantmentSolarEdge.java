package de.ellpeck.nyx.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nonnull;

import static net.minecraft.inventory.EntityEquipmentSlot.MAINHAND;
import static net.minecraft.inventory.EntityEquipmentSlot.OFFHAND;

public class NyxEnchantmentSolarEdge extends NyxEnchantment {
    public NyxEnchantmentSolarEdge() {
        super("solar_edge", Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{MAINHAND, OFFHAND});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 5 + (enchantmentLevel - 1) * 8;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    protected boolean canApplyTogether(@Nonnull Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof NyxEnchantmentLunarEdge);
    }
}

package de.ellpeck.nyx.enchantment;

import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import static net.minecraft.inventory.EntityEquipmentSlot.MAINHAND;
import static net.minecraft.inventory.EntityEquipmentSlot.OFFHAND;

public class NyxEnchantmentMagnetization extends NyxEnchantment {
    public NyxEnchantmentMagnetization() {
        super("magnetization", Rarity.UNCOMMON, EnumEnchantmentType.ALL, new EntityEquipmentSlot[]{MAINHAND, OFFHAND});
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 8 + (enchantmentLevel - 1) * 6;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 20;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}

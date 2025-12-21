package de.ellpeck.nyx.enchantment;

import de.ellpeck.nyx.capability.NyxWorld;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

import static net.minecraft.inventory.EntityEquipmentSlot.*;

public class NyxEnchantmentLunarShield extends NyxEnchantment {
    public NyxEnchantmentLunarShield() {
        super("lunar_shield", Rarity.UNCOMMON, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[]{HEAD, CHEST, LEGS, FEET});
    }

    @Override
    public int calcModifierDamage(int level, DamageSource source) {
        if (source.canHarmInCreative()) {
            return 0;
        } else {
            return MathHelper.floor((level + 1) * NyxWorld.moonPhase);
        }
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
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof EnchantmentProtection);
    }

}

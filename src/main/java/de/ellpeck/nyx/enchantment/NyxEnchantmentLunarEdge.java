package de.ellpeck.nyx.enchantment;

import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

import static net.minecraft.inventory.EntityEquipmentSlot.MAINHAND;
import static net.minecraft.inventory.EntityEquipmentSlot.OFFHAND;

public class NyxEnchantmentLunarEdge extends NyxEnchantment {
    public NyxEnchantmentLunarEdge() {
        super("lunar_edge", Rarity.UNCOMMON, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[]{MAINHAND, OFFHAND});
    }

    @Override
    public float calcDamageByCreature(int level, EnumCreatureAttribute creatureType) {
        float damagePerLevel = ((float) (NyxConfig.maxLevelLunarEdgeDamage - NyxConfig.minLevelLunarEdgeDamage)) / (getMaxLevel() - 1);
        float addtlDamage = ((float) NyxConfig.minLevelLunarEdgeDamage) + (float) Math.max(0, level - 1) * damagePerLevel;
        addtlDamage = (level == getMaxLevel()) ? ((float) NyxConfig.maxLevelLunarEdgeDamage) : addtlDamage; // To prevent floating-point damages when multiplying like 3.00001
        return ((float) NyxConfig.baseLunarEdgeDamage) + NyxWorld.moonPhase * addtlDamage;
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
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench) && !(ench instanceof EnchantmentDamage);
    }

    @Override
    public boolean canApply(ItemStack stack) {
        return stack.getItem() instanceof ItemAxe || super.canApply(stack);
    }
}

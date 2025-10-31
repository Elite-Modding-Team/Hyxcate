package de.ellpeck.nyx.enchantments;

import de.ellpeck.nyx.Nyx;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class NyxEnchantments extends Enchantment {
    protected NyxEnchantments(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
        this.setRegistryName(new ResourceLocation(Nyx.ID, name));
        this.setName(Nyx.ID + "." + name);
    }
}

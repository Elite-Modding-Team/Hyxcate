package mod.emt.hyxcate.enchantment;

import mod.emt.hyxcate.Hyxcate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.ResourceLocation;

public class HyxcateEnchantment extends Enchantment {
    protected HyxcateEnchantment(String name, Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
        super(rarityIn, typeIn, slots);
        this.setRegistryName(new ResourceLocation(Hyxcate.ID, name));
        this.setName(Hyxcate.ID + "." + name);
    }
}

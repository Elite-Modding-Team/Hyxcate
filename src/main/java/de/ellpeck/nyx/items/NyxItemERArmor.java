package de.ellpeck.nyx.items;

import de.ellpeck.nyx.init.NyxAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import com.google.common.collect.Multimap;

public class NyxItemERArmor extends NyxItemArmor {
    public AttributeModifier explosiveResistance;

    public NyxItemERArmor(ArmorMaterial material, int renderIndex, EntityEquipmentSlot equipmentSlot, double explosiveResistance) {
        super(material, renderIndex, equipmentSlot);
        this.explosiveResistance = new AttributeModifier(NyxAttributes.EXPLOSION_RESISTANCE_ID.toString(), explosiveResistance, 1);
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == this.armorType) {
            multimap.put(NyxAttributes.EXPLOSION_RESISTANCE.getName(), this.explosiveResistance);
        }

        return multimap;
    }
}

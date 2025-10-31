package de.ellpeck.nyx.compat.tinkers.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import de.ellpeck.nyx.Nyx;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class TraitGleamingArmor extends AbstractArmorTrait {
    public static final float DAMAGE_MULT = 0.5F;

    public TraitGleamingArmor() {
        super(Nyx.ID + "." + "gleaming", 0xA36BB4);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
        newDamage *= 1 - DAMAGE_MULT * Math.pow(player.getHealth() / player.getMaxHealth(), 3);
        return super.onDamaged(armor, player, source, damage, newDamage, event);
    }
}

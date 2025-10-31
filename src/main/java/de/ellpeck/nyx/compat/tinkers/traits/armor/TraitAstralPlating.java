package de.ellpeck.nyx.compat.tinkers.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import de.ellpeck.nyx.Nyx;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

public class TraitAstralPlating extends AbstractArmorTrait {
    public static final float DAMAGE_MULT = 0.5F;

    public TraitAstralPlating() {
        super(Nyx.ID + "." + "astral_plating", 0xDFCE62);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
        if (source.isFireDamage() || source == DamageSource.DROWN) {
            newDamage -= damage * DAMAGE_MULT;
        }

        return newDamage;
    }
}

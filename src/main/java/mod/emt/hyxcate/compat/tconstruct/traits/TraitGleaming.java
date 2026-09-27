package mod.emt.hyxcate.compat.tconstruct.traits;

import mod.emt.hyxcate.Hyxcate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

public class TraitGleaming extends AbstractTrait {
    public static final float DAMAGE_MULT = 0.4F;

    public TraitGleaming() {
        super(Hyxcate.ID + "." + "gleaming", 0xA36BB4);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        newDamage *= 1 + DAMAGE_MULT * Math.pow(player.getHealth() / player.getMaxHealth(), 3);
        return super.damage(tool, player, target, damage, newDamage, isCritical);
    }
}

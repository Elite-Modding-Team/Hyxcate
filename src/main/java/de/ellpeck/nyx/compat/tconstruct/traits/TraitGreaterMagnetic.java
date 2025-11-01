package de.ellpeck.nyx.compat.tconstruct.traits;

import de.ellpeck.nyx.Nyx;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.potion.TinkerPotion;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import javax.annotation.Nonnull;
import javax.vecmath.Vector3d;
import java.util.List;

// Pretty much the Magnetic trait from Tinkers' but better
public class TraitGreaterMagnetic extends AbstractTraitLeveled {
    public static TinkerPotion GreaterMagnetic = new MagneticPotion();

    public TraitGreaterMagnetic(int levels) {
        super(Nyx.ID + "." + "greater_magnetic", 0x554F75, 3, levels);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, name));
        GreaterMagnetic.apply(player, 60, data.level);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, name));
        GreaterMagnetic.apply(player, 60, data.level);
    }

    private static class MagneticPotion extends TinkerPotion {
        public MagneticPotion() {
            super(Util.getResource("greater_magnetic"), false, false);
        }

        @Override
        public boolean isReady(int duration, int strength) {
            return (duration & 1) == 0; // Basically %2
        }

        @Override
        public void performEffect(@Nonnull EntityLivingBase entity, int id) {
            double x = entity.posX;
            double y = entity.posY;
            double z = entity.posZ;
            double range = 6.0D;
            PotionEffect activePotionEffect = entity.getActivePotionEffect(this);
            if (activePotionEffect != null) {
                range += activePotionEffect.getAmplifier() * 0.3F;
            }

            List<EntityItem> items = entity.getEntityWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - range, y - range, z - range, x + range, y + range, z + range));
            int pulled = 0;
            for (EntityItem item : items) {
                if (item.getItem().isEmpty() || item.isDead) {
                    continue;
                }

                if (pulled > 200) {
                    break;
                }

                // Constant force!
                float strength = 0.15F;

                // Calculate direction: item -> player
                Vector3d vec = new Vector3d(x, y, z);
                vec.sub(new Vector3d(item.posX, item.posY, item.posZ));

                if (vec.lengthSquared() <= 0.05) {
                    continue;
                }

                vec.normalize();
                vec.scale(strength);

                // We calculated the movement vector and set it to the correct strength.. now we apply it \o/
                item.motionX += vec.x;
                item.motionY += vec.y;
                item.motionZ += vec.z;

                pulled++;
            }
        }
    }
}

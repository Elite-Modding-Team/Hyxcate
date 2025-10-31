package de.ellpeck.nyx.compat.tinkers.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTraitLeveled;
import de.ellpeck.nyx.Nyx;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import javax.vecmath.Vector3d;
import java.util.List;

public class TraitGreaterMagneticArmor extends AbstractArmorTraitLeveled {
    public TraitGreaterMagneticArmor(int levels) {
        super(Nyx.ID + "." + "greater_magnetic", 0x554F75, 3, levels);
    }

    @Override
    public void onAbilityTick(int level, World world, EntityPlayer player) {
        double x = player.posX;
        double y = player.posY;
        double z = player.posZ;
        double range = 2.5D + (level - 1) * 0.45F;

        List<EntityItem> items = player.getEntityWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x - range, y - range, z - range, x + range, y + range, z + range));
        int pulled = 0;
        for (EntityItem item : items) {
            if (item.getItem().isEmpty() || item.isDead || item.cannotPickup()) {
                continue;
            }

            if (pulled > 200) {
                break;
            }

            float strength = 0.15F;

            Vector3d vec = new Vector3d(x, y, z);
            vec.sub(new Vector3d(item.posX, item.posY, item.posZ));

            if (vec.lengthSquared() <= 0.05) {
                continue;
            }

            vec.normalize();
            vec.scale(strength);

            item.motionX += vec.x;
            item.motionY += vec.y;
            item.motionZ += vec.z;

            pulled++;
        }
    }
}

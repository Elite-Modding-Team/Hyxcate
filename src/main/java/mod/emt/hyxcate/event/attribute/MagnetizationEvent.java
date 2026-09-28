package mod.emt.hyxcate.event.attribute;

import mod.emt.hyxcate.init.HyxcateAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import javax.vecmath.Vector3d;
import java.util.List;

public class MagnetizationEvent {
    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;

        for (ItemStack stack : player.getEquipmentAndArmor()) {
            IAttributeInstance magnetization = player.getEntityAttribute(HyxcateAttributes.MAGNETIZATION);

            // Magnetization Attribute
            if (magnetization != null && !magnetization.getModifiers().isEmpty()) {
                float magnetizationValue = 0.0F;

                for (AttributeModifier attributemodifier : magnetization.getModifiers()) {
                    magnetizationValue += (float) attributemodifier.getAmount();
                }

                if (magnetizationValue <= 0) return;

                // If exceeding 10, set to 10 to prevent insanity
                if (magnetizationValue > 10.0F) {
                    magnetizationValue = 10.0F;
                }

                // Draw nearby items, with strength being based on attribute amount
                pullItems(player, 6.0D, 0.004F + (0.002F * magnetizationValue));
            }
        }
    }

    // Magnetization effect
    public static void pullItems(EntityPlayer player, double distance, float strength) {
        World world = player.getEntityWorld();
        AxisAlignedBB aabb = new AxisAlignedBB(player.posX - distance, player.posY - distance, player.posZ - distance, player.posX + distance, player.posY + distance, player.posZ + distance);
        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, aabb);

        int pulled = 0;
        for (EntityItem item : items) {
            if (item.getItem().isEmpty() || item.isDead || item.getEntityData().getBoolean("PreventRemoteMovement")) {
                continue;
            }

            if (pulled > 200) {
                break;
            }

            Vector3d vec = new Vector3d(player.posX, player.posY + 1, player.posZ);
            vec.sub(new Vector3d(item.posX, item.posY, item.posZ));

            if (vec.lengthSquared() <= 0.05) {
                continue;
            }

            vec.normalize();
            vec.scale(strength);

            item.motionX += vec.x;
            item.motionY += vec.y;
            item.motionZ += vec.z;

            // Prevent ground clamping
            item.onGround = false;

            pulled++;
        }
    }
}

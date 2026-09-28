package mod.emt.hyxcate.event.tool;

import mod.emt.hyxcate.item.tool.HyxcateToolBeamSword;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BeamSwordEvent {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        if (trueSource instanceof EntityPlayer) {
            Item heldItem = ((EntityPlayer) trueSource).getHeldItemMainhand().getItem();

            // Beam swords ignore armor
            if (heldItem instanceof HyxcateToolBeamSword) {
                damageSource.setDamageBypassesArmor();

                // Beam swords also ignore invincibility frames
                entity.hurtResistantTime = 0;
                entity.hurtTime = 0;
            }
        }
    }
}

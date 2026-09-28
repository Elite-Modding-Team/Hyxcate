package mod.emt.hyxcate.event;

import mod.emt.hyxcate.init.HyxcateItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ArmorEvent {
    @SubscribeEvent
    public void onAttackEvent(LivingAttackEvent event) {
        for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
            // Prevents screen shaking and damage sound from immune damage
            if (stack.getItem() == HyxcateItems.meteoriteBoots ||
                    stack.getItem() == HyxcateItems.frezariteBoots ||
                    stack.getItem() == HyxcateItems.kreknoriteBoots ||
                    stack.getItem() == HyxcateItems.tektiteBoots) {
                if (event.getSource() == DamageSource.HOT_FLOOR) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public void onDamageEvent(LivingDamageEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();

        if (damageSource == DamageSource.HOT_FLOOR) {
            for (ItemStack stack : entity.getArmorInventoryList()) {
                // All boots are immune to magma and other hot floor blocks
                if (stack.getItem() == HyxcateItems.meteoriteBoots ||
                        stack.getItem() == HyxcateItems.frezariteBoots ||
                        stack.getItem() == HyxcateItems.kreknoriteBoots ||
                        stack.getItem() == HyxcateItems.tektiteBoots) {
                    event.setAmount(0.0F);
                    event.setCanceled(true);
                }
            }
        }
    }
}

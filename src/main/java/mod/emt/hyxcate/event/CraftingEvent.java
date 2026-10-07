package mod.emt.hyxcate.event;

import mod.emt.hyxcate.init.HyxcateItems;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class CraftingEvent {
    @SubscribeEvent
    public void craftingSound(PlayerEvent.ItemCraftedEvent event) {
        if (!event.player.world.isRemote) {
            return;
        }

        if (!event.crafting.isEmpty() && event.crafting.getItem() == HyxcateItems.celestialEmblem) {
            event.player.playSound(HyxcateSoundEvents.ITEM_CELESTIAL_EMBLEM_CREATE.getSoundEvent(), 0.5F, 1.0F);
            return;
        }

        IInventory inv = event.craftMatrix;
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack stack = inv.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == HyxcateItems.celestialEmblem) {
                event.player.playSound(HyxcateSoundEvents.ITEM_CELESTIAL_EMBLEM_CREATE.getSoundEvent(), 0.5F, 1.0F);
                break;
            }
        }
    }
}

package mod.emt.hyxcate.event;

import mod.emt.hyxcate.init.HyxcateItems;
import mod.emt.hyxcate.init.HyxcateSoundEvents;
import mod.emt.hyxcate.item.tool.HyxcateToolBeamSword;
import mod.emt.hyxcate.item.tool.HyxcateToolCelestialWarhammer;
import mod.emt.hyxcate.item.tool.HyxcateToolTektiteGreatsword;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CraftingEvent {
    private static long lastCraftSoundTime = 0L;

    // TODO: Seems to not work with certain mods or modpacks (Hexxit II for example). There might be a better way?
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void craftingSound(PlayerEvent.ItemCraftedEvent event) {
        if (lastCraftSoundTime != event.player.world.getWorldTime()) {
            final ItemStack result = event.crafting;

            if (!result.isEmpty() && result.getItem() == HyxcateItems.celestialEmblem) {
                event.player.playSound(HyxcateSoundEvents.ITEM_CELESTIAL_EMBLEM_CREATE.getSoundEvent(), 0.5F, 1.0F);
                lastCraftSoundTime = event.player.world.getWorldTime();
                return;
            }

            final IInventory inv = event.craftMatrix;

            for (int slots = inv.getSizeInventory(), i = 0; i < slots; ++i) {
                if (inv.getStackInSlot(i).isEmpty() && inv.getStackInSlot(i).getItem() == HyxcateItems.celestialEmblem) {
                    event.player.playSound(HyxcateSoundEvents.ITEM_CELESTIAL_EMBLEM_CREATE.getSoundEvent(), 0.5F, 1.0F);
                    lastCraftSoundTime = event.player.world.getWorldTime();
                    break;
                }
            }
        }
    }
}

package mod.emt.hyxcate.event;

import mod.emt.hyxcate.item.tool.HyxcateToolBeamSword;
import mod.emt.hyxcate.item.tool.HyxcateToolCelestialWarhammer;
import mod.emt.hyxcate.item.tool.HyxcateToolTektiteGreatsword;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AnvilRepairEvent {
    // Unbreaking still applies to items on anvils regardless of whether the items don't accept it in enchantment tables or not
    // This event should fix that
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if (event.getLeft().isEmpty() || event.getRight().isEmpty()) {
            return;
        }

        if (event.getLeft().getItem() instanceof HyxcateToolBeamSword || event.getLeft().getItem() instanceof HyxcateToolCelestialWarhammer || event.getLeft().getItem() instanceof HyxcateToolTektiteGreatsword) {
            if (EnchantmentHelper.getEnchantments(event.getRight()).keySet().stream().anyMatch(e -> e == Enchantments.UNBREAKING)) {
                event.setCanceled(true);
            }
        }
    }
}

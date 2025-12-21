package de.ellpeck.nyx.init;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.enchantment.NyxEnchantmentLunarEdge;
import de.ellpeck.nyx.enchantment.NyxEnchantmentLunarShield;
import de.ellpeck.nyx.enchantment.NyxEnchantmentMagnetization;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Nyx.ID)
public class NyxEnchantments {

    public static Enchantment lunarEdge;
    public static Enchantment lunarShield;
    public static Enchantment magnetization;

    @SubscribeEvent
    public static void onEnchantmentRegistry(RegistryEvent.Register<Enchantment> event) {
        if (NyxConfig.enchantments) {
            event.getRegistry().registerAll(
                    lunarEdge = new NyxEnchantmentLunarEdge(),
                    lunarShield = new NyxEnchantmentLunarShield(),
                    magnetization = new NyxEnchantmentMagnetization()
            );
        }
    }
}

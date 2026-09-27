package mod.emt.hyxcate.init;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.enchantment.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Hyxcate.ID)
public class HyxcateEnchantments {

    public static Enchantment lunarEdge;
    public static Enchantment lunarShield;
    public static Enchantment magnetization;
    public static Enchantment solarEdge;
    public static Enchantment solarShield;

    @SubscribeEvent
    public static void onEnchantmentRegistry(RegistryEvent.Register<Enchantment> event) {
        if (HyxcateConfig.MASTER_SWITCHES.enchantmentsEnabled) {
            event.getRegistry().registerAll(
                    lunarEdge = new EnchantmentLunarEdge(),
                    lunarShield = new EnchantmentLunarShield(),
                    magnetization = new EnchantmentMagnetization(),
                    solarEdge = new EnchantmentSolarEdge(),
                    solarShield = new EnchantmentSolarShield()
            );
        }
    }
}

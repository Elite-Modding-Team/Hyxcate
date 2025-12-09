package de.ellpeck.nyx.init;

import javax.annotation.Nonnull;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.potions.NyxPotionParalysis;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = Nyx.ID)
public class NyxPotions {
    public static Potion PARALYSIS;
    public static PotionType PARALYSIS_NORMAL, PARALYSIS_LONG;

    private static Potion registerPotion(String name, Potion potion) {
        return potion.setRegistryName(new ResourceLocation(Nyx.ID, name)).
                setPotionName("effect." + Nyx.ID + "." + name + ".name");
    }

    @SuppressWarnings("unused")
    private static PotionType registerPotionType(String name, PotionType potionType) {
        return potionType.setRegistryName(new ResourceLocation(Nyx.ID, name));
    }

    @SubscribeEvent
    public static void registerPotions(@Nonnull final RegistryEvent.Register<Potion> event) {
        IForgeRegistry<Potion> registry = event.getRegistry();

        PARALYSIS = registerPotion("paralysis", new NyxPotionParalysis("paralysis", false, 16772589));

        registry.registerAll(PARALYSIS);
    }
}

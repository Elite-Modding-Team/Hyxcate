package de.ellpeck.nyx.init;

import javax.annotation.Nonnull;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.potions.*;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber(modid = Nyx.ID)
public class NyxPotions {
    public static Potion ASTRAL_EROSION, DEEP_FREEZE, INFERNO, PARALYSIS;
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

        ASTRAL_EROSION = registerPotion("astral_erosion", new NyxPotionCelestialErasure("astral_erosion", true, 16769431));
        DEEP_FREEZE = registerPotion("deep_freeze", new NyxPotionDeepFreeze("deep_freeze", true, 12638975)
                .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, NyxAttributes.MOVEMENT_SPEED_ID.toString(), -0.25D, 2)
                .registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, NyxAttributes.ATTACK_SPEED_ID.toString(), -0.2D, 2));
        INFERNO = registerPotion("inferno", new NyxPotionInferno("inferno", true, 5570560));
        PARALYSIS = registerPotion("paralysis", new NyxPotionParalysis("paralysis", true, 16772589)
                .registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, NyxAttributes.MOVEMENT_SPEED_ID.toString(), -1.0D, 2)
                .registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, NyxAttributes.ATTACK_SPEED_ID.toString(), -1.0D, 2));

        registry.registerAll(ASTRAL_EROSION, DEEP_FREEZE, INFERNO, PARALYSIS);
    }
}

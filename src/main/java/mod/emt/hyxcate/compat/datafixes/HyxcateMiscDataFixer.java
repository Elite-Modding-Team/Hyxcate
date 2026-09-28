package mod.emt.hyxcate.compat.datafixes;

import mod.emt.hyxcate.Hyxcate;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class HyxcateMiscDataFixer {
    @SubscribeEvent
    public void missingPotionMapping(RegistryEvent.MissingMappings<Potion> event) {
        for (RegistryEvent.MissingMappings.Mapping<Potion> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("nyx")) {
                Potion newPotion = ForgeRegistries.POTIONS.getValue(new ResourceLocation(Hyxcate.ID, entry.key.getPath()));
                if (newPotion != null) {
                    entry.remap(newPotion);
                }
            }
        }
    }

    @SubscribeEvent
    public void missingEnchantmentMapping(RegistryEvent.MissingMappings<Enchantment> event) {
        for (RegistryEvent.MissingMappings.Mapping<Enchantment> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("nyx")) {
                Enchantment newEnchantment = ForgeRegistries.ENCHANTMENTS.getValue(new ResourceLocation(Hyxcate.ID, entry.key.getPath()));
                if (newEnchantment != null) {
                    entry.remap(newEnchantment);
                }
            }
        }
    }

    @SubscribeEvent
    public void missingSoundEventMapping(RegistryEvent.MissingMappings<SoundEvent> event) {
        for (RegistryEvent.MissingMappings.Mapping<SoundEvent> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("nyx")) {
                SoundEvent newSoundEvent = ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(Hyxcate.ID, entry.key.getPath()));
                if (newSoundEvent != null) {
                    entry.remap(newSoundEvent);
                }
            }
        }
    }
}

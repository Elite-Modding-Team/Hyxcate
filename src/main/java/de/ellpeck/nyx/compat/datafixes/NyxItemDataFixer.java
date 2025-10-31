package de.ellpeck.nyx.compat.datafixes;

import de.ellpeck.nyx.Nyx;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class NyxItemDataFixer implements IFixableData {
    private static final Map<ResourceLocation, ResourceLocation> ITEM_NAME_MAPPINGS = new HashMap<>();

    static {
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "crystal"), new ResourceLocation(Nyx.ID, "cyber_crystal"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "gleaning_meteor_rock"), new ResourceLocation(Nyx.ID, "cyber_crystal"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_block"), new ResourceLocation(Nyx.ID, "meteorite_block"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_dust"), new ResourceLocation(Nyx.ID, "tektite_gem_cluster"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_finder"), new ResourceLocation(Nyx.ID, "meteor_detector"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_ingot"), new ResourceLocation(Nyx.ID, "meteorite_ingot"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_rock"), new ResourceLocation(Nyx.ID, "meteorite_rock"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_shard"), new ResourceLocation(Nyx.ID, "meteorite_shard"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "unrefined_crystal"), new ResourceLocation(Nyx.ID, "cyber_crystal"));

        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_axe"), new ResourceLocation(Nyx.ID, "meteorite_axe"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_bow"), new ResourceLocation("minecraft", "bow"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_hammer"), new ResourceLocation(Nyx.ID, "kreknorite_block"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_hoe"), new ResourceLocation(Nyx.ID, "meteorite_hoe"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_pickaxe"), new ResourceLocation(Nyx.ID, "meteorite_pickaxe"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_shovel"), new ResourceLocation(Nyx.ID, "meteorite_shovel"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_sword"), new ResourceLocation(Nyx.ID, "meteorite_sword"));

        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_boots"), new ResourceLocation(Nyx.ID, "meteorite_boots"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_chest"), new ResourceLocation(Nyx.ID, "meteorite_chestplate"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_helm"), new ResourceLocation(Nyx.ID, "meteorite_helmet"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_pants"), new ResourceLocation(Nyx.ID, "meteorite_leggings"));
    }

    public NyxItemDataFixer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        return compound;
    }

    @SubscribeEvent
    public void missingItemMapping(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
            ResourceLocation oldName = entry.key;
            ResourceLocation newName = ITEM_NAME_MAPPINGS.get(oldName);
            if (newName != null) {
                Item newItem = ForgeRegistries.ITEMS.getValue(newName);
                if (newItem != null) {
                    entry.remap(newItem);
                }
            }
        }
    }

}

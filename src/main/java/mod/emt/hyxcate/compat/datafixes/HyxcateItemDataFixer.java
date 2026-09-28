package mod.emt.hyxcate.compat.datafixes;

import mod.emt.hyxcate.Hyxcate;
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

public class HyxcateItemDataFixer implements IFixableData {
    private static final Map<ResourceLocation, ResourceLocation> ITEM_NAME_MAPPINGS = new HashMap<>();

    static {
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "crystal"), new ResourceLocation(Hyxcate.ID, "cyber_crystal"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "gleaning_meteor_rock"), new ResourceLocation(Hyxcate.ID, "meteorite_rock"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "lunar_water_bottle"), new ResourceLocation("minecraft", "glass_bottle"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_block"), new ResourceLocation(Hyxcate.ID, "meteorite_block"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_dust"), new ResourceLocation(Hyxcate.ID, "tektite_glass"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_finder"), new ResourceLocation(Hyxcate.ID, "meteor_detector"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_glass"), new ResourceLocation(Hyxcate.ID, "tektite_glass"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_ingot"), new ResourceLocation(Hyxcate.ID, "meteorite_ingot"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_rock"), new ResourceLocation(Hyxcate.ID, "meteorite_rock"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_shard"), new ResourceLocation(Hyxcate.ID, "meteorite_shard"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "unrefined_crystal"), new ResourceLocation(Hyxcate.ID, "cyber_crystal"));

        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_axe"), new ResourceLocation(Hyxcate.ID, "tektite_axe"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_bow"), new ResourceLocation(Hyxcate.ID, "tektite_bow"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_hammer"), new ResourceLocation(Hyxcate.ID, "celestial_warhammer"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_hoe"), new ResourceLocation(Hyxcate.ID, "tektite_hoe"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_pickaxe"), new ResourceLocation(Hyxcate.ID, "tektite_pickaxe"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_shovel"), new ResourceLocation(Hyxcate.ID, "tektite_shovel"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_sword"), new ResourceLocation(Hyxcate.ID, "tektite_greatsword"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "scythe"), new ResourceLocation(Hyxcate.ID, "tektite_greatsword"));

        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_boots"), new ResourceLocation(Hyxcate.ID, "tektite_boots"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_chest"), new ResourceLocation(Hyxcate.ID, "tektite_chestplate"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_helm"), new ResourceLocation(Hyxcate.ID, "tektite_helmet"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_pants"), new ResourceLocation(Hyxcate.ID, "tektite_leggings"));
    }

    public HyxcateItemDataFixer() {
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
            } else if (entry.key.getNamespace().equals("nyx")) {
                Item newItem = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Hyxcate.ID, entry.key.getPath()));
                if (newItem != null) {
                    entry.remap(newItem);
                }
            }
        }
    }
}

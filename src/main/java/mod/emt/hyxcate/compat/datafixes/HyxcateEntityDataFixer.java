package mod.emt.hyxcate.compat.datafixes;

import mod.emt.hyxcate.Hyxcate;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class HyxcateEntityDataFixer implements IFixableData {
    private static final Map<String, String> ATTRIBUTE_NAME_MAPPINGS = new HashMap<>();

    static {
        ATTRIBUTE_NAME_MAPPINGS.put("nyx.generic.explosion_resistance", Hyxcate.ID + ".generic.explosion_resistance");
        ATTRIBUTE_NAME_MAPPINGS.put("nyx.generic.moon_damage", Hyxcate.ID + ".generic.moon_damage");
        ATTRIBUTE_NAME_MAPPINGS.put("nyx.generic.moon_ward", Hyxcate.ID + ".generic.moon_ward");
        ATTRIBUTE_NAME_MAPPINGS.put("nyx.generic.magnetization", Hyxcate.ID + ".generic.magnetization");
        ATTRIBUTE_NAME_MAPPINGS.put("nyx.generic.paralysis", Hyxcate.ID + ".generic.paralysis");
        ATTRIBUTE_NAME_MAPPINGS.put("nyx.generic.sun_damage", Hyxcate.ID + ".generic.sun_damage");
        ATTRIBUTE_NAME_MAPPINGS.put("nyx.generic.sun_ward", Hyxcate.ID + ".generic.sun_ward");
    }

    public HyxcateEntityDataFixer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public NBTTagCompound fixTagCompound(NBTTagCompound compound) {
        if (compound.hasKey("id", 8)) {
            String id = compound.getString("id");
            if (id.startsWith("nyx:")) {
                compound.setString("id", Hyxcate.ID + id.substring(3));
            }
        }
        if (compound.hasKey("Attributes", 9)) {
            NBTTagList attributes = compound.getTagList("Attributes", 10);
            for (int i = 0; i < attributes.tagCount(); i++) {
                NBTTagCompound attr = attributes.getCompoundTagAt(i);
                if (attr.hasKey("Name", 8)) {
                    String oldName = attr.getString("Name");
                    String newName = ATTRIBUTE_NAME_MAPPINGS.get(oldName);
                    if (newName != null) {
                        attr.setString("Name", newName);
                    }
                }
            }
        }
        return compound;
    }

    @SubscribeEvent
    public void missingEntityEntryMapping(RegistryEvent.MissingMappings<EntityEntry> event) {
        for (RegistryEvent.MissingMappings.Mapping<EntityEntry> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("nyx")) {
                EntityEntry newEntity = ForgeRegistries.ENTITIES.getValue(new ResourceLocation(Hyxcate.ID, entry.key.getPath()));
                if (newEntity != null) {
                    entry.remap(newEntity);
                }
            }
        }
    }
}

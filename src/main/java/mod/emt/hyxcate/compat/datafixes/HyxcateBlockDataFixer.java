package mod.emt.hyxcate.compat.datafixes;

import mod.emt.hyxcate.Hyxcate;
import net.minecraft.block.Block;
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

public class HyxcateBlockDataFixer implements IFixableData {
    private static final Map<ResourceLocation, ResourceLocation> BLOCK_NAME_MAPPINGS = new HashMap<>();

    static {
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation("nyx", "crystal"), new ResourceLocation(Hyxcate.ID, "cyber_crystal"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation("nyx", "gleaning_meteor_rock"), new ResourceLocation(Hyxcate.ID, "meteorite_rock"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation("nyx", "lunar_water"), new ResourceLocation("minecraft", "water"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation("nyx", "lunar_water_cauldron"), new ResourceLocation("minecraft", "cauldron"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_block"), new ResourceLocation(Hyxcate.ID, "meteorite_block"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_glass"), new ResourceLocation(Hyxcate.ID, "tektite_glass"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation("nyx", "meteor_rock"), new ResourceLocation(Hyxcate.ID, "meteorite_rock"));
    }

    public HyxcateBlockDataFixer() {
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
    public void missingBlockMapping(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> entry : event.getAllMappings()) {
            ResourceLocation oldName = entry.key;
            ResourceLocation newName = BLOCK_NAME_MAPPINGS.get(oldName);
            if (newName != null) {
                Block newBlock = ForgeRegistries.BLOCKS.getValue(newName);
                if (newBlock != null) {
                    entry.remap(newBlock);
                }
            }
        }
    }

    @SubscribeEvent
    public void missingItemBlockMapping(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
            ResourceLocation oldName = entry.key;
            ResourceLocation newName = BLOCK_NAME_MAPPINGS.get(oldName);
            if (newName != null) {
                Item newItem = ForgeRegistries.ITEMS.getValue(newName);
                if (newItem != null) {
                    entry.remap(newItem);
                }
            }
        }
    }
}

package de.ellpeck.nyx.compat.datafixes;

import de.ellpeck.nyx.Nyx;
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

public class NyxBlockDataFixer implements IFixableData {
    private static final Map<ResourceLocation, ResourceLocation> BLOCK_NAME_MAPPINGS = new HashMap<>();

    static {
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "crystal"), new ResourceLocation(Nyx.ID, "cyber_crystal"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "gleaning_meteor_rock"), new ResourceLocation(Nyx.ID, "meteorite_rock"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "lunar_water"), new ResourceLocation("minecraft", "water"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "lunar_water_cauldron"), new ResourceLocation("minecraft", "cauldron"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_block"), new ResourceLocation(Nyx.ID, "meteorite_block"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_glass"), new ResourceLocation(Nyx.ID, "tektite_glass"));
        BLOCK_NAME_MAPPINGS.put(new ResourceLocation(Nyx.ID, "meteor_rock"), new ResourceLocation(Nyx.ID, "meteorite_rock"));
    }

    public NyxBlockDataFixer() {
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

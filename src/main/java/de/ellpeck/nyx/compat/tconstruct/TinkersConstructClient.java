package de.ellpeck.nyx.compat.tconstruct;

import de.ellpeck.nyx.event.NyxClientEvents;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TinkersConstructClient {
    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event) {
        NyxClientEvents.registerFluidRenderer(TinkersConstruct.FREZARITE_FLUID);
        NyxClientEvents.registerFluidRenderer(TinkersConstruct.KREKNORITE_FLUID);
        NyxClientEvents.registerFluidRenderer(TinkersConstruct.METEORITE_FLUID);
    }
}

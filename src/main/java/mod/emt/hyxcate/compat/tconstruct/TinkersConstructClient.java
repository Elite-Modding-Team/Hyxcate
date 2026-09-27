package mod.emt.hyxcate.compat.tconstruct;

import mod.emt.hyxcate.event.HyxcateClientEvents;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TinkersConstructClient {
    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event) {
        HyxcateClientEvents.registerFluidRenderer(TinkersConstruct.FREZARITE_FLUID);
        HyxcateClientEvents.registerFluidRenderer(TinkersConstruct.KREKNORITE_FLUID);
        HyxcateClientEvents.registerFluidRenderer(TinkersConstruct.METEORITE_FLUID);
    }
}

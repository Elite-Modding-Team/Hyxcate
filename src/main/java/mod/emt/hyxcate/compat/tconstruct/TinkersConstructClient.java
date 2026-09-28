package mod.emt.hyxcate.compat.tconstruct;

import mod.emt.hyxcate.init.HyxcateRegistry;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TinkersConstructClient {
    @SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event) {
        HyxcateRegistry.registerFluidRenderer(TinkersConstruct.FREZARITE_FLUID);
        HyxcateRegistry.registerFluidRenderer(TinkersConstruct.KREKNORITE_FLUID);
        HyxcateRegistry.registerFluidRenderer(TinkersConstruct.METEORITE_FLUID);
    }
}

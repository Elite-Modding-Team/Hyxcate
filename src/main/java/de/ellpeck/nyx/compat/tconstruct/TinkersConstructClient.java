package de.ellpeck.nyx.compat.tconstruct;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.events.NyxClientEvents;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = Nyx.ID, value = Side.CLIENT)
public class TinkersConstructClient {
    @SubscribeEvent
    public static void onModelRegistry(ModelRegistryEvent event) {
        NyxClientEvents.registerFluidRenderer(TinkersConstruct.FREZARITE_FLUID);
        NyxClientEvents.registerFluidRenderer(TinkersConstruct.KREKNORITE_FLUID);
        NyxClientEvents.registerFluidRenderer(TinkersConstruct.METEORITE_FLUID);
    }
}

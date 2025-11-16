package de.ellpeck.nyx.init;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entities.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = Nyx.ID)
public class NyxEntities {

    public static final ResourceLocation ALIEN_CREEPER = new ResourceLocation(Nyx.ID, "entities/alien_creeper");
    public static final ResourceLocation COMET_KITTY = new ResourceLocation(Nyx.ID, "entities/comet_kitty");

    private static int id = 1;

    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityEntry> event) {
        EntityRegistry.registerModEntity(new ResourceLocation(Nyx.ID, "falling_star"), NyxEntityFallingStar.class, Nyx.ID + ".falling_star", id++, Nyx.instance, 512, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Nyx.ID, "falling_meteor"), NyxEntityFallingMeteor.class, Nyx.ID + ".falling_meteor", id++, Nyx.instance, 512, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Nyx.ID, "alien_creeper"), NyxEntityAlienCreeper.class, Nyx.ID + ".alien_creeper", id++, Nyx.instance, 64, 1, true, 2498630, 14278883);
        EntityRegistry.registerModEntity(new ResourceLocation(Nyx.ID, "comet_kitty"), NyxEntityCometKitty.class, Nyx.ID + ".comet_kitty", id++, Nyx.instance, 64, 1, true, 2302251, 7560652);
        EntityRegistry.registerModEntity(new ResourceLocation(Nyx.ID, "alien_kitty"), NyxEntityAlienKitty.class, Nyx.ID + ".alien_kitty", id++, Nyx.instance, 64, 1, true, 65280, 0);
        //EntityRegistry.registerModEntity(new ResourceLocation(Nyx.ID, "stellar_protector"), NyxEntityStellarProtector.class, Nyx.ID + ".stellar_protector", id++, Nyx.instance, 64, 1, true, 2239283, 884535);
    }
}

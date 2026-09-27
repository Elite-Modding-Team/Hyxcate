package mod.emt.hyxcate.init;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = Hyxcate.ID)
public class HyxcateEntities {
    private static int id = 1;

    @SubscribeEvent
    public static void onEntityRegistry(RegistryEvent.Register<EntityEntry> event) {
        EntityRegistry.registerModEntity(new ResourceLocation(Hyxcate.ID, "falling_star"), EntityFallingStar.class, Hyxcate.ID + ".falling_star", id++, Hyxcate.instance, 512, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Hyxcate.ID, "falling_meteor"), EntityFallingMeteor.class, Hyxcate.ID + ".falling_meteor", id++, Hyxcate.instance, 512, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Hyxcate.ID, "alien_creeper"), EntityAlienCreeper.class, Hyxcate.ID + ".alien_creeper", id++, Hyxcate.instance, 64, 1, true, 2498630, 14278883);
        EntityRegistry.registerModEntity(new ResourceLocation(Hyxcate.ID, "comet_kitty"), EntityCometKitty.class, Hyxcate.ID + ".comet_kitty", id++, Hyxcate.instance, 64, 1, true, 2302251, 7560652);
        EntityRegistry.registerModEntity(new ResourceLocation(Hyxcate.ID, "alien_kitty"), EntityAlienKitty.class, Hyxcate.ID + ".alien_kitty", id++, Hyxcate.instance, 64, 1, true, 65280, 0);
        EntityRegistry.registerModEntity(new ResourceLocation(Hyxcate.ID, "eyezor"), EntityEyezor.class, Hyxcate.ID + ".eyezor", id++, Hyxcate.instance, 64, 1, true, 6242111, 7438135);
        EntityRegistry.registerModEntity(new ResourceLocation(Hyxcate.ID, "laser"), EntityLaser.class, Hyxcate.ID + ".laser", id++, Hyxcate.instance, 64, 1, true);
        //EntityRegistry.registerModEntity(new ResourceLocation(Hyxcate.ID, "stellar_protector"), EntityStellarProtector.class, Hyxcate.ID + ".stellar_protector", id++, Hyxcate.instance, 64, 1, true, 2239283, 884535);
    }
}

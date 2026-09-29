package mod.emt.hyxcate.event;

import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.network.HyxcatePacketHandler;
import mod.emt.hyxcate.network.HyxcatePacketWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WorldEvent {
    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        if (world.isRemote) return;
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(world);
        if (Hyxcate == null) return;
        if (entity instanceof EntityPlayerMP) {
            HyxcatePacketWorld packet = new HyxcatePacketWorld(Hyxcate);
            HyxcatePacketHandler.sendTo((EntityPlayerMP) entity, packet);
        }
    }

    @SubscribeEvent
    public void onWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        event.addCapability(new ResourceLocation(Hyxcate.ID, "world_cap"), new CapabilityCelestialEvent(event.getObject()));
    }
}

package mod.emt.hyxcate.event.entity;

import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.entity.ai.AIWolfSpecialMoon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WolfEvent {
    @SubscribeEvent
    public void onPlayerJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        if (world.isRemote) return;
        CapabilityCelestialEvent Hyxcate = CapabilityCelestialEvent.get(world);
        if (Hyxcate == null) return;
        if (entity instanceof EntityWolf) {
            EntityWolf wolf = (EntityWolf) entity;
            wolf.targetTasks.addTask(3, new AIWolfSpecialMoon(wolf));
        }
    }
}

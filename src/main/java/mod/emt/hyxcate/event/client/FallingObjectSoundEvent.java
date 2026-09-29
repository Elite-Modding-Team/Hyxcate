package mod.emt.hyxcate.event.client;

import mod.emt.hyxcate.entity.EntityFallingMeteor;
import mod.emt.hyxcate.entity.EntityFallingStar;
import mod.emt.hyxcate.util.helpers.SoundHelper;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FallingObjectSoundEvent {
    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onEntityJoinClient(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote) return;
        if (event.getEntity() instanceof EntityFallingMeteor) {
            SoundHelper.playClientSoundFallingMeteor(event.getEntity());
        } else if (event.getEntity() instanceof EntityFallingStar) {
            SoundHelper.playClientSoundFallingStar(event.getEntity());
        }
    }
}

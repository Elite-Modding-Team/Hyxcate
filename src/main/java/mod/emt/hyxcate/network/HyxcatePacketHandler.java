package mod.emt.hyxcate.network;

import mod.emt.hyxcate.Hyxcate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public final class HyxcatePacketHandler {
    private static SimpleNetworkWrapper network;

    public static void init() {
        network = new SimpleNetworkWrapper(Hyxcate.ID);
        network.registerMessage(HyxcatePacketWorld.Handler.class, HyxcatePacketWorld.class, 0, Side.CLIENT);
    }

    public static void sendTo(EntityPlayer player, IMessage message) {
        network.sendTo(message, (EntityPlayerMP) player);
    }
}

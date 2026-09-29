package mod.emt.hyxcate.network;

import io.netty.buffer.ByteBuf;
import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.IOException;

public class HyxcatePacketWorld implements IMessage {
    private NBTTagCompound info;

    public HyxcatePacketWorld(CapabilityCelestialEvent world) {
        this.info = world.serializeNBT(true);
    }

    public HyxcatePacketWorld() {

    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        try {
            this.info = buffer.readCompoundTag();
        } catch (IOException ignored) {
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer buffer = new PacketBuffer(buf);
        buffer.writeCompoundTag(this.info);
    }

    public static class Handler implements IMessageHandler<HyxcatePacketWorld, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(HyxcatePacketWorld message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if (world != null) {
                    CapabilityCelestialEvent cap = CapabilityCelestialEvent.get(world);
                    if (cap != null) {
                        cap.deserializeNBT(message.info, true);
                    }
                }
            });
            return null;
        }
    }
}

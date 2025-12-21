package de.ellpeck.nyx.network;

import de.ellpeck.nyx.capability.NyxWorld;
import io.netty.buffer.ByteBuf;
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

public class NyxPacketWorld implements IMessage {

    private NBTTagCompound info;

    public NyxPacketWorld(NyxWorld world) {
        this.info = world.serializeNBT(true);
    }

    public NyxPacketWorld() {

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

    public static class Handler implements IMessageHandler<NyxPacketWorld, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(NyxPacketWorld message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                if (world != null) {
                    NyxWorld nyx = NyxWorld.get(world);
                    if (nyx != null)
                        nyx.deserializeNBT(message.info, true);
                }
            });

            return null;
        }
    }
}
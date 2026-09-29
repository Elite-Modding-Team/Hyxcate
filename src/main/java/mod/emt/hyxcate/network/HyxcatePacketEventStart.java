package mod.emt.hyxcate.network;

import io.netty.buffer.ByteBuf;
import mod.emt.hyxcate.api.celestialevent.HyxcateLunarEvent;
import mod.emt.hyxcate.api.celestialevent.HyxcateSolarEvent;
import mod.emt.hyxcate.capability.CapabilityCelestialEvent;
import mod.emt.hyxcate.config.HyxcateConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HyxcatePacketEventStart implements IMessage {
    private boolean lunar;

    public HyxcatePacketEventStart() {
    }

    public HyxcatePacketEventStart(boolean lunar) {
        this.lunar = lunar;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.lunar = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(this.lunar);
    }

    public static class Handler implements IMessageHandler<HyxcatePacketEventStart, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(HyxcatePacketEventStart message, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                EntityPlayer player = Minecraft.getMinecraft().player;
                if (player == null || player.world == null) return;
                CapabilityCelestialEvent cap = CapabilityCelestialEvent.get(player.world);
                if (cap == null) return;
                ITextComponent text = null;
                SoundEvent sound = null;
                if (message.lunar) {
                    HyxcateLunarEvent event = cap.currentLunarEvent;
                    if (event != null) {
                        text = event.getStartMessage();
                        sound = event.getStartSound();
                    }
                } else {
                    HyxcateSolarEvent event = cap.currentSolarEvent;
                    if (event != null) {
                        text = event.getStartMessage();
                        sound = event.getStartSound();
                    }
                }
                if (text != null && HyxcateConfig.GENERAL.eventNotifications) {
                    player.sendMessage(text);
                }
                if (sound != null && HyxcateConfig.GENERAL.eventIntroSounds) {
                    Minecraft.getMinecraft().getSoundHandler().playSound(new PositionedSoundRecord(sound.getSoundName(), SoundCategory.AMBIENT, 10.0F, 1.0F, false, 0, ISound.AttenuationType.NONE, (float) player.posX, (float) player.posY + 32, (float) player.posZ));
                }
            });
            return null;
        }
    }
}

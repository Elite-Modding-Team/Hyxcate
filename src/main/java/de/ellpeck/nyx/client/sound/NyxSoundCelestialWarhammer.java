package de.ellpeck.nyx.client.sound;

import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NyxSoundCelestialWarhammer extends MovingSound {
    protected EntityPlayer player;
    protected BlockPos position;

    public NyxSoundCelestialWarhammer(float volume, float pitch) {
        super(NyxSoundEvents.ITEM_CELESTIAL_WARHAMMER_LAUNCH.getSoundEvent(), SoundCategory.PLAYERS);
        this.attenuationType = AttenuationType.NONE;
        this.player = Minecraft.getMinecraft().player;
        this.position = player.getPosition();
        this.xPosF = position.getX();
        this.yPosF = position.getY();
        this.zPosF = position.getZ();
        this.volume = volume;
        this.pitch = pitch;
    }

    @Override
    public void update() {
        this.xPosF = (float) player.posX;
        this.yPosF = (float) player.posY;
        this.zPosF = (float) player.posZ;
    }
}

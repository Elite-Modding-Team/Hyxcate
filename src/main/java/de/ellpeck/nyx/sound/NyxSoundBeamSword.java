package de.ellpeck.nyx.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NyxSoundBeamSword extends MovingSound implements ITickableSound {
    protected EntityPlayer player;
    protected BlockPos position;
    protected ItemStack swordStack;

    public NyxSoundBeamSword(ItemStack swordStack) {
        super(NyxSoundEvents.beamSwordIdle.getSoundEvent(), SoundCategory.PLAYERS);
        this.attenuationType = ISound.AttenuationType.NONE;
        this.repeat = true;
        this.repeatDelay = 0;
        this.player = Minecraft.getMinecraft().player;
        this.position = player.getPosition();
        this.xPosF = position.getX();
        this.yPosF = position.getY();
        this.zPosF = position.getZ();
        this.swordStack = swordStack;
    }

    @Override
    public void update() {
        this.xPosF = (float) player.posX;
        this.yPosF = (float) player.posY;
        this.zPosF = (float) player.posZ;
        if (!this.player.getHeldItemMainhand().equals(this.swordStack)) {
            this.volume -= 0.2F;
        }
    }

    @Override
    public boolean isDonePlaying() {
        return this.volume <= 0.0F;
    }
}

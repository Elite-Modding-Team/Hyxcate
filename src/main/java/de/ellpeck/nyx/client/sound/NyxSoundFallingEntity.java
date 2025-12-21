package de.ellpeck.nyx.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NyxSoundFallingEntity extends MovingSound {
    private final Entity entity;

    public NyxSoundFallingEntity(Entity entity, SoundEvent soundEvent, float volume) {
        super(soundEvent, SoundCategory.AMBIENT);
        this.entity = entity;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = volume;
    }

    @Override
    public void update() {
        if (this.entity.isDead || this.entity.collided) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) this.entity.posX;
            this.yPosF = (float) this.entity.posY;
            this.zPosF = (float) this.entity.posZ;
        }
    }
}

package de.ellpeck.nyx.client.sound;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class NyxSoundFallenEntity extends MovingSound {
    private final EntityItem entityItem;

    public NyxSoundFallenEntity(EntityItem entityItem, SoundEvent soundEvent, float volume) {
        super(soundEvent, SoundCategory.AMBIENT);
        this.entityItem = entityItem;
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = volume;
    }

    @Override
    public void update() {
        if (this.entityItem.isDead) {
            this.volume = this.volume - 0.05F;
        } else {
            this.xPosF = (float) this.entityItem.posX;
            this.yPosF = (float) this.entityItem.posY;
            this.zPosF = (float) this.entityItem.posZ;
        }
    }
}

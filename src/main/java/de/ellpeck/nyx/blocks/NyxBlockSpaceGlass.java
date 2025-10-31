package de.ellpeck.nyx.blocks;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

import java.util.Random;

public class NyxBlockSpaceGlass extends BlockGlass {
    public NyxBlockSpaceGlass() {
        super(Material.GLASS, false);
        this.setSoundType(SoundType.GLASS);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }
}

package de.ellpeck.nyx.blocks;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;

import java.util.Random;

import de.ellpeck.nyx.init.NyxRegistry;

public class NyxBlockSpaceGlass extends BlockGlass {
    public NyxBlockSpaceGlass() {
        super(Material.GLASS, false);
        this.setHarvestLevel("pickaxe", 0);
        this.setSoundType(NyxRegistry.LIGHT_CRYSTAL);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1;
    }
}

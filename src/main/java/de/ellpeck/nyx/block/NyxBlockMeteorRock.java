package de.ellpeck.nyx.block;

import de.ellpeck.nyx.capability.NyxWorld;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;
import java.util.function.Supplier;

public class NyxBlockMeteorRock extends Block {

    protected final Supplier<Item> droppedItem;
    private final EnumParticleTypes particleType;

    public NyxBlockMeteorRock(Supplier<Item> droppedItem, EnumParticleTypes particleType, SoundType soundType, int harvestLevel) {
        super(Material.ROCK);
        this.droppedItem = droppedItem;
        this.particleType = particleType;

        this.setHarvestLevel("pickaxe", harvestLevel);
        this.setSoundType(soundType);
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (!world.isRemote) {
            NyxWorld data = NyxWorld.get(world);
            if (data != null) {
                data.meteorLandingSites.remove(pos);
                data.sendToClients();
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // Cooled rocks only have a 1/4 chance to drop their respective material.
        // Not affected by Fortune.
        return rand.nextInt(3) == 0 ? this.droppedItem.get() : null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        for (int i = 0; i < 3; i++) {
            boolean side = rand.nextBoolean();
            float x = side ? rand.nextFloat() : rand.nextBoolean() ? 1 : 0;
            float z = !side ? rand.nextFloat() : rand.nextBoolean() ? 1 : 0;
            float y = rand.nextBoolean() ? 1 : 0;
            world.spawnParticle(this.particleType, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0, 0, 0);
        }
    }
}

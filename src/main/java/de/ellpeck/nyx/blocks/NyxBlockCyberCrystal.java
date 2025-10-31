package de.ellpeck.nyx.blocks;

import de.ellpeck.nyx.init.NyxBlocks;
import de.ellpeck.nyx.init.NyxRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class NyxBlockCyberCrystal extends Block {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(4 / 16F, 0, 4 / 16F, 12 / 16F, 12 / 16F, 12 / 16F);

    public NyxBlockCyberCrystal() {
        super(Material.ROCK);
        this.setHardness(3);
        this.setLightLevel(0.9375F);
        this.setSoundType(NyxRegistry.CRYSTAL);
        this.setTickRandomly(true);
        this.setHarvestLevel("pickaxe", 4);
        NyxBlocks.initBlock(this, "cyber_crystal", ItemBlock::new);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDS;
    }

    @Override
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        for (int i = 0; i < 3; i++) {
            boolean side = rand.nextBoolean();
            float x = side ? rand.nextFloat() : rand.nextBoolean() ? 1 : 0;
            float z = !side ? rand.nextFloat() : rand.nextBoolean() ? 1 : 0;
            float y = rand.nextBoolean() ? 1 : 0;
            world.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + x, pos.getY() + y, pos.getZ() + z, 0, 0, 0);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.cyber_crystal"));
    }
}

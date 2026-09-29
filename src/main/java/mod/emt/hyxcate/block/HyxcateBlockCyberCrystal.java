package mod.emt.hyxcate.block;

import com.invadermonky.futurefireproof.api.IFireproofBlock;
import mod.emt.hyxcate.init.HyxcateBlocks;
import mod.emt.hyxcate.init.HyxcateRegistry;
import mod.emt.hyxcate.util.ColorUtil;
import mod.emt.hyxcate.util.ParticleUtil;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

// If Future Fireproof is installed, make it fireproof like Netherite!
@Optional.Interface(modid = "futurefireproof", iface = "com.invadermonky.futurefireproof.api.IFireproofBlock", striprefs = true)
public class HyxcateBlockCyberCrystal extends Block implements IFireproofBlock {
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(4 / 16F, 0, 4 / 16F, 12 / 16F, 12 / 16F, 12 / 16F);

    public HyxcateBlockCyberCrystal() {
        super(Material.ROCK);
        this.setHardness(3);
        this.setLightLevel(0.625F);
        this.setSoundType(HyxcateRegistry.DENSE_CRYSTAL);
        this.setTickRandomly(true);
        this.setHarvestLevel("pickaxe", 4);
        this.useNeighborBrightness = true;
        HyxcateBlocks.initBlock(this, "cyber_crystal", ItemBlock::new);
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
        for (int i = 0; i < 1; i++) {
            double centerX = pos.getX() + 0.5D;
            double centerY = pos.getY() + 0.5D;
            double centerZ = pos.getZ() + 0.5D;
            double angle = rand.nextDouble() * Math.PI * 2.0D;
            double radius = 0.35D + rand.nextDouble() * 0.25D;
            ParticleUtil.spawnCrystalEnergy(world, (float) centerX, (float) centerY, (float) centerZ, angle, radius, 0.08D, 0.8F, 0.5F, 300, ColorUtil.CYBER_CRYSTAL);
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.hyxcate.cyber_crystal"));
    }
}

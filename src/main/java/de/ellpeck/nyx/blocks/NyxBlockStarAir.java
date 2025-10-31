package de.ellpeck.nyx.blocks;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.init.NyxBlocks;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.List;

public class NyxBlockStarAir extends BlockContainer {
    public NyxBlockStarAir() {
        super(Material.AIR);
        NyxBlocks.initBlock(this, "star_air", null);
        this.setLightLevel(1);
        GameRegistry.registerTileEntity(Tile.class, this.getRegistryName());
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

    @Override
    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean isAir(IBlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new Tile();
    }

    public static class Tile extends TileEntity implements ITickable {
        @Override
        public void update() {
            if (this.world.isRemote)
                return;
            AxisAlignedBB bounds = new AxisAlignedBB(this.pos).grow(1);
            List<EntityItem> items = this.world.getEntitiesWithinAABB(EntityItem.class, bounds, e ->
                    e.isEntityAlive() && e.getEntityData().getBoolean(Nyx.ID + ":fallen_star"));
            if (items.isEmpty())
                this.world.setBlockState(this.pos, Blocks.AIR.getDefaultState());
        }
    }
}

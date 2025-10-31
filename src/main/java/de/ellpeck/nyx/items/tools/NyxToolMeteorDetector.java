package de.ellpeck.nyx.items.tools;

import com.google.common.collect.Streams;
import de.ellpeck.nyx.capabilities.NyxWorld;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class NyxToolMeteorDetector extends Item {

    public NyxToolMeteorDetector() {
        // Copied from compass and edited
        this.addPropertyOverride(new ResourceLocation("angle"), new IItemPropertyGetter() {
            @SideOnly(Side.CLIENT)
            double rotation;
            @SideOnly(Side.CLIENT)
            double rota;
            @SideOnly(Side.CLIENT)
            long lastUpdateTick;
            // edit: add this variable for tracking time
            @SideOnly(Side.CLIENT)
            BlockPos meteorPos;

            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
                if (entityIn == null && !stack.isOnItemFrame()) {
                    return 0.0F;
                } else {
                    boolean flag = entityIn != null;
                    Entity entity = flag ? entityIn : stack.getItemFrame();

                    if (worldIn == null) {
                        worldIn = entity.world;
                    }

                    // edit: remove if check for surface world, since this should work in any dimension
                    double d0;
                    double d1 = flag ? (double) entity.rotationYaw : this.getFrameRotation((EntityItemFrame) entity);
                    d1 = MathHelper.positiveModulo(d1 / 360.0D, 1.0D);
                    double d2 = this.getSpawnToAngle(worldIn, entity) / (Math.PI * 2D);
                    // edit: spin randomly if no spawn angle was found
                    if (Double.isNaN(d2)) {
                        d0 = Math.random();
                    } else {
                        d0 = 0.5D - (d1 - 0.25D - d2);

                    }

                    if (flag) {
                        d0 = this.wobble(worldIn, d0);
                    }

                    return MathHelper.positiveModulo((float) d0, 1.0F);
                }
            }

            @SideOnly(Side.CLIENT)
            private double wobble(World worldIn, double d) {
                if (worldIn.getTotalWorldTime() != this.lastUpdateTick) {
                    this.lastUpdateTick = worldIn.getTotalWorldTime();
                    double d0 = d - this.rotation;
                    d0 = MathHelper.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
                    this.rota += d0 * 0.1D;
                    this.rota *= 0.8D;
                    this.rotation = MathHelper.positiveModulo(this.rotation + this.rota, 1.0D);
                }

                return this.rotation;
            }

            @SideOnly(Side.CLIENT)
            private double getFrameRotation(EntityItemFrame frame) {
                return MathHelper.wrapDegrees(180 + frame.facingDirection.getHorizontalIndex() * 90);
            }

            @SideOnly(Side.CLIENT)
            private double getSpawnToAngle(World world, Entity e) {
                // edit: find the nearest meteor instead of spawn
                if (this.meteorPos == null || world.getTotalWorldTime() % 100 == 0) {
                    NyxWorld data = NyxWorld.get(world);
                    if (data == null || data.meteorLandingSites.isEmpty())
                        return Double.NaN;
                    this.meteorPos = Streams.concat(data.meteorLandingSites.stream(), data.cachedMeteorPositions.stream())
                            .min((p1, p2) -> MathHelper.floor(e.getDistanceSq(p1) - e.getDistanceSq(p2))).get();
                }
                if (this.meteorPos == null)
                    return Double.NaN;
                return Math.atan2((double) this.meteorPos.getZ() - e.posZ, (double) this.meteorPos.getX() - e.posX);
            }
        });
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.meteor_detector"));
    }
}

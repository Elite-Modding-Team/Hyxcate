package de.ellpeck.nyx.entity;

import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.init.NyxItems;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class NyxEntityFallingStar extends Entity {

    protected float trajectoryX;
    protected float trajectoryY;
    protected float trajectoryZ;

    public NyxEntityFallingStar(World worldIn) {
        super(worldIn);
        this.setEntityBoundingBox(null);
        this.initTrajectory(1);
    }

    @Override
    public void onEntityUpdate() {
        this.customUpdate();
        if (!this.world.isRemote) this.move(MoverType.SELF, this.trajectoryX, this.trajectoryY, this.trajectoryZ);
        super.onEntityUpdate();
    }

    protected void customUpdate() {
        if (!this.isLoaded()) {
            this.setDead();
            return;
        }

        if (!this.world.isRemote) {
            if (this.collided) {
                this.world.playSound(null, this.posX, this.posY, this.posZ, NyxSoundEvents.ENTITY_STAR_IMPACT.getSoundEvent(), SoundCategory.AMBIENT, (float) NyxConfig.fallingStarImpactVolume, 1);

                EntityItem item = new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(NyxItems.fallenStar));
                this.world.spawnEntity(item);
                this.setDead();
            }
        } else {
            for (int i = 0; i < 2; i++) {
                double mX = -this.motionX + this.world.rand.nextGaussian() * 0.05;
                double mY = -this.motionY + this.world.rand.nextGaussian() * 0.05;
                double mZ = -this.motionZ + this.world.rand.nextGaussian() * 0.05;
                this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, true, this.posX, this.posY, this.posZ, mX, mY, mZ);
            }
        }
    }

    public boolean isLoaded() {
        // entities are only updated if they're at least 32 blocks away from unloaded chunks (for some reason)
        return this.world.isAreaLoaded(this.getPosition(), 60, false);
    }

    protected void initTrajectory(float multiplier) {
        this.trajectoryX = MathHelper.nextFloat(this.world.rand, 0.5F, 0.85F) * multiplier;
        if (this.world.rand.nextBoolean()) this.trajectoryX *= -1;
        this.trajectoryY = MathHelper.nextFloat(this.world.rand, -0.5F, -0.85F) * multiplier;
        this.trajectoryZ = MathHelper.nextFloat(this.world.rand, 0.5F, 0.85F) * multiplier;
        if (this.world.rand.nextBoolean()) this.trajectoryZ *= -1;
    }

    @Override
    protected void entityInit() {

    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        this.trajectoryX = compound.getFloat("trajectory_x");
        this.trajectoryY = compound.getFloat("trajectory_y");
        this.trajectoryZ = compound.getFloat("trajectory_z");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        compound.setFloat("trajectory_x", this.trajectoryX);
        compound.setFloat("trajectory_y", this.trajectoryY);
        compound.setFloat("trajectory_z", this.trajectoryZ);
    }
}

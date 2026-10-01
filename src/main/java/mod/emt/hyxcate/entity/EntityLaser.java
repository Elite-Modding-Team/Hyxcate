package mod.emt.hyxcate.entity;

import mod.emt.hyxcate.client.particle.HyxcateParticleHandler;
import mod.emt.hyxcate.util.ColorUtil;
import mod.emt.hyxcate.util.HyxcateDamageSource;
import io.netty.buffer.ByteBuf;
import mod.emt.hyxcate.util.ParticleUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

// TODO: Setup what effects the laser can do and some cleaning up
public class EntityLaser extends EntityThrowable implements IEntityAdditionalSpawnData {
    private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityLaser.class, DataSerializers.VARINT);

    private float damage;
    private boolean pierceBlocks = false;

    public EntityLaser(World world) {
        super(world);
    }

    public EntityLaser(World world, EntityLivingBase shooter, float damageAmount) {
        super(world, shooter);
        this.damage = damageAmount;
        this.rotationYaw = shooter.rotationYaw;
        this.prevRotationYaw = shooter.rotationYaw;
        this.rotationPitch = shooter.rotationPitch;
        this.prevRotationPitch = shooter.rotationPitch;
    }

    // Sets another color, otherwise default to red
    public EntityLaser(World world, EntityLivingBase shooter, float damageAmount, int color) {
        super(world, shooter);
        this.damage = damageAmount;
        this.setLaserColor(color);
        this.rotationYaw = shooter.rotationYaw;
        this.prevRotationYaw = shooter.rotationYaw;
        this.rotationPitch = shooter.rotationPitch;
        this.prevRotationPitch = shooter.rotationPitch;
    }

    public EntityLaser(final World world, final double x, final double y, final double z) {
        super(world, x, y, z);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(COLOR, 0xFF0000);
    }

    public void setLaserColor(int color) {
        this.dataManager.set(COLOR, color);
    }

    public int getLaserColor() {
        return this.dataManager.get(COLOR);
    }

    public void setPierceBlocks(boolean pierceBlocks) {
        this.pierceBlocks = pierceBlocks;
    }

    public boolean canPierceBlocks() {
        return this.pierceBlocks;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (compound.hasKey("LaserColor")) {
            setLaserColor(compound.getInteger("LaserColor"));
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        compound.setInteger("LaserColor", getLaserColor());
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.typeOfHit == RayTraceResult.Type.BLOCK && !canPierceBlocks()) {
            for (int i = 0; i < 10; ++i) {
                int colorInt = this.getLaserColor();
                float r = (float) (colorInt >> 16 & 255) / 255.0F;
                float g = (float) (colorInt >> 8 & 255) / 255.0F;
                float b = (float) (colorInt & 255) / 255.0F;
                double vx = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
                double vy = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
                double vz = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
                ParticleUtil.spawnParticleGlow(this.world, (float) this.posX, (float) this.posY, (float) this.posZ, (float) vx, (float) vy, (float) vz, r, g, b, 0.8F, 1.0F, 30);
            }
            this.setDead();
            return;
        }

        if (!this.world.isRemote) {
            EntityLivingBase shooter = this.thrower;
            Entity target = result.entityHit;

            if (result.typeOfHit == RayTraceResult.Type.ENTITY) {
                if (shooter != null && target instanceof EntityLivingBase && target != shooter && !target.isOnSameTeam(shooter)) {
                    double motionX = target.motionX;
                    double motionY = target.motionY;
                    double motionZ = target.motionZ;

                    if (target.attackEntityFrom(HyxcateDamageSource.causeIndirectLaserDamage(this, shooter).setProjectile(), this.damage)) {
                        // Prevents knockback because lasers don't do that
                        target.motionX = motionX;
                        target.motionY = motionY;
                        target.motionZ = motionZ;

                        target.setFire(1);
                        this.applyEnchantments(shooter, target);
                    }
                }
            }
        } else {
            int colorInt = this.getLaserColor();
            float r = (float) (colorInt >> 16 & 255) / 255.0F;
            float g = (float) (colorInt >> 8 & 255) / 255.0F;
            float b = (float) (colorInt & 255) / 255.0F;

            for (int i = 0; i < 5; ++i) {
                double vx = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
                double vy = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
                double vz = (this.world.rand.nextDouble() - 0.5D) * 0.05D;
                ParticleUtil.spawnParticleGlow(this.world, (float) this.posX, (float) this.posY, (float) this.posZ, (float) vx, (float) vy, (float) vz, r, g, b, 0.8F, 1.0F, 30);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        // Ignore water altering movement
        if (this.isInWater()) {
            this.motionX /= 0.8D;
            this.motionY /= 0.8D;
            this.motionZ /= 0.8D;
        }

        if (this.ticksExisted > 70) {
            this.setDead();
        }
    }

    // Fixes buggy projectile behavior on the client
    @Override
    public void writeSpawnData(ByteBuf data) {
        data.writeInt(thrower != null ? thrower.getEntityId() : -1);
    }

    @Override
    public void readSpawnData(ByteBuf data) {
        final Entity thrower = world.getEntityByID(data.readInt());

        if (thrower instanceof EntityLivingBase) {
            this.thrower = (EntityLivingBase) thrower;
        }
    }

    // Prevent water from causing projectiles to pause abruptly
    @Override
    public boolean handleWaterMovement() {
        return false;
    }

    @Override
    protected float getGravityVelocity() {
        return 0.0F;
    }
}

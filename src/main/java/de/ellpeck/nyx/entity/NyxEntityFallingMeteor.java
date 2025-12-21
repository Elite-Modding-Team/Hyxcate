package de.ellpeck.nyx.entity;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.capability.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.init.NyxBlocks;
import de.ellpeck.nyx.init.NyxSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.List;

public class NyxEntityFallingMeteor extends NyxEntityFallingStar {

    public static final DataParameter<Integer> SIZE = EntityDataManager.createKey(NyxEntityFallingMeteor.class, DataSerializers.VARINT);
    public static final DataParameter<Integer> TYPE = EntityDataManager.createKey(NyxEntityFallingMeteor.class, DataSerializers.VARINT);
    public boolean homing;
    public boolean disableMessage;
    public float speedModifier;
    public boolean spawnNoBlocks;

    public NyxEntityFallingMeteor(World worldIn) {
        super(worldIn);
        this.dataManager.set(SIZE, worldIn.rand.nextInt(3) + 1);
        this.dataManager.set(TYPE, worldIn.rand.nextInt(4) + 1);
        this.initTrajectory(2 * this.speedModifier);
    }

    public static NyxEntityFallingMeteor spawn(World world, BlockPos pos) {
        NyxEntityFallingMeteor meteor = new NyxEntityFallingMeteor(world);
        pos = world.getPrecipitationHeight(pos).up(MathHelper.getInt(world.rand, 64, 96));
        meteor.setPosition(pos.getX(), pos.getY(), pos.getZ());
        world.spawnEntity(meteor);
        return meteor;
    }

    private Block getMeteorBlock() {
        switch (this.dataManager.get(TYPE)) {
            case 1: // Meteorite
                return this.rand.nextBoolean() ? NyxBlocks.meteoriteRockHot : NyxBlocks.meteoriteRock;
            case 2: // Frezarite
                return this.rand.nextBoolean() ? NyxBlocks.frezariteRock : Blocks.PACKED_ICE;
            case 3: // Kreknorite
                return this.rand.nextBoolean() ? NyxBlocks.kreknoriteRock : Blocks.OBSIDIAN;
            case 4: // Unknown
                switch (this.rand.nextInt(6)) {
                    case 0:
                        return NyxBlocks.meteoriteRockHot;
                    case 1:
                        return NyxBlocks.meteoriteRock;
                    case 2:
                        return NyxBlocks.frezariteRock;
                    case 3:
                        return NyxBlocks.kreknoriteRock;
                    case 4:
                        return Blocks.PACKED_ICE;
                    case 5:
                        return Blocks.OBSIDIAN;
                }
        }
        return Blocks.AIR;
    }

    private Block getFillerBlock() {
        switch (this.dataManager.get(TYPE)) {
            case 2: // Frezarite
                return Blocks.PACKED_ICE;
            case 3: // Kreknorite
                return Blocks.MAGMA;
            case 4: // Unknown
                return this.rand.nextBoolean() ? Blocks.MAGMA : Blocks.PACKED_ICE;
            default: // Meteorite
                return Blocks.MAGMA;
        }
    }

    private Block getLiquidBlock() {
        switch (this.dataManager.get(TYPE)) {
            case 2: // Frezarite
                return Blocks.WATER;
            case 3: // Kreknorite
                return Blocks.LAVA;
            case 4: // Unknown
                return this.rand.nextBoolean() ? Blocks.LAVA : Blocks.WATER;
            default: // Meteorite
                return null;
        }
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(SIZE, 1);
        this.dataManager.register(TYPE, 1);
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        // init some stuff that /summon wouldn't have
        if (this.dataManager.get(SIZE) <= 0) this.dataManager.set(SIZE, 2);
        if (this.dataManager.get(TYPE) <= 0) this.dataManager.set(TYPE, 1);
        if (this.speedModifier <= 0) this.speedModifier = 0.5F;
        if (this.trajectoryX == 0 && this.trajectoryY == 0 && this.trajectoryZ == 0)
            this.initTrajectory(2 * this.speedModifier);
    }

    @Override
    public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
        // homing meteors should spawn higher up
        if (this.homing) y += 48;
        super.setLocationAndAngles(x, y, z, yaw, pitch);
    }

    @Override
    protected void customUpdate() {
        if (!this.world.isRemote) {
            // falling into the void
            if (this.posY <= -64) this.setDead();

            // move towards the closest player if we're homing
            if (this.homing) {
                EntityPlayer player = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 128, false);
                if (player != null && player.getDistanceSq(this) >= 32 * 32) {
                    Vec3d motion = new Vec3d(player.posX - this.posX, player.posY - this.posY, player.posZ - this.posZ);
                    motion = motion.normalize();
                    this.trajectoryX = (float) motion.x * 2 * this.speedModifier;
                    if (motion.y < 0) this.trajectoryY = (float) motion.y * 2 * this.speedModifier;
                    this.trajectoryZ = (float) motion.z * 2 * this.speedModifier;
                }
            }

            if (this.collided) {
                // if we removed trees, we want to continue flying after until we hit the ground
                if (this.removeObstacles(this.getPosition())) return;

                NyxWorld data = NyxWorld.get(this.world);
                if (data == null) return;

                Explosion exp = this.world.createExplosion(null, this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, this.dataManager.get(SIZE) * 4, true);
                if (!this.spawnNoBlocks) {
                    // Spawn meteor
                    int radius = this.dataManager.get(SIZE);
                    BlockPos center = this.getPosition().down(radius * 2);
                    for (int x = -radius; x <= radius; x++) {
                        for (int y = -radius; y <= radius; y++) {
                            for (int z = -radius; z <= radius; z++) {
                                BlockPos offsetPos = center.add(x, y, z);
                                if (x * x + y * y + z * z <= radius * radius && !this.world.getBlockState(offsetPos).getMaterial().isSolid())
                                    this.world.setBlockState(offsetPos, this.getMeteorBlock().getDefaultState());
                            }
                        }
                    }

                    // Spawn debris
                    for (BlockPos affected : exp.getAffectedBlockPositions()) {
                        if (this.world.getBlockState(affected).getMaterial().isSolid() || !this.world.getBlockState(affected.down()).isFullBlock() || this.world.rand.nextBoolean())
                            continue;
                        if (this.world.rand.nextInt(6) == 0) {
                            this.world.setBlockState(affected, this.getFillerBlock().getDefaultState());
                        } else if (this.world.rand.nextInt(8) == 0) {
                            // Frezarite
                            if (this.dataManager.get(TYPE) == 2) {
                                int snowLayers = this.world.rand.nextInt(3) + 1;
                                this.world.setBlockState(affected, Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, snowLayers));
                                // Kreknorite
                            } else if (this.dataManager.get(TYPE) == 3) {
                                this.world.setBlockState(affected, Blocks.FIRE.getDefaultState());
                                // Unknown
                            } else if (this.dataManager.get(TYPE) == 4) {
                                int snowLayers = this.world.rand.nextInt(3) + 1;
                                this.world.setBlockState(affected, this.rand.nextBoolean() ? Blocks.FIRE.getDefaultState() : Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, snowLayers));
                            }
                        } else if (this.getLiquidBlock() != null && this.world.rand.nextInt(10) == 0) {
                            this.world.setBlockState(affected, this.getLiquidBlock().getDefaultState());
                        } else if (this.world.rand.nextBoolean()) {
                            this.world.setBlockState(affected, this.getMeteorBlock().getDefaultState());

                            // TODO: Spawn crystals on the side of meteor rocks.
                            if (this.world.rand.nextInt(80) == 0) {
                                this.world.setBlockState(affected.up(), NyxBlocks.cyberCrystal.getDefaultState());
                            }
                        }
                    }
                }

                data.meteorLandingSites.add(this.getPosition());
                data.sendToClients();
                this.setDead();

                this.spawnMeteorMobs(world, exp.getAffectedBlockPositions());

                // send "I spawned" message
                if (!this.disableMessage) {
                    for (EntityPlayer player : this.world.playerEntities) {
                        String msg;
                        SoundEvent sound;
                        float pitch = 2.0F / this.dataManager.get(SIZE);
                        float volume;
                        double dist = player.getDistanceSq(this.posX, this.posY, this.posZ);
                        if (dist > 512 * 512) {
                            return;
                        }
                        float distSqrt = (float) Math.sqrt(dist);
                        if (dist <= 160 * 160) {
                            msg = ".meteor";
                            sound = NyxSoundEvents.ENTITY_METEOR_IMPACT.getSoundEvent();
                            // close volume: 1.0F at 0 blocks -> 0.1F at 160 blocks (10 chunks)
                            volume = Math.max(0.1F, 1.0F - (distSqrt / 160.0F) * 0.9F);
                        } else {
                            msg = ".meteor_far";
                            sound = NyxSoundEvents.ENTITY_METEOR_IMPACT_FAR.getSoundEvent();
                            // far volume: 1.0F at 160 blocks -> 0.1F at 512 blocks (32 chunks)
                            float farDist = distSqrt - 160.0F;
                            volume = Math.max(0.1F, 1.0F - (farDist / 352.0F) * 0.9F);
                        }
                        // play sound
                        if (player instanceof EntityPlayerMP && player.dimension == this.world.provider.getDimension()) {
                            ((EntityPlayerMP) player).connection.sendPacket(new SPacketSoundEffect(sound, SoundCategory.AMBIENT, player.posX, player.posY, player.posZ, volume, pitch));
                        }
                        // send message
                        if (NyxConfig.meteorMessage && dist > 16 * 16) {
                            ITextComponent text;
                            if (NyxConfig.meteorMessageVerbose) {
                                text = new TextComponentTranslation("info." + Nyx.ID + ".meteor_verbose");
                                text.appendText(" " + this.getPosition().getX() + ", " + this.getPosition().getY() + ", " + this.getPosition().getZ() + "!");
                            } else {
                                text = new TextComponentTranslation("info." + Nyx.ID + msg);
                            }
                            text.setStyle(new Style().setColor(TextFormatting.GRAY).setItalic(true));
                            player.sendMessage(text);
                        }
                    }
                }
            }
        } else if (this.isLoaded()) {
            // we only want to display particles if we're loaded
            float size = this.dataManager.get(SIZE) / 2F + 1;
            for (int i = 0; i < 60; i++) {
                double x = this.posX + MathHelper.nextDouble(this.world.rand, -size, size);
                double y = this.posY + MathHelper.nextDouble(this.world.rand, -size, size);
                double z = this.posZ + MathHelper.nextDouble(this.world.rand, -size, size);
                double mX = -this.motionX + this.world.rand.nextGaussian() * 0.02;
                double mY = -this.motionY + this.world.rand.nextGaussian() * 0.02;
                double mZ = -this.motionZ + this.world.rand.nextGaussian() * 0.02;

                EnumParticleTypes type;
                float f = this.world.rand.nextFloat();

                if (f >= 0.65F) {
                    type = this.dataManager.get(TYPE) == 2 ? EnumParticleTypes.SNOW_SHOVEL : EnumParticleTypes.FLAME;
                } else if (f >= 0.45F) {
                    type = this.dataManager.get(TYPE) == 2 ? EnumParticleTypes.SNOWBALL : EnumParticleTypes.LAVA;
                } else if (f >= 0.3F) {
                    type = this.dataManager.get(TYPE) == 2 ? EnumParticleTypes.CLOUD : EnumParticleTypes.SMOKE_NORMAL;
                } else {
                    type = this.dataManager.get(TYPE) == 2 ? EnumParticleTypes.EXPLOSION_NORMAL : EnumParticleTypes.SMOKE_LARGE;
                }

                this.world.spawnParticle(type, true, x, y, z, mX, mY, mZ);
            }
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("size", this.dataManager.get(SIZE));
        compound.setInteger("type", this.dataManager.get(TYPE));
        compound.setBoolean("homing", this.homing);
        compound.setBoolean("disable_message", this.disableMessage);
        compound.setFloat("speed", this.speedModifier);
        compound.setBoolean("spawn_no_blocks", this.spawnNoBlocks);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(SIZE, compound.getInteger("size"));
        this.dataManager.set(TYPE, compound.getInteger("type"));
        this.homing = compound.getBoolean("homing");
        this.disableMessage = compound.getBoolean("disable_message");
        this.speedModifier = compound.getFloat("speed");
        this.spawnNoBlocks = compound.getBoolean("spawn_no_blocks");
    }

    private boolean removeObstacles(BlockPos pos) {
        boolean any = false;

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    BlockPos offset = pos.add(x, y, z);

                    if (offset.distanceSq(this.posX, this.posY, this.posZ) >= 8 * 8) continue;

                    IBlockState state = this.world.getBlockState(offset);

                    if (!state.getBlock().isLeaves(state, this.world, offset) && !state.getBlock().isWood(this.world, offset) && !state.getMaterial().isLiquid())
                        continue;

                    this.world.setBlockToAir(offset);
                    this.removeObstacles(offset);
                    any = true;
                }
            }
        }

        return any;
    }

    private void spawnMeteorMobs(World world, List<BlockPos> affectedBlockPositions) {
        int meteorMobs = world.rand.nextInt(this.dataManager.get(SIZE) * 2) + 3;

        for (int i = affectedBlockPositions.size() - 1; i >= 0 && meteorMobs > 0; i--) {
            BlockPos blockPos = affectedBlockPositions.get(i);
            boolean isAir = world.isAirBlock(blockPos) && world.isAirBlock(blockPos.up());
            boolean isSolidGround = world.getBlockState(blockPos.down()).isOpaqueCube();

            if (isAir && isSolidGround && world.rand.nextBoolean()) {
                double x = blockPos.getX() + 0.5D;
                int y = blockPos.getY();
                double z = blockPos.getZ() + 0.5D;

                if (world.rand.nextInt(5) == 0) { // Kitties!
                    if (this.dataManager.get(TYPE) > 1) {
                        NyxEntityAlienKitty kitty = new NyxEntityAlienKitty(world, this.dataManager.get(TYPE));
                        kitty.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
                        world.spawnEntity(kitty);
                    } else {
                        NyxEntityCometKitty kitty = new NyxEntityCometKitty(world);
                        kitty.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
                        world.spawnEntity(kitty);
                    }
                } else { // Boring mobs
                    if (this.dataManager.get(TYPE) == 3 && world.rand.nextBoolean()) {
                        EntityBlaze blaze = new EntityBlaze(world); // TODO: Replace with meteor blaze
                        blaze.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
                        world.spawnEntity(blaze);
                    } else {
                        NyxEntityAlienCreeper creeper = new NyxEntityAlienCreeper(world, this.dataManager.get(TYPE));
                        creeper.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
                        world.spawnEntity(creeper);
                    }
                }

                meteorMobs--;
            }
        }
    }
}

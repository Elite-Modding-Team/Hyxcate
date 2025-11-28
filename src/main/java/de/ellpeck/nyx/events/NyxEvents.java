package de.ellpeck.nyx.events;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.capabilities.NyxWorld;
import de.ellpeck.nyx.config.NyxConfig;
import de.ellpeck.nyx.entities.NyxAIWolfSpecialMoon;
import de.ellpeck.nyx.entities.NyxEntityFallingMeteor;
import de.ellpeck.nyx.entities.NyxEntityFallingStar;
import de.ellpeck.nyx.events.lunar.NyxEventBloodMoon;
import de.ellpeck.nyx.events.lunar.NyxEventFullMoon;
import de.ellpeck.nyx.events.lunar.NyxEventHarvestMoon;
import de.ellpeck.nyx.events.lunar.NyxEventStarShower;
import de.ellpeck.nyx.events.solar.NyxEventRedSun;
import de.ellpeck.nyx.init.NyxAttributes;
import de.ellpeck.nyx.init.NyxEnchantments;
import de.ellpeck.nyx.init.NyxItems;
import de.ellpeck.nyx.init.NyxPotions;
import de.ellpeck.nyx.items.tools.*;
import de.ellpeck.nyx.network.NyxPacketHandler;
import de.ellpeck.nyx.network.NyxPacketWorld;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import de.ellpeck.nyx.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockEnchantmentTable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.vecmath.Vector3d;
import java.util.List;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = Nyx.ID)
public final class NyxEvents {
    public static int magnetizationLevel;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;

        // Celestial Warhammer Ability
        // We check fall distance because we need the player to be done falling when removing the tag
        if (player.onGround && player.fallDistance <= 0 && player.getEntityData().hasKey(Nyx.ID + ":leap_start")) {
            if (!player.world.isRemote) {
                long leapTime = player.world.getTotalWorldTime() - player.getEntityData().getLong(Nyx.ID + ":leap_start");

                if (leapTime >= 5) {
                    int radius = 6;
                    AxisAlignedBB area = new AxisAlignedBB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
                    DamageSource source = DamageSource.causePlayerDamage(player);
                    float damage = NyxConfig.celestialWarhammerAbilityDamage * Math.min((leapTime - 5) / 35F, 1);

                    for (EntityLivingBase entity : player.world.getEntitiesWithinAABB(EntityLivingBase.class, area, EntitySelectors.IS_ALIVE)) {
                        if (!entity.isOnSameTeam(player)) {
                            if (entity == player) continue;

                            entity.setFire(10 + (player.world.rand.nextInt(10)));
                            entity.attackEntityFrom(source, damage);
                            entity.knockBack(player, 3.0F, player.posX - entity.posX, player.posZ - entity.posZ);
                            entity.motionY = 1;
                        }
                    }

                    if (!player.world.isRemote) {
                        int particleAmount = 90;
                        double particleDistance = 3.0D;
                        IBlockState state = player.world.getBlockState(new BlockPos(player.posX, player.posY, player.posZ).down());
                        int blockId = Block.getStateId(state);

                        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.LAVA, player.posX, player.posY, player.posZ, particleAmount, particleDistance, 0.0D, particleDistance, 0.0D);
                        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, player.posX, player.posY, player.posZ, particleAmount, particleDistance, 0.0D, particleDistance, 0.15D);
                        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, player.posX, player.posY, player.posZ, particleAmount * 2, particleDistance, 0.0D, particleDistance, 1.0D, blockId);
                    }

                    player.world.playSound(null, player.getPosition(), NyxSoundEvents.hammerSmash.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, (1.0F + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2F) * 0.7F);
                    player.world.playSound(null, player.getPosition(), NyxSoundEvents.kreknoriteHit.getSoundEvent(), SoundCategory.PLAYERS, 1.25F, (1.0F + (player.world.rand.nextFloat() - player.world.rand.nextFloat()) * 0.2F) * 0.7F);
                }
            }

            player.getEntityData().removeTag(Nyx.ID + ":leap_start");
        }

        // TODO: Add this mechanic for meteorite tools
        // Magnetization Enchantment, controlled by the enchantment level
        for (ItemStack stack : player.getEquipmentAndArmor()) {
            magnetizationLevel = EnchantmentHelper.getEnchantmentLevel(NyxEnchantments.magnetization, stack);

            if (magnetizationLevel != 0) {
                pullItems(player, 4.0D * magnetizationLevel, 0.0125F * magnetizationLevel);
            }
        }
    }

    // Magnetization effect
    public static void pullItems(EntityPlayer player, double distanceAmount, float strengthAmount) {
        double distance = distanceAmount;
        int pulled = 0;
        float strength = strengthAmount;

        List<EntityItem> items = player.getEntityWorld().getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(player.posX - distance, player.posY - distance, player.posZ - distance, player.posX + distance, player.posY + distance, player.posZ + distance));
        for (EntityItem item : items) {
            if (item.getItem().isEmpty() || item.isDead || item.getEntityData().getBoolean("PreventRemoteMovement")) {
                continue;
            }

            if (pulled > 200) {
                break;
            }

            Vector3d vec = new Vector3d(player.posX, player.posY, player.posZ);
            vec.sub(new Vector3d(item.posX, item.posY, item.posZ));

            if (vec.lengthSquared() <= 0.05) {
                continue;
            }

            vec.normalize();
            vec.scale(strength);

            item.motionX += vec.x;
            item.motionY += vec.y;
            item.motionZ += vec.z;

            pulled++;
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        // Celestial Warhammer Leap Ability
        if (event.getEntityLiving().getEntityData().hasKey(Nyx.ID + ":leap_start"))
            event.setDamageMultiplier(0);
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        // Explosion Resistance Attribute
        if (event.getSource().isExplosion()) {
            IAttributeInstance explosionResistance = event.getEntityLiving().getEntityAttribute(NyxAttributes.EXPLOSION_RESISTANCE);

            if (explosionResistance != null && !explosionResistance.getModifiers().isEmpty()) {
                float explosionResistanceValue = 0.0F;

                for (AttributeModifier attributemodifier : explosionResistance.getModifiers()) {
                    explosionResistanceValue += (float) attributemodifier.getAmount();
                }
                if (explosionResistanceValue <= 0) return;

                // Reduce explosion damage by attribute amount
                event.setAmount(event.getAmount() * (1 - explosionResistanceValue));
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        NyxWorld data = NyxWorld.get(event.world);
        if (data == null) return;
        data.update();

        // Falling Stars
        if (!event.world.isRemote && NyxConfig.fallingStars && !NyxWorld.isDaytime(event.world) && event.world.getTotalWorldTime() % 20 == 0) {
            String dimension = event.world.provider.getDimensionType().getName();
            if (NyxConfig.allowedDimensions.contains(dimension)) {
                for (EntityPlayer player : event.world.playerEntities) {
                    if (event.world.rand.nextFloat() > (data.currentLunarEvent instanceof NyxEventStarShower ? NyxConfig.fallingStarRarityShower : NyxConfig.fallingStarRarity))
                        continue;
                    BlockPos startPos = player.getPosition().add(event.world.rand.nextGaussian() * 20, 0, event.world.rand.nextGaussian() * 20);
                    startPos = event.world.getPrecipitationHeight(startPos).up(MathHelper.getInt(event.world.rand, 32, 64));

                    NyxEntityFallingStar star = new NyxEntityFallingStar(event.world);
                    star.setPosition(startPos.getX(), startPos.getY(), startPos.getZ());
                    event.world.spawnEntity(star);
                }
            }
        }

        // Meteors
        meteors:
        if (!event.world.isRemote && NyxConfig.meteors && event.world.getTotalWorldTime() % 20 == 0) {
            if (event.world.playerEntities.isEmpty()) break meteors;
            EntityPlayer selectedPlayer = event.world.playerEntities.get(event.world.rand.nextInt(event.world.playerEntities.size()));
            if (selectedPlayer == null) break meteors;
            double spawnX = selectedPlayer.posX + MathHelper.nextDouble(event.world.rand, -NyxConfig.meteorSpawnRadius, NyxConfig.meteorSpawnRadius);
            double spawnZ = selectedPlayer.posZ + MathHelper.nextDouble(event.world.rand, -NyxConfig.meteorSpawnRadius, NyxConfig.meteorSpawnRadius);
            BlockPos spawnPos = new BlockPos(spawnX, 0, spawnZ);
            double chance = NyxConfig.getMeteorChance(event.world, data);
            MutableInt ticksInArea = data.playersPresentTicks.get(new ChunkPos(spawnPos));
            if (ticksInArea != null && ticksInArea.intValue() >= NyxConfig.meteorDisallowTime)
                chance /= Math.pow(2, ticksInArea.intValue() / (double) NyxConfig.meteorDisallowTime);
            if (chance <= 0 || event.world.rand.nextFloat() > chance) break meteors;
            if (!event.world.isBlockLoaded(spawnPos, false)) {
                // add meteor information to cache
                data.cachedMeteorPositions.add(spawnPos);
                data.sendToClients();
            } else {
                // spawn meteor entity
                NyxEntityFallingMeteor.spawn(data.world, spawnPos);
            }
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        World world = event.getWorld();
        if (world.isRemote) return;
        NyxWorld data = NyxWorld.get(world);
        if (data == null) return;
        Chunk chunk = event.getChunk();
        ChunkPos cp = chunk.getPos();

        // spawn meteors from the cache
        List<BlockPos> meteors = data.cachedMeteorPositions.stream().filter(p -> p.getX() >= cp.getXStart() && p.getZ() >= cp.getZStart() && p.getX() <= cp.getXEnd() && p.getZ() <= cp.getZEnd()).collect(Collectors.toList());
        for (BlockPos pos : meteors)
            NyxEntityFallingMeteor.spawn(data.world, pos);
        meteors.forEach(data.cachedMeteorPositions::remove);
        data.sendToClients();
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        // Delete monsters spawned by blood moon
        if (NyxConfig.bloodMoonVanish && !entity.world.isRemote && NyxWorld.isDaytime(entity.world) && entity.getEntityData().getBoolean(Nyx.ID + ":blood_moon_spawn")) {
            ((WorldServer) entity.world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX, entity.posY, entity.posZ, 10, 0.5, 1, 0.5, 0);
            entity.setDead();
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        if (world.isRemote) return;
        NyxWorld nyx = NyxWorld.get(world);
        if (nyx == null) return;

        if (entity instanceof EntityPlayerMP) {
            NyxPacketWorld packet = new NyxPacketWorld(nyx);
            NyxPacketHandler.sendTo((EntityPlayerMP) entity, packet);
        } else if (entity instanceof EntityWolf) {
            EntityWolf wolf = (EntityWolf) entity;
            wolf.targetTasks.addTask(3, new NyxAIWolfSpecialMoon(wolf));
        }
    }

    @SubscribeEvent
    public static void onExpDrop(LivingExperienceDropEvent event) {
        if (NyxConfig.enchantments && NyxConfig.maxLunarEdgeXpMult > 0) {
            EntityPlayer player = event.getAttackingPlayer();
            if (player == null) return;
            ItemStack held = player.getHeldItemMainhand();
            int level = EnchantmentHelper.getEnchantmentLevel(NyxEnchantments.lunarEdge, held);
            if (level <= 0) return;
            float exp = event.getDroppedExperience();
            float mod = level / (float) NyxEnchantments.lunarEdge.getMaxLevel();
            mod *= (float) NyxConfig.maxLunarEdgeXpMult;
            event.setDroppedExperience((int) (exp + MathHelper.floor(exp * mod)));
        }
    }

    @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!(entity instanceof IMob || entity instanceof EntityMob) || entity instanceof EntitySlime) return;

        // Don't spawn mobs during harvest moon
        if (event.getSpawner() == null) {
            NyxWorld nyx = NyxWorld.get(entity.world);
            if (nyx != null && nyx.currentLunarEvent instanceof NyxEventHarvestMoon) event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onSpawn(LivingSpawnEvent.SpecialSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        if (!(entity instanceof IMob || entity instanceof EntityMob)) return;
        NyxWorld nyx = NyxWorld.get(entity.world);
        if (nyx == null) return;

        // Bigger slimes
        // This will be replaced with a unique mob so we are disabling it for now
        /*if (entity instanceof EntitySlime) {
            EntitySlime slime = (EntitySlime) entity;
            int size = slime.getSlimeSize();

            if (nyx.currentLunarEvent instanceof NyxEventFullMoon) {
                int i = slime.world.rand.nextInt(5);
                if (i <= 1) size += 2;
                if (i <= 2) size += 2;
            } else if (nyx.currentLunarEvent instanceof NyxEventHarvestMoon) {
                int i = slime.world.rand.nextInt(15);
                if (i <= 8) size += 5;
            }
            if (size != slime.getSlimeSize()) {
                slime.setSlimeSize(size, true);
                // Cancelling this event just suppresses onInitialSpawn, doc is wrong
                event.setCanceled(true);
            }
        }*/

        if (nyx.currentLunarEvent instanceof NyxEventFullMoon) {
            // Set random effect
            if (NyxConfig.addPotionEffects && !(entity instanceof EntityCreeper)) {
                Potion effect = null;
                int i = entity.world.rand.nextInt(20);

                if (i <= 2) {
                    effect = MobEffects.SPEED;
                } else if (i <= 4) {
                    effect = MobEffects.STRENGTH;
                } else if (i <= 6) {
                    effect = MobEffects.REGENERATION;
                } else if (i <= 7) {
                    effect = MobEffects.INVISIBILITY;
                }

                if (effect != null) entity.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE));
            }

            // Spawn a second one
            if (NyxConfig.additionalMobsChance > 0 && entity.world.rand.nextInt(NyxConfig.additionalMobsChance) == 0)
                doExtraSpawn(entity, "full_moon_spawn");
        }

        if (nyx.currentSolarEvent instanceof NyxEventRedSun && entity.world.canSeeSky(entity.getPosition())) {
            IAttributeInstance maxHealthAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            double newMaxHealth = maxHealthAttribute.getBaseValue() * 1.5;
            maxHealthAttribute.setBaseValue(newMaxHealth);
            entity.setHealth((float) newMaxHealth);
        }
    }

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (Nyx.ID.equals(event.getModID())) NyxConfig.load();
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        NyxWorld nyx = NyxWorld.get(world);

        ench:
        if (NyxConfig.disallowDayEnchanting) {
            long time = world.getWorldTime() % 24000;
            if (time > 13000 && time < 23000) break ench;
            if (!(block instanceof BlockEnchantmentTable)) break ench;
            if (NyxConfig.enchantingWhitelistDimensions.contains(world.provider.getDimensionType().getName()))
                break ench;
            event.setUseBlock(Event.Result.DENY);
            player.sendStatusMessage(new TextComponentTranslation("info." + Nyx.ID + ".day_enchanting"), true);
        }

        if (nyx != null && nyx.currentLunarEvent != null && nyx.currentLunarEvent.shouldPreventSleeping() && !NyxConfig.specialMoonSleeping && block instanceof BlockBed) {
            if (nyx.currentLunarEvent instanceof NyxEventBloodMoon) {
                player.sendStatusMessage(new TextComponentTranslation("info." + Nyx.ID + ".blood_moon_sleeping"), true);
            }
        }
    }

    @SubscribeEvent
    public static void onWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        event.addCapability(new ResourceLocation(Nyx.ID, "world_cap"), new NyxWorld(event.getObject()));
    }

    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        NyxWorld nyx = NyxWorld.get(player.world);
        if (nyx != null && nyx.currentLunarEvent != null && nyx.currentLunarEvent.shouldPreventSleeping() && !NyxConfig.specialMoonSleeping)
            event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
    }

    private static void doExtraSpawn(Entity original, String key) {
        String addedSpawnKey = Nyx.ID + ":" + key;
        if (!original.getEntityData().getBoolean(addedSpawnKey)) {
            ResourceLocation name = EntityList.getKey(original);
            if (name != null) {
                boolean listed = NyxConfig.mobDuplicationBlacklist.contains(name.toString());
                if (NyxConfig.isMobDuplicationWhitelist != listed) return;

                for (int x = -2; x <= 2; x++) {
                    for (int y = -2; y <= 2; y++) {
                        for (int z = -2; z <= 2; z++) {
                            if (x == 0 && y == 0 && z == 0) continue;
                            BlockPos offset = original.getPosition().add(x, y, z);
                            if (!WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, original.world, offset))
                                continue;
                            Entity entity = EntityList.createEntityByIDFromName(name, original.world);
                            if (!(entity instanceof EntityLiving)) return;
                            EntityLiving living = (EntityLiving) entity;
                            entity.setLocationAndAngles(original.posX + x, original.posY + y, original.posZ + z, MathHelper.wrapDegrees(original.world.rand.nextFloat() * 360), 0);
                            living.rotationYawHead = living.rotationYaw;
                            living.renderYawOffset = living.rotationYaw;
                            living.getEntityData().setBoolean(addedSpawnKey, true);
                            if (!ForgeEventFactory.doSpecialSpawn(living, original.world, (float) original.posX + x, (float) original.posY + y, (float) original.posZ + z, null))
                                living.onInitialSpawn(original.world.getDifficultyForLocation(new BlockPos(living)), null);
                            original.world.spawnEntity(entity);
                            return;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEvent(LivingAttackEvent event) {
        for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {

            // Prevents screen shaking and damage sound from immune damage
            if (stack.getItem() == NyxItems.meteoriteBoots ||
                    stack.getItem() == NyxItems.frezariteBoots ||
                    stack.getItem() == NyxItems.kreknoriteBoots ||
                    stack.getItem() == NyxItems.tektiteBoots) {
                if (event.getSource() == DamageSource.HOT_FLOOR) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onDamageEvent(LivingDamageEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        if (entity instanceof EntityLivingBase && trueSource instanceof EntityLivingBase) {
            IAttributeInstance paralysis = ((EntityLivingBase) trueSource).getEntityAttribute(NyxAttributes.PARALYSIS);
            if (paralysis != null && !paralysis.getModifiers().isEmpty()) {
                float paralysisValue = 0.0F;

                for (AttributeModifier attributemodifier : paralysis.getModifiers()) {
                    paralysisValue += (float) attributemodifier.getAmount();
                }
                if (paralysisValue <= 0) return;

                // Inflicts mob with Paralysis when the attribute is successful
                if (Utils.setChance(paralysisValue)) {
                    entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, NyxSoundEvents.tektiteHit.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
                    entity.addPotionEffect(new PotionEffect(NyxPotions.PARALYSIS, 10 * 20, 0));
                }
            }
        }

        for (ItemStack stack : entity.getArmorInventoryList()) {
            // All boots are immune to magma and other hot floor blocks
            if (stack.getItem() == NyxItems.meteoriteBoots ||
                    stack.getItem() == NyxItems.frezariteBoots ||
                    stack.getItem() == NyxItems.kreknoriteBoots ||
                    stack.getItem() == NyxItems.tektiteBoots) {
                if (damageSource == DamageSource.HOT_FLOOR) {
                    event.setAmount(0.0F);
                    event.setCanceled(true);
                }
            }
        }
    }

    // Unbreaking still applies to items on anvils regardless of whether the items don't accept it in enchantment tables or not
    // This event should fix that
    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        if (event.getLeft().isEmpty() || event.getRight().isEmpty()) {
            return;
        }

        if (event.getLeft().getItem() instanceof NyxToolBeamSword || event.getLeft().getItem() instanceof NyxToolCelestialWarhammer || event.getLeft().getItem() instanceof NyxToolTektiteGreatsword) {
            if (EnchantmentHelper.getEnchantments(event.getRight()).keySet().stream().anyMatch(e -> e == Enchantments.UNBREAKING)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        // TODO: Change this to custom damage source
        if (damageSource == DamageSource.MAGIC) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, NyxSoundEvents.paralyzeZap.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        if (trueSource instanceof EntityPlayer && trueSource != null) {
            Item heldItem = ((EntityPlayer) trueSource).getHeldItemMainhand().getItem();

            // Beam swords ignore armor
            if (heldItem instanceof NyxToolBeamSword) {
                damageSource.setDamageBypassesArmor();

                // Beam swords also ignore invincibility frames
                entity.hurtResistantTime = 0;
                entity.hurtTime = 0;
            }
        }
    }

    // Re-enable mob ai when Paralysis effect ends
    @SubscribeEvent
    public static void onPotionExpired(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(NyxPotions.PARALYSIS) && !(entity instanceof EntityPlayer)) {
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).setNoAI(false);
            } else {
                entity.updateBlocked = false;
            }
        }
    }

    @SubscribeEvent
    public static void onPotionRemoved(PotionEvent.PotionExpiryEvent event) {
        EntityLivingBase entity = event.getEntityLiving();

        if (event.getPotionEffect().getPotion().equals(NyxPotions.PARALYSIS) && !(entity instanceof EntityPlayer)) {
            if (entity instanceof EntityLiving) {
                ((EntityLiving) entity).setNoAI(false);
            } else {
                entity.updateBlocked = false;
            }
        }
    }
}

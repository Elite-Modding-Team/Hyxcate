package mod.emt.hyxcate.event;

import com.expandedevents.api.event.ItemAttributeModifierEvent;
import com.expandedevents.api.event.LivingSprintStartEvent;
import mod.emt.hyxcate.Hyxcate;
import mod.emt.hyxcate.capability.HyxcateWorld;
import mod.emt.hyxcate.compat.gamestages.GameStages;
import mod.emt.hyxcate.config.HyxcateConfig;
import mod.emt.hyxcate.config.HyxcateData;
import mod.emt.hyxcate.entity.EntityEyezor;
import mod.emt.hyxcate.entity.EntityFallingMeteor;
import mod.emt.hyxcate.entity.EntityFallingStar;
import mod.emt.hyxcate.entity.ai.AIWolfSpecialMoon;
import mod.emt.hyxcate.event.lunar.LunarEventBloodMoon;
import mod.emt.hyxcate.event.lunar.LunarEventBlueMoon;
import mod.emt.hyxcate.event.lunar.LunarEventFullMoon;
import mod.emt.hyxcate.event.lunar.LunarEventStarShower;
import mod.emt.hyxcate.event.solar.SolarEventGrimEclipse;
import mod.emt.hyxcate.event.solar.SolarEventRedGiant;
import mod.emt.hyxcate.init.*;
import mod.emt.hyxcate.item.tool.IHyxcateTool;
import mod.emt.hyxcate.item.tool.HyxcateToolBeamSword;
import mod.emt.hyxcate.item.tool.HyxcateToolCelestialWarhammer;
import mod.emt.hyxcate.item.tool.HyxcateToolTektiteGreatsword;
import mod.emt.hyxcate.mixin.common.HyxcateEntityAccessor;
import mod.emt.hyxcate.network.HyxcatePacketHandler;
import mod.emt.hyxcate.network.HyxcatePacketWorld;
import mod.emt.hyxcate.util.HyxcateDamageSource;
import mod.emt.hyxcate.util.HyxcateUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
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
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.mutable.MutableInt;

import javax.vecmath.Vector3d;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = Hyxcate.ID)
public final class HyxcateEvents {
    public static int lunarEdgeLevel;
    public static int lunarShieldLevel;
    public static int magnetizationLevel;
    public static int solarEdgeLevel;
    public static int solarShieldLevel;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        EntityPlayer player = event.player;

        // Celestial Warhammer Ability
        // We check fall distance because we need the player to be done falling when removing the tag
        if (player.onGround && player.fallDistance <= 0 && player.getEntityData().hasKey(Hyxcate.ID + ":leap_start")) {
            if (!player.world.isRemote) {
                long leapTime = player.world.getTotalWorldTime() - player.getEntityData().getLong(Hyxcate.ID + ":leap_start");

                if (leapTime >= 5) {
                    int radius = 6;
                    AxisAlignedBB area = new AxisAlignedBB(player.posX - radius, player.posY - radius, player.posZ - radius, player.posX + radius, player.posY + radius, player.posZ + radius);
                    DamageSource source = DamageSource.causePlayerDamage(player);
                    float damage = HyxcateConfig.GENERAL.celestialWarhammerAbilityDamage * Math.min((leapTime - 5) / 35F, 1);

                    for (EntityLivingBase entity : player.world.getEntitiesWithinAABB(EntityLivingBase.class, area, EntitySelectors.IS_ALIVE)) {
                        if (!entity.isOnSameTeam(player)) {
                            if (entity == player) continue;

                            entity.addPotionEffect(new PotionEffect(HyxcatePotions.ASTRAL_EROSION, 8 * 20, 0, false, false));
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

                        // TODO: Cooler particles
                        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.END_ROD, player.posX, player.posY + 1.0D, player.posZ, particleAmount, particleDistance, 0.0D, particleDistance, 0.5D);
                        ((WorldServer) player.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, player.posX, player.posY, player.posZ, particleAmount * 2, particleDistance, 0.0D, particleDistance, 1.0D, blockId);
                    }

                    player.world.playSound(null, player.getPosition(), HyxcateSoundEvents.ITEM_CELESTIAL_WARHAMMER_SMASH.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F);
                }
            }

            player.getEntityData().removeTag(Hyxcate.ID + ":leap_start");
        }

        for (ItemStack stack : player.getEquipmentAndArmor()) {
            IAttributeInstance magnetization = player.getEntityAttribute(HyxcateAttributes.MAGNETIZATION);

            // Magnetization Attribute
            if (magnetization != null && !magnetization.getModifiers().isEmpty()) {
                float magnetizationValue = 0.0F;

                for (AttributeModifier attributemodifier : magnetization.getModifiers()) {
                    magnetizationValue += (float) attributemodifier.getAmount();
                }

                if (magnetizationValue <= 0) return;

                // If exceeding 10, set to 10 to prevent insanity
                if (magnetizationValue > 10.0F) {
                    magnetizationValue = 10.0F;
                }

                // Draw nearby items, with strength being based on attribute amount
                pullItems(player, 6.0D, 0.004F + (0.002F * magnetizationValue));
            }
        }

        if (Objects.requireNonNull(HyxcateWorld.get(player.world)).currentLunarEvent instanceof LunarEventBlueMoon) {
            player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 2, 1, false, false));
        }
    }

    // Magnetization effect
    public static void pullItems(EntityPlayer player, double distance, float strength) {
        World world = player.getEntityWorld();
        AxisAlignedBB aabb = new AxisAlignedBB(player.posX - distance, player.posY - distance, player.posZ - distance, player.posX + distance, player.posY + distance, player.posZ + distance);
        List<EntityItem> items = world.getEntitiesWithinAABB(EntityItem.class, aabb);

        int pulled = 0;
        for (EntityItem item : items) {
            if (item.getItem().isEmpty() || item.isDead || item.getEntityData().getBoolean("PreventRemoteMovement")) {
                continue;
            }

            if (pulled > 200) {
                break;
            }

            Vector3d vec = new Vector3d(player.posX, player.posY + 1, player.posZ);
            vec.sub(new Vector3d(item.posX, item.posY, item.posZ));

            if (vec.lengthSquared() <= 0.05) {
                continue;
            }

            vec.normalize();
            vec.scale(strength);

            item.motionX += vec.x;
            item.motionY += vec.y;
            item.motionZ += vec.z;

            // Prevent ground clamping
            item.onGround = false;

            pulled++;
        }
    }

    // Add attributes when their respective enchantments are active
    @SubscribeEvent
    public static void onItemAttribute(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();

        if (HyxcateConfig.MASTER_SWITCHES.enchantmentsEnabled) {
            lunarEdgeLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.lunarEdge, stack);
            double lunarEdgeBonus = HyxcateConfig.GENERAL.lunarEdgeDamageBase + (HyxcateConfig.GENERAL.lunarEdgeDamageSubsequent * lunarEdgeLevel);

            lunarShieldLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.lunarShield, stack);
            double lunarShieldBonus = HyxcateConfig.GENERAL.lunarShieldWardBase + (HyxcateConfig.GENERAL.lunarShieldWardSubsequent * lunarShieldLevel);

            magnetizationLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.magnetization, stack);
            double magnetizationBonus = 0.5D * magnetizationLevel;

            solarEdgeLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.solarEdge, stack);
            double solarEdgeBonus = HyxcateConfig.GENERAL.solarEdgeDamageBase + (HyxcateConfig.GENERAL.solarEdgeDamageSubsequent * solarEdgeLevel);

            solarShieldLevel = EnchantmentHelper.getEnchantmentLevel(HyxcateEnchantments.solarShield, stack);
            double solarShieldBonus = HyxcateConfig.GENERAL.solarShieldWardBase + (HyxcateConfig.GENERAL.solarShieldWardSubsequent * solarShieldLevel);

            if (lunarEdgeLevel > 0 && event.getSlotType() == EntityEquipmentSlot.MAINHAND) {
                Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.LUNAR_DAMAGE.getName());
                AttributeModifier toModify = null;

                for (AttributeModifier modifier : modifiers) {
                    if (modifier.getID().equals(HyxcateAttributes.LUNAR_DAMAGE_TOOL_ID)) {
                        toModify = modifier;
                        break;
                    }
                }

                if (toModify != null) {
                    event.removeModifier(HyxcateAttributes.LUNAR_DAMAGE, toModify);
                    event.addModifier(HyxcateAttributes.LUNAR_DAMAGE, new AttributeModifier(
                            toModify.getID(),
                            toModify.getName(),
                            toModify.getAmount() + lunarEdgeBonus,
                            toModify.getOperation())
                    );
                } else {
                    event.addModifier(HyxcateAttributes.LUNAR_DAMAGE, new AttributeModifier(
                            HyxcateAttributes.LUNAR_DAMAGE_TOOL_ID,
                            "Lunar Damage modifier",
                            lunarEdgeBonus,
                            Constants.AttributeModifierOperation.ADD)
                    );
                }
            }

            if (lunarShieldLevel > 0 && stack.getItem() instanceof ItemArmor && event.getSlotType() == ((ItemArmor) stack.getItem()).armorType) {
                UUID lunarWardArmorSlotID = HyxcateAttributes.LUNAR_WARD_ARMOR_ID.get(event.getSlotType());

                Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.LUNAR_WARD.getName());
                AttributeModifier toModify = null;

                for (AttributeModifier modifier : modifiers) {
                    if (modifier.getID().equals(lunarWardArmorSlotID)) {
                        toModify = modifier;
                        break;
                    }
                }

                if (toModify != null) {
                    event.removeModifier(HyxcateAttributes.LUNAR_WARD, toModify);
                    event.addModifier(HyxcateAttributes.LUNAR_WARD, new AttributeModifier(
                            toModify.getID(),
                            toModify.getName(),
                            toModify.getAmount() + lunarShieldBonus,
                            toModify.getOperation())
                    );
                } else {
                    event.addModifier(HyxcateAttributes.LUNAR_WARD, new AttributeModifier(
                            lunarWardArmorSlotID,
                            "Lunar Ward modifier",
                            lunarShieldBonus,
                            Constants.AttributeModifierOperation.ADD_MULTIPLE)
                    );
                }
            }

            if (solarEdgeLevel > 0 && event.getSlotType() == EntityEquipmentSlot.MAINHAND) {
                Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.SOLAR_DAMAGE.getName());
                AttributeModifier toModify = null;

                for (AttributeModifier modifier : modifiers) {
                    if (modifier.getID().equals(HyxcateAttributes.SOLAR_DAMAGE_TOOL_ID)) {
                        toModify = modifier;
                        break;
                    }
                }

                if (toModify != null) {
                    event.removeModifier(HyxcateAttributes.SOLAR_DAMAGE, toModify);
                    event.addModifier(HyxcateAttributes.SOLAR_DAMAGE, new AttributeModifier(
                            toModify.getID(),
                            toModify.getName(),
                            toModify.getAmount() + solarEdgeBonus,
                            toModify.getOperation())
                    );
                } else {
                    event.addModifier(HyxcateAttributes.SOLAR_DAMAGE, new AttributeModifier(
                            HyxcateAttributes.SOLAR_DAMAGE_TOOL_ID,
                            "Solar Damage modifier",
                            solarEdgeBonus,
                            Constants.AttributeModifierOperation.ADD)
                    );
                }
            }

            if (solarShieldLevel > 0 && stack.getItem() instanceof ItemArmor && event.getSlotType() == ((ItemArmor) stack.getItem()).armorType) {
                UUID solarWardArmorSlotID = HyxcateAttributes.SOLAR_WARD_ARMOR_ID.get(event.getSlotType());

                Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.SOLAR_WARD.getName());
                AttributeModifier toModify = null;

                for (AttributeModifier modifier : modifiers) {
                    if (modifier.getID().equals(solarWardArmorSlotID)) {
                        toModify = modifier;
                        break;
                    }
                }

                if (toModify != null) {
                    event.removeModifier(HyxcateAttributes.SOLAR_WARD, toModify);
                    event.addModifier(HyxcateAttributes.SOLAR_WARD, new AttributeModifier(
                            toModify.getID(),
                            toModify.getName(),
                            toModify.getAmount() + solarShieldBonus,
                            toModify.getOperation())
                    );
                } else {
                    event.addModifier(HyxcateAttributes.SOLAR_WARD, new AttributeModifier(
                            solarWardArmorSlotID,
                            "Solar modifier",
                            solarShieldBonus,
                            Constants.AttributeModifierOperation.MULTIPLY)
                    );
                }
            }

            // Checks are a bit hacky but we don't want the slot types to overlap
            if (magnetizationLevel > 0 && stack.getItem() instanceof ItemArmor && event.getSlotType() == ((ItemArmor) stack.getItem()).armorType) {
                UUID magnetizationArmorSlotId = HyxcateAttributes.MAGNETIZATION_ARMOR_ID.get(event.getSlotType());

                Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.MAGNETIZATION.getName());
                AttributeModifier toModify = null;

                for (AttributeModifier modifier : modifiers) {
                    if (modifier.getID().equals(magnetizationArmorSlotId)) {
                        toModify = modifier;
                        break;
                    }
                }

                if (toModify != null) {
                    event.removeModifier(HyxcateAttributes.MAGNETIZATION, toModify);
                    event.addModifier(HyxcateAttributes.MAGNETIZATION, new AttributeModifier(
                            toModify.getID(),
                            toModify.getName(),
                            toModify.getAmount() + magnetizationBonus,
                            toModify.getOperation())
                    );
                } else {
                    event.addModifier(HyxcateAttributes.MAGNETIZATION, new AttributeModifier(
                            magnetizationArmorSlotId,
                            "Magnetization modifier",
                            magnetizationBonus,
                            Constants.AttributeModifierOperation.ADD)
                    );
                }
            } else if (magnetizationLevel > 0 && !(stack.getItem() instanceof ItemArmor) && event.getSlotType() == EntityEquipmentSlot.MAINHAND) {
                Collection<AttributeModifier> modifiers = event.getOriginalModifiers().get(HyxcateAttributes.MAGNETIZATION.getName());
                AttributeModifier toModify = null;

                for (AttributeModifier modifier : modifiers) {
                    if (modifier.getID().equals(HyxcateAttributes.MAGNETIZATION_TOOL_ID)) {
                        toModify = modifier;
                        break;
                    }
                }

                if (toModify != null) {
                    event.removeModifier(HyxcateAttributes.MAGNETIZATION, toModify);
                    event.addModifier(HyxcateAttributes.MAGNETIZATION, new AttributeModifier(
                            toModify.getID(),
                            toModify.getName(),
                            toModify.getAmount() + magnetizationBonus,
                            toModify.getOperation())
                    );
                } else {
                    event.addModifier(HyxcateAttributes.MAGNETIZATION, new AttributeModifier(
                            HyxcateAttributes.MAGNETIZATION_TOOL_ID,
                            "Magnetization modifier",
                            magnetizationBonus,
                            Constants.AttributeModifierOperation.ADD)
                    );
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSprintStart(LivingSprintStartEvent event) {
        // Prevents affected players from sprinting
        if (event.getEntityLiving().isPotionActive(HyxcatePotions.DEEP_FREEZE) || event.getEntityLiving().isPotionActive(HyxcatePotions.PARALYSIS)) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        // Celestial Warhammer Leap Ability
        if (event.getEntityLiving().getEntityData().hasKey(Hyxcate.ID + ":leap_start"))
            event.setDamageMultiplier(0);
    }

    @SubscribeEvent
    public static void onDamage(LivingDamageEvent event) {
        long time = event.getEntity().world.getWorldTime() % 24000;
        boolean isNight = (time >= 13000 && time < 23000);
        boolean isDay = (time > 0 && time < 12000);

        // Explosion Resistance Attribute
        if (event.getSource().isExplosion()) {
            IAttributeInstance explosionResistance = event.getEntityLiving().getEntityAttribute(HyxcateAttributes.EXPLOSION_RESISTANCE);

            if (explosionResistance != null && !explosionResistance.getModifiers().isEmpty()) {
                float explosionResistanceValue = 0.0F;

                for (AttributeModifier attributemodifier : explosionResistance.getModifiers()) {
                    explosionResistanceValue += (float) attributemodifier.getAmount();
                }
                if (explosionResistanceValue <= 0) return;

                // If exceeding 100%, set to 100% to prevent healing
                if (explosionResistanceValue > 1.0F) {
                    explosionResistanceValue = 1.0F;
                }

                // Reduce explosion damage by attribute amount
                event.setAmount(event.getAmount() * (1.0F - explosionResistanceValue));
            }
        }

        // Lunar Ward Attribute
        if (isNight) {
            IAttributeInstance lunarWard = event.getEntityLiving().getEntityAttribute(HyxcateAttributes.LUNAR_WARD);

            if (lunarWard != null && !lunarWard.getModifiers().isEmpty()) {
                float lunarWardValue = 0.0F;

                for (AttributeModifier attributemodifier : lunarWard.getModifiers()) {
                    lunarWardValue += (float) attributemodifier.getAmount();
                }

                if (lunarWardValue <= 0) return;

                // If exceeding 100%, set to 100% to prevent healing
                if (lunarWardValue > 1.0F) {
                    lunarWardValue = 1.0F;
                }

                // Reduce damage by attribute amount
                event.setAmount(event.getAmount() * (1.0F - lunarWardValue));
            }
        }

        // Solar Ward Attribute
        if (isDay) {
            IAttributeInstance solarWard = event.getEntityLiving().getEntityAttribute(HyxcateAttributes.SOLAR_WARD);

            if (solarWard != null && !solarWard.getModifiers().isEmpty()) {
                float solarWardValue = 0.0F;

                for (AttributeModifier attributemodifier : solarWard.getModifiers()) {
                    solarWardValue += (float) attributemodifier.getAmount();
                }

                if (solarWardValue <= 0) return;

                // If exceeding 100%, set to 100% to prevent healing
                if (solarWardValue > 1.0F) {
                    solarWardValue = 1.0F;
                }

                // Reduce damage by attribute amount
                event.setAmount(event.getAmount() * (1.0F - solarWardValue));
            }
        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;
        HyxcateWorld data = HyxcateWorld.get(event.world);
        if (data == null) return;
        data.update();

        // Falling Stars
        if (!event.world.isRemote && HyxcateConfig.MASTER_SWITCHES.fallingStarEventsEnabled && !HyxcateWorld.isDaytime(event.world) && event.world.getTotalWorldTime() % 1200 == 0) {
            int dimension = event.world.provider.getDimensionType().getId();
            if (HyxcateData.ALLOWED_DIMENSIONS_LUNAR.contains(dimension)) {
                for (EntityPlayer player : event.world.playerEntities) {
                    if (!GameStages.checkGameStageFallingStarEvents(player)) continue;
                    if (event.world.rand.nextFloat() > (data.currentLunarEvent instanceof LunarEventStarShower ? HyxcateConfig.FALLING_STARS.chanceShowerM : HyxcateConfig.FALLING_STARS.chanceM))
                        continue;
                    BlockPos startPos = player.getPosition().add(event.world.rand.nextGaussian() * 20, 0, event.world.rand.nextGaussian() * 20);
                    startPos = event.world.getPrecipitationHeight(startPos).up(MathHelper.getInt(event.world.rand, 32, 64));

                    EntityFallingStar star = new EntityFallingStar(event.world);
                    star.setPosition(startPos.getX(), startPos.getY(), startPos.getZ());
                    event.world.spawnEntity(star);
                }
            }
        }

        // Meteors
        meteors:
        if (!event.world.isRemote && HyxcateConfig.MASTER_SWITCHES.meteorEventsEnabled && event.world.getTotalWorldTime() >= HyxcateConfig.METEORS.gracePeriod * 24000L && event.world.getTotalWorldTime() % 1200 == 0) {
            if (event.world.playerEntities.isEmpty()) break meteors;
            EntityPlayer selectedPlayer = event.world.playerEntities.get(event.world.rand.nextInt(event.world.playerEntities.size()));
            if (selectedPlayer == null || !GameStages.checkGameStageMeteorEvents(selectedPlayer)) break meteors;
            double spawnX = selectedPlayer.posX + MathHelper.nextDouble(event.world.rand, -HyxcateConfig.METEORS.spawnRadius, HyxcateConfig.METEORS.spawnRadius);
            double spawnZ = selectedPlayer.posZ + MathHelper.nextDouble(event.world.rand, -HyxcateConfig.METEORS.spawnRadius, HyxcateConfig.METEORS.spawnRadius);
            BlockPos spawnPos = new BlockPos(spawnX, 0, spawnZ);
            double chance = HyxcateUtils.getMeteorChance(event.world, data);
            MutableInt ticksInArea = data.playersPresentTicks.get(new ChunkPos(spawnPos));
            if (ticksInArea != null && ticksInArea.intValue() >= HyxcateConfig.METEORS.disallowTime)
                chance /= Math.pow(2, ticksInArea.intValue() / (double) HyxcateConfig.METEORS.disallowTime);
            if (chance <= 0 || event.world.rand.nextFloat() > chance) break meteors;
            if (!event.world.isBlockLoaded(spawnPos, false)) {
                // add meteor information to cache
                data.cachedMeteorPositions.add(spawnPos);
                data.sendToClients();
            } else {
                // spawn meteor entity
                EntityFallingMeteor.spawn(data.world, spawnPos);
            }
        }
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        World world = event.getWorld();
        if (world.isRemote) return;
        HyxcateWorld data = HyxcateWorld.get(world);
        if (data == null) return;
        Chunk chunk = event.getChunk();
        ChunkPos cp = chunk.getPos();

        // spawn meteors from the cache
        List<BlockPos> meteors = data.cachedMeteorPositions.stream().filter(p -> p.getX() >= cp.getXStart() && p.getZ() >= cp.getZStart() && p.getX() <= cp.getXEnd() && p.getZ() <= cp.getZEnd()).collect(Collectors.toList());
        for (BlockPos pos : meteors)
            EntityFallingMeteor.spawn(data.world, pos);
        meteors.forEach(data.cachedMeteorPositions::remove);
        data.sendToClients();
    }

    @SubscribeEvent
    public static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        // Delete monsters spawned by blood moon
        if (HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.mobsVanish && !entity.world.isRemote && HyxcateWorld.isDaytime(entity.world) && entity.getEntityData().getBoolean(Hyxcate.ID + ":blood_moon_spawn")) {
            ((WorldServer) entity.world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, entity.posX, entity.posY, entity.posZ, 10, 0.5, 1, 0.5, 0);
            entity.setDead();
        }

        // Stop affected players that are currently sprinting
        if (entity.isSprinting()) {
            if (entity.isPotionActive(HyxcatePotions.DEEP_FREEZE) || entity.isPotionActive(HyxcatePotions.PARALYSIS)) {
                entity.setSprinting(false);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerJoin(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        World world = entity.getEntityWorld();
        if (world.isRemote) return;
        HyxcateWorld Hyxcate = HyxcateWorld.get(world);
        if (Hyxcate == null) return;

        if (entity instanceof EntityPlayerMP) {
            HyxcatePacketWorld packet = new HyxcatePacketWorld(Hyxcate);
            HyxcatePacketHandler.sendTo((EntityPlayerMP) entity, packet);
        } else if (entity instanceof EntityWolf) {
            EntityWolf wolf = (EntityWolf) entity;
            wolf.targetTasks.addTask(3, new AIWolfSpecialMoon(wolf));
        }
    }

    @SubscribeEvent
    public static void onCheckSpawn(LivingSpawnEvent.CheckSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        HyxcateWorld Hyxcate = HyxcateWorld.get(entity.world);
        if (Hyxcate == null || !(entity instanceof IMob || entity instanceof EntityMob)) return;

        if (event.getSpawner() == null && entity.world.canSeeSky(entity.getPosition())) {
            ResourceLocation name = EntityList.getKey(entity);
            if (Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_BLOOD_MOON.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_BLOOD_MOON.contains(name)) {
                    event.setResult(Event.Result.DENY);
                }
            } else if (Hyxcate.currentLunarEvent instanceof LunarEventBlueMoon) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_BLUE_MOON.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_BLUE_MOON.contains(name)) {
                    event.setResult(Event.Result.DENY);
                }
            } else if (Hyxcate.currentLunarEvent instanceof LunarEventFullMoon) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_FULL_MOON.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_FULL_MOON.contains(name)) {
                    event.setResult(Event.Result.DENY);
                }
            } else if (Hyxcate.currentLunarEvent instanceof LunarEventStarShower) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_STAR_SHOWER.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_STAR_SHOWER.contains(name)) {
                    event.setResult(Event.Result.DENY);
                }
            }
            if (Hyxcate.currentSolarEvent instanceof SolarEventGrimEclipse) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_GRIM_ECLIPSE.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_GRIM_ECLIPSE.contains(name)) {
                    event.setResult(Event.Result.DENY);
                }
            } else if (Hyxcate.currentSolarEvent instanceof SolarEventRedGiant) {
                if (!HyxcateData.EXCLUSIVE_SPAWNS_RED_GIANT.isEmpty() && !HyxcateData.EXCLUSIVE_SPAWNS_RED_GIANT.contains(name)) {
                    event.setResult(Event.Result.DENY);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onSpawn(LivingSpawnEvent.SpecialSpawn event) {
        EntityLivingBase entity = event.getEntityLiving();
        HyxcateWorld Hyxcate = HyxcateWorld.get(entity.world);
        if (Hyxcate == null) return;

        if (Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon) {
            if (HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.spawnsExtraChance) == 0) {
                HyxcateUtils.handleExtraSpawn(entity, "blood_moon_spawn", HyxcateData.EXTRA_SPAWNS_BLOOD_MOON);
            }
            event.setCanceled(HyxcateUtils.handleReplacementSpawn(entity, "blood_moon_spawn", HyxcateData.REPLACEMENT_SPAWNS_BLOOD_MOON));
        } else if (Hyxcate.currentLunarEvent instanceof LunarEventBlueMoon) {
            if (HyxcateConfig.EVENTS_LUNAR.BLUE_MOON.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_LUNAR.BLUE_MOON.spawnsExtraChance) == 0) {
                HyxcateUtils.handleExtraSpawn(entity, "blue_moon_spawn", HyxcateData.EXTRA_SPAWNS_BLUE_MOON);
            }
            event.setCanceled(HyxcateUtils.handleReplacementSpawn(entity, "blue_moon_spawn", HyxcateData.REPLACEMENT_SPAWNS_BLUE_MOON));
        } else if (Hyxcate.currentLunarEvent instanceof LunarEventFullMoon) {
            if (HyxcateConfig.EVENTS_LUNAR.FULL_MOON.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_LUNAR.FULL_MOON.spawnsExtraChance) == 0) {
                HyxcateUtils.handleExtraSpawn(entity, "full_moon_spawn", HyxcateData.EXTRA_SPAWNS_FULL_MOON);
            }
            event.setCanceled(HyxcateUtils.handleReplacementSpawn(entity, "full_moon_spawn", HyxcateData.REPLACEMENT_SPAWNS_FULL_MOON));

            // Set random effect
            if (HyxcateConfig.EVENTS_LUNAR.FULL_MOON.addPotionEffects) {
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

                // TODO: Add a configure list of mobs that can and cannot get effects. Maybe we could do this for all events as well
                if (effect != null && !(entity instanceof EntityCreeper))
                    entity.addPotionEffect(new PotionEffect(effect, Integer.MAX_VALUE));
            }
        } else if (Hyxcate.currentLunarEvent instanceof LunarEventStarShower) {
            if (HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_LUNAR.STAR_SHOWER.spawnsExtraChance) == 0) {
                HyxcateUtils.handleExtraSpawn(entity, "star_shower_spawn", HyxcateData.EXTRA_SPAWNS_STAR_SHOWER);
            }
            event.setCanceled(HyxcateUtils.handleReplacementSpawn(entity, "star_shower_spawn", HyxcateData.REPLACEMENT_SPAWNS_STAR_SHOWER));
        }

        if (Hyxcate.currentSolarEvent instanceof SolarEventGrimEclipse) {
            if (HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_SOLAR.GRIM_ECLIPSE.spawnsExtraChance) == 0) {
                HyxcateUtils.handleExtraSpawn(entity, "grim_eclipse_spawn", HyxcateData.EXTRA_SPAWNS_GRIM_ECLIPSE);
            }
            event.setCanceled(HyxcateUtils.handleReplacementSpawn(entity, "grim_eclipse_spawn", HyxcateData.REPLACEMENT_SPAWNS_GRIM_ECLIPSE));
        } else if (Hyxcate.currentSolarEvent instanceof SolarEventRedGiant) {
            if (HyxcateConfig.EVENTS_SOLAR.RED_GIANT.spawnsExtraChance > 0 && entity.world.rand.nextInt(HyxcateConfig.EVENTS_SOLAR.RED_GIANT.spawnsExtraChance) == 0) {
                HyxcateUtils.handleExtraSpawn(entity, "red_giant_spawn", HyxcateData.EXTRA_SPAWNS_RED_GIANT);
            }
            event.setCanceled(HyxcateUtils.handleReplacementSpawn(entity, "red_giant_spawn", HyxcateData.REPLACEMENT_SPAWNS_RED_GIANT));

            // Increase health by 50%, make immune to fire
            IAttributeInstance maxHealthAttribute = entity.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
            double newMaxHealth = maxHealthAttribute.getBaseValue() * 1.5;
            maxHealthAttribute.setBaseValue(newMaxHealth);
            entity.setHealth((float) newMaxHealth);
            if (!entity.isImmuneToFire()) ((HyxcateEntityAccessor) entity).setIsImmuneToFire(true);
        }
    }

    @SubscribeEvent
    public static void onInteract(PlayerInteractEvent.RightClickBlock event) {
        EntityPlayer player = event.getEntityPlayer();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        HyxcateWorld hyxcate = HyxcateWorld.get(world);

        if (hyxcate != null && hyxcate.currentLunarEvent instanceof LunarEventBloodMoon && !HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.sleeping && block instanceof BlockBed)
            player.sendStatusMessage(new TextComponentTranslation("info." + Hyxcate.ID + ".blood_moon_sleeping"), true);
    }

    @SubscribeEvent
    public static void onWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        event.addCapability(new ResourceLocation(Hyxcate.ID, "world_cap"), new HyxcateWorld(event.getObject()));
    }

    @SubscribeEvent
    public static void onSleep(PlayerSleepInBedEvent event) {
        EntityPlayer player = event.getEntityPlayer();
        HyxcateWorld Hyxcate = HyxcateWorld.get(player.world);
        if (Hyxcate != null) {
            if (Hyxcate.currentLunarEvent instanceof LunarEventBloodMoon && !HyxcateConfig.EVENTS_LUNAR.BLOOD_MOON.sleeping) {
                event.setResult(EntityPlayer.SleepResult.OTHER_PROBLEM);
            } else if (Hyxcate.currentSolarEvent instanceof SolarEventGrimEclipse) {
                event.setResult(EntityPlayer.SleepResult.NOT_POSSIBLE_NOW); // TODO: Make conditional?
            }
        }
    }

    @SubscribeEvent
    public static void onAttackEvent(LivingAttackEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        for (ItemStack stack : event.getEntityLiving().getArmorInventoryList()) {
            // Prevents screen shaking and damage sound from immune damage
            if (stack.getItem() == HyxcateItems.meteoriteBoots ||
                    stack.getItem() == HyxcateItems.frezariteBoots ||
                    stack.getItem() == HyxcateItems.kreknoriteBoots ||
                    stack.getItem() == HyxcateItems.tektiteBoots) {
                if (event.getSource() == DamageSource.HOT_FLOOR) {
                    event.setCanceled(true);
                }
            }
        }

        // Don't harm other mobs of the same team
        if (trueSource instanceof EntityEyezor && trueSource != null) {
            if (entity.isOnSameTeam(trueSource)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onDamageEvent(LivingDamageEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        if (entity instanceof EntityLivingBase && trueSource instanceof EntityLivingBase) {
            Item heldItem = ((EntityLivingBase) trueSource).getHeldItemMainhand().getItem();
            IAttributeInstance paralysis = ((EntityLivingBase) trueSource).getEntityAttribute(HyxcateAttributes.PARALYSIS);

            if (paralysis != null && !paralysis.getModifiers().isEmpty()) {
                float paralysisValue = 0.0F;

                for (AttributeModifier attributemodifier : paralysis.getModifiers()) {
                    paralysisValue += (float) attributemodifier.getAmount();
                }
                // Inflicts mob with Paralysis when the attribute is successful
                if (paralysisValue > 0 && HyxcateUtils.setChance(paralysisValue)) {
                    entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_PARALYSIS_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
                    entity.addPotionEffect(new PotionEffect(HyxcatePotions.PARALYSIS, 8 * 20, 0));
                }
            }

            if (heldItem instanceof IHyxcateTool && !damageSource.damageType.equals("mob")) {
                ToolMaterial material = ((IHyxcateTool) heldItem).getToolMaterial();

                if (material == HyxcateItems.frezariteToolMaterial || material == HyxcateItems.kreknoriteToolMaterial) {
                    if (material == HyxcateItems.frezariteToolMaterial) {
                        entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_DEEP_FREEZE_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
                    } else {
                        entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_INFERNO_START.getSoundEvent(), SoundCategory.PLAYERS, 1.0F, 1.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
                    }

                    // Explosion deals AoE damage
                    for (Entity nearbyLivingEntity : entity.world.getEntitiesWithinAABBExcludingEntity(trueSource, entity.getEntityBoundingBox().grow(1.5D, 1.5D, 1.5D))) {
                        if (nearbyLivingEntity instanceof EntityLivingBase && !nearbyLivingEntity.isOnSameTeam(trueSource) && !nearbyLivingEntity.isEntityEqual(trueSource)) {
                            if (nearbyLivingEntity instanceof EntityLiving) {
                                EntityLiving entity2 = (EntityLiving) nearbyLivingEntity;

                                entity2.addPotionEffect(new PotionEffect(material == HyxcateItems.frezariteToolMaterial ? HyxcatePotions.DEEP_FREEZE : HyxcatePotions.INFERNO, 8 * 20, 0));
                            }

                            nearbyLivingEntity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase) trueSource), event.getAmount() + 4.0F);
                        }
                    }
                }
            }
        }

        if (damageSource == DamageSource.HOT_FLOOR) {
            for (ItemStack stack : entity.getArmorInventoryList()) {
                // All boots are immune to magma and other hot floor blocks
                if (stack.getItem() == HyxcateItems.meteoriteBoots ||
                        stack.getItem() == HyxcateItems.frezariteBoots ||
                        stack.getItem() == HyxcateItems.kreknoriteBoots ||
                        stack.getItem() == HyxcateItems.tektiteBoots) {
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

        if (event.getLeft().getItem() instanceof HyxcateToolBeamSword || event.getLeft().getItem() instanceof HyxcateToolCelestialWarhammer || event.getLeft().getItem() instanceof HyxcateToolTektiteGreatsword) {
            if (EnchantmentHelper.getEnchantments(event.getRight()).keySet().stream().anyMatch(e -> e == Enchantments.UNBREAKING)) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onZombieSummonAid(ZombieEvent.SummonAidEvent event) {
        if (event.getEntity() instanceof EntityEyezor) {
            event.setCustomSummonedAid(new EntityEyezor(event.getWorld()));

            if (((EntityLivingBase) event.getEntity()).getRNG().nextFloat() < ((EntityEyezor) event.getEntity()).getEntityAttribute(((EntityEyezor) event.getEntity()).getReinforcementsAttribute()).getAttributeValue()) {
                event.setResult(Event.Result.ALLOW);
            } else {
                event.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onHurtEvent(LivingHurtEvent event) {
        EntityLivingBase entity = event.getEntityLiving();
        DamageSource damageSource = event.getSource();
        Entity trueSource = damageSource.getTrueSource();

        if (damageSource == HyxcateDamageSource.CELESTIAL) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.RANDOM_STAR_AURA.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        if (damageSource == HyxcateDamageSource.DEEP_FREEZE) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_DEEP_FREEZE_START.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        if (damageSource == HyxcateDamageSource.INFERNO) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_INFERNO_START.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        if (damageSource == HyxcateDamageSource.PARALYSIS) {
            entity.world.playSound(null, entity.posX, entity.posY, entity.posZ, HyxcateSoundEvents.EFFECT_PARALYSIS_ZAP.getSoundEvent(), SoundCategory.NEUTRAL, 0.5F, 2.0F / (entity.world.rand.nextFloat() * 0.4F + 1.2F));
        }

        if (entity instanceof EntityLivingBase && trueSource instanceof EntityLivingBase) {
            long time = entity.world.getWorldTime() % 24000;
            boolean isNight = (time >= 13000 && time < 23000);
            boolean isDay = (time > 0 && time < 12000);

            float extraDamage = 0.0F;

            if (isNight) {
                extraDamage += getAttributeValue((EntityLivingBase) trueSource, HyxcateAttributes.LUNAR_DAMAGE);
            }

            if (isDay) {
                extraDamage += getAttributeValue((EntityLivingBase) trueSource, HyxcateAttributes.SOLAR_DAMAGE);
            }

            if (extraDamage <= 0) return;
            event.setAmount(event.getAmount() + extraDamage);
        }

        if (trueSource instanceof EntityPlayer) {
            Item heldItem = ((EntityPlayer) trueSource).getHeldItemMainhand().getItem();

            // Beam swords ignore armor
            if (heldItem instanceof HyxcateToolBeamSword) {
                damageSource.setDamageBypassesArmor();

                // Beam swords also ignore invincibility frames
                entity.hurtResistantTime = 0;
                entity.hurtTime = 0;
            }
        }
    }

    private static float getAttributeValue(EntityLivingBase entity, IAttribute attribute) {
        IAttributeInstance instance = entity.getEntityAttribute(attribute);

        if (instance != null) {
            return (float) instance.getAttributeValue();
        }

        return 0.0F;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEntityJoinClient(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote) return;

        if (event.getEntity() instanceof EntityFallingMeteor) {
            HyxcateUtils.playClientSoundFallingMeteor(event.getEntity());
        } else if (event.getEntity() instanceof EntityFallingStar) {
            HyxcateUtils.playClientSoundFallingStar(event.getEntity());
        }
    }
}

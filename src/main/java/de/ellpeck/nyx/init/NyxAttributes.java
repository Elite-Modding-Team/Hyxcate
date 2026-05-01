package de.ellpeck.nyx.init;

import com.google.common.collect.ImmutableMap;
import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entity.NyxEntityAlienCreeper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = Nyx.ID)
public class NyxAttributes {
    // Attribute UUIDs
    public static final UUID ARMOR_ID = UUID.fromString("936904F9-A021-413A-B19F-3096D8DBC345");
    public static final UUID ARMOR_TOUGHNESS_ID = UUID.fromString("0D08F11A-082E-402F-9D4E-285C6BFB98BC");
    public static final UUID ATTACK_DAMAGE_ID = UUID.fromString("BB872E86-462C-46F9-983F-08B17FF404BA");
    public static final UUID ATTACK_SPEED_ID = UUID.fromString("CCAAEBEB-6740-4F50-9249-3E1FFFCF3A35");
    public static final UUID EXPLOSION_RESISTANCE_ID = UUID.fromString("6613789A-775F-40DF-A09D-8FD732EDD0E7");
    public static final UUID KNOCKBACK_RESISTANCE_ID = UUID.fromString("7E042E2C-2B6A-48C3-91A4-46344C56B7DC");
    public static final UUID LUCK_ID = UUID.fromString("F6620677-4dCF-4D83-9F16-AA624695D30E");
    public static final UUID LUNAR_DAMAGE_TOOL_ID = UUID.fromString("1E212676-D247-4389-B3AE-D42DA3B84001");
    public static final UUID MAX_HEALTH_ID = UUID.fromString("35C90F6B-302F-4C21-B819-2FDFB866D45A");
    public static final UUID MAGNETIZATION_TOOL_ID = UUID.fromString("25CC8516-C975-4652-881C-C9B0F24867F6");
    public static final UUID MOVEMENT_SPEED_ID = UUID.fromString("F8DC5256-1DBD-465E-9326-CEFAE193D742");
    public static final UUID PARALYSIS_ID = UUID.fromString("1017D9CD-354A-4DE8-AB60-6522324F3C5C");
    public static final UUID REACH_DISTANCE_ID = UUID.fromString("CA7B27CC-504F-4007-BECE-806A8F512766");
    public static final UUID SOLAR_DAMAGE_TOOL_ID = UUID.fromString("50C1BDD1-B2AB-49AE-BD68-6B827394CDDA");

    public static final Map<EntityEquipmentSlot, UUID> MAGNETIZATION_ARMOR_ID = ImmutableMap.<EntityEquipmentSlot, UUID>builder()
            .put(EntityEquipmentSlot.HEAD, UUID.fromString("52B51E2C-B7D8-489F-A0BB-CBF9F6C66EDF"))
            .put(EntityEquipmentSlot.CHEST, UUID.fromString("AA5B5129-7233-4F8D-824C-023EBC078964"))
            .put(EntityEquipmentSlot.LEGS, UUID.fromString("F971808A-8CBC-45B4-B310-91AD02A0AAA4"))
            .put(EntityEquipmentSlot.FEET, UUID.fromString("D7B878C7-7D0C-489C-9004-0A472062F3C7"))
            .build();

    public static final Map<EntityEquipmentSlot, UUID> SOLAR_WARD_ARMOR_ID = ImmutableMap.<EntityEquipmentSlot, UUID>builder()
            .put(EntityEquipmentSlot.HEAD, UUID.fromString("65E16DD9-C49C-4B66-A290-85665B3A8A0B"))
            .put(EntityEquipmentSlot.CHEST, UUID.fromString("F3E07AB4-9888-4BD3-9D0A-CBCBEB0F85D5"))
            .put(EntityEquipmentSlot.LEGS, UUID.fromString("76406BEE-2535-4369-AD4F-687822FB3DBC"))
            .put(EntityEquipmentSlot.FEET, UUID.fromString("236F8EF0-97B8-478E-A2C6-2865B2E96B22"))
            .build();

    public static final Map<EntityEquipmentSlot, UUID> LUNAR_WARD_ARMOR_ID = ImmutableMap.<EntityEquipmentSlot, UUID>builder()
            .put(EntityEquipmentSlot.HEAD, UUID.fromString("65E16DD9-C49C-4B66-A290-85665B3A8A0B"))
            .put(EntityEquipmentSlot.CHEST, UUID.fromString("8D18C20F-6BBF-4A56-8CA1-FAD3786D31DE"))
            .put(EntityEquipmentSlot.LEGS, UUID.fromString("20ECD295-CB20-4BC8-AE15-CB79000D697A"))
            .put(EntityEquipmentSlot.FEET, UUID.fromString("CD51F6E2-7B7B-4F71-8276-035159E421CC"))
            .build();

    // Attributes
    public static final IAttribute EXPLOSION_RESISTANCE = new RangedAttribute(null, Nyx.ID + ".generic.explosion_resistance", 0.0D, 0.0D, 100.0D).setShouldWatch(true); // Cannot exceed past 100%
    public static final IAttribute LUNAR_DAMAGE = new RangedAttribute(null, Nyx.ID + ".generic.moon_damage", 0.0D, 0.0D, Double.MAX_VALUE).setShouldWatch(true);
    public static final IAttribute LUNAR_WARD = new RangedAttribute(null, Nyx.ID + ".generic.moon_ward", 0.0D, 0.0D, 100.0D).setShouldWatch(true); // Cannot exceed past 100%
    public static final IAttribute MAGNETIZATION = new RangedAttribute(null, Nyx.ID + ".generic.magnetization", 0.0D, 0.0D, 10.0D).setShouldWatch(true); // Cannot exceed 10 so it doesn't get too insane
    public static final IAttribute PARALYSIS = new RangedAttribute(null, Nyx.ID + ".generic.paralysis", 0.0D, 0.0D, 100.0D).setShouldWatch(true); // Cannot exceed past 100%
    public static final IAttribute SOLAR_DAMAGE = new RangedAttribute(null, Nyx.ID + ".generic.sun_damage", 0.0D, 0.0D, Double.MAX_VALUE).setShouldWatch(true);
    public static final IAttribute SOLAR_WARD = new RangedAttribute(null, Nyx.ID + ".generic.sun_ward", 0.0D, 0.0D, 100.0D).setShouldWatch(true); // Cannot exceed past 100%

    // Registers attributes to all entities
    @SubscribeEvent
    public static void onEntityConstructEvent(@Nonnull EntityEvent.EntityConstructing event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            // Global attributes go here
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            registerAttribute(entity.getAttributeMap(), EXPLOSION_RESISTANCE);
            registerAttribute(entity.getAttributeMap(), LUNAR_DAMAGE);
            registerAttribute(entity.getAttributeMap(), LUNAR_WARD);
            registerAttribute(entity.getAttributeMap(), PARALYSIS);
            registerAttribute(entity.getAttributeMap(), SOLAR_DAMAGE);
            registerAttribute(entity.getAttributeMap(), SOLAR_WARD);

            if (event.getEntity() instanceof EntityPlayer) {
                // Player attributes go here
                EntityPlayer player = (EntityPlayer) event.getEntity();
                registerAttribute(player.getAttributeMap(), MAGNETIZATION);
            }

            // Woe to those who encounter them
            if (event.getEntity() instanceof NyxEntityAlienCreeper) {
                NyxEntityAlienCreeper creeper = (NyxEntityAlienCreeper) event.getEntity();
                creeper.getEntityAttribute(EXPLOSION_RESISTANCE).applyModifier(new AttributeModifier("alien creeper explosion resistance", 1.0D, 1));
            }
        }
    }

    private static void registerAttribute(@Nonnull AbstractAttributeMap attributeMap, @Nonnull IAttribute attribute) {
        if (attributeMap.getAttributeInstance(attribute) == null) {
            attributeMap.registerAttribute(attribute);
        }
    }
}

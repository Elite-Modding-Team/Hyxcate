package de.ellpeck.nyx.init;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.entity.NyxEntityAlienCreeper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

import javax.annotation.Nonnull;

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
    public static final UUID MAX_HEALTH_ID = UUID.fromString("35C90F6B-302F-4C21-B819-2FDFB866D45A");
    public static final UUID MAGNETIZATION_ID = UUID.fromString("25CC8516-C975-4652-881C-C9B0F24867F6");
    public static final UUID MOVEMENT_SPEED_ID = UUID.fromString("F8DC5256-1DBD-465E-9326-CEFAE193D742");
    public static final UUID PARALYSIS_ID = UUID.fromString("1017D9CD-354A-4DE8-AB60-6522324F3C5C");
    public static final UUID REACH_DISTANCE_ID = UUID.fromString("CA7B27CC-504F-4007-BECE-806A8F512766");

    // Attributes
    public static final IAttribute EXPLOSION_RESISTANCE = new RangedAttribute(null, Nyx.ID + ".explosion_resistance", 0.0D, 0.0D, 100.0D).setShouldWatch(true); // Cannot exceed past 100%
    public static final IAttribute MAGNETIZATION = new RangedAttribute(null, Nyx.ID + ".magnetization", 0.0D, 0.0D, 10.0D).setShouldWatch(true); // Cannot exceed 10 so it doesn't get too insane
    public static final IAttribute PARALYSIS = new RangedAttribute(null, Nyx.ID + ".paralysis", 0.0D, 0.0D, 100.0D).setShouldWatch(true); // Cannot exceed past 100%

    // Registers attributes to all entities
    @SubscribeEvent
    public static void onEntityConstructEvent(@Nonnull EntityEvent.EntityConstructing event) {
        if (event.getEntity() instanceof EntityLivingBase) {
            // Global attributes go here
            EntityLivingBase entity = (EntityLivingBase) event.getEntity();
            registerAttribute(entity.getAttributeMap(), EXPLOSION_RESISTANCE);
            registerAttribute(entity.getAttributeMap(), PARALYSIS);

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

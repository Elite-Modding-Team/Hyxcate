package de.ellpeck.nyx.sound;

import de.ellpeck.nyx.Nyx;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public enum NyxSoundEvents {
    // Blocks
    BLOCK_DENSE_CRYSTAL_BREAK("block.dense_crystal.break"),
    BLOCK_DENSE_CRYSTAL_PLACE("block.dense_crystal.place"),
    BLOCK_LIGHT_CRYSTAL_BREAK("block.light_crystal.break"),
    BLOCK_LIGHT_CRYSTAL_HIT("block.light_crystal.hit"),
    BLOCK_LIGHT_CRYSTAL_PLACE("block.light_crystal.place"),
    BLOCK_LIGHT_CRYSTAL_STEP("block.light_crystal.step"),
    BLOCK_METEORIC_ROCK_BREAK("block.meteoric_rock.break"),
    BLOCK_METEORIC_ROCK_PLACE("block.meteoric_rock.place"),
    BLOCK_METEORIC_ROCK_STEP("block.meteoric_rock.step"),

    // Effects
    EFFECT_DEEP_FREEZE_START("effect.deep_freeze.start"),
    EFFECT_INFERNO_START("effect.inferno.start"),
    EFFECT_PARALYSIS_START("effect.paralysis.start"),
    EFFECT_PARALYSIS_ZAP("effect.paralysis.zap"),

    // Entities
    ENTITY_ALIEN_CREEPER_IDLE("entity.alien_creeper.idle"),
    ENTITY_ALIEN_KITTY_IDLE("entity.alien_kitty.idle"),
    ENTITY_COMET_KITTY_IDLE("entity.comet_kitty.idle"),
    ENTITY_METEOR_FALLING("entity.meteor.falling"),
    ENTITY_METEOR_IMPACT_FAR("entity.meteor.impact.far"),
    ENTITY_METEOR_IMPACT("entity.meteor.impact"),
    ENTITY_STAR_FALLING("entity.star.falling"),
    ENTITY_STAR_IDLE("entity.star.idle"),
    ENTITY_STAR_IMPACT("entity.star.impact"),

    // Equip
    EQUIP_CRYSTALLINE("equip.crystalline"),
    EQUIP_METALLIC("equip.metallic"),

    // Events (Lunar)
    EVENT_BLOOD_MOON_START("event.blood_moon.start"),
    EVENT_BLUE_MOON_START("event.blue_moon.start"),
    EVENT_FULL_MOON_START("event.full_moon.start"),
    EVENT_STAR_SHOWER_START("event.star_shower.start"),

    // Events (Solar)
    EVENT_GRIM_ECLIPSE_START("event.grim_eclipse.start"),
    EVENT_RED_SUN_START("event.red_giant.start"),
    EVENT_RED_SUN_START_SPECIAL("event.red_giant.start.special"),

    // Items
    ITEM_BEAM_SWORD_HIT("item.beam_sword.hit"),
    ITEM_BEAM_SWORD_IDLE("item.beam_sword.idle"),
    ITEM_BEAM_SWORD_SWING("item.beam_sword.swing"),
    ITEM_CELESTIAL_EMBLEM_CREATE("item.celestial_emblem.create"),
    ITEM_CELESTIAL_WARHAMMER_HIT("item.celestial_warhammer.hit"),
    ITEM_CELESTIAL_WARHAMMER_LAUNCH("item.celestial_warhammer.launch"),
    ITEM_CELESTIAL_WARHAMMER_SMASH("item.celestial_warhammer.smash"),
    ITEM_LASER_GUN_SHOOT("item.laser_gun.shoot"),
    ITEM_METEOR_DETECTOR_CANCEL("item.meteor_detector.cancel"),
    ITEM_METEOR_DETECTOR_CONFIRM("item.meteor_detector.confirm"),
    ITEM_METEOR_DETECTOR_PROMPT("item.meteor_detector.prompt"),

    // Random
    RANDOM_LASER("random.laser"),
    RANDOM_STAR_AURA("random.star_aura"),
    RANDOM_STAR_EXPLODE("random.star_explode");

    private final SoundEvent soundEvent;

    NyxSoundEvents(String path) {
        ResourceLocation resourceLocation = new ResourceLocation(Nyx.ID, path);
        this.soundEvent = new SoundEvent(resourceLocation);
        this.soundEvent.setRegistryName(resourceLocation);
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }
}

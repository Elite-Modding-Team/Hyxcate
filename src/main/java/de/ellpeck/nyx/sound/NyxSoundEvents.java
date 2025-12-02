package de.ellpeck.nyx.sound;

import de.ellpeck.nyx.Nyx;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public enum NyxSoundEvents {
    fallingStar("falling_star"),
    fallingStarImpact("falling_star_impact"),
    fallingMeteor("falling_meteor"),
    fallingMeteorImpact("falling_meteor_impact"),
    fallingMeteorImpactFar("falling_meteor_impact_far"),
    hammerSmash("hammer_smash"),
    celestialEmblem("celestial_emblem"),
    cometKittyIdle("comet_kitty_idle"),
    alienKittyIdle("alien_kitty_idle"),

    // Lunar Events
    bloodMoonStart("blood_moon_start"),
    harvestMoonStart("harvest_moon_start"),
    starShowerStart("star_shower_start"),

    // Solar Events
    redSunStart("red_sun_start"),
    redSunStartSpecial("red_sun_start_special"),
    solarEclipseStart("solar_eclipse_start"),

    // Blocks
    denseCrystalBreak("dense_crystal_break"),
    denseCrystalPlace("dense_crystal_place"),
    lightCrystalBreak("light_crystal_break"),
    lightCrystalHit("light_crystal_hit"),
    lightCrystalPlace("light_crystal_place"),
    lightCrystalStep("light_crystal_step"),
    meteoricRockBreak("meteoric_rock_break"),
    meteoricRockPlace("meteoric_rock_place"),
    meteoricRockStep("meteoric_rock_step"),

    // Effects
    paralyzeZap("paralyze_zap"),

    // Items
    beamSwordHit("beam_sword_hit"),
    beamSwordIdle("beam_sword_idle"),
    beamSwordSwing("beam_sword_swing"),
    equipCrystalline("equip_crystalline"),
    equipMetallic("equip_metallic"),
    frezariteHit("frezarite_hit"),
    kreknoriteHit("kreknorite_hit"),
    tektiteHit("tektite_hit"),
	hammerHit("hammer_hit"),
	hammerSpecialLaunch("hammer_special_launch"),
	hammerSpecialHit("hammer_special_hit");

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

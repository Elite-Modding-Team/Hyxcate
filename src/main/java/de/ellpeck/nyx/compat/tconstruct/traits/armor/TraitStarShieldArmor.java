package de.ellpeck.nyx.compat.tconstruct.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.sound.NyxSoundEvents;
import de.ellpeck.nyx.util.Utils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitStarShieldArmor extends AbstractArmorTrait {
    public TraitStarShieldArmor() {
        super(Nyx.ID + "." + "star_shield", 0xDFCE62);
    }

    // TODO: Some additional starry particles would be nice
    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
    	// 5% chance per piece
        if (Utils.setChance(0.05F)) {
            // Completely cancel out the damage
            event.setCanceled(true);
            player.world.playSound(null, player.getPosition(), NyxSoundEvents.fallingStarImpact.getSoundEvent(), SoundCategory.PLAYERS, 0.85F, 3.0F / (player.world.rand.nextFloat() * 0.4F + 1.2F));

            // Inflict Resistance III on the wielder (5 seconds)
            if (!player.world.isRemote) {
                if (player instanceof EntityPlayer) {
                    player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 5 * 20, 2));
                    TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ELECTRO, player, (int) damage);
                }
            }
        }

        return newDamage;
    }
}

package de.ellpeck.nyx.compat.tinkers.traits.armor;

import c4.conarm.lib.traits.AbstractArmorTrait;
import de.ellpeck.nyx.Nyx;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import slimeknights.tconstruct.common.Sounds;
import slimeknights.tconstruct.shared.client.ParticleEffect;
import slimeknights.tconstruct.tools.TinkerTools;

public class TraitSuperchargeArmor extends AbstractArmorTrait {
    public TraitSuperchargeArmor() {
        super(Nyx.ID + "." + "supercharge", 0xC82323);
    }

    @Override
    public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent event) {
        if (random.nextInt(15) == 0) {
            if (!player.world.isRemote) {
                if (player instanceof EntityPlayer) {
                    player.world.playSound(null, player.posX, player.posY, player.posZ, Sounds.shocking_discharge, SoundCategory.PLAYERS, 0.5F, 0.5F / (player.world.rand.nextFloat() * 0.4F + 1.2F));
                    player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 5 * 20, 2));
                    player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 5 * 20, 2));
                    TinkerTools.proxy.spawnEffectParticle(ParticleEffect.Type.HEART_ELECTRO, player, (int) damage);
                }
            }
        }

        return newDamage;
    }
}

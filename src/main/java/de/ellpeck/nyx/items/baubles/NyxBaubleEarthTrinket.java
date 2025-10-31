package de.ellpeck.nyx.items.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.bobmowzie.mowziesmobs.server.potion.PotionHandler;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;

import javax.annotation.Nullable;
import java.util.List;

public class NyxBaubleEarthTrinket extends Item implements IBauble {
    public NyxBaubleEarthTrinket() {
        this.setMaxStackSize(1);
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        player.addPotionEffect(new PotionEffect(PotionHandler.GEOMANCY, 2, 0, false, false));
    }

    @Override
    public void onEquipped(ItemStack stack, EntityLivingBase player) {
        player.playSound(MMSounds.EFFECT_GEOMANCY_CRASH, 0.85F, 1.0F);
    }

    @Override
    public void onUnequipped(ItemStack stack, EntityLivingBase player) {
        player.playSound(MMSounds.EFFECT_GEOMANCY_SMALL_CRASH, 3.0F, 1.0F);
    }

    @Override
    public BaubleType getBaubleType(ItemStack stack) {
        return BaubleType.TRINKET;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.EPIC;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> list, ITooltipFlag flag) {
        list.add(I18n.format("tooltip.nyx.earth_trinket.equip"));
        list.add(I18n.format(""));
        list.add(I18n.format("item.earthTalisman.text.2"));
        list.add(I18n.format("item.earthTalisman.text.3"));
        list.add(I18n.format("item.earthTalisman.text.4"));

        if (ConfigHandler.TOOLS_AND_ABILITIES.enableGeomancyTunneling) {
            list.add(I18n.format("item.earthTalisman.text.5"));
            list.add(I18n.format("item.earthTalisman.text.6"));
            list.add(I18n.format("item.earthTalisman.text.7"));
        }
    }
}

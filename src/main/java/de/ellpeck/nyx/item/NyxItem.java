package de.ellpeck.nyx.item;

import de.ellpeck.nyx.init.NyxItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class NyxItem extends Item {
    private final EnumRarity rarity;

    public NyxItem(EnumRarity rarity) {
        super();
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return rarity;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (this == NyxItems.celestialEmblem) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.celestial_emblem"));
        } else if (this == NyxItems.tektiteGemCluster) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.nyx.tektite_gem_cluster"));
        }
    }
}

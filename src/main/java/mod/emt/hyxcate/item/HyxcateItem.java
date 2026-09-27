package mod.emt.hyxcate.item;

import mod.emt.hyxcate.init.HyxcateItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class HyxcateItem extends Item {
    private final EnumRarity rarity;

    public HyxcateItem(EnumRarity rarity) {
        super();
        this.rarity = rarity;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return rarity;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if (this == HyxcateItems.celestialEmblem) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.hyxcate.celestial_emblem"));
        } else if (this == HyxcateItems.tektiteGemCluster) {
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.hyxcate.tektite_gem_cluster"));
        }
    }
}

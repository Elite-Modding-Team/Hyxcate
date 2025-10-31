package de.ellpeck.nyx.util;

import de.ellpeck.nyx.Nyx;
import de.ellpeck.nyx.config.NyxConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class NyxGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {
        return new ConfigGui(parentScreen);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    public static class ConfigGui extends GuiConfig {

        public ConfigGui(GuiScreen parentScreen) {
            super(parentScreen, getConfigElements(), Nyx.ID, false, false, getAbridgedConfigPath(NyxConfig.instance.toString()));
        }

        private static List<IConfigElement> getConfigElements() {
            return NyxConfig.instance.getCategoryNames().stream()
                    .map(e -> new ConfigElement(NyxConfig.instance.getCategory(e)))
                    .collect(Collectors.toList());
        }
    }
}
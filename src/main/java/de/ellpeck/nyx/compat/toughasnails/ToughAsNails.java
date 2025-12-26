package de.ellpeck.nyx.compat.toughasnails;

import de.ellpeck.nyx.compat.toughasnails.modifier.RedGiantModifier;
import toughasnails.api.temperature.TemperatureHelper;

public class ToughAsNails {
    public static void registerTemperatureModifiers() {
        TemperatureHelper.registerTemperatureModifier(new RedGiantModifier("nyx:red_giant"));
    }
}

package de.ellpeck.nyx.util;

import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class NyxColorUtils {

    /**
     * Adjust the brightness of a given RGB integer.
     *
     * @param rgb    The original color as an RGB integer.
     * @param factor The factor by which to adjust the brightness (e.g., 1.2 for 20% brighter, 0.8 for 20% darker).
     * @return The new color as an RGB integer.
     */
    public static int adjustBrightness(int rgb, float factor) {
        // Extract the RGB components
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Convert RGB to HSB
        float[] hsbVals = Color.RGBtoHSB(red, green, blue, null);

        // Adjust the brightness by the given factor
        hsbVals[2] = (factor > 1.0f) ? Math.min(hsbVals[2] * factor, 1.0f) : Math.max(hsbVals[2] * factor, 0.0f);

        // Convert back to RGB
        return Color.HSBtoRGB(hsbVals[0], hsbVals[1], hsbVals[2]);
    }

    /**
     * Returns an RGB integer as a Vec3d, where x = red, y = green, z = blue (values range 0.0 to 1.0).
     *
     * @param rgb The original color as an RGB integer.
     * @return The Vec3d containing the color (x = red, y = green, z = blue).
     */
    public static Vec3d getRgbIntAsVec3d(int rgb) {
        // Extract the RGB components from the integer
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Convert the RGB values to double (0.0 to 1.0 scale)
        double redDouble = red / 255.0D;
        double greenDouble = green / 255.0D;
        double blueDouble = blue / 255.0D;

        // Return a Vec3d containing the color
        return new Vec3d(redDouble, greenDouble, blueDouble);
    }

    /**
     * Returns an RGB integer as a float array, where the first element is the red component, the second element is the green component, and the third element is the blue component.
     * Values range from 0.0 to 1.0.
     *
     * @param rgb The original color as an RGB integer.
     * @return The float array containing the color (red, green, blue).
     */
    public static float[] getRgbIntAsFloatArray(int rgb) {
        // Extract the RGB components from the integer
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;

        // Convert the RGB values to float (0.0 to 1.0 scale)
        float redDouble = red / 255.0F;
        float greenDouble = green / 255.0F;
        float blueDouble = blue / 255.0F;

        // Return a float array containing the color
        return new float[]{redDouble, greenDouble, blueDouble};
    }
}

package mod.emt.hyxcate.util;

import net.minecraft.util.math.Vec3d;

import java.awt.*;

public class ColorUtil {
    public static int[][] CYBER_CRYSTAL = {
            {64, 106, 136},
            {194, 232, 255},
            {64, 106, 136},
            {194, 232, 255},
            {64, 106, 136},
            {194, 232, 255},
            {64, 106, 136},
            {194, 232, 255},
            {64, 106, 136},
            {194, 232, 255}
    };

    public static int[][] METEOR_FREZARITE = {
            {7, 77, 177},
            {26, 96, 255},
            {93, 177, 255},
            {143, 189, 255},
            {169, 204, 255}
    };

    public static int[][] METEOR_KREKNORITE = {
            {167, 2, 2},
            {212, 99, 39},
            {223, 132, 64},
            {211, 148, 59},
            {209, 193, 73}
    };

    public static int[][] METEOR_METEORITE = {
            {202, 113, 4},
            {219, 156, 23},
            {241, 219, 28},
            {243, 224, 142},
            {242, 233, 198}
    };

    public static int[][] RAINBOW(float hue) {
        int rgb = Color.HSBtoRGB(hue, 1.0F, 1.0F);
        return new int[][] {{(rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF}
        };
    }

    public static int[][] SHOOTING_STAR = {
            {255, 233, 124},
            {101, 201, 198},
            {241, 219, 28},
            {167, 247, 255},
            {251, 233, 142},
            {163, 83, 114}
    };

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

    public static int[] colorTransition(int[][] colors, float progress) {
        if (colors.length == 0) {
            return new int[]{255, 255, 255};
        }

        if (colors.length == 1) {
            return colors[0];
        }

        progress = Math.max(0.0F, Math.min(1.0F, progress));
        float scaled = progress * (colors.length - 1);
        int index = (int) Math.floor(scaled);

        if (index >= colors.length - 1) {
            return colors[colors.length - 1];
        }

        float localProgress = scaled - index;
        int[] current = colors[index];
        int[] next = colors[index + 1];

        return new int[] {
                (int) (current[0] + (next[0] - current[0]) * localProgress),
                (int) (current[1] + (next[1] - current[1]) * localProgress),
                (int) (current[2] + (next[2] - current[2]) * localProgress)
        };
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

    /**
     * Converts a float array representing RGB components to an RGB integer.
     * The float array is expected to contain red, green, and blue values in the range 0.0 to 1.0.
     *
     * @param rgb The float array containing the color (red, green, blue).
     * @return The resulting color as an RGB integer.
     */
    public static int getFloatArrayAsRgbInt(float[] rgb) {
        // Clamp each component to the valid range and convert to 0–255 integer values
        int red = Math.round(Math.max(0F, Math.min(1F, rgb[0])) * 255F);
        int green = Math.round(Math.max(0F, Math.min(1F, rgb[1])) * 255F);
        int blue = Math.round(Math.max(0F, Math.min(1F, rgb[2])) * 255F);

        // Pack the components into a single RGB integer
        return (red << 16) | (green << 8) | blue;
    }

    /**
     * Converts a Vec3d containing RGB components to a float array.
     * The vector components are expected to represent red (x), green (y), and blue (z),
     * in the range 0.0 to 1.0.
     *
     * @param vec The Vec3d containing the color (x = red, y = green, z = blue).
     * @return A float array containing the color (red, green, blue).
     */
    public static float[] getVec3dAsFloatArray(Vec3d vec) {
        return new float[]{(float) vec.x, (float) vec.y, (float) vec.z};
    }

    /**
     * Converts a float array representing RGB components to a Vec3d.
     * The array is expected to contain red, green, and blue values,
     * in the range 0.0 to 1.0.
     *
     * @param rgb The float array containing the color (red, green, blue).
     * @return A Vec3d containing the color (x = red, y = green, z = blue).
     */
    public static Vec3d getFloatArrayAsVec3d(float[] rgb) {
        return new Vec3d(rgb[0], rgb[1], rgb[2]);
    }
}

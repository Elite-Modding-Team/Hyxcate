package mod.emt.hyxcate.util;

import net.minecraft.util.math.MathHelper;

// TODO: Currently transitions only work when the event is starting and not when the event is ending due to how solar and lunar events are ordered
// Maybe start the end transition right before the event is about to end?
public class ColorTransitionUtil {
    private final float[] startColor = new float[3];
    private final float[] targetColor = new float[]{-1, -1, -1};
    private final float[] currentColor = new float[3];
    private boolean isTransitioning = false;
    private TargetType targetType = TargetType.DEFAULT_COLOR;

    public ColorTransitionUtil() {
        // Duration is intentionally handled by the caller
    }

    public void forceTransition(float[] startColor, float[] targetColor, TargetType targetType) {
        System.arraycopy(startColor, 0, this.startColor, 0, 3);
        System.arraycopy(targetColor, 0, this.targetColor, 0, 3);
        System.arraycopy(startColor, 0, this.currentColor, 0, 3);
        this.targetType = targetType;
        this.isTransitioning = true;
    }

    public float[] getCurrentColor(float progress) {
        if (!isTransitioning) {
            return currentColor;
        }

        progress = MathHelper.clamp(progress, 0F, 1F);

        // Basic lerp between startColor and targetColor
        currentColor[0] = startColor[0] + ((targetColor[0] - startColor[0]) * progress);
        currentColor[1] = startColor[1] + ((targetColor[1] - startColor[1]) * progress);
        currentColor[2] = startColor[2] + ((targetColor[2] - startColor[2]) * progress);

        if (progress >= 1F) {
            isTransitioning = false;
        }

        return currentColor;
    }

    public boolean isOverriding() {
        return isTransitioning || targetType != TargetType.DEFAULT_COLOR;
    }

    public enum TargetType {
        DEFAULT_COLOR,
        CUSTOM_COLOR
    }
}
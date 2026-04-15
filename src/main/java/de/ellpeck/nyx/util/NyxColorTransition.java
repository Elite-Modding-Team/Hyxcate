package de.ellpeck.nyx.util;

public class NyxColorTransition {

    private final float[] startColor = new float[3];
    private final float[] targetColor = new float[]{-1, -1, -1};
    private final float[] currentColor = new float[3];

    private long transitionStartTick = -1;

    private boolean isTransitioning = false;
    private TargetType targetType = TargetType.DEFAULT_COLOR;

    private final int durationTicks;

    public NyxColorTransition(int durationTicks) {
        this.durationTicks = durationTicks;
    }

    public void transition(float[] startColor, float[] targetColor, long currentTime, TargetType targetType) {

        if(targetType == TargetType.DEFAULT_COLOR && targetType == this.targetType) {
            // Allow updating target mid-transition when going back to the default color
            if(isTransitioning) {
                System.arraycopy(targetColor, 0, this.targetColor, 0, 3);
            }
            return;
        }

        // Avoid transition if target color is already the same
        if(targetColor[0] == this.targetColor[0] &&
           targetColor[1] == this.targetColor[1] &&
           targetColor[2] == this.targetColor[2]) {
            return;
        }

        if(isOverriding()) {
            startColor = currentColor;
        }

        System.arraycopy(startColor, 0, this.startColor, 0, 3);
        System.arraycopy(targetColor, 0, this.targetColor, 0, 3);
        System.arraycopy(startColor, 0, currentColor, 0, 3);

        transitionStartTick = currentTime;

        this.targetType = targetType;
        isTransitioning = true;

    }

    public void transition(float[] targetColor, long currentTime, TargetType targetType) {
        transition(currentColor, targetColor, currentTime, targetType);
    }

    public float[] getCurrentColor(long currentTime, float partialTicks) {

        if(!isTransitioning) {
            return currentColor;
        }

        float timeDifference = Math.abs(currentTime + partialTicks - transitionStartTick);
        float progress = durationTicks != -1
                ? Math.max(Math.min(timeDifference / (float) durationTicks, 1F), 0F)
                : 1F;

        // Basic lerp between startColor and targetColor
        currentColor[0] = startColor[0] + ((targetColor[0] - startColor[0]) * progress);
        currentColor[1] = startColor[1] + ((targetColor[1] - startColor[1]) * progress);
        currentColor[2] = startColor[2] + ((targetColor[2] - startColor[2]) * progress);

        if(progress == 1F) {
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

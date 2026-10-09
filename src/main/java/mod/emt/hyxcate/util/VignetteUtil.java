package mod.emt.hyxcate.util;

import java.util.Random;

public class VignetteUtil {
    private final float fadeInStep;
    private final float fadeOutStep;
    private final float maxAlpha;
    private boolean active = false;
    private boolean pulsing = false;
    private boolean flickering = false;
    private boolean fadingIn = true;
    private float alpha = 0.0F;
    private final Random random = new Random();
    private int flickerTimer = 0;

    public VignetteUtil(float fadeInSeconds, float fadeOutSeconds, float maxAlpha) {
        this.maxAlpha = maxAlpha;
        this.fadeInStep = maxAlpha / (fadeInSeconds * 20.0F);
        this.fadeOutStep = maxAlpha / (fadeOutSeconds * 20.0F);
    }

    public void setActive(boolean active) {
        this.active = active;
        if (active) {
            fadingIn = true;
        }
    }

    public void setPulsing(boolean pulsing) {
        this.pulsing = pulsing;
        if (pulsing) {
            active = true;
            fadingIn = true;
        } else {
            active = false;
        }
    }

    public void setFlickering(boolean flickering) {
        this.flickering = flickering;
        if (flickering) {
            active = true;
            flickerTimer = 0;
        } else {
            active = false;
        }
    }

    public void update() {
        if (flickering && active) {
            updateFlickering();
            return;
        }

        if (!active) {
            alpha -= fadeOutStep;
            if (alpha <= 0.0F) {
                alpha = 0.0F;
            }
            return;
        }

        if (fadingIn) {
            alpha += fadeInStep;
            if (alpha >= maxAlpha) {
                alpha = maxAlpha;
                if (pulsing) {
                    fadingIn = false;
                }
            }
        } else {
            alpha -= fadeOutStep;

            if (alpha <= 0.0F) {
                alpha = 0.0F;

                if (pulsing) {
                    fadingIn = true;
                }
            }
        }
    }

    private void updateFlickering() {
        if (flickerTimer > 0) {
            flickerTimer--;
            alpha -= fadeOutStep;
            if (alpha < 0.0F) {
                alpha = 0.0F;
            }
            return;
        }
        alpha = maxAlpha * (0.55F + random.nextFloat() * 0.45F);
        flickerTimer = 8 + random.nextInt(8);
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isVisible() {
        return alpha > 0.0F;
    }
}
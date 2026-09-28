package mod.emt.hyxcate.util;

import java.util.Random;

// Courtesy of UeberallGebannt for the chance methods
public class RandomUtil {
    public static final Random RANDOM = new Random();

    /**
     * Returns true with a certain chance
     *
     * @param chance The chance to return true
     * @param random The random instance to be used
     * @return true with a certain chance or false
     */
    public static boolean setChance(double chance, Random random) {
        double value = random.nextDouble();
        return value <= chance;
    }

    /**
     * Returns true with a certain chance
     *
     * @param chance The chance to return true
     * @return true with a certain chance or false
     */
    public static boolean setChance(double chance) {
        return setChance(chance, RANDOM);
    }
}

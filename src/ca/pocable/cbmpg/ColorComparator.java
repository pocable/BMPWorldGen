package ca.pocable.cbmpg;

import java.awt.*;
import java.util.Comparator;

/**
 * Comparator class for Colors required for the TreeMap.
 * @see BiomeColorizer
 */
public class ColorComparator implements Comparator<Color> {
    @Override
    public int compare(Color o1, Color o2) {
        return o1.getRGB() - o2.getRGB();
    }
}

package ca.pocable.cbmpg;

import java.awt.Color;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class to determine given a color or biome give the respective biome or color back.
 */
public class BiomeColorizer {

    // Since multiple colors can be for the same Biome, it doesn't make sense to let you get the Color given Biome.
    TreeMap<Color, Biome> colorBiomeHashMap = new TreeMap<>(new ColorComparator());

    public BiomeColorizer(){ }

    /**
     * Fills both colorBiomeHashMap and biomeColorHashMap with the default minecraft biomes.
     */
    public void generateGreyscaleDefaultBiomes(){
        int maxLength = Biome.values().length;
        for(Biome bio : Biome.values()) {
            int val = bio.ordinal();
            java.awt.Color newC = new java.awt.Color(val * (255 / maxLength), val * (255 / maxLength), val * (255 / maxLength));
            addBiomeColor(bio, newC);
        }
    }

    /**
     * Add a biome and color to be recognized as the same
     * @param b The biome.
     * @param c The color that represents the biome.
     */
    public void addBiomeColor(Biome b, Color c){
        colorBiomeHashMap.put(c, b);
    }

    /**
     * Removes a biome color combination.
     * @param b The biome to remove.
     * @param c The color to remove.
     */
    public void removeBiomeColor(Biome b, Color c){
        colorBiomeHashMap.remove(c);
    }

    /**
     * Get the Biome given the color provided. This is equivalent to a Map get.
     * @param c The color.
     * @return The Biome representing the color.
     */
    public Biome getBiomeGivenColor(Color c){
        return colorBiomeHashMap.get(c);
    }

    /***
     * Gets the biome closest to the given Color.
     * @param c The color of the pixel we are trying to get a biome for.
     * @return The closest Biome to that color.
     */
    public Biome getNearestBiome(Color c){

        // Check if there is an exact match
        Biome directCheck = colorBiomeHashMap.get(c);
        if(directCheck != null){
            return directCheck;
        }

        // Get the nearest through a TreeMap.
        Map.Entry<Color, Biome> low = colorBiomeHashMap.floorEntry(c);
        Map.Entry<Color, Biome> high = colorBiomeHashMap.ceilingEntry(c);
        if (low != null && high != null){
            boolean isLowCloser =  Math.abs(low.getKey().getRGB() - c.getRGB()) <
                    Math.abs(high.getKey().getRGB() - c.getRGB());
            return isLowCloser ? low.getValue() : high.getValue();
        }else if (low != null || high != null){
            return low != null ? low.getValue() : high.getValue();
        }else{
            Bukkit.getLogger().severe("Color " + c + " did not have any values for high or low. Placing THE_VOID.");
            return Biome.THE_VOID;
        }
    }

}

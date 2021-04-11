package ca.pocable.cbmpg;

import nl.rutgerkok.worldgeneratorapi.BiomeGenerator;
import org.bukkit.Bukkit;
import org.bukkit.block.Biome;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class BMPBiomeGen implements BiomeGenerator {
	
	Biome[][] data;
	HashMap<Color, Biome> colorToBiome;
	Biome defaultBiome;
	boolean isTiled;

	public BMPBiomeGen(String file, Biome defaultBiome){
		this(file, defaultBiome, false);
	}

	public BMPBiomeGen(String bmpFilePath, Biome defaultBiome, boolean isTiled) {
		super();

		this.defaultBiome = defaultBiome;
		this.isTiled = isTiled;
		colorToBiome = new HashMap<>();

		// Given each existing Biome, generate a unique grey color for it.
		int maxLength = Biome.values().length;
		for(Biome bio : Biome.values()) {
			int val = bio.ordinal();
			Color newC = new Color(val * (255 / maxLength), val * (255 / maxLength), val * (255 / maxLength));
			colorToBiome.put(newC, bio);
		}

		// Read the file and try to get the Biome index number from it.
		File f = new File(bmpFilePath);
		if(!f.exists()){
			Bukkit.getLogger().severe(String.format("BMP file %s is not found, the generator will not work.",
					bmpFilePath));
		}else {
			try {
				BufferedImage image = ImageIO.read(f);
				data = new Biome[image.getWidth()][image.getHeight()];
				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						int color = image.getRGB(x, y);
						Color toRound = new Color(color);
						data[x][y] = rgbToBiome(new Color(closestInteger(toRound.getRed(), 3),
								closestInteger(toRound.getGreen(), 3), closestInteger(toRound.getBlue(), 3)));
					}
				}
			} catch (IOException e) {
				Bukkit.getLogger().severe("Error reading the BMP file to convert to Biomes.\n" +
						e.getMessage() + "\n");
			}
		}
		
	}

	/**
	 * Given a, calculates the closest b value.
	 * @param a The initial value.
	 * @param b The value we want to be a multiple of.
	 * @return An int that is a multiple of b closest to a.
	 */
	private static int closestInteger(int a, int b) {
		return ((a / b)) * b;
	}
	
	/**
	 * Convert color to Biome.
	 * @param c The color.
	 * @return The biome
	 */
	public Biome rgbToBiome(Color c) {
		Biome b = colorToBiome.get(c);
		if(b == null) {
			return Biome.THE_VOID;
		}
		return b;
	}

	@Override
	public Biome getZoomedOutBiome(int x, int y, int z) {
		//x /= 4;
		//z /= 4; # Its by a factor of 4 as required by minecraft.

		// If its tiled, just mod to fit in our image.
		if(isTiled) {
			x = x % (data.length / 2);
			z = z % (data[0].length / 2);
		}

		// Check if the coordinates are within our wanted bounds.
		if(x < (data.length/2) && z < (data[0].length/2) && x > (-data.length/2) && z > (-data[0].length/2)) {
			return data[x + data.length/2][z + data[0].length/2];
		}

		// If its not, return the default biome specified in the config.
		return defaultBiome;
	}
}

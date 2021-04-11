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

/**
 * Biome generator given an image file.
 * @see BiomeColorizer
 */
public class BMPBiomeGen implements BiomeGenerator {
	
	Biome[][] data;
	Biome defaultBiome;
	boolean isTiled;
	BiomeColorizer colorizer;

	public BMPBiomeGen(String file, Biome defaultBiome, BiomeColorizer colorizer){
		this(file, defaultBiome, colorizer,false);
	}

	public BMPBiomeGen(String bmpFilePath, Biome defaultBiome, BiomeColorizer colorizer, boolean isTiled) {
		super();

		this.defaultBiome = defaultBiome;
		this.isTiled = isTiled;
		this.colorizer = colorizer;

		// Read the file and try to get the Biome index number from it.
		File f = new File(bmpFilePath);
		if(!f.exists()){
			Bukkit.getLogger().severe(String.format("BMP file %s is not found, the generator will not work.",
					bmpFilePath));
			Bukkit.getLogger().severe("Please re-verify the file is in the right spot." +
					" The server is going to crash.");
			Bukkit.getServer().shutdown();
		}else {
			try {
				BufferedImage image = ImageIO.read(f);
				data = new Biome[image.getWidth()][image.getHeight()];
				for (int x = 0; x < image.getWidth(); x++) {
					for (int y = 0; y < image.getHeight(); y++) {
						int color = image.getRGB(x, y);
						Color toRound = new Color(color);
						data[x][y] = colorizer.getNearestBiome(new Color(toRound.getRed(),
								toRound.getGreen(), toRound.getBlue()));
					}
				}
			} catch (IOException e) {
				Bukkit.getLogger().severe("Error reading the BMP file to convert to Biomes.\n" +
						e.getMessage());
				Bukkit.getLogger().severe("To prevent a crash, the server will shutdown.");
				Bukkit.getServer().shutdown();
			}
		}
		
	}

	@Override
	public Biome getZoomedOutBiome(int x, int y, int z) {
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

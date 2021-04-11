package ca.pocable.cbmpg;

import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import nl.rutgerkok.worldgeneratorapi.event.WorldGeneratorInitEvent;
import java.util.Set;

public class BMPWorldGen extends JavaPlugin implements Listener{

	ConfigurationSection BIOME_CONFIG;
	Set<String> CONTROLLED_WORLDS;
	BiomeColorizer colorizer;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		BIOME_CONFIG = getConfig().getConfigurationSection("generator_worlds");
		colorizer = new BiomeColorizer();
		colorizer.generateGreyscaleDefaultBiomes();

		if (BIOME_CONFIG != null) {
			CONTROLLED_WORLDS = BIOME_CONFIG.getKeys(false);
		}else{
			getLogger().severe("Invalid configuration. Could not find keys for generator_worlds.");
			this.setEnabled(false);
		}

		getServer().getPluginManager().registerEvents(this, this);
		
	}

	@EventHandler
	public void onWorldGeneratorInit(WorldGeneratorInitEvent e) {
		if(CONTROLLED_WORLDS.contains(e.getWorld().getName())) {
			String defaultBiomePath = getConfig().getString("generator_worlds." +
					e.getWorld().getName() + ".default_biome");

			String bmpPath = getConfig().getString("generator_worlds." +
					e.getWorld().getName() + ".image_file_name");

			boolean isTiled = getConfig().getBoolean("generator_worlds." +
					e.getWorld().getName() + ".tiled_world");

			getLogger().info(String.format("Found that world %s has a custom generator BMP at %s. Default biome is %s. World tiled: %b",
					e.getWorld().getName(), bmpPath, defaultBiomePath, isTiled));

			Biome defaultBiome = Biome.valueOf(defaultBiomePath);

			e.getWorldGenerator().setBiomeGenerator(new BMPBiomeGen(bmpPath, defaultBiome, colorizer, isTiled));
		}
	}
}

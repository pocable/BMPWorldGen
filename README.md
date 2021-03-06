# BMP Biome Gen
***This plugin requires [WorldGeneratorAPI](https://www.spigotmc.org/resources/worldgeneratorapi.77976/)***

Bukkit plugin to set biomes in your world 
while maintaining the natural generation! When
freshly generating a new world this plugin will
let you define biomes exactly to a 4x4 block radius.

NOTICE: If you remove the plugin from the server
the world generation will return to normal, but 
there will be obvious chunk walls. To get around this
you could try putting a MOUNTAIN biome there.

## Installation
Unzip the release file and place the folder and jar in your plugin
directory. Add the [WorldGeneratorAPI](https://www.spigotmc.org/resources/worldgeneratorapi.77976/)
jar into your plugins folder. Modify the config file and place your 
BMP file near the server jar. Make sure it's named what you put for the image_file_name.

To use the new generation, delete the world folders for worlds you chose. 
This plugin only works with a fresh world.

## Configuration
The config.yml should be setup like below:
```
generator_worlds:
    World Name:
        default_biome: The filler biome for every other section
        image_file_name: The BMP image file next to your server jar file. This can be a path as well.
        tiled_world: (true/false), if you want this image to be tiled across the world set it to true
    example_world:
        default_biome: OCEAN
        image_file_name: WorldGen.bmp
        tiled_world: false    
```
The world name should match the folder its stored in.
The plugin will log to console when it is loaded, if something is off
check there first.

Make sure the default biome is all caps. For exact
names, [check the color code page](COLORCODES.md).

## BMP Image Information and Color Coding
[Check out this page for the color coding.](COLORCODES.md)
Try to make it exact as possible, or it will
round and may not be what you expected.

The image size determines how much of the map will have controlled generation.
Since each pixel represents a 4x4 area, if you wanted to control generation
for a 1000x1000 block area you would want an image sized 250x250. Remember
that if you have tiling turned off it will fill everything
outside this 1000x1000 block area with the default biome.

## Examples
![First BMP Image](BMPOne.bmp?raw=true)
![First Gen Image](GenOne.png?raw=true)
Limitation in Minecraft means that one pixel
on the image represents around a 4x4 area. This
image uses THE_VOID, DESERT and PLAINS.



![Second BMP Image](BMPTwo.bmp?raw=true)
![Second Gen Image](GenTwo.png?raw=true)
More complex example, a MOUNTAINS ring with
PLAINS in the middle with NETHER_WASTES south
and a BAMBOO_JUNGLE north with a RIVER in the
middle.
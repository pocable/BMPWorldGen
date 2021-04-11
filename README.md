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

## BMP Image Color Coding
[Check out this page for the color coding.](COLORCODES.md)
Try to make it exact as possible, or it will
round and may not be what you expected.

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
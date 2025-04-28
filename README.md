A lightweight Minecraft plugin designed for SuperiorSkyblock2 servers, allowing automatic configuration of island upgrades
specifically setting the maximum blocks (like hoppers) based on upgrade levels.

How It Works
In SuperiorSkyblock2, sometimes when players upgrade their island (for example, buying a Hopper Limit upgrade),
the upgrade values can randomly glitch or not properly apply causing islands to have the wrong block limits.

This plugin fixes that problem by automatically setting the correct block limits using:

island.setBlockLimit(Key.of(Material.<material name>), <value>);

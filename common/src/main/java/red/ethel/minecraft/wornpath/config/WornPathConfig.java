package red.ethel.minecraft.wornpath.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Configuration data class for Worn Path mod settings.
 */
public class WornPathConfig {
    public int stepChance = 4;
    public int maxSteps = 3;
    public int maxSpreadDepth = 2;
    public int sheepProtectionRadius = 2;
    public Map<String, String> transitions = defaultTransitions();
    public List<String> overheadPassableTags = defaultOverheadPassableTags();
    public Set<String> underlyingProtectionBlocks = defaultUnderlyingProtectionBlocks();

    private static List<String> defaultOverheadPassableTags() {
        return new ArrayList<>(List.of("minecraft:replaceable", "minecraft:flowers"));
    }

    private static Set<String> defaultUnderlyingProtectionBlocks() {
        return new LinkedHashSet<>(List.of(
            "minecraft:white_wool", "minecraft:orange_wool", "minecraft:magenta_wool",
            "minecraft:light_blue_wool", "minecraft:yellow_wool", "minecraft:lime_wool",
            "minecraft:pink_wool", "minecraft:gray_wool", "minecraft:light_gray_wool",
            "minecraft:cyan_wool", "minecraft:purple_wool", "minecraft:blue_wool",
            "minecraft:brown_wool", "minecraft:green_wool", "minecraft:red_wool",
            "minecraft:black_wool"
        ));
    }

    private static Map<String, String> defaultTransitions() {
        var map = new LinkedHashMap<String, String>();
        map.put("minecraft:grass_block", "minecraft:dirt_path");
        map.put("minecraft:dirt", "minecraft:dirt_path");
        map.put("minecraft:dirt_path", "minecraft:packed_mud");
        map.put("minecraft:coarse_dirt", "minecraft:packed_mud");
        map.put("minecraft:packed_mud", "minecraft:mud_bricks");
        return map;
    }
}

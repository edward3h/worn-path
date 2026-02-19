package red.ethel.minecraft.wornpath.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    private static List<String> defaultOverheadPassableTags() {
        return new ArrayList<>(List.of("minecraft:replaceable", "minecraft:flowers"));
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

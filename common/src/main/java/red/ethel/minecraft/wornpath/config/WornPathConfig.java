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
    public List<String> transitions = new ArrayList<>(List.of(
            "minecraft:grass_block->minecraft:dirt_path",
            "minecraft:dirt->minecraft:dirt_path",
            "minecraft:dirt_path->minecraft:packed_mud",
            "minecraft:coarse_dirt->minecraft:packed_mud",
            "minecraft:packed_mud->minecraft:mud_bricks"
    ));

    /**
     * Converts the transitions list to a map for lookup.
     *
     * @return Map of source block ID to target block ID
     */
    public Map<String, String> getTransitionsMap() {
        Map<String, String> map = new LinkedHashMap<>();
        for (String entry : transitions) {
            String[] parts = entry.split("->");
            if (parts.length == 2) {
                map.put(parts[0].trim(), parts[1].trim());
            }
        }
        return map;
    }

    /**
     * Creates a copy of this config.
     *
     * @return A new WornPathConfig with the same values
     */
    public WornPathConfig copy() {
        WornPathConfig copy = new WornPathConfig();
        copy.stepChance = this.stepChance;
        copy.maxSteps = this.maxSteps;
        copy.maxSpreadDepth = this.maxSpreadDepth;
        copy.transitions = new ArrayList<>(this.transitions);
        return copy;
    }
}

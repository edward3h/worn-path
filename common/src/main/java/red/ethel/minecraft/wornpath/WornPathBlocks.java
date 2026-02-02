package red.ethel.minecraft.wornpath;

import java.util.Map;

public class WornPathBlocks {
    public static final int STEP_RANDOMNESS = 3;
    public static final int MAX_STEPS = 3;
    public static final Map<String, String> TRANSITIONS = Map.of(
            "minecraft:grass_block", "minecraft:dirt_path",
            "minecraft:dirt", "minecraft:dirt_path",
            "minecraft:dirt_path", "minecraft:packed_mud",
            "minecraft:coarse_dirt", "minecraft:packed_mud",
            "minecraft:packed_mud", "minecraft:mud_bricks"
    );
}


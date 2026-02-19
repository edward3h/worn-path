package red.ethel.minecraft.wornpath.config;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonArray;
import blue.endless.jankson.JsonObject;
import blue.endless.jankson.JsonPrimitive;
import blue.endless.jankson.api.SyntaxError;
import red.ethel.minecraft.wornpath.WornPathMod;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages loading and saving of the Worn Path configuration.
 */
public class WornPathConfigManager {
    private static final Jankson JANKSON = Jankson.builder().build();
    private static final String CONFIG_FILE_NAME = "worn_path.json5";

    private static Path configPath;
    private static WornPathConfig config;

    private WornPathConfigManager() {
    }

    /**
     * Initialises the config manager with the platform's config directory.
     *
     * @param configDir The platform-specific config directory
     */
    public static void init(Path configDir) {
        configPath = configDir.resolve(CONFIG_FILE_NAME);
        load();
    }

    /**
     * Loads the configuration from disk, creating defaults if the file doesn't exist.
     */
    public static void load() {
        if (configPath == null) {
            WornPathMod.LOGGER.warn("Config path not initialised, using defaults");
            config = new WornPathConfig();
            return;
        }

        if (Files.exists(configPath)) {
            try {
                JsonObject json = JANKSON.load(configPath.toFile());
                config = fromJson(json);
                WornPathMod.LOGGER.info("Loaded config from {}", configPath);
            } catch (IOException | SyntaxError e) {
                WornPathMod.LOGGER.error("Failed to load config from {}", configPath, e);
                config = new WornPathConfig();
            }
        } else {
            config = new WornPathConfig();
            save();
            WornPathMod.LOGGER.info("Created default config at {}", configPath);
        }
    }

    /**
     * Saves the current configuration to disk.
     */
    public static void save() {
        if (configPath == null) {
            WornPathMod.LOGGER.warn("Config path not initialised, cannot save");
            return;
        }

        try {
            JsonObject json = toJson(config);
            Files.writeString(configPath, json.toJson(true, true));
            WornPathMod.LOGGER.info("Saved config to {}", configPath);
        } catch (IOException e) {
            WornPathMod.LOGGER.error("Failed to save config to {}", configPath, e);
        }
    }

    private static WornPathConfig fromJson(JsonObject json) {
        WornPathConfig cfg = new WornPathConfig();

        if (json.containsKey("stepChance")) {
            cfg.stepChance = json.getInt("stepChance", cfg.stepChance);
        }
        if (json.containsKey("maxSteps")) {
            cfg.maxSteps = json.getInt("maxSteps", cfg.maxSteps);
        }
        if (json.containsKey("maxSpreadDepth")) {
            cfg.maxSpreadDepth = json.getInt("maxSpreadDepth", cfg.maxSpreadDepth);
        }
        if (json.containsKey("sheepProtectionRadius")) {
            cfg.sheepProtectionRadius = json.getInt("sheepProtectionRadius", cfg.sheepProtectionRadius);
        }
        if (json.containsKey("transitions")) {
            JsonObject obj = (JsonObject) json.get("transitions");
            if (obj != null) {
                Map<String, String> map = new LinkedHashMap<>();
                for (var key : obj.keySet()) {
                    if (obj.get(key) instanceof JsonPrimitive primitive) {
                        map.put(key, primitive.asString());
                    }
                }
                cfg.transitions = map;
            }
        }
        if (json.containsKey("overheadPassableTags")) {
            if (json.get("overheadPassableTags") instanceof JsonArray arr) {
                List<String> tags = new ArrayList<>();
                for (var element : arr) {
                    if (element instanceof JsonPrimitive primitive) {
                        tags.add(primitive.asString());
                    }
                }
                cfg.overheadPassableTags = tags;
            }
        }

        return cfg;
    }

    private static JsonObject toJson(WornPathConfig cfg) {
        JsonObject json = new JsonObject();
        json.put("stepChance", JsonPrimitive.of((long) cfg.stepChance));
        json.setComment("stepChance", "1 in N chance per step (e.g. 4 = 25% chance, 1 = always)");
        json.put("maxSteps", JsonPrimitive.of((long) cfg.maxSteps));
        json.setComment("maxSteps", "Steps needed to trigger block transition");
        json.put("maxSpreadDepth", JsonPrimitive.of((long) cfg.maxSpreadDepth));
        json.setComment("maxSpreadDepth", "How far transitions spread to neighbours (0 = no spread)");
        json.put("sheepProtectionRadius", JsonPrimitive.of((long) cfg.sheepProtectionRadius));
        json.setComment("sheepProtectionRadius", "Radius in blocks around a block within which sheep prevent conversion (0 = disabled)");

        JsonObject transitions = new JsonObject();
        for (var entry : cfg.transitions.entrySet()) {
            transitions.put(entry.getKey(), JsonPrimitive.of(entry.getValue()));
        }
        json.put("transitions", transitions);
        json.setComment("transitions", "Block transitions: source block ID -> target block ID");

        JsonArray tagsArray = new JsonArray();
        for (String tag : cfg.overheadPassableTags) {
            tagsArray.add(JsonPrimitive.of(tag));
        }
        json.put("overheadPassableTags", tagsArray);
        json.setComment("overheadPassableTags", "Block tags treated as passable overhead — conversion is still allowed when a matching block is above");

        return json;
    }

    /**
     * Gets the current configuration instance.
     *
     * @return The current config
     */
    public static WornPathConfig getConfig() {
        if (config == null) {
            config = new WornPathConfig();
        }
        return config;
    }

    /**
     * Sets and saves a new configuration.
     *
     * @param newConfig The new config to use
     */
    public static void setConfig(WornPathConfig newConfig) {
        config = newConfig;
        save();
    }

    // Convenience accessors

    public static int getStepChance() {
        return getConfig().stepChance;
    }

    public static int getMaxSteps() {
        return getConfig().maxSteps;
    }

    public static int getMaxSpreadDepth() {
        return getConfig().maxSpreadDepth;
    }

    public static int getSheepProtectionRadius() {
        return getConfig().sheepProtectionRadius;
    }

    public static Map<String, String> getTransitions() {
        return getConfig().transitions;
    }

    public static List<String> getOverheadPassableTags() {
        return getConfig().overheadPassableTags;
    }
}

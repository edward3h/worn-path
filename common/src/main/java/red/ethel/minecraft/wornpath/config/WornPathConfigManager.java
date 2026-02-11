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
        if (json.containsKey("transitions")) {
            JsonArray arr = (JsonArray) json.get("transitions");
            if (arr != null) {
                cfg.transitions.clear();
                for (var element : arr) {
                    if (element instanceof JsonPrimitive primitive) {
                        cfg.transitions.add(primitive.asString());
                    }
                }
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

        JsonArray transitions = new JsonArray();
        for (String transition : cfg.transitions) {
            transitions.add(JsonPrimitive.of(transition));
        }
        json.put("transitions", transitions);
        json.setComment("transitions", "Block progression chain as 'source->target' entries");

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

    public static Map<String, String> getTransitions() {
        return getConfig().getTransitionsMap();
    }
}

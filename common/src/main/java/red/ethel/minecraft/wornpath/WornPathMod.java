package red.ethel.minecraft.wornpath;

import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.IdentityHashMap;
import java.util.Map;

public final class WornPathMod {
    public static final String MOD_ID = "worn_path";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static final Map<Level, StepHandler> handlers = new IdentityHashMap<>();

    public static void init() {
        LOGGER.debug("WornPathMod initialising");
        // Platform-specific code (Fabric/NeoForge) must call onLevelUnload() when a
        // server level is unloaded so that the handler map does not leak levels.
    }

    public static void onLevelUnload(Level level) {
        handlers.remove(level);
    }

    public static StepHandler getHandler(Level level) {
        return handlers.computeIfAbsent(level, StepHandler::new);
    }
}

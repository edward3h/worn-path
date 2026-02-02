package red.ethel.minecraft.wornpath;

import dev.architectury.event.events.common.LifecycleEvent;
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
        LifecycleEvent.SERVER_LEVEL_UNLOAD.register(handlers::remove);
    }

    public static StepHandler getHandler(Level level) {
        return handlers.computeIfAbsent(level, StepHandler::new);
    }
}

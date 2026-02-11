package red.ethel.minecraft.wornpath.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import red.ethel.minecraft.wornpath.WornPathMod;
import red.ethel.minecraft.wornpath.config.WornPathConfigManager;

public final class WornPathModFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Initialise config before common setup
        WornPathConfigManager.init(FabricLoader.getInstance().getConfigDir());

        // Run our common setup.
        WornPathMod.init();
    }
}

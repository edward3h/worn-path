package red.ethel.minecraft.wornpath.neoforge;

import net.minecraft.server.level.ServerLevel;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;
import red.ethel.minecraft.wornpath.WornPathMod;
import red.ethel.minecraft.wornpath.config.WornPathConfigManager;
import red.ethel.minecraft.wornpath.config.WornPathConfigScreen;

@Mod(WornPathMod.MOD_ID)
public final class WornPathModNeoForge {
    public WornPathModNeoForge() {
        // Initialise config before common setup
        WornPathConfigManager.init(FMLPaths.CONFIGDIR.get());

        // Run our common setup.
        WornPathMod.init();

        // Register config screen factory
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (modContainer, parent) -> WornPathConfigScreen.create(parent));

        // Clean up handler cache when a server level unloads
        NeoForge.EVENT_BUS.addListener((LevelEvent.Unload event) -> {
            if (event.getLevel() instanceof ServerLevel level) {
                WornPathMod.onLevelUnload(level);
            }
        });
    }
}

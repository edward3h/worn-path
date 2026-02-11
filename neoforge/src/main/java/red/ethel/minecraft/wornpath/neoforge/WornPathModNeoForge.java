package red.ethel.minecraft.wornpath.neoforge;

import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
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
    }
}

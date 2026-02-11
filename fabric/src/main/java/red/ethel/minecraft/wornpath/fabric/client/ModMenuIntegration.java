package red.ethel.minecraft.wornpath.fabric.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import red.ethel.minecraft.wornpath.config.WornPathConfigScreen;

/**
 * Mod Menu integration for accessing the config screen.
 */
public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return WornPathConfigScreen::create;
    }
}

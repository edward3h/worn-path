package red.ethel.minecraft.wornpath.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * YACL-based configuration screen for Worn Path settings.
 */
public class WornPathConfigScreen {

    private WornPathConfigScreen() {
    }

    /**
     * Creates the configuration screen.
     *
     * @param parent The parent screen to return to
     * @return The config screen
     */
    public static Screen create(Screen parent) {
        WornPathConfig config = WornPathConfigManager.getConfig();
        WornPathConfig defaults = new WornPathConfig();

        return YetAnotherConfigLib.createBuilder()
                .title(Component.translatable("config.worn_path.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Component.translatable("config.worn_path.category.gameplay"))
                        .tooltip(Component.translatable("config.worn_path.category.gameplay.tooltip"))
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.worn_path.option.step_chance"))
                                .description(OptionDescription.of(
                                        Component.translatable("config.worn_path.option.step_chance.description")))
                                .binding(
                                        defaults.stepChance,
                                        () -> config.stepChance,
                                        value -> config.stepChance = value)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(1, 20)
                                        .step(1))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.worn_path.option.max_steps"))
                                .description(OptionDescription.of(
                                        Component.translatable("config.worn_path.option.max_steps.description")))
                                .binding(
                                        defaults.maxSteps,
                                        () -> config.maxSteps,
                                        value -> config.maxSteps = value)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(1, 20)
                                        .step(1))
                                .build())
                        .option(Option.<Integer>createBuilder()
                                .name(Component.translatable("config.worn_path.option.max_spread_depth"))
                                .description(OptionDescription.of(
                                        Component.translatable("config.worn_path.option.max_spread_depth.description")))
                                .binding(
                                        defaults.maxSpreadDepth,
                                        () -> config.maxSpreadDepth,
                                        value -> config.maxSpreadDepth = value)
                                .controller(opt -> IntegerSliderControllerBuilder.create(opt)
                                        .range(0, 10)
                                        .step(1))
                                .build())
                        .build())
                .save(WornPathConfigManager::save)
                .build()
                .generateScreen(parent);
    }
}

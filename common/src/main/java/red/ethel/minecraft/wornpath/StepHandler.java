package red.ethel.minecraft.wornpath;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static red.ethel.minecraft.wornpath.WornPathBlocks.TRANSITIONS;

public class StepHandler {

    record BlockKey(String blockId, BlockPos blockPos){}
    private final Level level;
    private final LoadingCache<@NotNull BlockKey, AtomicInteger> stepCounts;
    private final Map<Player, BlockPos> lastPos = new ConcurrentHashMap<>();

    public StepHandler(Level level) {
        this.level = level;
        stepCounts = Caffeine.newBuilder()
                .maximumSize(1000)
                .evictionListener((k, v, cause) -> WornPathMod.LOGGER.info("evict {} {}", k, v))
                .build(k -> new AtomicInteger());
        WornPathMod.LOGGER.info("new StepHandler {} {}", level, level.getRandom());
    }

    public void handle(Player playerEntity, BlockPos pos, BlockState state) {
        if (Objects.equals(pos, lastPos.put(playerEntity, pos))) {
            return;
        }
        int randomValue = level.getRandom().nextIntBetweenInclusive(0, WornPathBlocks.STEP_RANDOMNESS);
        if (randomValue > 0) {
            return;
        }
        var blockId = state.getBlockHolder().getRegisteredName();
        if (!TRANSITIONS.containsKey(blockId)) {
            return;
        }
        int stepCount = inc(blockId, pos);
        if (stepCount >= WornPathBlocks.MAX_STEPS) {
            var nextId = TRANSITIONS.get(blockId);
                        BlockState newState = BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(nextId)).defaultBlockState();
                        WornPathMod.LOGGER.info("Block {} new {}", blockId, newState);
                        level.setBlockAndUpdate(pos, newState);
        }
    }

    private int inc(String blockId, BlockPos pos) {
        return stepCounts.get(new BlockKey(blockId, pos)).incrementAndGet();
    }
}

package red.ethel.minecraft.wornpath;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import red.ethel.minecraft.wornpath.config.WornPathConfigManager;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StepHandler {

    record BlockKey(String blockId, BlockPos blockPos){}
    private final Level level;
    private final LoadingCache<@NotNull BlockKey, AtomicInteger> stepCounts;
    private final Map<Player, BlockPos> lastPos = new ConcurrentHashMap<>();

    public StepHandler(Level level) {
        this.level = level;
        stepCounts = Caffeine.newBuilder()
                .maximumSize(1000)
                .evictionListener((k, v, cause) -> WornPathMod.LOGGER.debug("evict {} {}", k, v))
                .build(k -> new AtomicInteger());
        WornPathMod.LOGGER.debug("new StepHandler {} {}", level, level.getRandom());
    }

    public void handle(Player playerEntity, BlockPos pos, BlockState state) {
        if (Objects.equals(pos, lastPos.put(playerEntity, pos))) {
            return;
        }
        int stepChance = WornPathConfigManager.getStepChance();
        if (stepChance > 1 && level.getRandom().nextInt(stepChance) != 0) {
            return;
        }
        tryTransition(pos, state, playerEntity, 0);
    }

    private void tryTransition(BlockPos pos, BlockState state, Player playerEntity, int depth) {
        var blockId = state.getBlockHolder().getRegisteredName();
        Map<String, String> transitions = WornPathConfigManager.getTransitions();
        if (!transitions.containsKey(blockId)) {
            return;
        }
        int stepCount = inc(blockId, pos);
        if (stepCount >= WornPathConfigManager.getMaxSteps()) {
            BlockState aboveState = level.getBlockState(pos.above());
            if (!aboveState.isAir() && !isPassableOverhead(aboveState)) {
                return;
            }
            int sheepRadius = WornPathConfigManager.getSheepProtectionRadius();
            if (sheepRadius > 0 && !level.getEntitiesOfClass(Sheep.class, new AABB(pos).inflate(sheepRadius)).isEmpty()) {
                return;
            }
            var nextId = transitions.get(blockId);
            BlockState newState = BuiltInRegistries.BLOCK.getValue(Identifier.parse(nextId)).defaultBlockState();
            WornPathMod.LOGGER.info("Transitioning {} to {} at depth {}", blockId, newState, depth);

            if (depth == 0) {
                double oldHeight = state.getCollisionShape(level, pos).max(net.minecraft.core.Direction.Axis.Y);
                double newHeight = newState.getCollisionShape(level, pos).max(net.minecraft.core.Direction.Axis.Y);
                level.setBlockAndUpdate(pos, newState);

                if (newHeight > oldHeight) {
                    playerEntity.teleportTo(playerEntity.getX(), playerEntity.getY() + (newHeight - oldHeight), playerEntity.getZ());
                }
            } else {
                level.setBlockAndUpdate(pos, newState);
            }

            if (depth < WornPathConfigManager.getMaxSpreadDepth()) {
                for (BlockPos neighbourPos : new BlockPos[]{pos.north(), pos.south(), pos.east(), pos.west()}) {
                    tryTransition(neighbourPos, level.getBlockState(neighbourPos), playerEntity, depth + 1);
                }
            }
        }
    }

    private boolean isPassableOverhead(BlockState state) {
        for (String tagId : WornPathConfigManager.getOverheadPassableTags()) {
            TagKey<Block> tag = TagKey.create(Registries.BLOCK, Identifier.parse(tagId));
            if (state.is(tag)) {
                return true;
            }
        }
        return false;
    }

    private int inc(String blockId, BlockPos pos) {
        return Objects.requireNonNull(stepCounts.get(new BlockKey(blockId, pos))).incrementAndGet();
    }
}

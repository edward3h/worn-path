package red.ethel.minecraft.wornpath.mixin;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.ethel.minecraft.wornpath.WornPathMod;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mixin({Block.class})
public class BlockMixin {

    @Unique
    private final Map<ServerPlayer, BlockPos> lastPos = new ConcurrentHashMap<>();

    @Inject(at = @At("HEAD"), method = "stepOn")
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (entity instanceof Player playerEntity && level instanceof ServerLevel serverLevel) {
            var handler = WornPathMod.getHandler(serverLevel);
            handler.handle(playerEntity, pos, state);
        }
    }
}

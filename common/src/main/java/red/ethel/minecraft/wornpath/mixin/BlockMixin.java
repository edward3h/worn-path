package red.ethel.minecraft.wornpath.mixin;


import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import red.ethel.minecraft.wornpath.WornPathMod;

@Mixin({Block.class})
public class BlockMixin {

    @Inject(at = @At("HEAD"), method = "stepOn")
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity, CallbackInfo ci) {
        if (entity instanceof Player playerEntity && level instanceof ServerLevel serverLevel) {
            var handler = WornPathMod.getHandler(serverLevel);
            handler.handle(playerEntity, pos, state);
        }
    }
}

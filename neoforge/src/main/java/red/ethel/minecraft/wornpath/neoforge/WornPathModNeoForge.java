package red.ethel.minecraft.wornpath.neoforge;

import net.neoforged.fml.common.Mod;

import red.ethel.minecraft.wornpath.WornPathMod;

@Mod(WornPathMod.MOD_ID)
public final class WornPathModNeoForge {
    public WornPathModNeoForge() {
        // Run our common setup.
        WornPathMod.init();
    }
}

# Worn Path Minecraft Mod

Multi-platform Minecraft mod for Fabric and NeoForge (native multi-loader, no Architectury).
Where players walk, paths may appear.

## Multi-Version Branch Strategy

Each Minecraft version has its own branch:

| Branch | Minecraft version |
|---|---|
| `main` | latest supported MC version (26.1.x) |
| `mc/1.21.11` | MC 1.21.11 |
| `mc/1.21.1` | MC 1.21.1 |

Bug fixes and features are developed on one branch and cherry-picked to others. Each branch has its own `gradle.properties` with the correct dependency versions. Published artefacts include the MC version in the version string (e.g. `1.5.3+1.21.11`).

`mod_version` must be kept in sync across all branches. When a fix is cherry-picked and published, bump `mod_version` to the same value on every branch before moving on.

To port a commit to another MC version:
```
git cherry-pick <commit-hash>
# fix any MC API incompatibilities
git push
```

## Build Commands

- `./gradlew build` — Build all platform variants
- `./gradlew clean` — Clean build artefacts

Java version required depends on the branch (see `.tool-versions`).

## Project Structure

Multi-platform Architectury project with three subprojects:

- `common/` — Shared code across all platforms
- `fabric/` — Fabric-specific implementation
- `neoforge/` — NeoForge-specific implementation

Platform-agnostic logic goes in `common`. Each platform subproject has a thin entry point that delegates to common code.

## Code Conventions

- **Package**: `red.ethel.minecraft.wornpath`
  - Platform-specific: `.fabric`, `.neoforge`
  - Mixins: `.mixin`
- **Mod ID**: `worn_path`
- **Java**: 25 (on main/26.1.x), 21 (on mc/1.21.x branches)
- No automated code style enforcement currently configured

## Key Dependencies (main / 26.1.x)

| Dependency | Version |
|---|---|
| Minecraft | 26.1.2 |
| Fabric Loader | 0.19.2+ |
| Fabric API | 0.148.2+26.1.2 |
| NeoForge | 26.1.2.55-beta |
| Fabric Loom | 1.16.2 |
| ModDevGradle | 2.0.141 |
| Caffeine (caching) | 3.2.4 |

Note: MC 26.1.x is the first unobfuscated Minecraft release — no Mojang mappings or remapping step needed. Use `net.fabricmc.fabric-loom` (not `fabric-loom-remap`) for Fabric builds.

## Mixins

Config: `common/src/main/resources/worn_path.mixins.json`
Package: `red.ethel.minecraft.wornpath.mixin`
Compatibility level: JAVA_21 (update to JAVA_25 for 26.1.x builds)

# Worn Path Minecraft Mod

Architectury-based multi-platform Minecraft mod for Fabric and NeoForge.
Where players walk, paths may appear.

## Multi-Version Branch Strategy

Each Minecraft version has its own branch:

| Branch | Minecraft version |
|---|---|
| `main` | latest supported MC version |
| `mc/1.21.11` | MC 1.21.11 |

Bug fixes and features are developed on one branch and cherry-picked to others. Each branch has its own `gradle.properties` with the correct dependency versions. Published artefacts include the MC version in the version string (e.g. `1.5.1+1.21.11`).

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
- **Java**: 25, standard conventions
- No automated code style enforcement currently configured

## Key Dependencies (MC 26.1.2 / `main` branch)

| Dependency | Version |
|---|---|
| Minecraft | 26.1.2 |
| Fabric Loader | 0.18.4+ |
| Fabric API | 0.146.0+26.1.2 |
| NeoForge | 26.1.2.10-beta |
| Architectury API | pending — not yet released for MC 26.1 |
| Caffeine (caching) | 3.2.3 |

> **Note**: MC 26.1 removed obfuscation — Parchment mappings are no longer needed. Official Mojang mappings now include parameter names.

## MC 26.1 API Changes

Notable changes that may require code fixes when porting:

- `ItemStack` can no longer be created until a world is loaded — use `ItemStackTemplate` instead
- `GuiGraphics` renamed to `GuiGraphicsExtractor`; `Screen#render` became `Screen#extractRenderState`
- `ColorProviderRegistry.BLOCK` replaced with `BlockColorRegistry`

## Mixins

Config: `common/src/main/resources/worn_path.mixins.json`
Package: `red.ethel.minecraft.wornpath.mixin`
Compatibility level: JAVA_25

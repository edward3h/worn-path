# Worn Path Minecraft Mod

Architectury-based multi-platform Minecraft mod for Fabric and NeoForge (MC 1.21.10).
Where players walk, paths may appear.

## Build Commands

- `./gradlew build` — Build all platform variants
- `./gradlew clean` — Clean build artefacts

Java 21 required (managed via asdf, see `.tool-versions`).

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
- **Java**: 21, standard conventions
- No automated code style enforcement currently configured

## Key Dependencies

| Dependency | Version |
|---|---|
| Minecraft | 1.21.10 |
| Fabric Loader | 0.18.4+ |
| Fabric API | 0.138.4+1.21.10 |
| NeoForge | 21.10.34-beta |
| Architectury API | 18.0.6+ |
| Caffeine (caching) | 3.2.0 |
| Parchment mappings | 2025.10.12 |

## Mixins

Config: `common/src/main/resources/worn_path.mixins.json`
Package: `red.ethel.minecraft.wornpath.mixin`
Compatibility level: JAVA_21

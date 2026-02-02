# Worn Path

An Architectury-based Minecraft mod for Fabric and NeoForge (MC 1.21.10).
Where players walk, paths may appear.

## For Players

### What Does It Do?

Worn Path gradually transforms blocks beneath frequently walked areas into
path blocks. The more players traverse a route, the more defined the path
becomes.

### Installation

1. Install [Fabric Loader](https://fabricmc.net/) (0.18.4+) or
   [NeoForge](https://neoforged.net/) (21.10+) for Minecraft 1.21.10.
2. Install the [Architectury API](https://modrinth.com/mod/architectury-api)
   for your platform.
3. Drop the appropriate Worn Path `.jar` into your `mods/` folder.

### Compatibility

| Platform | Minecraft | Loader |
|---|---|---|
| Fabric | 1.21.10 | Fabric Loader 0.18.4+ |
| NeoForge | 1.21.10 | NeoForge 21.10+ |

Both platforms require the Architectury API (18.0.6+).

## For Developers

### Building

Requires Java 21 (managed via [asdf](https://asdf-vm.com/), see `.tool-versions`).

```sh
./gradlew build
```

Build artefacts are produced for both Fabric and NeoForge platforms.

### Project Structure

This is a multi-platform [Architectury](https://docs.architectury.dev/) project:

- `common/` — Shared code across all platforms
- `fabric/` — Fabric-specific implementation
- `neoforge/` — NeoForge-specific implementation

Platform-agnostic logic goes in `common/`. Each platform subproject has a thin
entry point that delegates to common code.

### Key Dependencies

| Dependency | Version |
|---|---|
| Minecraft | 1.21.10 |
| Fabric Loader | 0.18.4+ |
| Fabric API | 0.138.4+1.21.10 |
| NeoForge | 21.10.34-beta |
| Architectury API | 18.0.6+ |
| Caffeine (caching) | 3.2.0 |

### Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on building, submitting
issues, and code conventions.

## Licence

This project is licensed under the [MIT Licence](LICENSE).

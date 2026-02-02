# Contributing to Worn Path

## Building the Project

Requires Java 21. Then run:

```sh
./gradlew build
```

## Submitting Issues

Please use [GitHub Issues](https://github.com/edward3h/worn-path/issues) to report bugs or request features.

## Pull Requests

1. Fork the repository and create a feature branch.
2. Make your changes, ensuring the project builds cleanly (`./gradlew build`).
3. Open a pull request against `main`.

## Code Conventions

- **Package**: `red.ethel.minecraft.wornpath`
  - Platform-specific: `.fabric`, `.neoforge`
  - Mixins: `.mixin`
- **Mod ID**: `worn_path`
- **Java**: 21, standard conventions
- Platform-agnostic logic goes in `common/`. Each platform subproject has a thin entry point that delegates to common code.

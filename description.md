A server side mod. Tracks where players walk, and if they walk over the same block enough times, it will be converted to a different block.

With the default settings, grass or dirt get converted to dirt path, dirt path or coarse dirt to packed mud, packed mud to mud bricks.

Conversion is prevented when:

- a solid block is directly overhead (plants and flowers are allowed)
- sheep are grazing nearby (configurable radius, 2 blocks by default)
- a protecting block is placed directly underneath — defaults to all 16 wool colours, configurable

## Configuration

Settings can be changed by editing `config/worn_path.json5`.
This file is generated after the mod has been initialized for the first time.
Comments in the file explain the settings.

### YACL

If YACL is available, it can be used in the client to configure some of the settings for a single player world.

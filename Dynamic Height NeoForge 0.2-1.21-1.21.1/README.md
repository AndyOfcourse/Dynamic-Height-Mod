## NeoForge 1.21.10 port
- Uses `net.neoforged.gradle.userdev` (Java 21); tweak `neo_version`/`neo_gradle_version` in `gradle.properties` if you want a different loader build.
- Refresh Gradle in your IDE after import; run tasks from the Gradle tool window (e.g. `runClient`, `runServer`) once the userdev plugin finishes downloading.
- Mod metadata now lives in `src/main/resources/META-INF/neoforge.mods.toml`; mixins stay in `dynamicheight.mixins.json`.
- The original Fabric project is untouched in `../Dynamic Height Fabric 0.1-1.21.10` for reference.

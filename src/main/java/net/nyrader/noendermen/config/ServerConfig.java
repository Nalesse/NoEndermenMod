package net.nyrader.noendermen.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig
{
    public static final ServerConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Boolean> BLOCK_ENDERMEN_SPAWNS;
    public final ModConfigSpec.ConfigValue<Boolean> ENABLE_BLOCK_LIGHTING;
    public final ModConfigSpec.ConfigValue<Integer> BLOCK_LIGHT_LEVEL;
    public final ModConfigSpec.ConfigValue<Boolean> ENABLE_TELEPORT;
    public final ModConfigSpec.DoubleValue TELEPORT_CHANCE;
    public final ModConfigSpec.ConfigValue<Integer> TELEPORT_RADIUS;
    public final ModConfigSpec.ConfigValue<Integer> TELEPORT_HEIGHT;
    public final ModConfigSpec.ConfigValue<Integer> MAX_TELEPORT_ATTEMPTS;
    public final ModConfigSpec.ConfigValue<Boolean> PLAY_TELEPORT_SOUND;

    private ServerConfig(ModConfigSpec.Builder builder)
    {
        BLOCK_ENDERMEN_SPAWNS = builder
                .comment("When enabled prevents endermen from spawning")
                .translation("noendermen.config.block_endermen_spawns")
                .define("block_endermen_spawns", true);

        ENABLE_BLOCK_LIGHTING = builder
                .comment("When enabled fossilised ender gives off light")
                .translation("noendermen.config.enable_block_lighting")
                .define("enable_block_lighting", true);

        BLOCK_LIGHT_LEVEL = builder
                .comment("Sets the brightness of fossilised ender")
                .translation("noendermen.config.block_light_level")
                .defineInRange("block_light_level", 9, 0, 15);

        ENABLE_TELEPORT = builder
                .comment("When enabled fossilised ender has a random chance to teleport when mined")
                .translation("noendermen.config.enable_teleport")
                .define("enable_teleport", true);

        TELEPORT_CHANCE = builder
                .comment("The chance that a fossilized ender block will teleport when mined")
                .translation("noendermen.config.random_teleport_chance")
                .defineInRange("random_teleport_chance", 0.3, 0, 1);

        TELEPORT_RADIUS = builder
                .comment("The maximum number of blocks fossilized ender can teleport on the X and Z axis")
                .translation("noendermen.config.random_teleport_radius")
                .define("random_teleport_radius", 8);

        TELEPORT_HEIGHT = builder
                .comment("The maximum number of blocks that fossilized ender can teleport on the Y axis")
                .translation("noendermen.config.random_teleport_height")
                .define("random_teleport_height", 2);

        MAX_TELEPORT_ATTEMPTS = builder
                .comment("The maximum number of times to try finding a valid teleport position")
                .translation("noendermen.config.max_teleport_attempts")
                .define("max_teleport_attempts", 16);

        PLAY_TELEPORT_SOUND = builder
                .comment("When enabled plays the enderman teleport SFX when the block teleports")
                .translation("noendermen.config.play_teleport_sound")
                .define("play_teleport_sound", true);

    }

    static
    {
        Pair<ServerConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(ServerConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}

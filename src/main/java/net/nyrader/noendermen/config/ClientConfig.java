package net.nyrader.noendermen.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;
import java.util.List;


public class ClientConfig
{

    public static final ClientConfig CONFIG;
    public static final ModConfigSpec CONFIG_SPEC;

    public final ModConfigSpec.ConfigValue<Boolean> ENABLE_BLOCK_PARTICLES;
    public final ModConfigSpec.ConfigValue<Double> PARTICLE_MAX_DISTANCE;
    public final ModConfigSpec.ConfigValue<Double> PARTICLE_SCALE;
    public final ModConfigSpec.ConfigValue<List<? extends Double>> PARTICLE_COLOR;

    private ClientConfig(ModConfigSpec.Builder builder)
    {
        ENABLE_BLOCK_PARTICLES = builder
                .comment("Enables block particles on ore blocks when the player gets close")
                .translation("noendermen.config.enable_block_particles")
                .define("enable_block_particles", true);

        PARTICLE_MAX_DISTANCE = builder
                .comment("How far away a player needs to be from a block for the particles to turn off")
                .translation("noendermen.config.particle_max_distance")
                .define("particle_max_distance", 16d);

        PARTICLE_SCALE = builder
                .comment("Controls how large the block particles are")
                .translation("noendermen.config.particle_scale")
                .define("particle_scale", 1d);

        PARTICLE_COLOR = builder
                .comment("Sets the particle color as RGB with each value being 0-1")
                .comment("It is recommended to use a light color since Minecraft's particle system will randomly darken each spawned particle")
                .translation("noendermen.config.particle_color")
                .defineList(
                        "particle_color",
                        List.of(0.4, 1.0, 0.75),
                        () -> 1.0,
                        o -> o instanceof Double d && d >= 0 && d <= 1
                );
    }

    static
    {
        Pair<ClientConfig, ModConfigSpec> pair =
                new ModConfigSpec.Builder().configure(ClientConfig::new);

        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }
}
package net.nyrader.noendermen.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.nyrader.noendermen.NoEndermen;

import java.util.List;

@EventBusSubscriber(modid = NoEndermen.MODID)
public class ClientConfig
{
    private static final ModConfigSpec.Builder BUILDER =
            new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue ENABLE_BLOCK_PARTICLES =
            BUILDER
                    .comment("Enables block particles on ore blocks when the player gets close.")
                    .define("enableBlockParticles", true);

    private static final ModConfigSpec.ConfigValue<Double> PARTICLE_MAX_DISTANCE =
            BUILDER
                    .comment("How far away a player needs to be from a block for the particles to turn off.")
                    .define("particleMaxDistance", 16d);

    private static final ModConfigSpec.ConfigValue<Float> PARTICLE_SCALE =
            BUILDER
                    .comment("Controls how large the block particles are.")
                    .define("particleScale", 1f);

    private static final ModConfigSpec.ConfigValue<List<? extends Double>> PARTICLE_COLOR =
            BUILDER
                    .comment("Sets the particle color as RGB with each value being 0-1.")
                    .comment("It is recommended to use a light color since Minecraft's particle system will randomly darken each spawned particle.")
                    .defineList(
                            "particleColor",
                            List.of(0.4, 1.0, 0.75),
                            () -> 1.0,
                            o -> o instanceof Double d && d >= 0 && d <= 1
                    );

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean enableBlockParticles;
    public static double particleMaxDistance;
    public static float particleScale;
    public static List<? extends Double> particleColorList;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        if (event.getConfig().getSpec() != SPEC)
            return;

        enableBlockParticles = ENABLE_BLOCK_PARTICLES.get();
        particleMaxDistance = PARTICLE_MAX_DISTANCE.get();
        particleScale = PARTICLE_SCALE.get();
        particleColorList = PARTICLE_COLOR.get();
    }
}
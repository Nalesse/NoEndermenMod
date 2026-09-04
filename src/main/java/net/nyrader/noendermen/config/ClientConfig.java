package net.nyrader.noendermen.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.joml.Vector3f;

import java.util.List;

@Mod.EventBusSubscriber(modid = net.nyrader.noendermen.NoEndermen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue ENABLE_BLOCK_PARTICLES = BUILDER
            .comment("Enables block particles on ore blocks when the player gets close.")
            .define("enableBlockParticles", true);

    private static final ForgeConfigSpec.ConfigValue<Double> PARTICLE_MAX_DISTANCE = BUILDER
            .comment("How far away a player needs to be from a block for the particles to turn off.")
            .define("particleMaxDistance", 16d);

    private static final ForgeConfigSpec.ConfigValue<Float> PARTICLE_SCALE = BUILDER
            .comment("Controls how large the block particles are.")
            .define("particleScale", 1f);

    private static final ForgeConfigSpec.ConfigValue<List<? extends Double>> PARTICLE_COLOR = BUILDER
            .comment("Sets the particle color as RGB with each value being 0-1.")
            .comment("It is recommended to use a light color since Minecraft's particle system will randomly darken each spawned particle.")
            .defineList("particleColor",
                    List.of(0.4, 1.0, 0.75),
                    o -> o instanceof Double d && d >= 0 && d <= 1);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

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

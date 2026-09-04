package net.nyrader.noendermen.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = net.nyrader.noendermen.NoEndermen.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerConfig
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue BLOCK_ENDERMEN_SPAWNS = BUILDER
            .comment("When enabled prevents endermen from spawning")
            .define("preventEndermenSpawns", true);

    private static final ForgeConfigSpec.BooleanValue ENABLE_BLOCK_LIGHTING = BUILDER
            .comment("When enabled fossilised ender gives off light. Only in the overworld")
            .define("enableBlockLighting", true);

    private static final ForgeConfigSpec.ConfigValue<Integer> BLOCK_LIGHT_LEVEL = BUILDER
            .comment("Sets the brightness of fossilised ender")
            .define("blockLightLevel", 9);

    private static final ForgeConfigSpec.BooleanValue ENABLE_TELEPORT = BUILDER
            .comment("When enabled fossilised ender has a random chance to teleport when mined.")
            .define("enableTeleport", true);

    private static final ForgeConfigSpec.DoubleValue TELEPORT_CHANCE = BUILDER
            .comment("The chance that a fossilized ender block will teleport when mined")
            .defineInRange("randomTeleportChance", 0.3f, 0, 1);

    private static final ForgeConfigSpec.ConfigValue<Integer> TELEPORT_RADIUS = BUILDER
            .comment("The maximum number of blocks fossilized ender can teleport on the X and Z axis")
            .define("randomTeleportRadius", 8);

    private static final ForgeConfigSpec.ConfigValue<Integer> TELEPORT_HEIGHT = BUILDER
            .comment("The maximum number of blocks that fossilized ender can teleport on the y axis")
            .define("randomTeleportHeight", 2);

    private static final ForgeConfigSpec.ConfigValue<Integer> MAX_TELEPORT_ATTEMPTS = BUILDER
            .comment("The maximum number of times to try finding a valid teleport position")
            .define("maxTeleportAttempts", 16);

    private static final ForgeConfigSpec.BooleanValue PLAY_TELEPORT_SOUND = BUILDER
            .comment("When enabled plays the enderman teleport SFX when the block teleports")
            .define("playTeleportSound", true);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean blockEndermanSpawns;
    public static boolean enableBlockLighting;
    public static Integer blockLightLevel;
    public static boolean enableTeleport;
    public static Double teleportChance;
    public static Integer teleportRadius;
    public static Integer teleportHeight;
    public static Integer maxTeleportAttempts;
    public static boolean playTeleportSound;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        if (event.getConfig().getSpec() != SPEC)
            return;

        blockEndermanSpawns = BLOCK_ENDERMEN_SPAWNS.get();
        enableBlockLighting = ENABLE_BLOCK_LIGHTING.get();
        blockLightLevel = BLOCK_LIGHT_LEVEL.get();
        enableTeleport = ENABLE_TELEPORT.get();
        teleportChance = TELEPORT_CHANCE.get();
        teleportRadius = TELEPORT_RADIUS.get();
        teleportHeight = TELEPORT_HEIGHT.get();
        maxTeleportAttempts = MAX_TELEPORT_ATTEMPTS.get();
        playTeleportSound = PLAY_TELEPORT_SOUND.get();
    }


}

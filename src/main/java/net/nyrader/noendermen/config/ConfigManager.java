package net.nyrader.noendermen.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.ArrayList;
import java.util.List;
public final class ConfigManager
{
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;


   public static final ForgeConfigSpec.ConfigValue<List<? extends String>> dimension_whitelist;

   public static final ForgeConfigSpec.ConfigValue<Boolean> generate_ores;

   public static final ForgeConfigSpec.ConfigValue<Integer> overworld_veins_per_chunk;
   public static final ForgeConfigSpec.ConfigValue<Integer> nether_veins_per_chunk;

   public static final ForgeConfigSpec.ConfigValue<Integer> overworld_max_vein_size;
   public static final ForgeConfigSpec.ConfigValue<Integer> nether_max_vein_size;

   public static final ForgeConfigSpec.ConfigValue<Integer> overworld_min_ylevel;
   public static final ForgeConfigSpec.ConfigValue<Integer> nether_min_ylevel;

   public static final ForgeConfigSpec.ConfigValue<Integer> overworld_max_ylevel;
   public static final ForgeConfigSpec.ConfigValue<Integer> nether_max_ylevel;




    static
    {
        BUILDER.comment("Ore Generation settings for the NoEndermen Mod");
        BUILDER.push("oregeneration");

        generate_ores = BUILDER
                .comment("Enables or disables ore generation")
                .define("generate_ores", true);

        ArrayList<String> defaultWhitelist = new ArrayList<>();
        defaultWhitelist.add("minecraft:overworld");
        defaultWhitelist.add("minecraft:the_nether");

        dimension_whitelist = BUILDER
                .comment("Only Works for Overworld and Nether")
                .defineList("dimension_whitelist", defaultWhitelist, obj -> obj instanceof String);

        BUILDER.push("overworld");

        overworld_veins_per_chunk = BUILDER
                        .comment("How rare the ore is in a chunk")
                        .define("overworld_veins_per_chunk", 3);

        overworld_max_vein_size = BUILDER
                .comment("The maximum vein size ores can spawn in. Values of less then 3 seem to cause ores not to spawn")
                .define("overworld_max_vein_size", 3);

        overworld_max_ylevel = BUILDER
                .comment("The maximum y level the ore can spawn at")
                .define("overworld_max_ylevel", 16);

        overworld_min_ylevel = BUILDER
                .comment("The minimum y level the ore can spawn at")
                .define("overworld_min_ylevel", 10);

        BUILDER.pop();

        BUILDER.push("nether");

        nether_veins_per_chunk = BUILDER
                .comment("How rare the ore is in a chunk")
                .define("nether_veins_per_chunk", 10);

        nether_max_vein_size = BUILDER
                .comment("The maximum vein size ores can spawn in. Values of less then 3 seem to cause ores not to spawn")
                .define("nether_max_vein_size", 3);

        nether_max_ylevel = BUILDER
                .comment("The maximum y level the ore can spawn at")
                .define("nether_max_ylevel", 117);

        nether_min_ylevel = BUILDER
                .comment("The minimum y level the ore can spawn at")
                .define("nether_min_ylevel", 10);

        BUILDER.pop();

        BUILDER.pop();
        SPEC = BUILDER.build();

    }
}

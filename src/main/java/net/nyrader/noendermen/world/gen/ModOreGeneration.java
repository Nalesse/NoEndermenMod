package net.nyrader.noendermen.world.gen;


import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.Dimension;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.nyrader.noendermen.config.ConfigManager;

import java.util.Objects;

public class ModOreGeneration
{

    public static void generateOres(final BiomeLoadingEvent event)
    {
        if (!ConfigManager.generate_ores.get()) return;


        if (ConfigManager.dimension_whitelist.get().contains(Dimension.OVERWORLD.getLocation().toString()))
        {
            OreType enderStone = OreType.ENDERSTONE;

            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    addOreToDimention(enderStone, Dimension.OVERWORLD.getLocation().toString()));
        }
        if (ConfigManager.dimension_whitelist.get().contains(Dimension.THE_NETHER.getLocation().toString()))
        {
            OreType enderStone = OreType.ENDERRACK;

            event.getGeneration().withFeature(GenerationStage.Decoration.UNDERGROUND_ORES,
                    addOreToDimention(enderStone, Dimension.THE_NETHER.getLocation().toString()));
        }
    }

    private static ConfiguredFeature<?, ?> registerOreFeature(OreType ore, OreFeatureConfig oreFeatureConfig,
                                                              ConfiguredPlacement configuredPlacement)
    {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, Objects.requireNonNull(ore.getBlock().get().getRegistryName()),
                Feature.ORE.withConfiguration(oreFeatureConfig).withPlacement(configuredPlacement).variableCount(ore.getMaxVeinSize()).count(ore.getVeinsPerChunk()));
    }

    private static ConfiguredFeature<?, ?> addOreToDimention(OreType ore, String dimensionName)
    {
        OreFeatureConfig oreFeatureConfig = null;

        if (dimensionName.equals(Dimension.OVERWORLD.getLocation().toString()))
        {
            oreFeatureConfig = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
                    ore.getBlock().get().getDefaultState(), ore.getMaxVeinSize());
        }
        else if (dimensionName.equals(Dimension.THE_NETHER.getLocation().toString()))
        {
            oreFeatureConfig = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK,
                    ore.getBlock().get().getDefaultState(), ore.getMaxVeinSize());
        }
        else if (dimensionName.equals(Dimension.THE_END.getLocation().toString()))
        {
            oreFeatureConfig = new OreFeatureConfig(new BlockMatchRuleTest(Blocks.END_STONE),
                    ore.getBlock().get().getDefaultState(), ore.getMaxVeinSize());
        }

        ConfiguredPlacement<TopSolidRangeConfig> configuredPlacement = Placement.RANGE.configure(
                new TopSolidRangeConfig(ore.getMinHeight(), ore.getMinHeight(), ore.getMaxHeight()));

        return registerOreFeature(ore, oreFeatureConfig, configuredPlacement);
    }
}

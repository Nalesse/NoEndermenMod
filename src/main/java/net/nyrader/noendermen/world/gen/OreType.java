package net.nyrader.noendermen.world.gen;

import net.minecraft.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.nyrader.noendermen.block.ModBlocks;
import net.nyrader.noendermen.config.ConfigManager;

public enum OreType
{
    ENDERSTONE(Lazy.of(ModBlocks.ENDERSTONE), ConfigManager.overworld_max_vein_size.get(),
            ConfigManager.overworld_min_ylevel.get(), ConfigManager.overworld_max_ylevel.get(), ConfigManager.overworld_veins_per_chunk.get()),
    ENDERRACK(Lazy.of(ModBlocks.ENDERRACK), ConfigManager.nether_max_vein_size.get(),
            ConfigManager.nether_min_ylevel.get(), ConfigManager.nether_max_ylevel.get(), ConfigManager.nether_veins_per_chunk.get())

    ;



    private final Lazy<Block> block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;
    private final int veinsPerChunk;

    OreType(Lazy<Block> block, int maxVeinSize, int minHeight, int maxHeight, int veinsPerChunk) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.veinsPerChunk = veinsPerChunk;
    }

    public Lazy<Block> getBlock() {
        return block;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getVeinsPerChunk() {
        return veinsPerChunk;
    }

    public static OreType get(Block block)
    {
        for (OreType ore : values())
        {
            if (block == ore.block) return ore;
        }

        return null;
    }
}

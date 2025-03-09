package net.nyrader.noendermen.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.nyrader.noendermen.NoEndermen;
import net.nyrader.noendermen.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, NoEndermen.MOD_ID);

    public static final RegistryObject<Block> ENDERSTONE = registryBlock("enderstone",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(1.5f
                            ,6f)
                    .sound(SoundType.STONE)));

    public static final RegistryObject<Block> ENDERRACK = registryBlock("enderrack",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK)
                    .harvestLevel(2).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(0.4f)
                    .sound(SoundType.NETHERRACK)));

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

    private static <T extends Block>RegistryObject<T> registryBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().group(ItemGroup.MATERIALS)));
    }
}
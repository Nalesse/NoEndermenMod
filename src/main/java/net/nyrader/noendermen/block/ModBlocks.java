package net.nyrader.noendermen.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.nyrader.noendermen.NoEndermen;
import net.nyrader.noendermen.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NoEndermen.MODID);

    public static final DeferredBlock<Block> FOSSILIZED_ENDER = registerBlock("fossilizedender", () ->
            new FossilizedEnderBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)
                    .requiresCorrectToolForDrops()));

    public static final DeferredBlock<Block> NETHER_FOSSILIZED_ENDER = registerBlock("netherfossilizedender", () ->
            new FossilizedEnderBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK)
                    .strength(3, 3)
                    .requiresCorrectToolForDrops()));


    public static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> blockSupplier)
    {
        DeferredBlock<T> block = BLOCKS.register(name, blockSupplier);

        ModItems.ITEMS.register(name,
                () -> new BlockItem(
                        block.get(),
                        new Item.Properties()));

        return block;
    }
}

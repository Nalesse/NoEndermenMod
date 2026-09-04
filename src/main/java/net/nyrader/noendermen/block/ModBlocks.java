package net.nyrader.noendermen.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.nyrader.noendermen.NoEndermen;
import net.nyrader.noendermen.config.ClientConfig;
import net.nyrader.noendermen.item.ModItems;
import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(
            ForgeRegistries.BLOCKS,
            NoEndermen.MOD_ID);

    public static final RegistryObject<Block> FossilizedEnder = registerBlock("fossilizedender",
            () -> new FossilizedEnderBlock(BlockBehaviour.Properties
            .copy(Blocks.DEEPSLATE)));

    public static final RegistryObject<Block> NetherFossilizedEnder = registerBlock("netherfossilizedender",
            () -> new FossilizedEnderBlock(BlockBehaviour.Properties
            .copy(Blocks.NETHERRACK)
            .strength(3,3)));

    public static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = ModBlocks.BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

}

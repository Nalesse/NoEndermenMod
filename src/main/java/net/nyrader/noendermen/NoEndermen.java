package net.nyrader.noendermen;

import com.example.examplemod.Config;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.nyrader.noendermen.block.ModBlocks;
import net.nyrader.noendermen.config.ClientConfig;
import net.nyrader.noendermen.config.ServerConfig;
import net.nyrader.noendermen.item.ModItems;

@Mod(NoEndermen.MOD_ID)
public class NoEndermen
{
    public static final String MOD_ID = "noendermen";


    public NoEndermen(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);

        modEventBus.addListener(this::addCreative);

        context.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        context.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
        {
            ModItems.ITEMS.getEntries().forEach(item ->
            {
                event.accept(item.get());
            });
        }
    }

    @Mod.EventBusSubscriber(modid = NoEndermen.MOD_ID)
    public static class EndermenSpawnEventHandler
    {
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void OnEntityJoinLevel(EntityJoinLevelEvent event)
        {
            if (!ServerConfig.blockEndermanSpawns) { return; }

            if (!event.getLevel().isClientSide && event.getEntity() instanceof EnderMan)
            {
                event.setCanceled(true);
            }
        }
    }
}
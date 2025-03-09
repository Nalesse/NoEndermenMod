package net.nyrader.noendermen;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.nyrader.noendermen.block.ModBlocks;
import net.nyrader.noendermen.config.ConfigManager;
import net.nyrader.noendermen.item.ModItems;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NoEndermen.MOD_ID)
public class NoEndermen
{

    public static final String MOD_ID = "noendermen";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public NoEndermen() {
        // Register the setup method for modloading
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);

        eventBus.addListener(this::setup);
        // Register the enqueueIMC method for modloading
        eventBus.addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        eventBus.addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        eventBus.addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register Config File
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigManager.SPEC, "noendermen-common.toml");
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {

    }

    private void processIMC(final InterModProcessEvent event)
    {

    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            //LOGGER.info("HELLO from Register Block");
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onBiomeLoading(BiomeLoadingEvent event){
        event.getSpawns().getSpawner(EntityClassification.MONSTER).removeIf(entry -> entry.type == EntityType.ENDERMAN);
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        // Check if the entity being spawned is an Enderman and cancel its spawning.
        if (event.getEntity() instanceof EndermanEntity) {
            event.setCanceled(true);
        }
    }

}

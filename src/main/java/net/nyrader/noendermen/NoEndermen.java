package net.nyrader.noendermen;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.nyrader.noendermen.block.ModBlocks;
import net.nyrader.noendermen.item.ModItems;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NoEndermen.MODID)
public class NoEndermen {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "noendermen";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public NoEndermen(IEventBus modEventBus, ModContainer modContainer)
    {
        ModBlocks.BLOCKS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);

        modEventBus.addListener(NoEndermen::addCreative);
        NeoForge.EVENT_BUS.addListener(NoEndermen::onEntityJoinLevel);
    }

    private static void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS)
        {
            ModItems.ITEMS.getEntries().forEach(item ->
                    event.accept(item.get()));
        }
    }

    private static void onEntityJoinLevel(EntityJoinLevelEvent event)
    {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof EnderMan)
        {
            event.setCanceled(true);
        }
    }
}

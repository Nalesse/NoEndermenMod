package net.nyrader.noendermen.world;

import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.nyrader.noendermen.NoEndermen;
import net.nyrader.noendermen.world.gen.ModOreGeneration;

@Mod.EventBusSubscriber(modid = NoEndermen.MOD_ID)
public class ModWorldEvents
{
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void biomeLoadingEvent(final BiomeLoadingEvent event)
    {
        ModOreGeneration.generateOres(event);
    }
}

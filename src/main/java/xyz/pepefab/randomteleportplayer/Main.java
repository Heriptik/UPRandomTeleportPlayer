package xyz.pepefab.randomteleportplayer;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.Logger;
import xyz.pepefab.randomteleportplayer.Commands.RandomTeleportPlayerCommand;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION, serverSideOnly = true, acceptableRemoteVersions = "*")
public class Main
{
    public static final String MODID = "uprandomteleportplayer";
    public static final String NAME = "UPRandomTeleportPlayer";
    public static final String VERSION = "0.1";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        logger.info("[UPRandomTeleportPlayer] Initialisation effectué avec succès. Version: " + Main.VERSION);
    }

    @EventHandler
    public void onServerStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new RandomTeleportPlayerCommand());
    }
}

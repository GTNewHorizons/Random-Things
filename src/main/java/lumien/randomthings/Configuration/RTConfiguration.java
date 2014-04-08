package lumien.randomthings.Configuration;

import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class RTConfiguration
{
	public static void init(FMLPreInitializationEvent event)
	{
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();

		ConfigBlocks.playerInterface = config.get("Blocks", "PlayerInterface", true).getBoolean(true);
		ConfigBlocks.fluidDisplay = config.get("Blocks", "FluidDisplay", true).getBoolean(true);
		ConfigBlocks.fertilizedDirt = ConfigBlocks.fertilizedDirtTilled = config.get("Blocks", "FertilizedDirt", true).getBoolean(true);
		ConfigBlocks.itemCollector = config.get("Blocks", "ItemCollector", true).getBoolean(true);
		ConfigBlocks.onlineDetector = config.get("Blocks", "OnlineDetector", true).getBoolean(true);
		ConfigBlocks.imbuingStation = config.get("Blocks", "ImbuingStation", true).getBoolean(true);
		ConfigBlocks.fluidRouter = config.get("Blocks", "FluidRouter", true).getBoolean(true);

		ConfigItems.filter = config.get("Items", "Filter", true).getBoolean(true);
		ConfigItems.biomeSolution = config.get("Items", "BiomeSolution", true).getBoolean(true);
		ConfigItems.biomePainter = config.get("Items", "BiomePainter", true).getBoolean(true);
		ConfigItems.whitestone = config.get("Items", "Whitestone", true).getBoolean(true);
		ConfigItems.magneticForce = config.get("Items","MagneticForce",true).getBoolean(true);
		ConfigItems.voidStone = config.get("Items", "VoidStone", true).getBoolean(true);
		ConfigItems.dropFilter = config.get("Items", "DropFilter", true).getBoolean(true);
		
		ConfigItems.creativeSword = config.get("Items", "CreativeSword", true).getBoolean(true);
		ConfigItems.creativeGrower = config.get("Items", "CreativeGrower", true).getBoolean(true);

		VanillaChanges.RANDOM_BACKGROUNDS = config.get("VanillaChanges", "RandomBackgrounds", true, "The normal dirt background will be replaced with a random block each start").getBoolean(true);
		VanillaChanges.FASTER_LEAVEDECAY = config.get("VanillaChanges", "FasterLeaveDecay", true ,"Leaves will decay much faster when no longer connected to a log").getBoolean(true);
		VanillaChanges.THROWABLES_MOTION = config.get("VanillaChanges", "ThrowableMotion", true , "When you throw something or shoot an arrow the motion of the player will be added to the motion of the projectile").getBoolean(true);

		Settings.ANIMATED_TEXTURES = config.get("Settings", "AnimatedTextures", true).getBoolean(true);

		config.save();
	}
}

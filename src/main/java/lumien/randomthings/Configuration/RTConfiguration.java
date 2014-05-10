package lumien.randomthings.Configuration;

import lumien.randomthings.Handler.BackgroundHandler;
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
		ConfigBlocks.moonSensor = config.get("Blocks", "MoonSensor", true).getBoolean(true);
		ConfigBlocks.notificationInterface = config.get("Blocks", "NotificationInterface", true).getBoolean(true);

		ConfigItems.biomeSolution = config.get("Items", "BiomeSolution", true).getBoolean(true);
		ConfigItems.biomePainter = config.get("Items", "BiomePainter", true).getBoolean(true);
		ConfigItems.whitestone = config.get("Items", "Whitestone", true).getBoolean(true);
		ConfigItems.magneticForce = config.get("Items", "MagneticForce", true).getBoolean(true);
		ConfigItems.voidStone = config.get("Items", "VoidStone", true).getBoolean(true);
		ConfigItems.dropFilter = config.get("Items", "DropFilter", true).getBoolean(true);
		ConfigItems.enderLetter = config.get("Items", "EnderLetter", true).getBoolean(true);

		ConfigItems.creativeSword = config.get("Items", "CreativeSword", true).getBoolean(true);
		ConfigItems.creativeGrower = config.get("Items", "CreativeGrower", true).getBoolean(true);
		ConfigItems.creativeChestGenerator = config.get("Items", "CreativeChestGenerator", true).getBoolean(true);

		ConfigDungeonLoot.WHITESTONE_CHANCE = config.get("DungeonLoot", "WhiteStone", 3).getInt();

		VanillaChanges.MODIFIED_BACKGROUND = config.get("VanillaChanges", "ModifiedBackgrounds", true, "The normal dirt background will be replaced with a different block each start").getBoolean(true);
		VanillaChanges.LOCKED_GAMMA = config.get("VanillaChanges", "LockedGamma", false,"Locks the Gamma to 0").getBoolean(false);
		
		BackgroundHandler.fixedBackground = config.get("VanillaChanges", "fixedBackground", "", "If this is not empty the options background will not be random but the one specified here. This has to be the name of a block texture without the .png").getString();

		// Moved To Class Transformer
		// VanillaChanges.FASTER_LEAVEDECAY = config.get("VanillaChanges",
		// "FasterLeaveDecay", true
		// ,"Leaves will decay much faster when no longer connected to a log").getBoolean(true);

		VanillaChanges.THROWABLES_MOTION = config.get("VanillaChanges", "ThrowableMotion", false, "When you throw something or shoot an arrow the motion of the player will be added to the motion of the projectile").getBoolean(false);

		Settings.ANIMATED_TEXTURES = config.get("Settings", "AnimatedTextures", true).getBoolean(true);

		config.save();
	}
}

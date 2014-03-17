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

		ConfigItems.filter = config.get("Items", "Filter", true).getBoolean(true);
		ConfigItems.biomeSolution = config.get("Items", "BiomeSolution", true).getBoolean(true);
		ConfigItems.biomePainter = config.get("Items", "BiomePainter", true).getBoolean(true);
		ConfigItems.whitestone = config.get("Items", "Whitestone", true).getBoolean(true);
		ConfigItems.magneticForce = config.get("Items","MagneticForce",true).getBoolean(true);

		VanillaChanges.RANDOM_BACKGROUNDS = config.get("VanillaChanges", "RandomBackgrounds", true).getBoolean(true);
		VanillaChanges.FASTER_LEAVEDECAY = config.get("VanillaChanges", "FasterLeaveDecay", true).getBoolean(true);

		Settings.ANIMATED_TEXTURES = config.get("Settings", "AnimatedTextures", true).getBoolean(true);

		config.save();
	}
}

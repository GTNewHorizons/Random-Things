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
		ConfigBlocks.spectreLamp = config.get("Blocks", "SpectreLamp", true).getBoolean(true);

		ConfigItems.biomeSolution = config.get("Items", "BiomeSolution", true).getBoolean(true);
		ConfigItems.biomePainter = config.get("Items", "BiomePainter", true).getBoolean(true);
		ConfigItems.whitestone = config.get("Items", "Whitestone", true).getBoolean(true);
		ConfigItems.magneticForce = config.get("Items", "MagneticForce", true).getBoolean(true);
		ConfigItems.voidStone = config.get("Items", "VoidStone", true).getBoolean(true);
		ConfigItems.dropFilter = config.get("Items", "DropFilter", true).getBoolean(true);
		ConfigItems.enderLetter = config.get("Items", "EnderLetter", true).getBoolean(true);
		ConfigItems.spectreKey = config.get("Items", "SpectreKey", true).getBoolean(true);
		ConfigItems.soundRecorder = config.get("Items", "SoundRecorder", true).getBoolean(true);
		ConfigItems.spectreArmor = config.get("Items", "SpectreArmor", true).getBoolean(true);
		ConfigItems.spectreSword = config.get("Items", "SpectreSword", true).getBoolean(true);

		ConfigItems.creativeSword = config.get("Items", "CreativeSword", true).getBoolean(true);
		ConfigItems.creativeGrower = config.get("Items", "CreativeGrower", true).getBoolean(true);
		ConfigItems.creativeChestGenerator = config.get("Items", "CreativeChestGenerator", true).getBoolean(true);

		ConfigDungeonLoot.WHITESTONE_CHANCE = config.get("DungeonLoot", "WhiteStone", 3).getInt();

		VanillaChanges.MODIFIED_BACKGROUND = config.get("VanillaChanges", "ModifiedBackgrounds", true, "The normal dirt background will be replaced with a different block each start").getBoolean(true);
		VanillaChanges.LOCKED_GAMMA = config.get("VanillaChanges", "LockedGamma", false, "Locks the Gamma to 0").getBoolean(false);

		BackgroundHandler.fixedBackground = config.get("VanillaChanges", "fixedBackground", "", "If this is not empty the options background will not be random but the one specified here. This has to be the name of a block texture without the .png").getString();

		VanillaChanges.THROWABLES_MOTION = config.get("VanillaChanges", "ThrowableMotion", false, "When you throw something or shoot an arrow the motion of the player will be added to the motion of the projectile").getBoolean(false);

		Settings.ANIMATED_TEXTURES = config.get("Settings", "AnimatedTextures", true).getBoolean(true);
		Settings.FERTILIZEDDIRT_GROWTHINDICATOR = config.get("Settings", "FertilizedDirtGrowthIndicator", false, "Bonemeal particles will appear whenever fertilized dirt boosts the plant").getBoolean(false);

		config.save();
	}
}
